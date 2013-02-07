package by.deniotokiari.lastfmmusicplay.fragment.vk;

import java.util.ArrayList;
import java.util.List;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;
import by.deniotokiari.lastfmmusicplay.fragment.PageInfo;
import by.deniotokiari.lastfmmusicplay.fragment.vk.page.UserTracksFragment;
import by.deniotokiari.lastfmmusicplay.fragment.vk.page.WallTracksFragment;

public class VkPagerFragment extends AbstractPagerFragment {

	@Override
	protected String[] pagesName() {
		String[] strings = getResources().getStringArray(R.array.vk_pages_name);
		return strings;
	}

	@Override
	protected List<PageInfo> pages() {
		List<PageInfo> list = new ArrayList<PageInfo>();
		list.add(new PageInfo(UserTracksFragment.class, null));
		list.add(new PageInfo(WallTracksFragment.class, null));
		return list;
	}

}
