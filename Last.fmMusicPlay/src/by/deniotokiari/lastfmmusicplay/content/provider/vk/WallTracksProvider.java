package by.deniotokiari.lastfmmusicplay.content.provider.vk;

import android.content.ContentValues;
import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.content.contract.vk.WallTrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.vk.WallTrack;
import by.deniotokiari.lastfmmusicplay.content.provider.AbstractProvider;

public class WallTracksProvider extends AbstractProvider {

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		WallTrack track = new WallTrack(values.getAsString(KEY_DATA));
		contentValues.put(WallTrackContract.Columns.ARTIST,
				track.getArtist());
		contentValues
				.put(WallTrackContract.Columns.TITLE, track.getTitle());
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		return WallTrackContract.TABLE_NAME_WALL_TRACKS;
	}

}
