package com.ldroid.kwei.common.net;

import android.text.TextUtils;

import com.ldroid.kwei.MainApp;
import com.ldroid.kwei.common.lib.volley.Cache;
import com.ldroid.kwei.common.lib.volley.Request;
import com.ldroid.kwei.common.lib.volley.RequestQueue;
import com.ldroid.kwei.common.lib.volley.VolleyLog;
import com.ldroid.kwei.common.lib.volley.toolbox.ClearCacheRequest;
import com.ldroid.kwei.common.lib.volley.toolbox.Volley;

public class NetRequestQueue {

	public static final String TAG = "NetRequestQueue";

	private static class Holder {
		private static NetRequestQueue sEngine = new NetRequestQueue();
	}

	public static NetRequestQueue getInstance() {
		return Holder.sEngine;
	}

	private NetRequestQueue() {
	}

	private RequestQueue mRequestQueue;

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(MainApp.getContext());
		}

		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		VolleyLog.d("Adding request to queue: %s", req.getUrl());
		// req.setShouldCache(false);
		getRequestQueue().add(req);
	}
	
	
	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		// req.setShouldCache(false);
		// req.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1.0f));
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	public void clearCache(Runnable r) {
		Cache cache = getRequestQueue().getCache();
		ClearCacheRequest cReq = new ClearCacheRequest(cache, r);
		addToRequestQueue(cReq);
	}

}
