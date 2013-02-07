package by.deniotokiari.lastfmmusicplay.content.provider.vk;

import android.content.ContentValues;
import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.content.contract.vk.UserTrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.vk.UserTrack;
import by.deniotokiari.lastfmmusicplay.content.provider.AbstractProvider;

public class UserTracksProvider extends AbstractProvider {

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		UserTrack track = new UserTrack(values.getAsString(KEY_DATA));
		contentValues.put(UserTrackContract.Columns.ARTIST, track.getArtist());
		contentValues.put(UserTrackContract.Columns.TITLE, track.getTitle());
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		return UserTrackContract.TABLE_NAME_USER_TRACKS;
	}

}
