package by.deniotokiari.lastfmmusicplay.adapter;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.content.contract.TagContract;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TagsAdapter extends AbstractCursorAdapter {

	public TagsAdapter(Context context) {
		super(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
		TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
		TextView textView3 = (TextView) view.findViewById(android.R.id.hint);
		textView2.setText(cursor.getString(TagContract.INDEX_NAME));
		textView1.setVisibility(View.GONE);
		textView3.setText(cursor.getString(TagContract.INDEX_RANK));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.adapter_text, null);
	}

}
