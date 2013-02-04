package by.deniotokiari.lastfmmusicplay.fragment.main.page;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.LibraryAdapter;
import by.deniotokiari.lastfmmusicplay.fragment.lastfm.LastfmPagerFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class LibraryFragment extends ListFragment {

	public static final int PAGE_NUM = 0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new LibraryAdapter(getActivity(), getResources()
				.getStringArray(R.array.library_sections)));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
		case 0:
			FragmentTransaction transaction = getParentFragment()
					.getFragmentManager().beginTransaction();
			transaction.replace(R.id.content, new LastfmPagerFragment());
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}

}
