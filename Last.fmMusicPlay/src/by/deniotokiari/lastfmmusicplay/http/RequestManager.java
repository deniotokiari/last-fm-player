package by.deniotokiari.lastfmmusicplay.http;

import by.deniotokiari.lastfmmusicplay.content.Callback;
import by.deniotokiari.lastfmmusicplay.content.CommonAsyncTask;

public class RequestManager {

	private static RequestManager instance;

	private RequestManager() {

	}

	public static RequestManager getInstance() {
		if (instance == null) {
			instance = new RequestManager();
		}
		return instance;
	}

	public void get(Callback<Object> callback, String url) {
		(new CommonAsyncTask<Object>(callback, url) {

			@Override
			public Object process(String source) throws Exception {
				if (source == null || source.length() == 0) {
					return null;
				}
				return source;
			}
		}).start();
	}

}