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
import by.deniotokiari.lastfmmusicplay.adapter.ArtistAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.api.LastfmAuthHelper;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.ArtistContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Artist;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractLastfmListFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist.ArtistPagerFragment;

public class LibraryArtistsFragment extends AbstractLastfmListFragment {

	private static final int itemsPerPage = 50;
	private static final Uri uri = ArtistContract.URI_ARTISTS;
	private static final String sortOrder = ArtistContract.Columns.RANK
			+ " DESC";
	private static final String[] jsonKeys = { Artist.ROOT, Artist.ITEM };

	public LibraryArtistsFragment() {
		super(jsonKeys, itemsPerPage, uri, null, null, sortOrder);
	}

	@Override
	protected String url() {
		return LastFmAPI.libraryGetArtists(LastfmAuthHelper.getUserName(),
				itemsPerPage, getOffset() + 1);
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
