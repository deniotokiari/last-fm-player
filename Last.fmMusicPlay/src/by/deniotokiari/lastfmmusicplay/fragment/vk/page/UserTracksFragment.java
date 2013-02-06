package by.deniotokiari.lastfmmusicplay.fragment.vk.page;

import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.TrackAdapter;
import by.deniotokiari.lastfmmusicplay.api.VkAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.vk.UserTrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.vk.Track;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractListFragment;

public class UserTracksFragment extends AbstractListFragment {
	
	private static final Uri uri = UserTrackContract.URI_USER_TRACKS;
	private static final String[] jsonKeys = { Track.ROOT };
	private static int count = 20;

	public UserTracksFragment() {
		super(jsonKeys, uri, null, null, null);
	}

	@Override
	protected String url() {
		return VkAPI.audioGet(count, getOffset());
	}

	@Override
	protected int changeOffset(int itemsCount) {
		if (itemsCount < 0) {
			return 0;
		}
		return getOffset() + itemsCount;
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new TrackAdapter(getActivity());
	}
 
}
