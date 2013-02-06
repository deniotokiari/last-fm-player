package by.deniotokiari.lastfmmusicplay.fragment.playlist;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.TrackAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.PlaylistTrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.other.Track;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractLastfmListFragment;

public class PlaylistTracksFragment extends AbstractLastfmListFragment {

	private String playlistId;
	private static final String selection = PlaylistTrackContract.Columns.PLAYLIST_ID
			+ " = ?";
	private static final Uri uri = PlaylistTrackContract.URI_PLAYLIST_TRACKS;
	private static String[] jsonKeys = { Track.ROOT, null, null };
	private static String[] selectionArgs;

	public PlaylistTracksFragment() {
		super(jsonKeys, 1, uri, selection, selectionArgs, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		playlistId = getArguments().getString(PlaylistFragment.EXTRA_KEY_ID);
		String[] strings = { playlistId };
		selectionArgs = strings;
		jsonKeys[1] = Track.KEY_PLAYLIST_ID;
		jsonKeys[2] = playlistId;
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	protected int changeOffset(int itemsCount) {
		if (itemsCount != 0) {
			setEndOfData(true);
			return 1;
		}
		return 1;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this.getActivity(), uri, null, selection, selectionArgs, null);
	}
	
	@Override
	protected String url() {
		return LastFmAPI.playlistFetch(playlistId);
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new TrackAdapter(getActivity());
	}

}
