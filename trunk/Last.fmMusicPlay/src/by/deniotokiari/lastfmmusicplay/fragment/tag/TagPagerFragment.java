package by.deniotokiari.lastfmmusicplay.fragment.tag;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;
import by.deniotokiari.lastfmmusicplay.fragment.PageInfo;

public class TagPagerFragment extends AbstractPagerFragment {
	
	private String tag;

	@Override
	protected String[] pagesName() {
		String[] strings = getResources().getStringArray(R.array.lastfm_tags_pages_name);
		return strings;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		tag = getArguments().getString(TagsFragment.KEY_TAG);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected List<PageInfo> pages() {
		Bundle args = new Bundle();
		args.putString(TagsFragment.KEY_TAG, tag);
		List<PageInfo> list = new ArrayList<PageInfo>();
		return list;
	}

}
