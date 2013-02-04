package by.deniotokiari.lastfmmusicplay.content.provider;

import android.content.ContentValues;
import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.content.AbstractProvider;
import by.deniotokiari.lastfmmusicplay.content.contract.TagContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Tag;

public class TagsProvider extends AbstractProvider {

	@Override
	public ContentValues getContentValues(ContentValues values, Uri uri) {
		ContentValues contentValues = new ContentValues();
		Tag tag = new Tag(values.getAsString(KEY_DATA));
		contentValues.put(TagContract.Columns.NAME, tag.getName());
		contentValues.put(TagContract.Columns.RANK, tag.getRank());
		return contentValues;
	}

	@Override
	public String tableName(Uri uri) {
		return TagContract.TABLE_NAME;
	}

}
