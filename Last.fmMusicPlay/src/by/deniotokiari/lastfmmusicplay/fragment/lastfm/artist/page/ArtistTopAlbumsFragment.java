package by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist.page;

import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.AlbumAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.AlbumContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Album;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.AbstractLastfmListFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.AlbumTracksFragment;

public class ArtistTopAlbumsFragment extends AbstractLastfmListFragment {
	
	private static final int itemsPerPage = 50;
	private static final Uri uri = AlbumContract.URI_ARTIST_TOP_ALBUMS;
	private static final String[] jsonKeys = { Album.ROOT_ARTIST_TOP_ALBUMS, Album.ITEM };
	private static final String sortOrder = AlbumContract.Columns.RANK + " DESC";
	private static final String selection = AlbumContract.Columns.ARTIST + " = ?";
	private static String[] selectionArgs;
	private static String artist;

	public ArtistTopAlbumsFragment() {
		super(jsonKeys, itemsPerPage, uri, selection, selectionArgs, sortOrder);
	}

	@Override
	protected String url() {
		Log.d("LOG", LastFmAPI.artistGetTopAlbums(artist, itemsPerPage, getOffset() + 1));
		return LastFmAPI.artistGetTopAlbums(artist, itemsPerPage, getOffset() + 1);
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new AlbumAdapter(getActivity());
	}

	public static void setArtist(String str) {
		artist = str;
		String[] strings = { artist };
		selectionArgs = strings;
	}
	
	@Override
	protected int changeOffset(int itemsCount) {
		if (itemsCount != 0) {
			setEndOfData(true);
		}
		return 1;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cursor =(Cursor) getListView().getItemAtPosition(position);
		String artist = cursor.getString(AlbumContract.INDEX_ARTIST);
		String album = cursor.getString(AlbumContract.INDEX_NAME);
		String album_art = cursor.getString(AlbumContract.INDEX_IMAGE);
		AlbumTracksFragment.setArgs(artist, album, album_art);
		FragmentTransaction transaction = getParentFragment().getFragmentManager().beginTransaction();
		transaction.replace(R.id.content, new AlbumTracksFragment());
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
}
