package com.ldroid.kwei.common.ui.observer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

public class SmsContentObserver extends ContentObserver {

	public static final String SMS_URI_INBOX = "content://sms/inbox";

	private Activity activity = null;
	private String smsContent = "";
	private EditText verifyText = null;

	public SmsContentObserver(Activity activity, Handler handler, EditText verifyText) {
		super(handler);
		this.activity = activity;
		this.verifyText = verifyText;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		try {

			Cursor cursor = null;// 光标
			// 读取收件箱中指定号码的短信
			cursor = activity.managedQuery(Uri.parse(SMS_URI_INBOX),
					new String[] { "address", "body", "person" }, null, null,
					"date desc");
			if (cursor != null) {// 如果短信为未读模式
				cursor.moveToFirst();
				if (cursor.moveToFirst()) {
					String smsbody = cursor.getString(cursor
							.getColumnIndex("body"));
					if (smsbody.contains("宇商网和乐网") && smsbody.contains("验证码")) {
						String regEx = "[0-9]{6}";
						Pattern p = Pattern.compile(regEx);
						Matcher m = p.matcher(smsbody.toString());
						String res = "";
						if (m.find()) {
							res = m.group().substring(0, 6);
						}
						smsContent = res.trim().toString();
						verifyText.setText(smsContent);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
