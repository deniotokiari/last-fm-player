package by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist.page.ArtistTopAlbumsFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist.page.ArtistTopTracksFragment;

public class ArtistPagerFragment extends AbstractPagerFragment {

	public static final String KEY_ARTIST = "artist";

	@Override
	protected String[] pagesName() {
		String[] strings = getResources().getStringArray(
				R.array.lastfm_artist_pages_name);
		return strings;
	}

	@Override
	protected List<Fragment> pages() {
		Bundle args = new Bundle();
		args.putString(KEY_ARTIST, getArguments().getString(KEY_ARTIST));
		List<Fragment> list = new ArrayList<Fragment>();
		Fragment fragment = new ArtistTopTracksFragment();
		fragment.setArguments(args);
		list.add(fragment);
		fragment = new ArtistTopAlbumsFragment();
		fragment.setArguments(args);
		list.add(fragment);
		return list;
	}

}
