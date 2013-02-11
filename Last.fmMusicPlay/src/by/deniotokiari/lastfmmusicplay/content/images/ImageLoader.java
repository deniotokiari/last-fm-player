package by.deniotokiari.lastfmmusicplay.content.images;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import by.deniotokiari.lastfmmusicplay.ContextHolder;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.http.HttpManager;

public class ImageLoader {

	private static ImageLoader instance;

	public static ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}

	private ImageCache mCache;
	private List<Callback> mQueue;

	public interface Callback {

		String getUrl();

		void success(Bitmap bm);

		void onError(Exception e);
	}

	private ImageLoader() {
		mQueue = Collections.synchronizedList(new ArrayList<Callback>());
		int cacheSize = 4 * 1024 * 1024 / 8;
		mCache = new ImageCache(ContextHolder.getInstance().getContext(),
				cacheSize);
	}

	public void bind(final ImageView imageView, final String url) {
		Bitmap bitmap = mCache.get(url);
/*		if (mCache.containsKey(url)) {
			bitmap = mStorage.get(url);
		}*/
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			return;
		} else {
			imageView.setImageResource(R.drawable.default_album);
			mQueue.add(0, new Callback() {

				@Override
				public void success(Bitmap bm) {
					imageView.setImageBitmap(bm);
				}

				@Override
				public String getUrl() {
					return url;
				}

				@Override
				public void onError(Exception e) {

				}

			});
		}
		proceed();
	}

	public void bind(final BaseAdapter adapter, final ImageView imageView,
			final String url) {
		Bitmap bitmap = mCache.get(url);
	/*	if (mStorage.containsKey(url)) {
			bitmap = mStorage.get(url);
		}*/
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			return;
		} else {
			imageView.setImageResource(R.drawable.default_album);
			mQueue.add(0, new Callback() {

				@Override
				public void success(Bitmap bm) {
					adapter.notifyDataSetChanged();
				}

				@Override
				public String getUrl() {
					return url;
				}

				@Override
				public void onError(Exception e) {

				}

			});
		}
		proceed();
	}

	private void proceed() {
		if (mQueue.isEmpty()) {
			return;
		}
		final Callback callback = mQueue.remove(0);
		new AsyncTask<Callback, Void, Object>() {

			@Override
			protected Object doInBackground(Callback... params) {
				String url = params[0].getUrl();
				Bitmap bitmap = mCache.getImage(url);
				if (bitmap != null) {
					return bitmap;
				} else {
					bitmap = getBitmapFromURL(url);
					if (bitmap == null) {
						return null;
					}
					mCache.putImage(url, bitmap);
					return bitmap;
				}
			}

			@Override
			protected void onPostExecute(Object result) {
				super.onPostExecute(result);
				if (result instanceof Bitmap) {
					Bitmap bitmap = (Bitmap) result;
					//mStorage.put(callback.getUrl(), bitmap);
					callback.success(bitmap);
				} else {
					callback.onError((Exception) result);
				}
			}

		}.execute(callback);
	}

	private Bitmap getBitmapFromURL(String url) {
		if (url.length() == 0) {
			return null;
		}
		return HttpManager.getInstance().loadAsBitmap(url);
	}

}