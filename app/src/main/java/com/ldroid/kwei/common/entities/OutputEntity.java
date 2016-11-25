package com.ldroid.kwei.common.entities;

import java.util.ArrayList;

import android.text.TextUtils;

import com.ldroid.kwei.common.lib.gson.annotations.Expose;

public  class OutputEntity<ReList,Info> extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8297238643328522856L;

	@Expose
	public String code;
	@Expose
	public String message;
	@Expose
	public ArrayList<ReList> reList;
	@Expose
	public Info info ;

	public String getErrorMsg() {
		return TextUtils.isEmpty(message) ? "网络或服务器异常" : message;
	}

}