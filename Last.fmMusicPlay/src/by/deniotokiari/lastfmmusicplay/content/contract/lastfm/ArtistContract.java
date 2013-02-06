package by.deniotokiari.lastfmmusicplay.content.contract.lastfm;

import android.net.Uri;
import android.provider.BaseColumns;

public class ArtistContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.ArtistsProvider";
	public static final Uri URI_ARTISTS = Uri.parse("content://" + AUTHORITY
			+ "/artists");
	public static final Uri URI_TAG_ARTISTS = Uri.parse("content://" + AUTHORITY
			+ "/tag_artists");
	public static final String TABLE_NAME = "ARTISTS";
	public static final String TABLE_TAG_NAME = "TAG_ARTISTS";

	public static final class Columns implements BaseColumns {

		public static final String ARTIST_ID = _ID;
		public static final String NAME = "NAME";
		public static final String RANK = "RANK";
		public static final String IMAGE = "IMAGE";
		public static final String TAG = "TAG";

	}

	public static final int INDEX_ID = 0;
	public static final int INDEX_NAME = 1;
	public static final int INDEX_RANK = 2;
	public static final int INDEX_IMAGE = 3;
	public static final int INDEX_TAG = 4;
	
	private static String createTableTemplate = "CREATE TABLE %s" 
			+ " (" + Columns.ARTIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Columns.NAME + " VARCHAR NOT NULL, " + Columns.RANK
			+ " INTEGER NOT NULL, " + Columns.IMAGE + " VARCHAR, " 
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
