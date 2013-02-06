package by.deniotokiari.lastfmmusicplay.fragment.playlist;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.PlaylistAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.api.LastfmAuthHelper;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.PlaylistContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Playlist;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractLastfmListFragment;

public class PlaylistFragment extends AbstractLastfmListFragment {

	public static final String EXTRA_KEY_ID = "ID";

	private static final Uri uri = PlaylistContract.URI_PLAYLISTS;
	private static final String[] jsonKeys = { Playlist.ROOT, Playlist.ITEM };

	public PlaylistFragment() {
		super(jsonKeys, 1, uri, null, null, null);
	}

	@Override
	protected int changeOffset(int itemsCount) {
		if (itemsCount != 0) {
			setEndOfData(true);
		}
		return 1;
	}

	@Override
	protected String url() {
		return LastFmAPI.userGetPlaylists(LastfmAuthHelper.getUserName());
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new PlaylistAdapter(getActivity());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cursor = (Cursor) getListView().getItemAtPosition(position);
		String playlistId = cursor
				.getString(PlaylistContract.INDEX_PLAYLIST_ID);
		Bundle bundle = new Bundle();
		bundle.putString(EXTRA_KEY_ID, playlistId);
		Fragment fragment = new PlaylistTracksFragment();
		fragment.setArguments(bundle);
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content, fragment);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

}
