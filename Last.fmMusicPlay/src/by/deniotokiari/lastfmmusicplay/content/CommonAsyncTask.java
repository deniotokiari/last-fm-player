package by.deniotokiari.lastfmmusicplay.content;

import by.deniotokiari.lastfmmusicplay.http.HttpManager;
import by.deniotokiari.lastfmmusicplay.http.HttpManager.TYPE;
import android.os.AsyncTask;

public abstract class CommonAsyncTask<T> extends AsyncTask<String, Void, T> {

	private Callback<T> mCallback;
	private Exception e;
	private String[] mParams;
	private String mUri;
	private String mId;
	private TYPE mType;

	protected abstract T process(String source) throws Exception;

	public CommonAsyncTask(Callback<T> callback, TYPE type,String... params) {
		mCallback = callback;
		mParams = params;
		mType = type;
		try {
			mUri = params[1];
		} catch (Exception e) {
			mUri = null;
		}
		try {
			mId = params[2];
		} catch (Exception e) {
			
		}
	}

	@Override
	protected T doInBackground(String... params) {
		try {
			String result = HttpManager.getInstance().loadAsString(params[0], mType);
			return process(result);
		} catch (Exception e) {
			e.printStackTrace();
			this.e = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		if (e != null) {
			mCallback.onError(e, mId);
		} else {
			mCallback.onSuccess(result, mUri, mId);
		}
	}

	public void start() {
		execute(mParams);
	}
}