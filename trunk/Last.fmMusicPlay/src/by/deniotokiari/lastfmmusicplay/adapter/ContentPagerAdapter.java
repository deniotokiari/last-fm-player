package by.deniotokiari.lastfmmusicplay.adapter;

import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ContentPagerAdapter extends FragmentStatePagerAdapter {

	private String[] mPagesName;
	private List<Fragment> mPages;

	public ContentPagerAdapter(FragmentManager manager, String[] pagesName,
			List<Fragment> pages) {
		super(manager);
		mPagesName = pagesName;
		mPages = pages;
	}

	@Override
	public Fragment getItem(int position) {
		return mPages.get(position);
	}

	@Override
	public int getCount() {
		return mPages.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mPagesName[position];
	}

}
