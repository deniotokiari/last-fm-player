package by.deniotokiari.lastfmmusicplay.fragment.lastfm;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;
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
	protected List<Fragment> pages() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(new LibraryTracksFragment());
		list.add(new LovedTracksFragment());
		list.add(new LibraryArtistsFragment());
		list.add(new LibraryAlbumsFragment());
		return list;
	}

}
