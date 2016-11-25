package com.ldroid.kwei.common.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.text.TextUtils;

import com.ldroid.kwei.MainApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ImageUtils
 * <ul>
 * convert between Bitmap, byte array, Drawable
 * <li>{@link #bitmapToByte(Bitmap)}</li>
 * <li>{@link #bitmapToDrawable(Bitmap)}</li>
 * <li>{@link #byteToBitmap(byte[])}</li>
 * <li>{@link #byteToDrawable(byte[])}</li>
 * <li>{@link #drawableToBitmap(Drawable)}</li>
 * <li>{@link #drawableToByte(Drawable)}</li>
 * </ul>
 * <ul>
 * </ul>
 * <ul>
 * scale image
 * <li>{@link #scaleImageTo(Bitmap, int, int)}</li>
 * <li>{@link #scaleImage(Bitmap, float, float)}</li>
 * </ul>
 */
public class ImageUtils {

    /**
     * convert Bitmap to byte array
     * 
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     * 
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     * 
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable)d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     * 
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     * 
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     * 
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }



    /**
     * scale image
     * 
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float)newWidth / org.getWidth(), (float)newHeight / org.getHeight());
    }

    /**
     * scale image
     * 
     * @param org
     * @param scaleWidth sacle of width
     * @param scaleHeight scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

    /**
     * close inputStream
     * 
     * @param s
     */
    private static void closeInputStream(InputStream s) {
        if (s == null) {
            return;
        }

        try {
            s.close();
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////////////
    
    /////////////////////////////////////////////////////////////////////////////////////
    
	public static Bitmap readBitmapFromAssets(InputStream is) {
		Bitmap bm = null;
		try {
			bm = BitmapFactory.decodeStream(is, null, getPurgeableBitmapOptions());
		} catch (OutOfMemoryError e) {
			System.gc();
		}
		return bm;
	}

	/**
	 * 从SD卡文件中读取Bitmap
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap readBitmapFromFile(String filePath) {
		try {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			return BitmapFactory.decodeFile(filePath, opt);
		} catch (Exception e) {
		}
		return null;
	}


	/**
	 * 计算缩放比
	 */
	public static int getRatioSize(int resourceWidth, int resourceHeight,int maxHeight) {
		int ratio = 1;
		if (resourceWidth > resourceHeight && resourceWidth > maxHeight) {
			ratio = resourceWidth / maxHeight;
		} else if (resourceWidth < resourceHeight && resourceHeight > maxHeight) {
			ratio = resourceHeight / maxHeight;
		}
		if (ratio <= 0)
			ratio = 1;
		return ratio;
	}

	/**
	 * 按大小压缩
	 * 
	 * @param bitmap
	 * @param limitByte
	 * @return
	 */
	public static Bitmap compressBitmap(Bitmap bitmap, int limitByte) {
		ByteArrayOutputStream baoStream = null;
		try {
			baoStream = new ByteArrayOutputStream();
			int options = 100;
			int fileLenght = baoStream.toByteArray().length;
			// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			while (fileLenght > limitByte) {
				options -= 10;
				baoStream.reset();
				bitmap.compress(Bitmap.CompressFormat.JPEG, options, baoStream);
			}
			if (baoStream.size() == 0) {
				return null;
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baoStream.toByteArray());
			return createPurgeableBitmap(isBm);
		} catch (Exception e) {
		} finally {
			if (baoStream != null) {
				try {
					baoStream.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 根据InputStream创建inPurgeable = true的Bitmap
	 * 
	 * @param is
	 *            输入流
	 * @return Bitmap
	 */
	public static Bitmap createPurgeableBitmap(InputStream is) {
		Bitmap bm = null;
		try {
			bm = BitmapFactory.decodeStream(is, null, getPurgeableBitmapOptions());
		} catch (OutOfMemoryError e) {
			System.gc();
		}
		return bm;
	}

	/**
	 * 创建Bitmap时，获得可被系统回收的Options
	 * 
	 * @return
	 */
	private static BitmapFactory.Options getPurgeableBitmapOptions() {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Config.RGB_565;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inTargetDensity = MainApp.getContext().getResources().getDisplayMetrics().densityDpi;
		return opts;
	}

	/**
	 * 取出位图，根据屏幕宽度来压缩
	 * 
	 * @param imgPath
	 * @return
	 */
	public static Bitmap readBitmapFromFile(String imgPath, int width) {
		if (TextUtils.isEmpty(imgPath)) {
			return null;
		}
		Bitmap bm = null;
		try {

			BitmapFactory.Options bounds = new BitmapFactory.Options();
			bounds.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imgPath, bounds);

			if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
				return null;
			bounds.inJustDecodeBounds = false;
			bounds.inPurgeable = true;
			int inSampleSize = computeSampleSize(bounds, -1, width * width);

			bounds.inSampleSize = inSampleSize;
			bm = BitmapFactory.decodeFile(imgPath, bounds);
			int degree = readPictureDegree(imgPath);
			if (bm != null) {
				bm = rotaingImageView(degree, bm);
			}
		} catch (OutOfMemoryError e) {
			System.gc();
		}

		return bm;
	}

	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
		}
		return degree;
	}

	/*
	 * 缩略图缩小比例计算函数
	 */
	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength,
			int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength,
			int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h
				/ maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 将Bitmap写到sd缓存中
	 * 
	 * @param bm
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static void writeBitmap2Cache(Bitmap bm, String path) {
		if (TextUtils.isEmpty(path) || bm == null) {
			return;
		}
		File myCaptureFile = new File(path);
		FileOutputStream bos = null;
		try {
			bos = new FileOutputStream(myCaptureFile);
			if (bos != null) {
				bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
				bos.close();
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
    
    
}
