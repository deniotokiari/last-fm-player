package by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist.page;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.TrackAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.TrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Track;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractLastfmListFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist.ArtistPagerFragment;

public class ArtistTopTracksFragment extends AbstractLastfmListFragment {

	private static final int itemsPerPage = 50;
	private static final Uri uri = TrackContract.URI_ARTIST_TOP_TRACKS;
	private static final String[] jsonKeys = { Track.ROOT_ARTIST_TOP_TRACKS,
			Track.ITEM };
	private String artist;
	private static final String sortOrder = TrackContract.Columns.RANK + " ASC";
	private static final String selection = TrackContract.Columns.ARTIST
			+ " = ?";
	private static String[] selectionArgs;

	public ArtistTopTracksFragment() {
		super(jsonKeys, itemsPerPage, uri, selection, selectionArgs, sortOrder);
	}

	@Override
	protected String url() {
		return LastFmAPI.artistGetTopTracks(artist, itemsPerPage,
				getOffset() + 1);
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new TrackAdapter(getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		artist = getArguments().getString(ArtistPagerFragment.KEY_ARTIST);
		String[] strings = { artist };
		selectionArgs = strings;
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this.getActivity(), uri, null, selection,
				selectionArgs, sortOrder);
	}

}
