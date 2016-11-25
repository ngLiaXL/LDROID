package com.ldroid.kwei.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.content.Context;

public class TimeUtils {
	

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");

    
	private TimeUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

    
    /**
     * long time to string
     * 
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }
    
	public static DatePickerDialog getDateDialog(Context context,
			DatePickerDialog.OnDateSetListener l) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		Date myDate = new Date();
		calendar.setTime(myDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog dlg = new DatePickerDialog(context, l, year, month, day);
		return dlg;
	}
	
	/**
	 * 几天前
	 * @param day
	 * @return
	 */
	public static String getDaysAgo(int day) {
		return DATE_FORMAT_DATE.format(new Date(getCurrentTimeInLong() + day * 24 * 60 * 60 * 1000));
	}
	
	/**
	 * 月份开始日期
	 * @param month
	 * @return
	 */
	public static String getMonthAgoFirstDay(int month) {
		return getYearMonthFirstDay(Calendar.getInstance().get(Calendar.YEAR), Calendar
				.getInstance().get(Calendar.MONTH)  + month + 1);
	}
	/**
	 * 月份结束日期
	 * @param month
	 * @return
	 */
	public static String getMonthAgoEndDay(int month) {
		return getYearMonthEndDay(Calendar.getInstance().get(Calendar.YEAR), Calendar
				.getInstance().get(Calendar.MONTH) + month + 1);
	}
	
	
	
	
	
	
	
	public static String getYearFirstDay() {
		return getYearMonthFirstDay(Calendar.getInstance().get(Calendar.YEAR),1);
	}

	public static String getYearEndDay() {
		return getYearMonthFirstDay(Calendar.getInstance().get(Calendar.YEAR),12);
	}
	/**
	 * 月份开始时间
	 * 
	 * @param month
	 * @return
	 */
	public static String getYearMonthFirstDay(int year, int month) {
		String currentYear = Integer.toString(year);
		String paramMonth = Integer.toString(month);
		if (month < 10) {
			paramMonth = "0" + month;
		}
		Date date = null;
		try {
			date = DATE_FORMAT_DATE.parse(currentYear + "-" + paramMonth + "-" + "01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			return DATE_FORMAT_DATE.format(date);
		}
		return null;
	}
	
	
	/**
	 * 月份结束日期
	 * 
	 * @param monthNum
	 * @return
	 */
	public static String getYearMonthEndDay(int year,int monthNum) {
		String tempYear = Integer.toString(year);
		String tempMonth = Integer.toString(monthNum);
		String tempDay = "31";
		if (tempMonth.equals("1") || tempMonth.equals("3") || tempMonth.equals("5")
				|| tempMonth.equals("7") || tempMonth.equals("8") || tempMonth.equals("10")
				|| tempMonth.equals("12")) {
			tempDay = "31";
		}
		if (tempMonth.equals("4") || tempMonth.equals("6") || tempMonth.equals("9")
				|| tempMonth.equals("11")) {
			tempDay = "30";
		}
		if (tempMonth.equals("2")) {
			if (isLeapYear(year)) {
				tempDay = "29";
			} else {
				tempDay = "28";
			}
		}
		Date date = null;
		try {
			date = DATE_FORMAT_DATE.parse(tempYear + "-" + tempMonth + "-" + tempDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			return DATE_FORMAT_DATE.format(date);
		}
		return null;
		
	}
	
	
	
	/*****************************************
	 * @功能 判断某年是否为闰年
	 * @return boolean
	 * @throws ParseException
	 ****************************************/
	public static boolean isLeapYear(int yearNum) {
		boolean isLeep = false;
		/** 判断是否为闰年，赋值给一标识符flag */
		if ((yearNum % 4 == 0) && (yearNum % 100 != 0)) {
			isLeep = true;
		} else if (yearNum % 400 == 0) {
			isLeep = true;
		} else {
			isLeep = false;
		}
		return isLeep;
	}
}
