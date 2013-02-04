package by.deniotokiari.lastfmmusicplay.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ContentPagerAdapter extends FragmentPagerAdapter {

	private String[] pagesName;
	private List<Fragment> pages;

	public ContentPagerAdapter(FragmentManager manager, String[] pagesName,
			List<Fragment> pages) {
		super(manager);
		this.pagesName = pagesName;
		this.pages = pages;
	}

	@Override
	public Fragment getItem(int position) {
		return pages.get(position);
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return pagesName[position];
	}

}
