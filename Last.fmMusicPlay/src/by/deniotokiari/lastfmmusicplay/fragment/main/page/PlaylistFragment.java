package by.deniotokiari.lastfmmusicplay.fragment.main.page;

import by.deniotokiari.lastfmmusicplay.adapter.TrackAdapter;
import by.deniotokiari.lastfmmusicplay.content.contract.PlayerPlaylistContract;
import by.deniotokiari.lastfmmusicplay.playlist.PlaylistManager;
import by.deniotokiari.lastfmmusicplay.service.MusicPlayService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ListView;

public class PlaylistFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	public static final int PAGE_NUM = 2;
	private static final Uri URI = PlayerPlaylistContract.URI_PLAYER_PLAYLIST;
	private TrackAdapter mAdapter;
	private MusicPlayService mService;
	private ServiceConnection mConnection;
	private IntentFilter mFilter;
	private BroadcastReceiver mBroadcastReceiver;

	private boolean isBound;

	@Override
	public void onResume() {
		super.onResume();
		mFilter = new IntentFilter();
		mFilter.addAction(MusicPlayService.ACTION_ON_PREPARE);
		mBroadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(MusicPlayService.ACTION_ON_PREPARE)) {
					((TrackAdapter) getListAdapter()).setCheked(PlaylistManager
							.getInstance().getPosition());
				}
			}

		};
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
				mBroadcastReceiver, mFilter);
		mConnection = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName componentName) {
				isBound = false;
			}

			@Override
			public void onServiceConnected(ComponentName componentName,
					IBinder binder) {
				mService = ((MusicPlayService.MyBinder) binder).getService();
				isBound = true;
			}

		};
		getActivity().bindService(
				new Intent(getActivity(), MusicPlayService.class), mConnection,
				0);
	}

	@Override
	public void onPause() {
		super.onPause();
		getActivity().unbindService(mConnection);
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
				mBroadcastReceiver);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mAdapter = new TrackAdapter(getActivity());
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this.getActivity(), URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor.getCount() != 0) {
			setListAdapter(mAdapter);
			mAdapter.swapCursor(cursor);
			if (PlaylistManager.getInstance().getPosition() != -1) {
				((TrackAdapter) getListAdapter()).setCheked(PlaylistManager
						.getInstance().getPosition());
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		((TrackAdapter) getListAdapter()).setCheked(position);
		PlaylistManager.getInstance().setPosition(position);
		if (isBound) {
			mService.start();
		}
	}

}
