package com.ldroid.kwei.common.lib.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.ldroid.kwei.BuildConfig;
import com.ldroid.kwei.common.lib.volley.toolbox.ImageLoader;
import com.ldroid.kwei.common.util.FileUtils;

/**
 * 
 * @ClassName: VolleyImageCache
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: Owen
 * @date: 2013-11-25 上午9:08:28
 * 
 */
public class VolleyImageCache implements ImageLoader.ImageCache {

	private LruCache<String, Bitmap> mMemCache;

	private DiskLruCache mDiskCache;

	private CompressFormat mCompressFormat = CompressFormat.JPEG;

	private static int IO_BUFFER_SIZE = 8 * 1024;

	private int mCompressQuality = 70;

	private static final int APP_VERSION = 1;

	private static final int VALUE_COUNT = 1;

	private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;
	private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;

	public static final String DISK_CACHE = "ea_cache";

	public VolleyImageCache() {
		initMemCache(getDefaultLruCacheSize());
		initDiskCache(DISK_CACHE, DISK_IMAGECACHE_SIZE, DISK_IMAGECACHE_COMPRESS_FORMAT,
				DISK_IMAGECACHE_QUALITY);
	}

	private void initDiskCache(String uniqueName, int diskCacheSize, CompressFormat compressFormat,
			int quality) {
		try {
			final File diskCacheDir = FileUtils.getDiskCacheDir(uniqueName);
			mDiskCache = DiskLruCache.open(diskCacheDir, APP_VERSION, VALUE_COUNT, diskCacheSize);
			mCompressFormat = compressFormat;
			mCompressQuality = quality;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initMemCache(int maxSize) {
		mMemCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}

		};
	}

	public static int getDefaultLruCacheSize() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;

		return cacheSize;
	}

	private boolean writeBitmapToFile(Bitmap bitmap, DiskLruCache.Editor editor)
			throws IOException, FileNotFoundException {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(editor.newOutputStream(0), IO_BUFFER_SIZE);
			return bitmap.compress(mCompressFormat, mCompressQuality, out);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@Override
	public void putBitmap(String key, Bitmap data) {
		key = createKey(key);

		// mem
		mMemCache.put(key, data);
		// disk
		DiskLruCache.Editor editor = null;
		try {
			editor = mDiskCache.edit(key);
			if (editor == null) {
				return;
			}

			if (writeBitmapToFile(data, editor)) {
				mDiskCache.flush();
				editor.commit();
				if (BuildConfig.DEBUG) {
				}
			} else {
				editor.abort();
				if (BuildConfig.DEBUG) {
				}
			}
		} catch (IOException e) {
			if (BuildConfig.DEBUG) {
			}
			try {
				if (editor != null) {
					editor.abort();
				}
			} catch (IOException ignored) {
			}
		}

	}

	@Override
	public Bitmap getBitmap(String key) {

		key = createKey(key);

		Bitmap bitmap = null;

		bitmap = mMemCache.get(key);

		if (bitmap != null) {
			return bitmap;
		}

		DiskLruCache.Snapshot snapshot = null;
		try {

			snapshot = mDiskCache.get(key);
			if (snapshot == null) {
				return null;
			}
			final InputStream in = snapshot.getInputStream(0);
			if (in != null) {
				final BufferedInputStream buffIn = new BufferedInputStream(in, IO_BUFFER_SIZE);
				bitmap = BitmapFactory.decodeStream(buffIn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (snapshot != null) {
				snapshot.close();
			}
		}

		if (BuildConfig.DEBUG) {
		}

		return bitmap;

	}

	public boolean containsKey(String key) {

		boolean contained = false;
		DiskLruCache.Snapshot snapshot = null;
		try {
			snapshot = mDiskCache.get(key);
			contained = snapshot != null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (snapshot != null) {
				snapshot.close();
			}
		}

		return contained;

	}

	public void clearCache() {
		if (BuildConfig.DEBUG) {
		}
		try {
			mDiskCache.delete();
		} catch (IOException e) {
			e.printStackTrace() ;
		}
	}

	/**
	 * 清除缓存
	 */
	public void evictAll() {
		if (mMemCache != null) {
			mMemCache.evictAll();
		}
	}

	public File getCacheFolder() {
		return mDiskCache.getDirectory();
	}

	/**
	 * Creates a unique cache key based on a url value
	 * 
	 * @param url
	 *            url to be used in key creation
	 * @return cache key value
	 */
	private String createKey(String url) {
		return String.valueOf(url.hashCode());
	}

}
