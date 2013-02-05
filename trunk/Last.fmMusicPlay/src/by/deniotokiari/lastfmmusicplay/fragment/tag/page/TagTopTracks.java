package by.deniotokiari.lastfmmusicplay.fragment.tag.page;

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
import by.deniotokiari.lastfmmusicplay.fragment.tag.TagsFragment;

public class TagTopTracks extends AbstractLastfmListFragment {

	private static final int itemsPerPage = 50;
	private static final Uri uri = TrackContract.URI_TAG_TRACKS;
	private static final String[] jsonKeys = { Track.ROOT_TAG_TRACKS,
			Track.ITEM };
	private static final String selection = TrackContract.Columns.TAG + " = ?";
	private static String[] selectionArgs;
	private static String sortOrder = TrackContract.Columns.RANK + " ASC";
	private String tag;

	public TagTopTracks() {
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
		return LastFmAPI.tagGetTopTracks(tag, itemsPerPage, getOffset() + 1);
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new TrackAdapter(getActivity());
	}

}
