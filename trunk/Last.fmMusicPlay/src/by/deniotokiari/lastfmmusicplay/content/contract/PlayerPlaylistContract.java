package by.deniotokiari.lastfmmusicplay.content.contract;

import android.net.Uri;
import android.provider.BaseColumns;

public class PlayerPlaylistContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.PlayerPlaylistProvider";
	public static final Uri URI_PLAYER_PLAYLIST = Uri.parse("content://"
			+ AUTHORITY + "/player_playlist");
	public static final String TABLE_NAME = "PLAYER_PLAYLIST_TRACK";

	public static final class Columns implements BaseColumns {

		public static final String TRACK_ID = _ID;
		public static final String TITLE = "TITLE";
		public static final String ARTIST = "ARTIST";
		public static final String ALBUM = "ALBUM";

	}

	public static final int INDEX_ID = 0;
	public static final int INDEX_TITLE = 1;
	public static final int INDEX_ARTIST = 2;
	public static final int INDEX_ALBUM = 3;

	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + Columns.TRACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Columns.TITLE + " VARCHAR NOT NULL, " + Columns.ARTIST
			+ " VARCHAR NOT NULL, " + Columns.ALBUM + " VARCHAR" + ")";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

}
