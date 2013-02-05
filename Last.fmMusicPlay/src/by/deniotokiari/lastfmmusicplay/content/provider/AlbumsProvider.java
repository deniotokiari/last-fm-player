package by.deniotokiari.lastfmmusicplay.content.provider;

import android.content.ContentValues;
import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.content.AbstractProvider;
import by.deniotokiari.lastfmmusicplay.content.contract.AlbumContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Album;

public class AlbumsProvider extends AbstractProvider {

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		Album album = new Album(values.getAsString(KEY_DATA));
		contentValues.put(AlbumContract.Columns.NAME, album.getName());
		contentValues.put(AlbumContract.Columns.ARTIST, album.getArtist());
		contentValues.put(AlbumContract.Columns.RANK, album.getRank());
		contentValues.put(AlbumContract.Columns.IMAGE, album.getImage());
		contentValues.put(AlbumContract.Columns.TAG, album.getTag());
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		if (uri.equals(AlbumContract.URI_LIBRARY_ALBUMS)) {
			return AlbumContract.TABLE_NAME_LIBRARY_ALBUMS;
		} else if (uri.equals(AlbumContract.URI_ARTIST_TOP_ALBUMS)) {
			return AlbumContract.TABLE_NAME_ARTIST_TOP_ALBUMS;
		} else if (uri.equals(AlbumContract.URI_TAG_TOP_ALBUMS)) {
			return AlbumContract.TABLE_NAME_TAG_TOP_ALBUMS;
		} else {
			return null;
		}
	}

}
