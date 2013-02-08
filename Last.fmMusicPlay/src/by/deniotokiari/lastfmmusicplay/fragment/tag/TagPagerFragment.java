package by.deniotokiari.lastfmmusicplay.fragment.tag;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;
import by.deniotokiari.lastfmmusicplay.fragment.PageInfo;
import by.deniotokiari.lastfmmusicplay.fragment.tag.page.TagTopAlbumsFragment;
import by.deniotokiari.lastfmmusicplay.fragment.tag.page.TagTopArtistsFragment;
import by.deniotokiari.lastfmmusicplay.fragment.tag.page.TagTopTracksFragment;

public class TagPagerFragment extends AbstractPagerFragment {

	@Override
	protected String[] pagesName() {
		String[] strings = getResources().getStringArray(R.array.lastfm_tags_pages_name);
		return strings;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected List<PageInfo> pages() {
		Bundle args = new Bundle();
		args.putString(TagsFragment.KEY_TAG, getArguments().getString(TagsFragment.KEY_TAG));
		List<PageInfo> list = new ArrayList<PageInfo>();
		list.add(new PageInfo(TagTopTracksFragment.class, args));
		list.add(new PageInfo(TagTopArtistsFragment.class, args));
		list.add(new PageInfo(TagTopAlbumsFragment.class, args));
		return list;
	}

}
