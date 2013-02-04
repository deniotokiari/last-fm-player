package by.deniotokiari.lastfmmusicplay.fragment.lastfm;

import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.fragment.AbstractListFragment;

abstract public class AbstractLastfmListFragment extends AbstractListFragment {

	private int ITEMS_PER_PAGE;

	public AbstractLastfmListFragment(String[] jsonKeys, int itemsPerPage,
			Uri uri, String selection, String[] selectionArgs, String sortOrder) {
		super(jsonKeys, uri, selection, selectionArgs, sortOrder);
		ITEMS_PER_PAGE = itemsPerPage;
	}

	@Override
	protected int changeOffset(int itemsCount) {
		if (getOffset() == 0) {
			return itemsCount / ITEMS_PER_PAGE;
		}
		return getOffset() + 1;
	}

}
