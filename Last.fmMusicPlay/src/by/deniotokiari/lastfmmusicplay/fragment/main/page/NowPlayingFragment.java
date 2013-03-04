package by.deniotokiari.lastfmmusicplay.fragment.main.page;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.api.LastfmAuthHelper;
import by.deniotokiari.lastfmmusicplay.content.Callback;
import by.deniotokiari.lastfmmusicplay.content.images.ImageLoader;
import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Artist;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Track;
import by.deniotokiari.lastfmmusicplay.http.RequestManager;
import by.deniotokiari.lastfmmusicplay.playlist.PlaylistManager;
import by.deniotokiari.lastfmmusicplay.preferences.PreferencesHelper;
import by.deniotokiari.lastfmmusicplay.service.MusicPlayService;
// TODO imp service start to another way
public class NowPlayingFragment extends Fragment implements OnClickListener,
		OnSeekBarChangeListener {

	public static final int PAGE_NUM = 1;

	public static final String PREF_NAME = "nowplaying";
	public static final String PREF_KEY_ARTIST = "artist";
	public static final String PREF_KEY_URL = "url";
	public static final String PREF_KEY_TRACK = "track";
	public static final String PREF_KEY_LOVED = "loved";

	private TextView mTextViewTrackTitle;
	private TextView mTextViewArtistTitle;
	private TextView mTextViewDurationCurrent;
	private TextView mTextViewDurationAll;
	private ToggleButton mButtonPlayPause;
	private ToggleButton mButtonShuffle;
	private ToggleButton mButtonPrevious;
	private ToggleButton mButtonNext;
	private ToggleButton mButtonRepeat;
	private ToggleButton mButtonLoved;
	private SeekBar mSeekBar;
	private ProgressBar mProgressBar;
	private ImageView mImageViewArtist;

	private MusicPlayService mService = new MusicPlayService();
	private ServiceConnection mConnection;
	private BroadcastReceiver mReceiver;
	private IntentFilter mFilter;

	private boolean isTracking = false;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mTextViewTrackTitle = (TextView) getActivity().findViewById(
				R.id.txv_trackTitle);
		mTextViewArtistTitle = (TextView) getActivity().findViewById(
				R.id.txv_artistTitle);
		mTextViewDurationCurrent = (TextView) getActivity().findViewById(
				R.id.txv_durationCurrent);
		mTextViewDurationAll = (TextView) getActivity().findViewById(
				R.id.txv_durationAll);
		mButtonPlayPause = (ToggleButton) getActivity().findViewById(
				R.id.btn_play);
		mButtonShuffle = (ToggleButton) getActivity().findViewById(
				R.id.btn_shuffle);
		mButtonPrevious = (ToggleButton) getActivity().findViewById(
				R.id.btn_prev);
		mButtonNext = (ToggleButton) getActivity().findViewById(
				R.id.btn_forward);
		mButtonRepeat = (ToggleButton) getActivity().findViewById(
				R.id.btn_repeat);
		mButtonLoved = (ToggleButton) getActivity().findViewById(R.id.btn_love);
		mSeekBar = (SeekBar) getActivity().findViewById(R.id.sb_progress);
		mProgressBar = (ProgressBar) getActivity().findViewById(
				R.id.prepareMusicService);
		mImageViewArtist = (ImageView) getActivity().findViewById(
				R.id.imgv_artist);
		mButtonShuffle.setOnClickListener(this);
		mButtonRepeat.setOnClickListener(this);
		mButtonPlayPause.setOnClickListener(this);
		mButtonNext.setOnClickListener(this);
		mButtonPrevious.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(this);
		mButtonLoved.setOnClickListener(this);
		mButtonRepeat.setChecked(PreferencesHelper.getInstance().getBoolean(
				MusicPlayService.PREF_NAME, MusicPlayService.PREF_KEY_REPEAT));
		mButtonShuffle.setChecked(PreferencesHelper.getInstance().getBoolean(
				MusicPlayService.PREF_NAME, MusicPlayService.PREF_KEY_SHUFFLE));
		disableControls();
		getActivity().startService(
				new Intent(getActivity(), MusicPlayService.class));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.view_fragment_page_nowplaying,
				container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		mFilter = new IntentFilter();
		mFilter.addAction(MusicPlayService.ACTION_ON_PREPARE);
		mFilter.addAction(MusicPlayService.ACTION_ON_BUFFERING_UPDATE);
		mFilter.addAction(MusicPlayService.ACTION_ON_PLAY);
		mFilter.addAction(MusicPlayService.ACTION_ON_PROGRESS_CHANGE);
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(MusicPlayService.ACTION_ON_PREPARE)) {
					mProgressBar.setVisibility(View.VISIBLE);
					mSeekBar.setSecondaryProgress(0);
					mSeekBar.setProgress(0);
					initNowPlaying();

				} else if (action.equals(MusicPlayService.ACTION_ON_PLAY)) {
					mProgressBar.setVisibility(View.GONE);
					mButtonPlayPause
							.setBackgroundResource(R.drawable.states_pause);
					mButtonPlayPause.setChecked(true);
					enableControls();
				} else if (action
						.equals(MusicPlayService.ACTION_ON_BUFFERING_UPDATE)) {
					mSeekBar.setSecondaryProgress(mService.getBufferedPercent());
				} else if (action
						.equals(MusicPlayService.ACTION_ON_PROGRESS_CHANGE)) {
					if (!isTracking) {
						mSeekBar.setProgress((int) (((float) mService
								.getCurrentPosition() / mService.getDuration()) * 100));
					}
					mTextViewDurationAll
							.setText(convert(mService.getDuration()));
					mTextViewDurationCurrent.setText(convert(mService
							.getCurrentPosition()));
				}
			}

		};
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
				mReceiver, mFilter);
		mConnection = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {

			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mService = ((MusicPlayService.MyBinder) service).getService();
				if (PlaylistManager.getInstance().getPosition() != -1) {
					initNowPlaying();
					enableControls();
					if (mService.isPlaying()) {
						mButtonPlayPause
								.setBackgroundResource(R.drawable.states_pause);
						mButtonPlayPause.setChecked(true);
					}
				}
			}
		};
		getActivity().bindService(
				new Intent(getActivity(), MusicPlayService.class), mConnection,
				0);
	}

	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
				mReceiver);
		getActivity().unbindService(mConnection);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (!mService.isPlaying()) {
			mService.stopSelf();
		}
	}

	private void initNowPlaying() {
		mTextViewTrackTitle.setText(PlaylistManager.getInstance().getTitle());
		String artist = PlaylistManager.getInstance().getArtist();
		String track = PlaylistManager.getInstance().getTitle();
		mTextViewArtistTitle.setText(artist);
		if (artist.equals(PreferencesHelper.getInstance().getString(PREF_NAME,
				PREF_KEY_ARTIST))) {
			ImageLoader.getInstance().bind(
					mImageViewArtist,
					PreferencesHelper.getInstance().getString(PREF_NAME,
							PREF_KEY_URL), 1);
		} else {
			PreferencesHelper.getInstance().putString(PREF_NAME,
					PREF_KEY_ARTIST, artist);
			RequestManager.getInstance().get(new Callback<Object>() {

				@Override
				public void onSuccess(Object t, Object... objects) {
					CommonJson json = new CommonJson((String) t, Artist.ITEM);
					String imageUrl = json.getLastArrayItem(
							Artist.KEY_ROOT_IMAGE, Artist.KEY_IMAGE);
					PreferencesHelper.getInstance().putString(PREF_NAME,
							PREF_KEY_URL, imageUrl);
					ImageLoader.getInstance().bind(mImageViewArtist, imageUrl,
							1);
				}

				@Override
				public void onError(Throwable e, Object... objects) {

				}

			}, LastFmAPI.artistGetInfo(artist));
		}
		if (track.equals(PreferencesHelper.getInstance().getString(PREF_NAME,
				PREF_KEY_TRACK))) {
			mButtonLoved.setChecked(PreferencesHelper.getInstance().getBoolean(
					PREF_NAME, PREF_KEY_LOVED));
		} else {
			PreferencesHelper.getInstance().putString(PREF_NAME,
					PREF_KEY_TRACK, track);
			RequestManager.getInstance().get(
					new Callback<Object>() {

						@Override
						public void onSuccess(Object t, Object... objects) {
							CommonJson json = new CommonJson((String) t,
									Track.ITEM);
							String loved = json
									.getString(Track.KEY_USER_LOVED_TRACK);
							if (loved.equals(Track.KEY_USER_LOVED_TRACK_TRUE)) {
								PreferencesHelper.getInstance().putBoolean(
										PREF_NAME, PREF_KEY_LOVED, true);
								mButtonLoved.setChecked(true);
							} else {
								PreferencesHelper.getInstance().putBoolean(
										PREF_NAME, PREF_KEY_LOVED, false);
								mButtonLoved.setChecked(false);
							}
						}

						@Override
						public void onError(Throwable e, Object... objects) {

						}

					},
					LastFmAPI.trackGetInfo(track, artist,
							LastfmAuthHelper.getUserName()));
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		isTracking = true;
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int position = (mService.getDuration() / 100) * seekBar.getProgress();
		mService.seekTo(position);
		isTracking = false;
	}

	@Override
	public void onClick(View view) {
		boolean isChecked = false;
		if (view instanceof ToggleButton) {
			isChecked = ((ToggleButton) view).isChecked();
		}
		switch (view.getId()) {
		case R.id.btn_play:
			if (isChecked) {
				mButtonPlayPause.setBackgroundResource(R.drawable.states_pause);
				if (mService.getCurrentPosition() < 0) {
					mService.start();
				} else {
					mService.play();
				}
			} else {
				mButtonPlayPause.setBackgroundResource(R.drawable.states_play);
				mService.pause();
			}
			break;
		case R.id.btn_shuffle:
			if (isChecked) {
				mButtonShuffle.setChecked(true);
				PreferencesHelper.getInstance().putBoolean(
						MusicPlayService.PREF_NAME,
						MusicPlayService.PREF_KEY_SHUFFLE, true);
				mService.setShuffle(true);
			} else {
				mButtonShuffle.setChecked(false);
				PreferencesHelper.getInstance().putBoolean(
						MusicPlayService.PREF_NAME,
						MusicPlayService.PREF_KEY_SHUFFLE, false);
				mService.setShuffle(false);
			}
			break;
		case R.id.btn_repeat:
			if (isChecked) {
				mButtonRepeat.setChecked(true);
				PreferencesHelper.getInstance().putBoolean(
						MusicPlayService.PREF_NAME,
						MusicPlayService.PREF_KEY_REPEAT, true);
				mService.setRepeat(true);
			} else {
				mButtonRepeat.setChecked(false);
				PreferencesHelper.getInstance().putBoolean(
						MusicPlayService.PREF_NAME,
						MusicPlayService.PREF_KEY_REPEAT, false);
				mService.setRepeat(false);
			}
			break;
		case R.id.btn_forward:
			mService.next();
			break;
		case R.id.btn_prev:
			mService.previous();
			break;
		case R.id.btn_love:
			String request;
			if (isChecked) {
				mButtonLoved.setChecked(true);
				request = LastFmAPI.trackLove(PlaylistManager.getInstance()
						.getTitle(), PlaylistManager.getInstance().getArtist());
			} else {
				mButtonLoved.setChecked(false);
				request = LastFmAPI.trackUnlove(PlaylistManager.getInstance()
						.getTitle(), PlaylistManager.getInstance().getArtist());
			}
			mButtonLoved.setEnabled(false);
			Callback<Object> callback = new Callback<Object>() {

				@Override
				public void onSuccess(Object t, Object... objects) {
					mButtonLoved.setEnabled(true);
				}

				@Override
				public void onError(Throwable e, Object... objects) {
					mButtonLoved.setEnabled(true);
				}

			};
			RequestManager.getInstance().post(callback, request);
		}
	}

	private void disableControls() {
		mButtonPrevious.setEnabled(false);
		mButtonPlayPause.setEnabled(false);
		mButtonNext.setEnabled(false);
		mSeekBar.setEnabled(false);
	}

	private void enableControls() {
		mButtonPrevious.setEnabled(true);
		mButtonPlayPause.setEnabled(true);
		mButtonNext.setEnabled(true);
		mSeekBar.setEnabled(true);
	}

	private String convert(int ms) {
		int time = ms / 1000;
		String seconds = Integer.toString(time % 60);
		String minutes = Integer.toString((time % 3600) / 60);
		for (int i = 0; i < 2; i++) {
			if (seconds.length() < 2) {
				seconds = "0" + seconds;
			}
			if (minutes.length() < 2) {
				minutes = "0" + minutes;
			}

		}
		return String.format("%s:%s", minutes, seconds);
	}

}
