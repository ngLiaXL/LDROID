package com.ldroid.kwei.common.lib.cache;

import com.ldroid.kwei.common.lib.volley.RequestQueue;
import com.ldroid.kwei.common.lib.volley.toolbox.ImageLoader;
import com.ldroid.kwei.common.net.NetManager;

/**
 * 
 * @ClassName: ImageDownloader
 * @Description:volley缓存
 * @author: Owen Liang
 * @date: 2014-1-14 下午8:06:44
 * 
 */
public class ImageDownloader {

	private static ImageDownloader mInstance;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private VolleyImageCache mVolleyImageCache;

	private ImageDownloader() {
		mRequestQueue = NetManager.getInstance().getRequestQueue();
		mVolleyImageCache = new VolleyImageCache();
		mImageLoader = new ImageLoader(mRequestQueue, mVolleyImageCache);
	}

	public static ImageDownloader getInstance() {
		if (mInstance == null) {
			mInstance = new ImageDownloader();
		}
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		return this.mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		return this.mImageLoader;
	}

	public void clearMemCache() {
		if (mVolleyImageCache != null) {
			mVolleyImageCache.evictAll();
		}
	}
}