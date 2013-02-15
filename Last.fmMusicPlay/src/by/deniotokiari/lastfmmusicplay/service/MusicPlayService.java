package by.deniotokiari.lastfmmusicplay.service;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import by.deniotokiari.lastfmmusicplay.api.VkAPI;
import by.deniotokiari.lastfmmusicplay.content.Callback;
import by.deniotokiari.lastfmmusicplay.content.json.vk.Track;
import by.deniotokiari.lastfmmusicplay.http.RequestManager;
import by.deniotokiari.lastfmmusicplay.notification.MusicNotification;
import by.deniotokiari.lastfmmusicplay.playlist.PlaylistManager;
import by.deniotokiari.lastfmmusicplay.preferences.PreferencesHelper;

public class MusicPlayService extends Service implements OnCompletionListener,
		OnPreparedListener, OnErrorListener, OnSeekCompleteListener,
		OnBufferingUpdateListener {

	public static final String ACTION_ON_PLAY = "by.last.fmplay.deniotokiari.service.ACTION_ON_PLAY";
	public static final String ACTION_ON_PREPARE = "by.last.fmplay.deniotokiari.service.ACTION_ON_PREPARE";
	public static final String ACTION_ON_BUFFERING_UPDATE = "by.last.fmplay.deniotokiari.service.ACTION_ON_BUFFERING_UPDATE";
	public static final String ACTION_ON_PROGRESS_CHANGE = "by.last.fmplay.deniotokiari.service.ACTION_ON_PROGRESS_CHANGE";

	public static final String PREF_NAME = "music_service";
	public static final String PREF_KEY_REPEAT = "repeat";
	public static final String PREF_KEY_SHUFFLE = "shuffle";
	public static final String PREF_KEY_CURRENT_POSITION = "current_position";

	private MediaPlayer mMediaPlayer = new MediaPlayer();
	private Handler mHandler = new Handler();
	private MyBinder mBinder = new MyBinder();

	private boolean REPEAT;
	private boolean SHUFFLE;
	private boolean isPaused; 

	private int buffered;
	private int CURRENT_POSITION;

	@Override
	public void onCreate() {
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnErrorListener(this);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnBufferingUpdateListener(this);
		mMediaPlayer.setOnSeekCompleteListener(this);
		REPEAT = PreferencesHelper.getInstance().getBoolean(PREF_NAME,
				PREF_KEY_REPEAT);
		SHUFFLE = PreferencesHelper.getInstance().getBoolean(PREF_NAME,
				PREF_KEY_SHUFFLE);
		CURRENT_POSITION = PreferencesHelper.getInstance().getInt(PREF_NAME, PREF_KEY_CURRENT_POSITION);
		Log.d("LOG",String.valueOf(CURRENT_POSITION));
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		initNotification();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mMediaPlayer != null) {
			CURRENT_POSITION = mMediaPlayer.getCurrentPosition();
			PreferencesHelper.getInstance().putInt(PREF_NAME, PREF_KEY_CURRENT_POSITION, CURRENT_POSITION);
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.release();
		}
		cancelNotification();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
		buffered = percent;
		LocalBroadcastManager.getInstance(getApplicationContext())
				.sendBroadcast(new Intent(ACTION_ON_BUFFERING_UPDATE));
	}

	@Override
	public void onSeekComplete(MediaPlayer mediaPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onError(MediaPlayer mediaPlayer, int i, int j) {
		return true;
	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		play();
	}

	@Override
	public void onCompletion(MediaPlayer mediaPlayer) {
		CURRENT_POSITION = 0;
		PreferencesHelper.getInstance().putInt(PREF_NAME, PREF_KEY_CURRENT_POSITION, CURRENT_POSITION);
		next();
	}

	public class MyBinder extends Binder {

		public MusicPlayService getService() {
			return MusicPlayService.this;
		}

	}

	private void initNotification() {
		MusicNotification.getInstance()
				.createNotification(
						String.format("%s - %s", PlaylistManager.getInstance()
								.getArtist(), PlaylistManager.getInstance()
								.getTitle()));
	}

	private void cancelNotification() {
		MusicNotification.getInstance().destroyNotification();
	}

	public void pause() {
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
			isPaused = true;
		}
	}

	private void primarySeekBarProgressUpdater() {
		LocalBroadcastManager.getInstance(getApplicationContext())
				.sendBroadcast(new Intent(ACTION_ON_PROGRESS_CHANGE));
		if (mMediaPlayer.isPlaying()) {
			Runnable update = new Runnable() {

				@Override
				public void run() {
					primarySeekBarProgressUpdater();
				}

			};
			mHandler.postDelayed(update, 999);
		}
	}

	private void startPlay(final String request) {
		if (request == null) {
			stopSelf();
		}
		LocalBroadcastManager.getInstance(getApplicationContext())
				.sendBroadcast(new Intent(ACTION_ON_PREPARE));
		RequestManager.getInstance().get(new Callback<Object>() {

			@Override
			public void onSuccess(Object t, Object... objects) {
				Track track = new Track(t);
				if (!track.getUrl().equals("")) {
					Log.d("LOG", track.getUrl());
					start(track.getUrl());
				} else {
					this.onError(null);
				}
			}

			@Override
			public void onError(Throwable e, Object... objects) {
				RequestManager.getInstance().get(new Callback<Object>() {

					@Override
					public void onSuccess(Object t, Object... objects) {
						Track track = new Track(t);
						if (!track.getUrl().equals("")) {
							Log.d("LOG", track.getUrl());
							start(track.getUrl());
						}
					}

					@Override
					public void onError(Throwable e, Object... objects) {
						Log.d("LOG", "error in url request");
						next();
					}

				}, VkAPI.audioSearch(request.split("- ")[1], 1));
			}

		}, VkAPI.audioSearch(request, 1));
	}

	private void start(String url) {
		mMediaPlayer.reset();
		if (!mMediaPlayer.isPlaying()) {
			try {
				mMediaPlayer.setDataSource(url);
				mMediaPlayer.prepareAsync();
				initNotification();
			} catch (IllegalArgumentException e) {

			} catch (SecurityException e) {

			} catch (IllegalStateException e) {

			} catch (IOException e) {

			}
		}
	}

	public void play() {
		if (!mMediaPlayer.isPlaying()) {
			mMediaPlayer.start();
			isPaused = false;
			LocalBroadcastManager.getInstance(getApplicationContext())
					.sendBroadcast(new Intent(ACTION_ON_PLAY));
			primarySeekBarProgressUpdater();
		}
	}

	private void stop() {
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
		}
	}

	public boolean isPlaying() {
		return mMediaPlayer.isPlaying();
	}
	
	public void start() {
		stop();
		startPlay(PlaylistManager.getInstance().getTrack());
	}
	
	public void setShuffle(boolean b) {
		SHUFFLE = b;
	}
	
	public void setRepeat(boolean b) {
		REPEAT = b;
	}
	
	public int getBufferedPercent() {
		return buffered;
	}
	
	public int getDuration() {
		return mMediaPlayer.getDuration();
	}
	
	public int getCurrentPosition() {
		return mMediaPlayer.getCurrentPosition();
	}
	
	public void seekTo(int position) {
		mMediaPlayer.seekTo(position);
	}
	
	public void next() {
		Log.d("LOG", "NEXT");	
		startPlay(PlaylistManager.getInstance().getNext(SHUFFLE, REPEAT));
	}
	
	public void previous() {
		Log.d("LOG", "PREV");
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
}
