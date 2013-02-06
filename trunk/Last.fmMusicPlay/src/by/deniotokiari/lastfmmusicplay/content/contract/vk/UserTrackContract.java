package by.deniotokiari.lastfmmusicplay.content.contract.vk;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserTrackContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.UserTracksProvider";
	public static final Uri URI_USER_TRACKS = Uri.parse("content://"
			+ AUTHORITY + "/user_tracks");
	public static final String TABLE_NAME_USER_TRACKS = "USER_TRACKS";

	public static final class Columns implements BaseColumns {

		public static final String TRACKS_ID = _ID;
		public static final String TITLE = "TITLE";
		public static final String ARTIST = "ARTIST";

	}

	public static final int INDEX_ID = 0;
	public static final int INDEX_TITLE = 1;
	public static final int INDEX_ARTIST = 2;

	public static final String CREATE_TABLE = "CREATE TABLE "
			+ TABLE_NAME_USER_TRACKS + " (" + Columns.TRACKS_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Columns.TITLE
			+ " VARCHAR, " + Columns.ARTIST + " VARCHAR" + ")";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME_USER_TRACKS;

}
