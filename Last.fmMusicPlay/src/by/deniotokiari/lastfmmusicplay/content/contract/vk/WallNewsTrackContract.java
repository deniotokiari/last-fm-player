package by.deniotokiari.lastfmmusicplay.content.contract.vk;

import android.net.Uri;
import android.provider.BaseColumns;

public class WallNewsTrackContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.WallTracksProvider";
	public static final Uri URI_WALL_TRACKS = Uri.parse("content://"
			+ AUTHORITY + "/wall_tracks");
	public static final String TABLE_NAME_WALL_TRACKS = "WALL_TRACKS";

	public static final class Columns implements BaseColumns {

		public static final String TRACK_ID = _ID;
		public static final String TITLE = "TITLE";
		public static final String ARTIST = "ARTIST";

	}

	public static final int INDEX_ID = 0;
	public static final int INDEX_TITLE = 1;
	public static final int INDEX_ARTIST = 2;

	public static final String CREATE_TABLE = "CREATE TABLE "
			+ TABLE_NAME_WALL_TRACKS + " (" + Columns.TRACK_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Columns.TITLE
			+ " VARCHAR, " + Columns.ARTIST + " VARCHAR" + ")";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME_WALL_TRACKS;

}
