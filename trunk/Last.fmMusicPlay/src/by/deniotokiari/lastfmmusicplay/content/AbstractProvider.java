package by.deniotokiari.lastfmmusicplay.content;

import by.deniotokiari.lastfmmusicplay.db.DBHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

abstract public class AbstractProvider extends ContentProvider {

	private DBHelper mDbHelper;

	public static final String KEY_DATA = "data";
	
	abstract public ContentValues getContentValues(ContentValues values, Uri uri);

	abstract public String tableName(Uri uri);

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = mDbHelper.addContent(tableName(uri),
				getContentValues(values, uri));
		Uri contentUri = ContentUris.withAppendedId(uri, id);
		return contentUri;
	}

	@Override
	public boolean onCreate() {
		mDbHelper = new DBHelper(getContext(), null);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = mDbHelper.getContent(tableName(uri), projection,
				selection, selectionArgs, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

}
