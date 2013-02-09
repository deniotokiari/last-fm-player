package by.deniotokiari.lastfmmusicplay.service;

import by.deniotokiari.lastfmmusicplay.preferences.PreferencesHelper;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

public class MusicPlayService extends Service implements OnCompletionListener,
		OnPreparedListener, OnErrorListener, OnSeekCompleteListener,
		OnBufferingUpdateListener {

	public static final String PREF_NAME = "music_service";
	public static final String PREF_KEY_REPEAT = "repeat";
	public static final String PREF_KEY_SHUFFLE = "shuffle";

	private MediaPlayer mMediaPlayer = new MediaPlayer();
	private Handler mHandler = new Handler();
	private MyBinder binder = new MyBinder();

	private boolean REPEAT;
	private boolean SHUFFLE;

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
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.release();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSeekComplete(MediaPlayer mediaPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onError(MediaPlayer mediaPlayer, int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCompletion(MediaPlayer mediaPlayer) {
		// TODO Auto-generated method stub

	}

	public class MyBinder extends Binder {

		public MusicPlayService getService() {
			return MusicPlayService.this;
		}

	}

}
