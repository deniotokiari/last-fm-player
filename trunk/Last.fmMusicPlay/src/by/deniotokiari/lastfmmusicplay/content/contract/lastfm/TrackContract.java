package by.deniotokiari.lastfmmusicplay.content.contract.lastfm;

import android.net.Uri;
import android.provider.BaseColumns;

public class TrackContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.TracksProvider";

	/** Uri **/
	public static final Uri URI_LIBRARY_TRACKS = Uri.parse("content://"
			+ AUTHORITY + "/library_tracks");
	public static final Uri URI_LOVED_TRACKS = Uri.parse("content://"
			+ AUTHORITY + "/loved_tracks");
	public static final Uri URI_ARTIST_TOP_TRACKS = Uri.parse("content://"
			+ AUTHORITY + "/artist_top_tracks");
	public static final Uri URI_ALBUM_TRACKS = Uri.parse("content://"
			+ AUTHORITY + "/album_top_tracks");
	public static final Uri URI_TAG_TRACKS = Uri.parse("content://" + AUTHORITY
			+ "/tag_tracks");

	/** Table name **/
	public static final String TABLE_NAME_LIBRARY_TRACKS = "LIBRARY_TRACKS";
	public static final String TABLE_NAME_LOVED_TRACKS = "LOVED_TRACKS";
	public static final String TABLE_NAME_ARTIST_TOP_TRACKS = "ARTIST_TOP_TRACKS";
	public static final String TABLE_NAME_ALBUM_TRACKS = "ALBUM_TRACKS";
	public static final String TABLE_NAME_TAG_TRACKS = "TAG_TRACKS";

	public static final class Columns implements BaseColumns {

		public static final String TRACK_ID = _ID;
		public static final String TITLE = "TITLE";
		public static final String ARTIST = "ARTIST";
		public static final String ALBUM = "ALBUM";
		public static final String ALBUM_ART = "ALBUM_ART";
		public static final String RANK = "RANK";
		public static final String TAG = "TAG";

	}

	public static final int INDEX_ID = 0;
	public static final int INDEX_TITLE = 1;
	public static final int INDEX_ARTIST = 2;
	public static final int INDEX_ALBUM = 3;
	public static final int INDEX_ALBUM_ART = 4;
	public static final int INDEX_RANK = 5;
	public static final int INDEX_TAG = 6;

	private static String createTableTemplate = "CREATE TABLE %s ("
			+ Columns.TRACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Columns.TITLE + " VARCHAR NOT NULL, " + Columns.ARTIST
			+ " VARCHAR NOT NULL, " + Columns.ALBUM + " VARCHAR, "
			+ Columns.ALBUM_ART + " VARCHAR, " + Columns.RANK + " INTEGER, "
			+ Columns.TAG + " VARCHAR"
			+ ")";
	private static String dropTableTemplate = "DROP TABLE IF EXISTS %s";

	public static String createTable(String tableName) {
		return String.format(createTableTemplate, tableName);
	}

	public static String dropTable(String tableName) {
		return String.format(dropTableTemplate, tableName);
	}

}
