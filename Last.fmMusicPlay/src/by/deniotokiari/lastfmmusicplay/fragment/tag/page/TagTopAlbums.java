package by.deniotokiari.lastfmmusicplay.fragment.tag.page;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.AlbumAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.AlbumContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Album;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractLastfmListFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.AlbumTracksFragment;
import by.deniotokiari.lastfmmusicplay.fragment.tag.TagsFragment;

public class TagTopAlbums extends AbstractLastfmListFragment {
	
	private static final int itemsPerPage = 50;
	private static final Uri uri = AlbumContract.URI_TAG_TOP_ALBUMS;
	private static final String selection = AlbumContract.Columns.TAG + " = ?";
	private static final String sortOrder = AlbumContract.Columns.RANK + " ASC";
	private static final String[] jsonKeys = { Album.ROOT_TAG_TOP_ALBUMS, Album.ITEM };
	private static String[] selectionArgs;
	private String tag;

	public TagTopAlbums() {
		super(jsonKeys, itemsPerPage, uri, selection, selectionArgs, sortOrder);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		tag = getArguments().getString(TagsFragment.KEY_TAG);
		String[] strings = { tag };
		selectionArgs = strings;
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this.getActivity(), uri, null, selection,
				selectionArgs, sortOrder);
	}
	
	@Override
	protected int changeOffset(int itemsCount) {
		if (itemsCount > 0) {
			setEndOfData(true);
			return 0;
		}
		return 0;
	}

	@Override
	protected String url() {
		return LastFmAPI.tagGetTopAlbums(tag, itemsPerPage, getOffset() + 1);
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
