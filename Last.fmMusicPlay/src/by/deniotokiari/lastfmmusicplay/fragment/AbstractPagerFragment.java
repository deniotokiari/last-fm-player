package by.deniotokiari.lastfmmusicplay.fragment;

import java.util.List;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.ContentPagerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract public class AbstractPagerFragment extends Fragment {

	private String[] mPagesName;
	private List<Fragment> mPages;
	private PagerAdapter mAdapter;
	private ViewPager mViewPager;

	protected abstract String[] pagesName();

	protected abstract List<Fragment> pages();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.view_pager_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mPagesName = pagesName();
		mPages = pages();
		if (getAdapter() == null) {
			mAdapter = new ContentPagerAdapter(getChildFragmentManager(),
					mPagesName, mPages);
		} else {
			mAdapter = getAdapter();
		}
		mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
		mViewPager.setAdapter(mAdapter);
		PagerTabStrip pagerTabStrip = (PagerTabStrip) getActivity()
				.findViewById(R.id.pagerTabStrip);
		// TODO color imp
		pagerTabStrip.setTabIndicatorColor(Color.parseColor("#dedede"));
		pagerTabStrip.setTextColor(Color.parseColor("#ffffff"));
		pagerTabStrip.setBackgroundColor(Color.parseColor("#2d2d2d"));
	}

	protected void setPage(int page) {
		mViewPager.setCurrentItem(page);
	}

	protected PagerAdapter getAdapter() {
		return null;
	}

}
