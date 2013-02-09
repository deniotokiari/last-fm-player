package by.deniotokiari.lastfmmusicplay.fragment.main.page;

import by.deniotokiari.lastfmmusicplay.adapter.TrackAdapter;
import by.deniotokiari.lastfmmusicplay.content.contract.PlayerPlaylistContract;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.TrackContract;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class PlaylistFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	public static final int PAGE_NUM = 2;
	private static final Uri URI = PlayerPlaylistContract.URI_PLAYER_PLAYLIST;
	private TrackAdapter mAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
	}

}
