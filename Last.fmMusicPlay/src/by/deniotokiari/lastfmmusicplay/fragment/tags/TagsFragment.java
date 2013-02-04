package by.deniotokiari.lastfmmusicplay.fragment.tags;

import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.TagsAdapter;
import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.TagContract;
import by.deniotokiari.lastfmmusicplay.content.json.lastfm.Tag;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractListFragment;

public class TagsFragment extends AbstractListFragment {

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
	
}
