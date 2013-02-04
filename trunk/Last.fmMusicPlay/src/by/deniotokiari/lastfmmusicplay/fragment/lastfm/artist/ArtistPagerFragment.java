package by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractPagerFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist.page.ArtistTopAlbumsFragment;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.artist.page.ArtistTopTracksFragment;

public class ArtistPagerFragment extends AbstractPagerFragment {
	
	public static final String KEY_ARTIST = "artist";
	
	@Override
	protected String[] pagesName() {
		String[] strings = getResources().getStringArray(R.array.lastfm_artist_pages_name);
		return strings;
	}

	@Override
	protected List<Fragment> pages() {
		String artist = getArguments().getString(KEY_ARTIST);
		ArtistTopTracksFragment.setArtist(artist);
		ArtistTopAlbumsFragment.setArtist(artist);
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(new ArtistTopTracksFragment());
		list.add(new ArtistTopAlbumsFragment());
		return list;
	}
	
}
