package by.deniotokiari.lastfmmusicplay.fragment;

import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.adapter.AbstractCursorAdapter;
import by.deniotokiari.lastfmmusicplay.content.ContentRequestBuilder;
import by.deniotokiari.lastfmmusicplay.playlist.PlaylistManager;
import by.deniotokiari.lastfmmusicplay.service.GetDataService;
import by.deniotokiari.lastfmmusicplay.service.MusicPlayService;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

abstract public class AbstractListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private String id;

	public static int FOOTER_RES = R.layout.view_footer;
	private int itemsCount = 0;
	private int offset = 0;

	protected boolean isLoading = false;
	protected boolean isEndOfData = false;
	protected boolean isError = false;

	protected MusicPlayService mService;
	private ServiceConnection mConnection;
	protected boolean isBound;

	protected AbstractCursorAdapter mAdapter;
	private View mFooterView;
	private BroadcastReceiver mReceiver;
	private IntentFilter mFilter;
	protected Handler mHandler;
	protected ProgressDialog mProgressDialog;

	/** Sql args to query **/
	private Uri uri;
	private String selection;
	protected String[] selectionArgs;
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
		id = String.valueOf(getClass().getSimpleName().hashCode());
		if (savedInstanceState != null) {
			isLoading = savedInstanceState.getBoolean(id);
		}
		mHandler = new Handler();
		id = String.valueOf(getClass().getSimpleName().hashCode());
		mFooterView = getLayoutInflater(savedInstanceState).inflate(FOOTER_RES,
				null, false);
		mProgressDialog = new ProgressDialog(getActivity());
		getListView().addFooterView(mFooterView);
		mHandler.post(hideFooter);
		mAdapter = adapter();
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		this.getLoaderManager().initLoader(Integer.valueOf(id), null, this);
		setOnScrollListener();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(id, isLoading);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();
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
						// TODO
						setEndOfData(true);
					}
					mHandler.post(hideFooter);
					isLoading = false;
					isError = true;
				} else if (action.equals(GetDataService.ACTION_ON_SUCCESS + id)) {
					mHandler.post(hideFooter);
					isLoading = false;
				}
			}

		};
		LocalBroadcastManager.getInstance(this.getActivity()).registerReceiver(
				mReceiver, mFilter);
		mConnection = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName componentName) {
				isBound = false;
			}

			@Override
			public void onServiceConnected(ComponentName componentName,
					IBinder binder) {
				mService = ((MusicPlayService.MyBinder) binder).getService();
				isBound = true;
			}

		};
		getActivity().bindService(
				new Intent(getActivity(), MusicPlayService.class), mConnection,
				0);
	}

	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this.getActivity())
				.unregisterReceiver(mReceiver);
		getActivity().unbindService(mConnection);
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
		if (itemsCount == 0) {
			offset = 0;
		}
		if (getListAdapter() == null && cursor.getCount() != 0) {
			setListAdapter(mAdapter);
			mAdapter.swapCursor(cursor);
			isLoading = false;

		} else if (cursor.getCount() != 0) {
			mAdapter.swapCursor(cursor);
		}
		if (cursor.getCount() == 0 && !isEndOfData && !isLoading) {
			load();
		}
		if (isEndOfData) {
			try {
				getListView().removeFooterView(mFooterView);
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, final int position, long id) {
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setMessage(getResources()
				.getString(R.string.processing));
		mProgressDialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				PlaylistManager.getInstance().setPlaylist(position, uri,
						selection, selectionArgs, sortOrder);
				Looper.prepare();
				mHandler.post(dismissProgressDialog);
				if (isBound) {
					mService.start();
				}
			}

		}).start();
		mAdapter.setCheked(position);
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

	private Runnable hideFooter = new Runnable() {

		@Override
		public void run() {
			mFooterView.setVisibility(View.GONE);
		}

	};

	protected Runnable dismissProgressDialog = new Runnable() {

		@Override
		public void run() {
			mProgressDialog.dismiss();
		}

	};

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
