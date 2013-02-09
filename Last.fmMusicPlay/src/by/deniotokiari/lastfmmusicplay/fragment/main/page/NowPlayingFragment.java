package by.deniotokiari.lastfmmusicplay.fragment.main.page;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.playlist.PlaylistManager;
import by.deniotokiari.lastfmmusicplay.preferences.PreferencesHelper;
import by.deniotokiari.lastfmmusicplay.service.MusicPlayService;
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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class NowPlayingFragment extends Fragment implements OnClickListener,
		OnSeekBarChangeListener {

	public static final int PAGE_NUM = 1;

	private TextView mTextViewTrackTitle;
	private TextView mTextViewArtistTitle;
	private ToggleButton mButtonPlayPause;
	private ToggleButton mButtonShuffle;
	private ToggleButton mButtonPrevious;
	private ToggleButton mButtonNext;
	private ToggleButton mButtonRepeat;
	private SeekBar mSeekBar;

	private MusicPlayService mService =  new MusicPlayService();
	private ServiceConnection mConnection;
	private BroadcastReceiver mReceiver;
	private IntentFilter mFilter;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mTextViewTrackTitle = (TextView) getActivity().findViewById(
				R.id.txv_trackTitle);
		mTextViewArtistTitle = (TextView) getActivity().findViewById(
				R.id.txv_artistTitle);
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
		mSeekBar = (SeekBar) getActivity().findViewById(R.id.sb_progress);
		mButtonShuffle.setOnClickListener(this);
		mButtonRepeat.setOnClickListener(this);
		mButtonPlayPause.setOnClickListener(this);
		mButtonNext.setOnClickListener(this);
		mButtonPrevious.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(this);
		disableControls();
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
					disableControls();
					mSeekBar.setSecondaryProgress(0);
					mSeekBar.setProgress(0);
					mButtonPlayPause
							.setBackgroundResource(R.drawable.states_play);
					mButtonPlayPause.setChecked(false);
					initNowPlaying();
				} else if (action.equals(MusicPlayService.ACTION_ON_PLAY)) {
					mButtonPlayPause
							.setBackgroundResource(R.drawable.states_pause);
					mButtonPlayPause.setChecked(true);
					enableControls();
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
				if (mService.isPlaying()) {
					initNowPlaying();
					enableControls();
					mButtonPlayPause
							.setBackgroundResource(R.drawable.states_pause);
					mButtonPlayPause.setChecked(true);
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
		mTextViewArtistTitle.setText(PlaylistManager.getInstance().getArtist());
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

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
				mService.play();
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

}
