package by.deniotokiari.lastfmmusicplay.content.contract.vk;

import android.net.Uri;
import android.provider.BaseColumns;

public class WallTrackContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.WallTracksProvider";
	public static final Uri URI_WALL_TRACKS = Uri.parse("content://"
			+ AUTHORITY + "/wall_tracks");
	public static final Uri URI_NEWS_TRACKS = Uri.parse("content://"
			+ AUTHORITY + "/news_tracks");
	public static final String TABLE_NAME_WALL_TRACKS = "WALL_TRACKS";
	public static final String TABLE_NAME_NEWS_TRACKS = "NEWS_TRACKS";

	public static final class Columns implements BaseColumns {

		public static final String TRACK_ID = _ID;
		public static final String TITLE = "TITLE";
		public static final String ARTIST = "ARTIST";

	}

	public static final int INDEX_ID = 0;
	public static final int INDEX_TITLE = 1;
	public static final int INDEX_ARTIST = 2;

	private static String createTableTemplate = "CREATE TABLE %s " + " ("
			+ Columns.TRACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Columns.TITLE + " VARCHAR, " + Columns.ARTIST + " VARCHAR" + ")";
	private static String dropTableTemplate = "DROP TABLE IF EXISTS %s";

	public static String createTable(String tableName) {
		return String.format(createTableTemplate, tableName);
	}

	public static String dropTable(String tableName) {
		return String.format(dropTableTemplate, tableName);
	}
}
