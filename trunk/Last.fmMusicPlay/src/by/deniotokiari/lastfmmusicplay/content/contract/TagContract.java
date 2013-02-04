package by.deniotokiari.lastfmmusicplay.content.contract;

import android.net.Uri;
import android.provider.BaseColumns;

public class TagContract {

	public static final String AUTHORITY = "by.deniotokiari.lastfmmusicplay.provider.TagsProvider";
	public static final Uri URI_TAGS = Uri.parse("content://" + AUTHORITY + "/tags");
	public static final String TABLE_NAME = "TAGS";
	
	public static final class Columns implements BaseColumns {
		
		public static final String TAG_ID = _ID;
		public static final String NAME = "NAME";
		public static final String RANK = "count";
		
	}
	
	public static final int INDEX_ID = 0;
	public static final int INDEX_NAME = 1;
	public static final int INDEX_RANK = 2;
	
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
			+ Columns.TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Columns.NAME + " VARCHAR NOT NULL, " 
			+ Columns.RANK + " INTEGER NOT NULL)";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
