package by.deniotokiari.lastfmmusicplay.fragment.lastfm;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.TrackAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.TrackContract;
import by.deniotokiari.lastfmmusicplay.content.images.ImageLoader;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Track;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractLastfmListFragment;
import by.deniotokiari.lastfmmusicplay.playlist.PlaylistManager;

public class AlbumTracksFragment extends AbstractLastfmListFragment {

	public static final String EXTRA_KEY_ALBUM = "album";
	public static final String EXTRA_KEY_ARTIST = "artist";
	public static final String EXTRA_KEY_URL = "url";
	public static final String JSON_KEY = "album";

	private View mHeader;
	public static final int HEADER_RES = R.layout.view_album_header;
	private static final int itemsPerPage = 1;
	private String album;
	private String artist;
	private String url;
	private static String[] selectionArgs;
	private static final String selection = TrackContract.Columns.ALBUM
			+ " = ?";
	private static final Uri uri = TrackContract.URI_ALBUM_TRACKS;
	private static final String sortOrder = null;//TrackContract.Columns.RANK + " ASC";
	private static String[] jsonKeys = { Track.ROOT_ALBUM_TRACKS,
			Track.ITEM_ROOT_ALBUM_TRACKS, Track.ITEM, null, null };

	public AlbumTracksFragment() {
		super(jsonKeys, itemsPerPage, uri, selection, selectionArgs, sortOrder);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		album = getArguments().getString(EXTRA_KEY_ALBUM);
		artist = getArguments().getString(EXTRA_KEY_ARTIST);
		url = getArguments().getString(EXTRA_KEY_URL);
		String[] strings = { album };
		selectionArgs = strings;
		jsonKeys[3] = JSON_KEY;
		jsonKeys[4] = album;
		super.onViewCreated(view, savedInstanceState);
		mHeader = getLayoutInflater(savedInstanceState).inflate(HEADER_RES,
				null, false);
		getListView().addHeaderView(mHeader, null, false);
		TextView textView1 = (TextView) mHeader.findViewById(R.id.album_title);
		TextView textView2 = (TextView) mHeader.findViewById(R.id.album_artist);
		textView1.setText(album);
		textView2.setText(artist);
		ImageView imageView = (ImageView) mHeader.findViewById(R.id.album_art);
		ImageLoader.getInstance().bind(imageView, url, 0);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this.getActivity(), uri, null, selection, selectionArgs, sortOrder);
	}

	@Override
	protected String url() {
		return LastFmAPI.albumGetInfo(artist, album);
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
	protected AbstractCursorAdapter adapter() {
		return new TrackAdapter(getActivity());
	}
	
	@Override
	public void onListItemClick(ListView l, View v, final int position, long id) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				PlaylistManager.getInstance().setPlaylist(position - 1, uri, selection,
						selectionArgs, sortOrder);
			}
			
		}).start();
	}

}
