package by.deniotokiari.lastfmmusicplay.fragment;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.content.ContentRequestBuilder;
import by.deniotokiari.lastfmmusicplay.playlist.PlaylistManager;
import by.deniotokiari.lastfmmusicplay.service.GetDataService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

abstract public class AbstractListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private String id;

	public static int FOOTER_RES = R.layout.view_footer;
	private static int LOADER_ID = 1;
	private int itemsCount;
	private int offset;

	protected boolean isLoading;
	protected boolean isEndOfData;
	protected boolean isError;

	private AbstractCursorAdapter mAdapter;
	private View mFooterView;
	private BroadcastReceiver mReceiver;
	private IntentFilter mFilter;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			hideFooter();
		}

	};

	/** Sql args to query **/
	private Uri uri;
	private String selection;
	private String[] selectionArgs;
	private String sortOrder;

	/** Json keys for parser **/
	private String[] jsonKeys;

	abstract protected String url();

	abstract protected int changeOffset(int itemsCount);

	abstract protected AbstractCursorAdapter adapter();

	public AbstractListFragment(String[] jsonKeys, Uri uri, String selection,
			String[] selectionArgs, String sortOrder) {
		this.jsonKeys = jsonKeys;
		this.uri = uri;
		this.selection = selection;
		this.selectionArgs = selectionArgs;
		this.sortOrder = sortOrder;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isLoading = false;
		isEndOfData = false;
		isError = false;
		itemsCount = 0;
		offset = 0;
		mFooterView = getLayoutInflater(savedInstanceState).inflate(FOOTER_RES,
				null, false);
		getListView().addFooterView(mFooterView);
		hideFooter();
		mAdapter = adapter();
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		this.getLoaderManager().initLoader(LOADER_ID++, null, this);
		setOnScrollListener();
	}

	@Override
	public void onResume() {
		super.onResume();
		id = String.valueOf(hashCode());
		mFilter = new IntentFilter();
		mFilter.addAction(GetDataService.ACTION_ON_ERROR + id);
		mFilter.addAction(GetDataService.ACTION_ON_SUCCESS + id);
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(GetDataService.ACTION_ON_ERROR + id)) {
					String message = intent
							.getStringExtra(GetDataService.EXTRA_KEY_MESSAGE);
					if (message != null
							&& message.equals(GetDataService.ERROR_MSG)) {
						isEndOfData = true;
						setListAdapter(mAdapter);
						getListView().setVisibility(View.INVISIBLE);
					}
					if (isError) {
						// TODO dlg error imp and isEndOfData
						setEndOfData(true);
					}
					// hideFooter();
					mHandler.sendEmptyMessage(0);
					isLoading = false;
					isError = true;
					Log.d("LOG", "INVOKED ERROR #" + id);
				} else if (action.equals(GetDataService.ACTION_ON_SUCCESS + id)) {
					// hideFooter();
					mHandler.sendEmptyMessage(0);
					Log.d("LOG", "INVOKED SUCCESS #" + id);
					isLoading = false;
				}
			}

		};
		LocalBroadcastManager.getInstance(this.getActivity()).registerReceiver(
				mReceiver, mFilter);
	}

	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this.getActivity())
				.unregisterReceiver(mReceiver);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this.getActivity(), uri, null, selection,
				selectionArgs, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		itemsCount = cursor.getCount();
		offset = changeOffset(itemsCount);
		if (getListAdapter() == null && cursor.getCount() != 0) {
			setListAdapter(mAdapter);
			mAdapter.swapCursor(cursor);

		} else if (cursor.getCount() != 0) {
			mAdapter.swapCursor(cursor);
		}
		if (cursor.getCount() == 0 && !isEndOfData) {
			load();
		}
		if (isEndOfData) {
			// TODO imp end of data
			try {
				getListView().removeFooterView(mFooterView);
			} catch (Exception e) {

			}
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		PlaylistManager.getInstance().setPlaylist(position, uri, selection, selectionArgs, sortOrder);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	private void setOnScrollListener() {
		getListView().setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (isNeedLoadingNextData(firstVisibleItem, visibleItemCount,
						totalItemCount)) {
					load();
				}
			}

		});
	}

	private boolean isNeedLoadingNextData(int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		boolean lastItem = firstVisibleItem + visibleItemCount == totalItemCount
				&& getListView().getChildAt(visibleItemCount - 1) != null
				&& getListView().getChildAt(visibleItemCount - 1).getBottom() <= getListView()
						.getHeight();
		return !isEndOfData && lastItem && !isLoading;
	}

	private void showFooter() {
		mFooterView.setVisibility(View.VISIBLE);
	}

	private void hideFooter() {
		mFooterView.setVisibility(View.GONE);
	}

	protected int getItemsCount() {
		return itemsCount;
	}

	protected void setEndOfData(boolean flag) {
		isEndOfData = flag;
	}

	protected int getOffset() {
		return offset;
	}

	protected void load() {
		isLoading = true;
		showFooter();
		ContentRequestBuilder builder = new ContentRequestBuilder();
		builder.setUrl(url()).setKeys(jsonKeys).setUri(uri).setId(id);
		Intent intent = new Intent(getActivity(), GetDataService.class);
		intent.putExtra(GetDataService.CONTENT_REQUEST_BUILDER, builder);
		getActivity().startService(intent);
	}

}
