package by.deniotokiari.lastfmmusicplay.service;

import java.util.List;

import by.deniotokiari.lastfmmusicplay.content.Callback;
import by.deniotokiari.lastfmmusicplay.content.ContentRequestBuilder;
import by.deniotokiari.lastfmmusicplay.content.json.CommonJsonAsyncTask;
import by.deniotokiari.lastfmmusicplay.content.provider.AbstractProvider;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class GetDataService extends Service implements Callback<List<String>> {

	public static final String EXTRA_KEY_MESSAGE = "message";
	public static final String ACTION_ON_ERROR = "by.deniotokiari.lastfmmusicplay.service.ACTION_ON_ERROR";
	public static final String ACTION_ON_SUCCESS = "by.deniotokiari.lastfmmusicplay.service.ACTION_ON_SUCCESS";
	public static final String CONTENT_REQUEST_BUILDER = "content_request_builder";
	public static final String ERROR_MSG = "No result";

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		handleCommand(intent);
		return START_STICKY;
	}

	@Override
	public void onSuccess(final List<String> t, final Object... objects) {
		final String id = (String) objects[1];
		if (t == null) {
			this.onError(new Throwable(ERROR_MSG), id);
			return;
		}
		if (t.size() == 0) {
			this.onError(new Throwable(), id);
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				Uri uri = Uri.parse((String) objects[0]);
				for (String string : t) {
					ContentValues values = new ContentValues();
					values.put(AbstractProvider.KEY_DATA, string);
					getContentResolver().insert(uri, values);
				}
				getContentResolver().notifyChange(uri, null);
				LocalBroadcastManager.getInstance(getApplicationContext())
						.sendBroadcastSync(new Intent(ACTION_ON_SUCCESS + id));
			}
		}).start();
	}

	@Override
	public void onError(Throwable e, Object... objects) {
		String id = (String) objects[0];
		Intent intent = new Intent(ACTION_ON_ERROR + id);
		intent.putExtra(EXTRA_KEY_MESSAGE, e.getMessage());
		LocalBroadcastManager.getInstance(getApplicationContext())
				.sendBroadcast(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void handleCommand(Intent intent) {
		if (intent == null) {
			return;
		}
		ContentRequestBuilder builder = (ContentRequestBuilder) intent
				.getSerializableExtra(CONTENT_REQUEST_BUILDER);
		if (builder == null) {
			return;
		}
		String[] keys = builder.getKeys();
		String url = builder.getUrl();
		String uri = builder.getUri();
		String id = builder.getId();
		new CommonJsonAsyncTask(this, keys, url, uri, id).start();
	}

}
