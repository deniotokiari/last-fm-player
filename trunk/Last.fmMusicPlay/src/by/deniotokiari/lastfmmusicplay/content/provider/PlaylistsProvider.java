package by.deniotokiari.lastfmmusicplay.content.provider;

import android.content.ContentValues;
import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.content.AbstractProvider;
import by.deniotokiari.lastfmmusicplay.content.contract.PlaylistContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Playlist;

public class PlaylistsProvider extends AbstractProvider {

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		Playlist playlist = new Playlist(values.getAsString(KEY_DATA));
		contentValues.put(PlaylistContract.Columns.PLAYLIST_ID,
				playlist.getId());
		contentValues.put(PlaylistContract.Columns.TITLE, playlist.getTitle());
		contentValues.put(PlaylistContract.Columns.SIZE, playlist.getSize());
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		return PlaylistContract.TABLE_NAME;
	}

}
