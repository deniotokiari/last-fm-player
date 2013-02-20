package by.deniotokiari.lastfmmusicplay.fragment.lastfm;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;
import by.deniotokiari.lastfmmusicplay.fragment.PageInfo;

public class AlbumTracksPagerFragment extends AbstractPagerFragment {

	@Override
	protected String[] pagesName() {
		String[] pages = { "Album" };
		return pages;
	}

	@Override
	protected List<PageInfo> pages() {
		Bundle args = getArguments();
		List<PageInfo> list = new ArrayList<PageInfo>();
		list.add(new PageInfo(AlbumTracksFragment.class, args));
		return list;
	}

}
