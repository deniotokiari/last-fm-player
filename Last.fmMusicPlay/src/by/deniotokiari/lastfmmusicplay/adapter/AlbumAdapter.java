package by.deniotokiari.lastfmmusicplay.adapter;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.AlbumContract;
import by.deniotokiari.lastfmmusicplay.content.images.ImageLoader;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumAdapter extends AbstractCursorAdapter {

	public AlbumAdapter(Context context) {
		super(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
		TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
		ImageView imageView = (ImageView) view.findViewById(android.R.id.content);
		textView1.setText(cursor.getString(AlbumContract.INDEX_NAME));
		textView2.setText(cursor.getString(AlbumContract.INDEX_ARTIST));
		String url = cursor.getString(AlbumContract.INDEX_IMAGE);
		ImageLoader.getInstance().bind(this, imageView, url);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.adapter_text_image, null);
	}
	
}
