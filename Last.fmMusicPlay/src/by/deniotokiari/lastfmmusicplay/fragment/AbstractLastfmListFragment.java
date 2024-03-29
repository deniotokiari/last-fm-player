package by.deniotokiari.lastfmmusicplay.fragment;

import android.net.Uri;

abstract public class AbstractLastfmListFragment extends AbstractListFragment {

	private int ITEMS_PER_PAGE;

	public AbstractLastfmListFragment(String[] jsonKeys, int itemsPerPage,
			Uri uri, String selection, String[] selectionArgs, String sortOrder) {
		super(jsonKeys, uri, selection, selectionArgs, sortOrder);
		ITEMS_PER_PAGE = itemsPerPage;
	}

	@Override
	protected int changeOffset(int itemsCount) {
		if (itemsCount % ITEMS_PER_PAGE != 0) {
			setEndOfData(true);
		}
		if (getOffset() == 0) {
			return itemsCount / ITEMS_PER_PAGE;
		}
		return getOffset() + 1;
	}

}
