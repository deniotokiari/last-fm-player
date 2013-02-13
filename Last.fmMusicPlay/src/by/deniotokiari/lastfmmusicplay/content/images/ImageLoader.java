package by.deniotokiari.lastfmmusicplay.content.images;

import java.util.Stack;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import by.deniotokiari.lastfmmusicplay.ContextHolder;
import by.deniotokiari.lastfmmusicplay.R;
import by.deniotokiari.lastfmmusicplay.http.HttpManager;

public class ImageLoader {

	private static ImageLoader instance;
	private static final int RES_DEFAULT_ALBUM = R.drawable.default_album;

	public static ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}
	
	private class ImageInfo {
		
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
	private Stack<ImageInfo> mQueue;
	private Loader mLoader;

	private ImageLoader() {
		int cacheSize = 4 * 1024 * 1024 / 8;
		mCache = new ImageCache(ContextHolder.getInstance().getContext(),
				cacheSize);
		mHandler = new Handler();
		mQueue = new Stack<ImageInfo>();
		mQueue.setSize(5);
		mLoader = new Loader();
	}

	public void bind(ImageView imageView, String url) {
		bind(null, imageView, url);
	}
	
	public void bind(BaseAdapter adapter, ImageView imageView, String url) {
		Bitmap bitmap = mCache.getImage(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageResource(RES_DEFAULT_ALBUM);
			synchronized (mQueue) {
				mQueue.push(new ImageInfo(url, imageView, adapter));
				mQueue.notifyAll();
			}
			if (mLoader.getState() == Thread.State.NEW) {
				mLoader.start();
			} 
		}

	}
	
	public void stopLoaderThread() {
		mLoader.interrupt();
	}
	
	private class Loader extends Thread {
		
		@Override
		public void run() {
			while(true) {
				if (mQueue.size() == 0) {
					synchronized (mQueue) {
						try {
							mQueue.wait();
						} catch (InterruptedException e) {

						}
					}
				}
				if (mQueue.size() != 0) {
					ImageInfo imageInfo;
					synchronized (mQueue) {
						imageInfo = mQueue.pop();
					}
					if (imageInfo != null) {
						Bitmap bitmap = getBitmapFromURL(imageInfo.url);
						if (bitmap != null) {
							mCache.putImage(imageInfo.url, bitmap);
							mHandler.post(new Displayer(imageInfo.adapter, bitmap,
									imageInfo.imageView));
						}
					}
				}
				if (Thread.interrupted()) {
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
			} else {
				imageView.setImageResource(RES_DEFAULT_ALBUM);
			}
		}
		
	}

	private Bitmap getBitmapFromURL(String url) {
		Log.d("LOG", url);
		if (url.length() == 0) {
			return null;
		}
		return HttpManager.getInstance().loadAsBitmap(url);
	}

}