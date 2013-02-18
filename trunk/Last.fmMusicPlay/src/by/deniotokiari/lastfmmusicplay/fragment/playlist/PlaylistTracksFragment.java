package by.deniotokiari.lastfmmusicplay.fragment.playlist;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.TrackAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.PlaylistTrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.other.Track;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractLastfmListFragment;
import by.deniotokiari.lastfmmusicplay.playlist.PlaylistManager;

public class PlaylistTracksFragment extends AbstractLastfmListFragment {

	private String playlistId;
	private static final String selection = PlaylistTrackContract.Columns.PLAYLIST_ID
			+ " = ?";
	private static final Uri uri = PlaylistTrackContract.URI_PLAYLIST_TRACKS;
	private static String[] jsonKeys = { Track.ROOT, null, null };
	private static String[] selectionArgs;

	public PlaylistTracksFragment() {
		super(jsonKeys, 1, uri, selection, selectionArgs, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		playlistId = getArguments().getString(PlaylistFragment.EXTRA_KEY_ID);
		String[] strings = { playlistId };
		selectionArgs = strings;
		jsonKeys[1] = Track.KEY_PLAYLIST_ID;
		jsonKeys[2] = playlistId;
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	protected int changeOffset(int itemsCount) {
		if (itemsCount != 0) {
			setEndOfData(true);
			return 1;
		}
		return 1;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this.getActivity(), uri, null, selection, selectionArgs, null);
	}
	
	@Override
	protected String url() {
		return LastFmAPI.playlistFetch(playlistId);
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new TrackAdapter(getActivity());
	}

	
	@Override
	public void onListItemClick(ListView l, View v, final int position, long id) {
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setMessage(getResources()
				.getString(R.string.processing));
		mProgressDialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				PlaylistManager.getInstance().setPlaylist(position, uri,
						selection, selectionArgs, null);
				mHandler.post(dismissProgressDialog);
				if (isBound) {
					mService.start();
				}
			}

		}).start();
		mAdapter.setCheked(position);
	}
	
}
