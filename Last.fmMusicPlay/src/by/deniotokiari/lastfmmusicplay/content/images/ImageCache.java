package by.deniotokiari.lastfmmusicplay.content.images;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import by.deniotokiari.lastfmmusicplay.utilities.Md5;

public class ImageCache extends LruCache<String, Bitmap> {

	private String cacheDir;

	public ImageCache(Context context, int cacheSize) {
		super(cacheSize);
		this.cacheDir = context.getCacheDir() + "/images";
		new File(this.cacheDir).mkdir();
	}

	public Bitmap getImage(String key) {
		Bitmap bitmap = get(Md5.md5(key));
		if (bitmap != null) {
			return bitmap;
		}
		File image = new File(cacheDir + "/" + Md5.md5(key));
		if (image.exists()) {
			bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
			if (bitmap == null) {
				return null;
			}
			put(key, bitmap);
			return bitmap;
		}
		return null;
	}

	public Bitmap putImage(String key, Bitmap bitmap) {
		File image = new File(cacheDir + "/" + Md5.md5(key));
		if (image.exists()) {
			if (get(Md5.md5(key)) != null) {
				return bitmap;
			}
			return put(Md5.md5(key), bitmap);
		}
		try {
			image.createNewFile();
			FileOutputStream stream = new FileOutputStream(image);
			bitmap.compress(CompressFormat.PNG, 100, stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return put(Md5.md5(key), bitmap);
	}

	synchronized public void clear() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				File file = new File(cacheDir);
				File[] files = file.listFiles();
				if (files == null) {
					return;
				}
				for (File f : files) {
					f.delete();
				}
				clear();

			}

		}).start();
	}

}
