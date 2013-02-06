package by.deniotokiari.lastfmmusicplay.content.provider;

import android.content.ContentValues;
import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.content.AbstractProvider;
import by.deniotokiari.lastfmmusicplay.content.contract.PlaylistTrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.other.Track;

public class PlaylistTracksProvider extends AbstractProvider {

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		Track track = new Track(values.getAsString(KEY_DATA));
		contentValues.put(PlaylistTrackContract.Columns.PLAYLIST_ID,
				track.getPlaylistId());
		contentValues
				.put(PlaylistTrackContract.Columns.TITLE, track.getTitle());
		contentValues.put(PlaylistTrackContract.Columns.ARTIST,
				track.getArtist());
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		return PlaylistTrackContract.TABLE_NAME;
	}

}
