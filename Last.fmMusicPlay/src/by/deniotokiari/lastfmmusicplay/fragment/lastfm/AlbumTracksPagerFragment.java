package by.deniotokiari.lastfmmusicplay.fragment.lastfm;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;

public class AlbumTracksPagerFragment extends AbstractPagerFragment {

	@Override
	protected String[] pagesName() {
		String[] pages = { "Album" };
		return pages;
	}

	@Override
	protected List<Fragment> pages() {
		Bundle args = getArguments();
		List<Fragment> list = new ArrayList<Fragment>();
		Fragment fragment = new AlbumTracksFragment();
		fragment.setArguments(args);
		list.add(fragment);
		return list;
	}

}
