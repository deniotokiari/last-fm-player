package by.deniotokiari.lastfmmusicplay.content.contract.lastfm;

import android.net.Uri;
import android.provider.BaseColumns;

public class PlaylistContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.PlaylistsProvider";
	public static final Uri URI_PLAYLISTS = Uri.parse("content://" + AUTHORITY
			+ "/playlists");
	public static final String TABLE_NAME = "PLAYLISTS";

	public static final class Columns implements BaseColumns {

		public static final String ID = _ID;
		public static final String PLAYLIST_ID = "ID";
		public static final String TITLE = "TITLE";
		public static final String SIZE = "SIZE";

	}

	public static final int INDEX_ID = 0;
	public static final int INDEX_PLAYLIST_ID = 1;
	public static final int INDEX_TITLE = 2;
	public static final int INDEX_SIZE = 3;

	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Columns.PLAYLIST_ID + " VARCHAR NOT NULL, " + Columns.TITLE
			+ " VARCHAR, "
			+ Columns.SIZE + " VARCHAR"
			+ ")";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
