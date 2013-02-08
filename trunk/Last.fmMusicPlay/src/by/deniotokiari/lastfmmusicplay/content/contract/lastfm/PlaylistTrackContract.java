package by.deniotokiari.lastfmmusicplay.content.contract.lastfm;

import android.net.Uri;
import android.provider.BaseColumns;

public class PlaylistTrackContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.PlaylistTracksProvider";
	public static final Uri URI_PLAYLIST_TRACKS = Uri.parse("content://"
			+ AUTHORITY + "/playlist_tracks");
	public static final String TABLE_NAME = "PLAYLIST_TRACKS";

	public static final class Columns implements BaseColumns {

		public static final String TRACK_ID = _ID;
		public static final String TITLE = "TITLE";
		public static final String ARTIST = "ARTIST";
		public static final String PLAYLIST_ID = "PLAYLIST_ID";

	}

	public static final int INDEX_ID = 0;
	public static final int INDEX_TITLE = 1;
	public static final int INDEX_ARTIST = 2;
	public static final int INDEX_PLAYLIST_ID = 3;

	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + Columns.TRACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Columns.TITLE + " VARCHAR, " + Columns.ARTIST + " VARCHAR, "
			+ Columns.PLAYLIST_ID + " INTEGER NOT NULL" + ")";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

}
