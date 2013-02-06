package by.deniotokiari.lastfmmusicplay.adapter;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.content.contract.lastfm.TrackContract;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrackAdapter extends AbstractCursorAdapter {

	public TrackAdapter(Context context) {
		super(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
		TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
		TextView textView3 = (TextView) view.findViewById(android.R.id.hint);
		textView1.setText(cursor.getString(TrackContract.INDEX_ARTIST));
		textView2.setText(cursor.getString(TrackContract.INDEX_TITLE));
		textView3.setText(String.valueOf(cursor.getPosition() + 1));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.adapter_text, null);
	}

}
