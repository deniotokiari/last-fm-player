package by.deniotokiari.lastfmmusicplay.adapter;

import by.deniotokiari.lastfmmusicplay.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LibraryAdapter extends ArrayAdapter<String> {

	public LibraryAdapter(Context context, String[] sections) {
		super(context, R.layout.adapter_library, sections);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(getContext(), R.layout.adapter_library,
					null);
		}
		TextView textView = (TextView) convertView
				.findViewById(android.R.id.text1);
		textView.setText(getItem(position));
		return convertView;
	}

}
