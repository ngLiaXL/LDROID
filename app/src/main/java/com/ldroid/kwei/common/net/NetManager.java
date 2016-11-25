package com.ldroid.kwei.common.net;

import java.util.Map;

import com.ldroid.kwei.common.lib.volley.Request;
import com.ldroid.kwei.common.lib.volley.RequestQueue;
import com.ldroid.kwei.common.trace.DebugLog;

public class NetManager {

	public static final String TAG = "NetManager";

	private NetRequestQueue mRequestQueue;

	private static class NetManagerHolder {
		private static NetManager sEngine = new NetManager();
	}

	public static NetManager getInstance() {
		return NetManagerHolder.sEngine;
	}

	private NetManager() {
		mRequestQueue = NetRequestQueue.getInstance();

	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		if (mRequestQueue != null) {
			mRequestQueue.addToRequestQueue(req, tag);
		}
		// for log
		if (DebugLog.isDebuggable()) {
			try {
				GsonRequest<T> cReq = (GsonRequest<T>) req;
				DebugLog.d(cReq.getUrl() + " params="
								+ ((Map<String, String>) cReq.getParams()).toString());
			} catch (Exception e) {
				e.printStackTrace() ;
			}
		}
	}

	public <T> void addToRequestQueue(Request<T> req) {
		if (mRequestQueue != null) {
			mRequestQueue.addToRequestQueue(req);
		}
		// for log
		if (DebugLog.isDebuggable()) {
			try {
				GsonRequest<T> cReq = (GsonRequest<T>) req;
				DebugLog.d(cReq.getUrl() + " params="
								+ ((Map<String, String>) cReq.getParams()).toString());
			} catch (Exception e) {
			}
		}
	}

	public void clearCache(Runnable r) {
		if (mRequestQueue != null) {
			mRequestQueue.clearCache(r);
		}
	}

	public RequestQueue getRequestQueue() {
		return mRequestQueue.getRequestQueue();
	}
}
