package by.deniotokiari.lastfmmusicplay.playlist;

import by.deniotokiari.lastfmmusicplay.ContextHolder;
import by.deniotokiari.lastfmmusicplay.content.contract.PlayerPlaylistContract;
import by.deniotokiari.lastfmmusicplay.content.provider.PlayerPlaylistProvider;
import by.deniotokiari.lastfmmusicplay.preferences.PreferencesHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class PlaylistManager {

	public static final String PREF_NAME = "playlist";
	public static final String PREF_KEY_POSITION = "position";

	public static final int INDEX_TITLE = 1;
	public static final int INDEX_ARTIST = 2;
	public static final int INDEX_ALBUM = 3;
	private static PlaylistManager instance;
	private static Context mContext = ContextHolder.getInstance().getContext();
	private static final Uri mUri = PlayerPlaylistContract.URI_PLAYER_PLAYLIST;
	private static int POSITION;

	public static PlaylistManager getInstance() {
		if (instance == null) {
			instance = new PlaylistManager();
			POSITION = PreferencesHelper.getInstance().getInt(PREF_NAME, PREF_KEY_POSITION);
		}
		return instance;
	}

	public void setPlaylist(int position ,final Uri uri, final String selection,
			final String[] selectionArgs, final String sortOrder) {
		POSITION = position;
		PreferencesHelper.getInstance().putInt(PREF_NAME, PREF_KEY_POSITION, POSITION);
		new Thread(new Runnable() {

			@Override
			public void run() {
				mContext.getContentResolver().delete(mUri, null, null);
				Cursor cursor = mContext.getContentResolver().query(uri, null,
						selection, selectionArgs, sortOrder);
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					ContentValues values = new ContentValues();
					values.put(PlayerPlaylistProvider.KEY_TITLE,
							cursor.getString(INDEX_TITLE));
					values.put(PlayerPlaylistProvider.KEY_ARTIST,
							cursor.getString(INDEX_ARTIST));
					values.put(PlayerPlaylistProvider.KEY_ALBUM,
							cursor.getString(INDEX_ALBUM));
					mContext.getContentResolver().insert(mUri, values);
					cursor.moveToNext();
				}
				cursor.close();
				mContext.getContentResolver().notifyChange(mUri, null);
			}

		}).start();
	}

	private String getString(int index) {
		Cursor cursor = mContext.getContentResolver().query(mUri, null, null,
				null, null);
		cursor.moveToPosition(POSITION);
		String result = cursor.getString(index);
		cursor.close();
		return result;
	}

	public String getTitle() {
		return getString(INDEX_TITLE);
	}

	public String getArtist() {
		return getString(INDEX_ARTIST);
	}

	public String getAlbum() {
		return getString(INDEX_ALBUM);
	}

}
