package com.ldroid.kwei.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ldroid.kwei.MainApp;

public class ConfigDao {

	private static ConfigDao sInstance;
	private final String SETTING_INFOS = "xx";
	private final SharedPreferences mSharePref;
	private final Editor mEditor;

	public static ConfigDao getInstance() {
		if (sInstance == null) {
			synchronized (ConfigDao.class) {
				if (sInstance == null) {
					sInstance = new ConfigDao(MainApp.getContext());
				}
			}
		}
		return sInstance;
	}

	private ConfigDao(Context context) {
		mSharePref = context.getSharedPreferences(SETTING_INFOS, Context.MODE_PRIVATE);
		mEditor = mSharePref.edit();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////////////////////////////

	public void setMemberId(long l) {
		mEditor.putLong("member_id", l).commit();
	}

	public long getMemberId() {
		return mSharePref.getLong("member_id", -1);
	}

}
