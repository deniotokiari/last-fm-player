package by.deniotokiari.lastfmmusicplay.content.provider;

import android.content.ContentValues;
import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.content.AbstractProvider;
import by.deniotokiari.lastfmmusicplay.content.contract.TrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Track;

public class TracksProvider extends AbstractProvider {

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		Track track = new Track(values.getAsString(KEY_DATA));
		contentValues.put(TrackContract.Columns.TITLE, track.getTitle());
		contentValues.put(TrackContract.Columns.ARTIST, track.getArtist());
		contentValues.put(TrackContract.Columns.ALBUM, track.getAlbum());
		contentValues.put(TrackContract.Columns.ALBUM_ART, track.getAlbumArt());
		contentValues.put(TrackContract.Columns.RANK, track.getRank());
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		if (uri.equals(TrackContract.URI_LIBRARY_TRACKS)) {
			return TrackContract.TABLE_NAME_LIBRARY_TRACKS;
		} else if (uri.equals(TrackContract.URI_LOVED_TRACKS)) {
			return TrackContract.TABLE_NAME_LOVED_TRACKS;
		} else if (uri.equals(TrackContract.URI_ARTIST_TOP_TRACKS)) {
			return TrackContract.TABLE_NAME_ARTIST_TOP_TRACKS;
		} else if (uri.equals(TrackContract.URI_ALBUM_TRACKS)) {
			return TrackContract.TABLE_NAME_ALBUM_TRACKS;
		} else {
			return null;
		}
	}

}
