package by.deniotokiari.lastfmmusicplay.db;

import by.deniotokiari.lastfmmusicplay.content.contract.PlayerPlaylistContract;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.AlbumContract;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.ArtistContract;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.PlaylistContract;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.PlaylistTrackContract;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.TagContract;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.TrackContract;
import by.deniotokiari.lastfmmusicplay.content.contract.vk.UserTrackContract;
import by.deniotokiari.lastfmmusicplay.content.contract.vk.WallTrackContract;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "store.db";
	private static final int DATABASE_VERSION = 2;

	public DBHelper(Context context, CursorFactory cursorFactory) {
		super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.beginTransaction();
			db.execSQL(TrackContract
					.createTable(TrackContract.TABLE_NAME_LIBRARY_TRACKS));
			db.execSQL(TrackContract
					.createTable(TrackContract.TABLE_NAME_LOVED_TRACKS));
			db.execSQL(TrackContract
					.createTable(TrackContract.TABLE_NAME_ARTIST_TOP_TRACKS));
			db.execSQL(TrackContract
					.createTable(TrackContract.TABLE_NAME_ALBUM_TRACKS));
			db.execSQL(ArtistContract.createTable(ArtistContract.TABLE_NAME));
			db.execSQL(ArtistContract
					.createTable(ArtistContract.TABLE_TAG_NAME));
			db.execSQL(AlbumContract
					.createTable(AlbumContract.TABLE_NAME_LIBRARY_ALBUMS));
			db.execSQL(AlbumContract
					.createTable(AlbumContract.TABLE_NAME_ARTIST_TOP_ALBUMS));
			db.execSQL(TagContract.CREATE_TABLE);
			db.execSQL(TrackContract
					.createTable(TrackContract.TABLE_NAME_TAG_TRACKS));
			db.execSQL(AlbumContract
					.createTable(AlbumContract.TABLE_NAME_TAG_TOP_ALBUMS));
			db.execSQL(PlaylistContract.CREATE_TABLE);
			db.execSQL(PlaylistTrackContract.CREATE_TABLE);
			db.execSQL(UserTrackContract.CREATE_TABLE);
			db.execSQL(WallTrackContract
					.createTable(WallTrackContract.TABLE_NAME_WALL_TRACKS));
			db.execSQL(PlayerPlaylistContract.CREATE_TABLE);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		try {
			db.beginTransaction();
			db.execSQL(TrackContract
					.dropTable(TrackContract.TABLE_NAME_LIBRARY_TRACKS));
			db.execSQL(TrackContract
					.dropTable(TrackContract.TABLE_NAME_LOVED_TRACKS));
			db.execSQL(TrackContract
					.dropTable(TrackContract.TABLE_NAME_ARTIST_TOP_TRACKS));
			db.execSQL(TrackContract
					.dropTable(TrackContract.TABLE_NAME_ALBUM_TRACKS));
			db.execSQL(ArtistContract.dropTable(ArtistContract.TABLE_NAME));
			db.execSQL(ArtistContract.dropTable(ArtistContract.TABLE_TAG_NAME));
			db.execSQL(AlbumContract
					.dropTable(AlbumContract.TABLE_NAME_LIBRARY_ALBUMS));
			db.execSQL(AlbumContract
					.dropTable(AlbumContract.TABLE_NAME_ARTIST_TOP_ALBUMS));
			db.execSQL(TagContract.DROP_TABLE);
			db.execSQL(TrackContract
					.dropTable(TrackContract.TABLE_NAME_TAG_TRACKS));
			db.execSQL(AlbumContract
					.dropTable(AlbumContract.TABLE_NAME_TAG_TOP_ALBUMS));
			db.execSQL(PlaylistContract.DROP_TABLE);
			db.execSQL(PlaylistTrackContract.DROP_TABLE);
			db.execSQL(UserTrackContract.DROP_TABLE);
			db.execSQL(WallTrackContract
					.dropTable(WallTrackContract.TABLE_NAME_WALL_TRACKS));
			db.execSQL(PlayerPlaylistContract.DROP_TABLE);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		onCreate(db);
	}

	public Cursor getContent(String table, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(table, projection, selection, selectionArgs,
				null, null, sortOrder);
		return cursor;
	}

	public long addContent(String table, ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		return db.insert(table, null, values);
	}
	
	public int deleteAll(String tableName) {
		SQLiteDatabase db = getWritableDatabase();
		return db.delete(tableName, null, null);
	}

}
