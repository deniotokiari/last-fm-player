package by.deniotokiari.lastfmmusicplay.fragment.lastfm;

import java.util.ArrayList;
import java.util.List;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;
import by.deniotokiari.lastfmmusicplay.fragment.PageInfo;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.page.LibraryAlbumsFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.page.LibraryArtistsFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.page.LibraryTracksFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.page.LovedTracksFragment;

public class LastfmPagerFragment extends AbstractPagerFragment {

	@Override
	protected String[] pagesName() {
		String[] strings = getResources().getStringArray(
				R.array.lastfm_pages_name);
		return strings;
	}

	@Override
	protected List<PageInfo> pages() {
		List<PageInfo> list = new ArrayList<PageInfo>();
		list.add(new PageInfo(LibraryTracksFragment.class, null));
		list.add(new PageInfo(LovedTracksFragment.class, null));
		list.add(new PageInfo(LibraryArtistsFragment.class, null));
		list.add(new PageInfo(LibraryAlbumsFragment.class, null));
		return list;
	}

}
