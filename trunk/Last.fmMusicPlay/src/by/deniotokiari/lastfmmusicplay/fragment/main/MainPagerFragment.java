package by.deniotokiari.lastfmmusicplay.fragment.main;

import java.util.ArrayList;
import java.util.List;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.MainPagerAdapter;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;
import by.deniotokiari.lastfmmusicplay.fragment.PageInfo;
import by.deniotokiari.lastfmmusicplay.fragment.main.page.LibraryFragment;
import by.deniotokiari.lastfmmusicplay.fragment.main.page.NowPlayingFragment;
import by.deniotokiari.lastfmmusicplay.fragment.main.page.PlaylistFragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class MainPagerFragment extends AbstractPagerFragment {

	@Override
	protected String[] pagesName() {
		String[] strings = getResources().getStringArray(
				R.array.main_pages_name);
		return strings;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setPage(NowPlayingFragment.PAGE_NUM);
	}

	@Override
	protected List<PageInfo> pages() {
		List<PageInfo> list = new ArrayList<PageInfo>();
		list.add(new PageInfo(LibraryFragment.class, null));
		list.add(new PageInfo(NowPlayingFragment.class, null));
		list.add(new PageInfo(PlaylistFragment.class, null));
		return list;
	} 

	@Override
	protected PagerAdapter getAdapter() {
		return new MainPagerAdapter(getActivity(), getChildFragmentManager(),
				pagesName(), pages());
	}

}
