package by.deniotokiari.lastfmmusicplay.adapter;

import java.util.List;

import by.deniotokiari.lastfmmusicplay.fragment.PageInfo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {

	private String[] mPagesName;
	private List<PageInfo> mPages;
	private Context mContext;

	public MainPagerAdapter(Context context, FragmentManager manager,
			String[] pagesName, List<PageInfo> pages) {
		super(manager);
		mPagesName = pagesName;
		mPages = pages;
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
		PageInfo pageInfo = mPages.get(position);
		Fragment fragment = Fragment.instantiate(mContext, pageInfo.getCls().getName(),
				pageInfo.getBundle());
		return fragment;
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
