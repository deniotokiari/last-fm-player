package by.deniotokiari.lastfmmusicplay.fragment.tag;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.TagsAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.TagContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Tag;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractListFragment;

public class TagsFragment extends AbstractListFragment {

	public static final String KEY_TAG = "tag";
	
	private static final String[] jsonKeys = { Tag.ROOT, Tag.ITEM };
	private static final Uri uri = TagContract.URI_TAGS;
	private static final String sortOrder = TagContract.Columns.RANK + " DESC";
	
	public TagsFragment() {
		super(jsonKeys, uri, null, null, sortOrder);
	}

	@Override
	protected String url() {
		return LastFmAPI.tagGetTopTags();
	}

	@Override
	protected int changeOffset(int itemsCount) {
		if (itemsCount > 0) {
			setEndOfData(true);
			return 0;
		}
		return 0;
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new TagsAdapter(getActivity());
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cursor = (Cursor) getListView().getItemAtPosition(position);
		String tag = cursor.getString(TagContract.INDEX_NAME);
		Bundle bundle = new Bundle();
		bundle.putString(KEY_TAG, tag);
		Fragment fragment = new TagPagerFragment();
		fragment.setArguments(bundle);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.content, fragment);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
}
