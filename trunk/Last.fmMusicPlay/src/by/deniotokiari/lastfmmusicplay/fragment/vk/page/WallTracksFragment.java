package by.deniotokiari.lastfmmusicplay.fragment.vk.page;

import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.adapter.TrackAdapter;
import by.deniotokiari.lastfmmusicplay.api.VkAPI;
import by.deniotokiari.lastfmmusicplay.content.contract.vk.WallTrackContract;
import by.deniotokiari.lastfmmusicplay.content.json.vk.WallTrack;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractListFragment;

public class WallTracksFragment extends AbstractListFragment {

	private static final Uri uri = WallTrackContract.URI_WALL_TRACKS;
	private static final String[] jsonKeys = { WallTrack.ROOT,
			WallTrack.ROOT_ITEMS, WallTrack.KEY_TYPE, "audio" };
	private static int count = 100;

	public WallTracksFragment() {
		super(jsonKeys, uri, null, null, null);
	}

	@Override
	protected String url() {
		return VkAPI.wallGet(count, getOffset());
	}

	@Override
	protected int changeOffset(int itemsCount) {
		if (itemsCount < 0) {
			return 0;
		}
		return getOffset() + count + 1;
	}

	@Override
	protected AbstractCursorAdapter adapter() {
		return new TrackAdapter(getActivity());
	}

}
