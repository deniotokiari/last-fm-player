package by.deniotokiari.lastfmmusicplay.playlist;

import by.deniotokiari.lastfmmusicplay.ContextHolder;
import by.deniotokiari.lastfmmusicplay.content.contract.PlayerPlaylistContract;
import by.deniotokiari.lastfmmusicplay.content.provider.PlayerPlaylistProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class PlaylistManager {

	public static final int INDEX_TITLE = 1;
	public static final int INDEX_ARTIST = 2;
	public static final int INDEX_ALBUM = 3;
	private static PlaylistManager instance;
	private static Context mContext = ContextHolder.getInstance().getContext();
	private static final Uri mUri = PlayerPlaylistContract.URI_PLAYER_PLAYLIST;

	public static PlaylistManager getInstance() {
		if (instance == null) {
			instance = new PlaylistManager();
		}
		return instance;
	}

	public void setPlaylist(final Uri uri, final String selection,
			final String[] selectionArgs, final String sortOrder) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mContext.getContentResolver().delete(mUri, null, null);
				Cursor cursor = mContext.getContentResolver().query(uri, null,
						selection, selectionArgs, sortOrder);
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					Log.d("LOG", cursor.getString(INDEX_ARTIST) + " - " + cursor.getString(INDEX_TITLE));
					ContentValues values = new ContentValues();
					values.put(PlayerPlaylistProvider.KEY_TITLE, cursor.getString(INDEX_TITLE));
					values.put(PlayerPlaylistProvider.KEY_ARTIST, cursor.getString(INDEX_ARTIST));
					values.put(PlayerPlaylistProvider.KEY_ALBUM, cursor.getString(INDEX_ALBUM));
					mContext.getContentResolver().insert(mUri, values);
					cursor.moveToNext();
				}
				cursor.close();
				mContext.getContentResolver().notifyChange(mUri, null);
			}

		}).start();
	}

}
