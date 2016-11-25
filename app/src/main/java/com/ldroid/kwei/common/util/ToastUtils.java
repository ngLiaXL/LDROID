package com.ldroid.kwei.common.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastUtils {

	private static long sLastShowTime = -1;

	private static void showToast(final Context context, final int textid, final String text,
			final int delay, final boolean allowToastQueue) {
		long currentTime = System.currentTimeMillis();
		if (!allowToastQueue && currentTime - sLastShowTime < 3000)
			return;
		else {
			sLastShowTime = currentTime;
		}
		if (context == null)
			return;
		new Handler(context.getMainLooper()).post(new Runnable() {

			@Override
			public void run() {
				if (textid == -1) {
					Toast.makeText(context, text, delay).show();
				} else {
					Toast.makeText(context, textid, delay).show();
				}
			}
		});
	}

	public static void showShortToast(final Context context, final int textid) {
		showToast(context, textid, null, Toast.LENGTH_SHORT, false);
	}

	public static void showShortToast(final Context context, final String text) {
		showToast(context, -1, text, Toast.LENGTH_SHORT, false);
	}

	public static void showLongToast(final Context context, final int textid) {
		showToast(context, textid, null, Toast.LENGTH_LONG, false);

	}

	public static void showLongToast(final Context context, final String text) {
		showToast(context, -1, text, Toast.LENGTH_LONG, false);
	}

	/**
	 * 
	 * @param context
	 * @param textid
	 * @param allowToastQueue
	 *            是否允许Toast等待显示, 如果不允许, 3秒内的第二条Toast将不被显示
	 */
	public static void showShortToast(final Context context, final int textid,
			boolean allowToastQueue) {
		showToast(context, textid, null, Toast.LENGTH_SHORT, allowToastQueue);
	}

	/**
	 * 
	 * @param context
	 * @param text
	 * @param allowToastQueue
	 *            是否允许Toast等待显示, 如果不允许, 3秒内的第二条Toast将不被显示
	 */
	public static void showShortToast(final Context context, final String text,
			boolean allowToastQueue) {
		showToast(context, -1, text, Toast.LENGTH_SHORT, allowToastQueue);
	}

	/**
	 * 
	 * @param context
	 * @param textid
	 * @param allowToastQueue
	 *            是否允许Toast等待显示, 如果不允许, 3秒内的第二条Toast将不被显示
	 */
	public static void showLongToast(final Context context, final int textid,
			boolean allowToastQueue) {
		showToast(context, textid, null, Toast.LENGTH_LONG, allowToastQueue);

	}

	/**
	 * 
	 * @param context
	 * @param text
	 * @param allowToastQueue
	 *            是否允许Toast等待显示, 如果不允许, 3秒内的第二条Toast将不被显示
	 */
	public static void showLongToast(final Context context, final String text,
			boolean allowToastQueue) {
		showToast(context, -1, text, Toast.LENGTH_LONG, allowToastQueue);
	}
}
