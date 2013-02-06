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
import by.deniotokiari.lastfmmusicplay.adapter.ArtistAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.ArtistContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Artist;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractLastfmListFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist.ArtistPagerFragment;
import by.deniotokiari.lastfmmusicplay.fragment.tag.TagsFragment;

public class TagTopArtists extends AbstractLastfmListFragment {

	private static final int itemsPerPage = 50;
	private static final Uri uri = ArtistContract.URI_TAG_ARTISTS;
	private static final String[] jsonKeys = { Artist.ROOT_TAG_ARTISTS,
			Artist.ITEM };
	private static final String selection = ArtistContract.Columns.TAG + " = ?";
	private static final String sortOrder = ArtistContract.Columns.RANK
			+ " ASC";
	private static String[] selectionArgs;
	private String tag;

	public TagTopArtists() {
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
	protected String url() {
		return LastFmAPI.tagGetTopArtists(tag, itemsPerPage, getOffset() + 1);
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new ArtistAdapter(getActivity());
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cursor =(Cursor) getListView().getItemAtPosition(position);
		String artist = cursor.getString(ArtistContract.INDEX_NAME);
		Bundle args = new Bundle();
		args.putString(ArtistPagerFragment.KEY_ARTIST, artist);
		FragmentTransaction transaction = getParentFragment().getFragmentManager().beginTransaction();
		Fragment fragment = new ArtistPagerFragment();
		fragment.setArguments(args);
		transaction.replace(R.id.content, fragment);
		transaction.addToBackStack(null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.commit();
	}

}
