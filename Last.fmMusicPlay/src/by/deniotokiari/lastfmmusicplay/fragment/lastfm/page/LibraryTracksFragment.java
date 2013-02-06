package by.deniotokiari.lastfmmusicplay.fragment.lastfm.page;

import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.TrackAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.api.LastfmAuthHelper;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.TrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Track;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractLastfmListFragment;

public class LibraryTracksFragment extends AbstractLastfmListFragment {

	private static final String[] jsonKeys = { Track.ROOT_LIBRARY_TRACKS,
			Track.ITEM };
	private static final int itemsPerPage = 20;
	private static final Uri uri = TrackContract.URI_LIBRARY_TRACKS;
	private static final String sortOrder = TrackContract.Columns.RANK
			+ " DESC";

	public LibraryTracksFragment() {
		super(jsonKeys, itemsPerPage, uri, null, null, sortOrder);
	}

	@Override
	protected String url() {
		return LastFmAPI.libraryGetTracks(LastfmAuthHelper.getUserName(),
				itemsPerPage, getOffset() + 1);
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new TrackAdapter(getActivity());
	}

}
