package by.deniotokiari.lastfmmusicplay.content.images;

import java.util.Stack;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import by.deniotokiari.lastfmmusicplay.ContextHolder;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.http.HttpManager;

public class ImageLoader {

	private static ImageLoader instance;
	private static final int RES_DEFAULT_ALBUM = R.drawable.default_album;
	private static final int RES_DEFAULT_ARTIST = R.drawable.default_artist;

	public static ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}

	public class ImageInfo {

		public String url;
		public ImageView imageView;
		public BaseAdapter adapter;

		public ImageInfo(String url, ImageView imageView, BaseAdapter adapter) {
			this.url = url;
			this.imageView = imageView;
			this.adapter = adapter;
		}

	}

	private ImageCache mCache;
	private Handler mHandler;
	private Queue mQueue;

	private ImageLoader() {
		int cacheSize = 4 * 1024 * 1024 / 8;
		mCache = new ImageCache(ContextHolder.getInstance().getContext(),
				cacheSize);
		mHandler = new Handler();
		mQueue = new Queue();

	}

	public void bind(ImageView imageView, String url, int res) {
		bind(null, imageView, url, res);
	}

	public void bind(BaseAdapter adapter, ImageView imageView, String url,
			int res) {
		Bitmap bitmap = mCache.getImage(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			if (res == 0) {
				imageView.setImageResource(RES_DEFAULT_ALBUM);
			} else if (res == 1) {
				imageView.setImageResource(RES_DEFAULT_ARTIST);
			}
			mQueue.push(new ImageInfo(url, imageView, adapter));
			if (mQueue.getState() == Thread.State.NEW) {
				mQueue.start();
			}
		}

	}

	public void stopQueueThread() {
		mQueue.interrupt();
	}

	private class Queue extends Thread {

		private Stack<ImageInfo> queue;

		public Queue() {
			queue = new Stack<ImageInfo>();
			queue.setSize(5);
		}

		public void push(ImageInfo imageInfo) {
			synchronized (queue) {
				queue.push(imageInfo);
				queue.notifyAll();
			}
		}

		@Override
		public void run() {
			while (true) {
				if (queue.size() == 0) {
					synchronized (queue) {
						try {
							queue.wait();
						} catch (InterruptedException e) {

						}
					}
				}
				if (queue.size() != 0) {
					ImageInfo imageInfo;
					synchronized (queue) {
						imageInfo = queue.pop();
					}
					if (imageInfo != null) {
						Bitmap bitmap = getBitmapFromURL(imageInfo.url);
						if (bitmap != null) {
							mCache.putImage(imageInfo.url, bitmap);
							mHandler.post(new Displayer(imageInfo.adapter,
									bitmap, imageInfo.imageView));
						}
					}
				}
				if (interrupted()) {
					break;
				}
			}
		}

	}

	private class Displayer implements Runnable {

		private BaseAdapter adapter;
		private Bitmap bitmap;
		private ImageView imageView;

		public Displayer(BaseAdapter adapter, Bitmap bitmap, ImageView imageView) {
			this.adapter = adapter;
			this.bitmap = bitmap;
			this.imageView = imageView;
		}

		@Override
		public void run() {
			if (bitmap != null) {
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				} else {
					imageView.setImageBitmap(bitmap);
				}
			}
		}

	}

	private Bitmap getBitmapFromURL(String url) {
		if (url.length() == 0) {
			return null;
		}
		return HttpManager.getInstance().loadAsBitmap(url);
	}
	
	public void clearCache() {
		mCache.clear();
	}

}