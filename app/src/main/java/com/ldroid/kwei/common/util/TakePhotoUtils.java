package com.ldroid.kwei.common.util;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class TakePhotoUtils {
	
	
	public static String getFilePathFromUri(Uri shownUri, Activity activity) {
		String imgPath = null;
		if (shownUri != null) {
			if (isFileImageUri(shownUri)) {
				String uriStr = shownUri.toString();
				try {
					imgPath = uriStr.substring(uriStr.indexOf("file://") + "file://".length());
				} catch (Exception e) {
					// TODO: handle exception
				}
				return imgPath;
			}
			String[] proj = { MediaStore.Images.Media.DATA };

			try {
				Cursor cursor = activity.getContentResolver().query(shownUri, proj, null, null,
						null);
				if (cursor != null && cursor.moveToFirst()) {
					int actual_image_column_index = cursor
							.getColumnIndex(MediaStore.Images.Media.DATA);
					imgPath = cursor.getString(actual_image_column_index);
				}
				cursor.close();
			} catch (Exception e) {
			}
		}
		return imgPath;
	}

	private static boolean isFileImageUri(Uri shownUri) {
		if (shownUri == null) {
			return false;
		}
		String uriStr = shownUri.toString().toLowerCase();
		if (uriStr.startsWith("file://")) {
			if (isImageFormat(uriStr)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isImageFormat(String fileName) {
		if (fileName == null) {
			return false;
		}
		fileName = fileName.toLowerCase();
		if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")
				|| fileName.endsWith(".bmp") || fileName.endsWith(".jpe")
				|| fileName.endsWith(".gif") || fileName.endsWith(".wbmp")) {
			return true;
		}
		return false;
	}

}
