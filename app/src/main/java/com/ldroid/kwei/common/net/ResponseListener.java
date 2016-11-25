/**
 * 
 */
package com.ldroid.kwei.common.net;

import com.ldroid.kwei.common.lib.volley.VolleyError;

public interface ResponseListener<T> {

	void onResponse(T response);

	void onErrorResponse(VolleyError error);

}
