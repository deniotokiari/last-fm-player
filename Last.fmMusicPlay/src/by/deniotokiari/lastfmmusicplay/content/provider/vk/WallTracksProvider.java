package by.deniotokiari.lastfmmusicplay.content.provider.vk;

import android.content.ContentValues;
import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.content.contract.vk.WallNewsTrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.vk.WallNewsTrack;
import by.deniotokiari.lastfmmusicplay.content.provider.AbstractProvider;

public class WallTracksProvider extends AbstractProvider {

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		WallNewsTrack track = new WallNewsTrack(values.getAsString(KEY_DATA));
		contentValues.put(WallNewsTrackContract.Columns.ARTIST, track.getArtist());
		contentValues.put(WallNewsTrackContract.Columns.TITLE, track.getTitle());
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		return WallNewsTrackContract.TABLE_NAME_WALL_TRACKS;
	}

}
