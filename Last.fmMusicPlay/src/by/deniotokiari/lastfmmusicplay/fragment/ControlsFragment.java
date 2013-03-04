package by.deniotokiari.lastfmmusicplay.fragment;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.fragment.main.page.NowPlayingFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ControlsFragment extends Fragment implements OnClickListener {

	private LinearLayout mControlsGroup;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.view_controls, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mControlsGroup = (LinearLayout) getActivity().findViewById(
				R.id.lnl_controls);
		mControlsGroup.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.lnl_controls:
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.activity_main, new NowPlayingFragment());
			// TODO impl custom animation
			transaction.addToBackStack(null);
			transaction.commit();
			break;
		}
	}

}
