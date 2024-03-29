package by.deniotokiari.lastfmmusicplay.content.provider;

import by.deniotokiari.lastfmmusicplay.content.contract.PlayerPlaylistContract;
import android.content.ContentValues;
import android.net.Uri;

public class PlayerPlaylistProvider extends AbstractProvider {

	public static final String KEY_TITLE = "title";
	public static final String KEY_ARTIST = "artist";

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		String title = values.getAsString(KEY_TITLE);
		String artist = values.getAsString(KEY_ARTIST);
		contentValues.put(PlayerPlaylistContract.Columns.TITLE,
				title);
		contentValues.put(PlayerPlaylistContract.Columns.ARTIST,
				artist);
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		return PlayerPlaylistContract.TABLE_NAME;
	}

}
