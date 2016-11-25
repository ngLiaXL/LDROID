package com.ldroid.kwei.common.util;

import java.io.File;

public class ExtraPathUtils {

	public static final String FILE_DIR 		= "xxxx";
	
	private ExtraPathUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static String getAppExtraFilePath(String fileName, String parentName) {
		File appExtraRootFile = new File(getAppExtraRootDirectoryPath() + parentName);
		if (!appExtraRootFile.exists()) {
			if (!makeDir(appExtraRootFile)) {
				return null;
			}
		}
		return appExtraRootFile.getAbsolutePath() + File.separator + fileName;
	}

	public static boolean makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		return dir.mkdir();
	}

	public static String getAppExtraRootDirectoryPath() {
		if (SDCardUtils.getSDCardAllSize() > SizeUtils.MB_2_BYTE) {
			return SDCardUtils.getSDCardPath() + FILE_DIR + File.separator;
		}
		return SDCardUtils.getInternalPath() + FILE_DIR + File.separator;
	}

}
