package by.deniotokiari.lastfmmusicplay.content.provider.lastfm;

import android.content.ContentValues;
import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.ArtistContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Artist;
import by.deniotokiari.lastfmmusicplay.content.provider.AbstractProvider;

public class ArtistsProvider extends AbstractProvider {

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		Artist artist = new Artist(values.getAsString(KEY_DATA));
		contentValues.put(ArtistContract.Columns.NAME, artist.getName());
		contentValues.put(ArtistContract.Columns.RANK, artist.getRank());
		contentValues.put(ArtistContract.Columns.IMAGE, artist.getImage());
		contentValues.put(ArtistContract.Columns.TAG, artist.getTag());
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		if (uri.equals(ArtistContract.URI_ARTISTS)) {
			return ArtistContract.TABLE_NAME;
		} else if (uri.equals(ArtistContract.URI_TAG_ARTISTS)) {
			return ArtistContract.TABLE_TAG_NAME;
		}
		return null;
	}

}
