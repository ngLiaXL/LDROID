package com.ldroid.kwei.common.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ldroid.kwei.common.net.AppAssembly;
import com.ldroid.kwei.common.util.JsonUtils;

public abstract class InputEntity {

	protected ArrayList<String> errors = new ArrayList<String>();

	protected String method;

	public void setMethod(String method) {
		this.method = method;
	}

	public Boolean checkInput() {
		return true;
	}

	public ArrayList<String> getErrors() {
		return this.errors;
	}

	public Map<String, String> getParams() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("data", jsonThis());
		return params;
	}
	
	public String jsonThis() {
		return JsonUtils.toJson(this);
	}

	public String getUrlPrefix() {
		return AppAssembly.getUrl();
	}

	public String getUrl() {
		return new StringBuffer().append(getUrlPrefix()).append(method).toString();
	}

}