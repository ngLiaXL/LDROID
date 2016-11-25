package com.ldroid.kwei.common.net;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.ldroid.kwei.common.entities.InputEntity;
import com.ldroid.kwei.common.lib.gson.JsonSyntaxException;
import com.ldroid.kwei.common.lib.volley.AuthFailureError;
import com.ldroid.kwei.common.lib.volley.NetworkResponse;
import com.ldroid.kwei.common.lib.volley.ParseError;
import com.ldroid.kwei.common.lib.volley.Request;
import com.ldroid.kwei.common.lib.volley.Response;
import com.ldroid.kwei.common.lib.volley.Response.ErrorListener;
import com.ldroid.kwei.common.lib.volley.Response.Listener;
import com.ldroid.kwei.common.lib.volley.toolbox.HttpHeaderParser;
import com.ldroid.kwei.common.trace.DebugLog;
import com.ldroid.kwei.common.util.JsonUtils;

public class GsonRequest<T> extends Request<T> {

	private Listener<T> mListener;
	private Type mTypeOfT;
	private InputEntity mIn;

	/**
	 * 
	 * @param method
	 *            {@link Method}
	 * @param url
	 * @param listener
	 * @param errorListener
	 */
	public GsonRequest(int method, String url, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
	}

	public GsonRequest(String url, Listener<T> listener, ErrorListener errorListener) {
		this(Method.POST, url, listener, errorListener);
	}

	public <In extends InputEntity> GsonRequest(String url, Listener<T> listener,
			ErrorListener errorListener, In in) {
		this(Method.POST, url, listener, errorListener);
		this.mIn = in;
	}

	public void setTypeOfT(Type typeOfT) {
		this.mTypeOfT = typeOfT;
	}

	@Override
	public Map<String, String> getParams() throws AuthFailureError {
		return this.mIn.getParams();
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			T parsedGSON = JsonUtils.fromJson(jsonString, mTypeOfT);
			DebugLog.d(String.format("[%1$s]", jsonString));
			return Response.success(parsedGSON, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException je) {
			return Response.error(new ParseError(je));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept-Encoding", "gzip");
		return headers;
	}

}