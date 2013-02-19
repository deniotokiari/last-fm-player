package by.deniotokiari.lastfmmusicplay.fragment.vk;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;
import by.deniotokiari.lastfmmusicplay.fragment.vk.page.UserTracksFragment;
import by.deniotokiari.lastfmmusicplay.fragment.vk.page.WallTracksFragment;

public class VkPagerFragment extends AbstractPagerFragment {

	@Override
	protected String[] pagesName() {
		String[] strings = getResources().getStringArray(R.array.vk_pages_name);
		return strings;
	}

	@Override
	protected List<Fragment> pages() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(new UserTracksFragment());
		list.add(new WallTracksFragment());
		return list;
	}

}
