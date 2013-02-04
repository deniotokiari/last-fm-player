package by.deniotokiari.lastfmmusicplay.content.contract;

import android.net.Uri;
import android.provider.BaseColumns;

public class ArtistContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.ArtistsProvider";
	public static final Uri URI_ARTISTS = Uri.parse("content://" + AUTHORITY
			+ "/artists");
	public static final String TABLE_NAME = "ARTISTS";

	public static final class Columns implements BaseColumns {

		public static final String ARTIST_ID = _ID;
		public static final String NAME = "NAME";
		public static final String RANK = "RANK";
		public static final String IMAGE = "IMAGE";

	}

	public static final int INDEX_ID = 0;
	public static final int INDEX_NAME = 1;
	public static final int INDEX_RANK = 2;
	public static final int INDEX_IMAGE = 3;

	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + Columns.ARTIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Columns.NAME + " VARCHAR NOT NULL, " + Columns.RANK
			+ " INTEGER NOT NULL, " + Columns.IMAGE + " VARCHAR" + ")";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

}
