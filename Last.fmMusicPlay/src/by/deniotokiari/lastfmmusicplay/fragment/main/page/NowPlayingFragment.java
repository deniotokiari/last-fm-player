package by.deniotokiari.lastfmmusicplay.fragment.main.page;

import by.deniotokiari.lastfmmusicplay.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NowPlayingFragment extends Fragment {

	public static final int PAGE_NUM = 1;
	
	public NowPlayingFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.view_fragment_page_nowplaying, container, false);
	}

}
