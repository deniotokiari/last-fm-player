package by.deniotokiari.lastfmmusicplay.content.provider;

import android.content.ContentValues;
import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.content.AbstractProvider;
import by.deniotokiari.lastfmmusicplay.content.contract.ArtistContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Artist;

public class ArtistsProvider extends AbstractProvider {

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		Artist artist = new Artist(values.getAsString("data"));
		contentValues.put(ArtistContract.Columns.NAME, artist.getName());
		contentValues.put(ArtistContract.Columns.RANK, artist.getRank());
		contentValues.put(ArtistContract.Columns.IMAGE, artist.getImage());
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		return ArtistContract.TABLE_NAME;
	}

}
