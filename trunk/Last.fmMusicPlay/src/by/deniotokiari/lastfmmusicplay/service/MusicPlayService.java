package by.deniotokiari.lastfmmusicplay.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import by.deniotokiari.lastfmmusicplay.ContextHolder;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.api.VkAPI;
import by.deniotokiari.lastfmmusicplay.content.Callback;
import by.deniotokiari.lastfmmusicplay.content.json.vk.Track;
import by.deniotokiari.lastfmmusicplay.http.RequestManager;
import by.deniotokiari.lastfmmusicplay.notification.MusicNotification;
import by.deniotokiari.lastfmmusicplay.playlist.PlaylistManager;
import by.deniotokiari.lastfmmusicplay.preferences.PreferencesHelper;

public class MusicPlayService extends Service implements OnCompletionListener,
		OnPreparedListener, OnErrorListener, OnBufferingUpdateListener {

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
	private SharedPreferences preferences;

	private boolean isRepeat;
	private boolean isShuffle;
	private boolean isPaused;
	private boolean isScrobbled;

	private int buffered;
	private int currentPosition;
	private int duration;

	@Override
	public void onCreate() {
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnErrorListener(this);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnBufferingUpdateListener(this);
		preferences = PreferenceManager
				.getDefaultSharedPreferences(ContextHolder.getInstance()
						.getContext());
		isRepeat = PreferencesHelper.getInstance().getBoolean(PREF_NAME,
				PREF_KEY_REPEAT);
		isShuffle = PreferencesHelper.getInstance().getBoolean(PREF_NAME,
				PREF_KEY_SHUFFLE);
		currentPosition = PreferencesHelper.getInstance().getInt(PREF_NAME,
				PREF_KEY_CURRENT_POSITION);
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
			currentPosition = mMediaPlayer.getCurrentPosition();
			PreferencesHelper.getInstance().putInt(PREF_NAME,
					PREF_KEY_CURRENT_POSITION, currentPosition);
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
	public boolean onError(MediaPlayer mediaPlayer, int i, int j) {
		return true;
	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		play();
	}

	@Override
	public void onCompletion(MediaPlayer mediaPlayer) {
		mediaPlayer.stop();
		currentPosition = 0;
		PreferencesHelper.getInstance().putInt(PREF_NAME,
				PREF_KEY_CURRENT_POSITION, currentPosition);
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

	private void scrobble() {
		if (mMediaPlayer.getCurrentPosition() > duration / 2 && !isScrobbled) {
			isScrobbled = true;
			Date date = Calendar.getInstance().getTime();
			Log.d("LOG", LastFmAPI.trackScrobble(PlaylistManager.getInstance()
					.getArtist(), PlaylistManager.getInstance().getTitle(),
					date));
			RequestManager.getInstance().post(
					new Callback<Object>() {

						@Override
						public void onSuccess(Object t, Object... objects) {
							Log.d("LOG", "Scrobble success");
						}

						@Override
						public void onError(Throwable e, Object... objects) {

						}

					},
					LastFmAPI.trackScrobble(PlaylistManager.getInstance()
							.getArtist(), PlaylistManager.getInstance()
							.getTitle(), date));
		}
	}

	private void primarySeekBarProgressUpdater() {
		LocalBroadcastManager.getInstance(getApplicationContext())
				.sendBroadcast(new Intent(ACTION_ON_PROGRESS_CHANGE));
		if (preferences.getBoolean(
				getResources().getString(R.string.pref_key_scrobble), false)) {
			scrobble();
		}
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
		isScrobbled = false;
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
			duration = mMediaPlayer.getDuration();
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
		isShuffle = b;
	}

	public void setRepeat(boolean b) {
		isRepeat = b;
	}

	public int getBufferedPercent() {
		return buffered;
	}

	public int getDuration() {
		return duration;
	}

	public int getCurrentPosition() {
		return mMediaPlayer.getCurrentPosition();
	}

	public void seekTo(int position) {
		mMediaPlayer.seekTo(position);
	}

	public void next() {
		Log.d("LOG", "NEXT");
		startPlay(PlaylistManager.getInstance().getNext(isShuffle, isRepeat));
	}

	public void previous() {
		Log.d("LOG", "PREV");
		startPlay(PlaylistManager.getInstance().getPrevious(isShuffle, isRepeat));
	}

	public boolean isPaused() {
		return isPaused;
	}

}
