package by.deniotokiari.lastfmmusicplay.fragment.lastfm.page;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.AlbumAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.api.LastfmAuthHelper;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.AlbumContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Album;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractLastfmListFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.AlbumTracksFragment;

public class LibraryAlbumsFragment extends AbstractLastfmListFragment {

	private static int itemsPerPage = 50;
	private static Uri uri = AlbumContract.URI_LIBRARY_ALBUMS;
	private static String sortOrder = AlbumContract.Columns.RANK + " DESC";
	private static String[] jsonKeys = { Album.ROOT_LIBRARY_ALBUMS, Album.ITEM };

	public LibraryAlbumsFragment() {
		super(jsonKeys, itemsPerPage, uri, null, null, sortOrder);
	}

	@Override
	protected String url() {
		return LastFmAPI.libraryGetAlbums(LastfmAuthHelper.getUserName(),
				itemsPerPage, getOffset() + 1);
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new AlbumAdapter(getActivity());
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cursor =(Cursor) getListView().getItemAtPosition(position);
		String artist = cursor.getString(AlbumContract.INDEX_ARTIST);
		String album = cursor.getString(AlbumContract.INDEX_NAME);
		String album_art_url = cursor.getString(AlbumContract.INDEX_IMAGE);
		Bundle bundle = new Bundle();
		bundle.putString(AlbumTracksFragment.EXTRA_KEY_ALBUM, album);
		bundle.putString(AlbumTracksFragment.EXTRA_KEY_ARTIST, artist);
		bundle.putString(AlbumTracksFragment.EXTRA_KEY_URL, album_art_url);
		FragmentTransaction transaction = getParentFragment().getFragmentManager().beginTransaction();
		Fragment fragment = new AlbumTracksFragment();
		fragment.setArguments(bundle);
		transaction.replace(R.id.content, fragment);
		transaction.addToBackStack(null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.commit();
	}

}
