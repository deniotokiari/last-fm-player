package by.deniotokiari.lastfmmusicplay.content.provider;

import java.util.ArrayList;
import java.util.List;

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
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return mDbHelper.deleteAll(tableName(uri));
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
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

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		return mDbHelper.addContent(tableName(uri),
				getContentValues(values, uri));
	}

	private ContentValues[] getContentValues(ContentValues[] values, Uri uri) {
		List<ContentValues> contentValues = new ArrayList<ContentValues>();
		for (ContentValues val : values) {
			contentValues.add(getContentValues(val, uri));
		}
		return contentValues.toArray(new ContentValues[] {});
	}

}
