package by.deniotokiari.lastfmmusicplay.content.contract.lastfm;

import android.net.Uri;
import android.provider.BaseColumns;

public class AlbumContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.AlbumsProvider";
	public static final Uri URI_LIBRARY_ALBUMS = Uri.parse("content://" + AUTHORITY
			+ "/library_albums");
	public static final Uri URI_ARTIST_TOP_ALBUMS = Uri.parse("content://" + AUTHORITY
			+ "/artist_top_albums");
	public static final Uri URI_TAG_TOP_ALBUMS = Uri.parse("content://" + AUTHORITY
			+ "/tag_top_albums");
	public static final String TABLE_NAME_LIBRARY_ALBUMS = "LIBRARY_ALBUMS";
	public static final String TABLE_NAME_ARTIST_TOP_ALBUMS = "ARTIST_TOP_ALBUMS";
	public static final String TABLE_NAME_TAG_TOP_ALBUMS = "TAG_TOP_ALBUMS";

	public static final class Columns implements BaseColumns {

		public static final String ALBUM_ID = _ID;
		public static final String NAME = "NAME";
		public static final String ARTIST = "ARTIST";
		public static final String RANK = "RANK";
		public static final String IMAGE = "IMAGE";
		public static final String TAG = "TAG";

	}

	public static final int INDEX_ID = 0;
	public static final int INDEX_NAME = 1;
	public static final int INDEX_ARTIST = 2;
	public static final int INDEX_RANK = 3;
	public static final int INDEX_IMAGE = 4;
	public static final int INDEX_TAG = 5;

	private static String createTableTemplate = "CREATE TABLE %s ("
			+ Columns.ALBUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Columns.NAME + " VARCHAR NOT NULL, " + Columns.ARTIST
			+ " VARCHAR, " + Columns.RANK + " INTEGER NOT NULL, "
			+ Columns.IMAGE + " VARCHAR, "
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
