package com.ls.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author cjw 2017-05-04
 * 日期时间工具类
 */
public class DateTimeUtil {
	// 生成时间戳字符串
	public static String getTimestamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 生成当前格式化日期
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormatDate() {
		Date date = new Date();// 实例化日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期
		return df.format(date);
	}

	/**
	 * 生成当前格式化日期的指定N分钟后的
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormatDateAfterNow(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		Date afterDate = new Date(now.getTime() + 60000 * n);
		return sdf.format(afterDate);
	}

	/***
	 * 转换日期格式 yyyy-MM-dd HH:mm:ss --->yyyy/M/d HH:mm:ss
	 */
	public static String convertFormatDate(String dateline) {
		if (dateline == null) {
			return "";
		}
		if (dateline.equals("")) {
			return dateline;
		}
		//dateline = dateline.replace("-", "/");
		try {
			/*SimpleDateFormat df = new SimpleDateFormat("yyyy/M/d HH:mm:ss");// 格式化日期*/	        
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dt = df.parse(dateline);
			return df.format(dt);
		} catch (Exception e) {
			return dateline;
		}
	}

	public static String getMidFormatDate() {
		Date date = new Date();// 实例化日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");// 格式化日期
		return df.format(date);
	}

	/**
	 * 生成当前格式化日期 // MM-dd HH:mm
	 * 
	 * @return MM-dd HH:mm
	 */
	public static String getShortFormatDate() {
		Date date = new Date();// 实例化日期
		SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");// 格式化日期
		return df.format(date);
	}

	/**
	 * 生成当前格式化日期 // yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @return yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static String getLongFormatDate() {
		Date date = new Date();// 实例化日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");// 格式化日期
		return df.format(date);
	}

	/**
	 * 生成当前日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getCurrenDate() {
		Date date = new Date();// 实例化日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期
		return df.format(date);
	}

	/**
	 * 生成当前月份
	 * 
	 * @return HH
	 */
	public static String getCurrentMonth() {
		Date date = new Date();// 实例化日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");// 格式化日期
		return df.format(date);
	}

	/**
	 * 生成当前小时
	 * 
	 * @return HH
	 */
	public static String getCurrentHour() {
		Date date = new Date();// 实例化日期
		SimpleDateFormat df = new SimpleDateFormat("HH");// 格式化日期
		return df.format(date);
	}

	/**
	 * 获取昨天日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getYerterdayDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return yesterday;
	}

	/**
	 * 根据月份获取天数
	 * 
	 * @return yyyy-MM-dd
	 */
	public static int getDayCountByMonth(String month) {
		try {
			if (month == null) {
				return 31;
			}
			if (month.equals("")) {
				return 31;
			}
			if (month.length() < 7) {
				return 31;
			}
			if (month.length() > 7) {
				month = month.substring(0, 7);
			}
			if (month.split("-").length != 2) {
				return 31;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Calendar calendar = new GregorianCalendar();
			Date date1 = sdf.parse(month);
			calendar.setTime(date1);
			return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			return 31;
		}

	}

	/**
	 * 获取X分钟前的日期时间
	 * 
	 * @param mm
	 *            int 分钟数
	 * @return String Lyyyy-MM-dd HH:mm:ss
	 **/
	public static String getBeforeDataTime(int mm) {
		Calendar calendar = Calendar.getInstance();
		/* HOUR_OF_DAY 指示一天中的小时 */
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - mm);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*
		 * System.out.println(mm+"分钟前的时间：" + df.format(calendar.getTime()));
		 * System.out.println("当前的时间：" + df.format(new Date()));
		 */
		return df.format(calendar.getTime());
	}

	/**
	 * 比较日期大小
	 * 
	 * @dateline :yyyy-MM-dd
	 * @return int 参数日期在当前日期之前return 负数(-天数差)，之后返回正数(天数差)
	 */
	public static int compareDate(String dateline) {
		if (dateline == "") {
			return 0;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期
		try {
			Date dt = df.parse(dateline);
			Date now = df.parse(getCurrenDate());
			return dt.compareTo(now);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 比较日期大小
	 * 
	 * @dateline :yyyy-MM-dd
	 * @return int 参数日期在当前日期之前return 负数(-天数差)，之后返回正数(天数差)
	 */
	public static int compareDate(String dateline1, String dateline2) {
		if (dateline1 == "" || dateline2 == "") {
			return 0;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期
		try {
			Date dt1 = df.parse(dateline1);
			Date dt2 = df.parse(dateline2);
			return dt1.compareTo(dt2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * 生成00:30:00.000时间随机
	 */
	public static String randomTime() {
		String last = ":00.000";
		String hh = "0" + String.valueOf((int) (1 + Math.random() * 3));
		String mm = "";
		int m = (int) (1 + Math.random() * 50);
		if (m < 10) {
			mm = "0" + String.valueOf(m);
		} else {
			mm = String.valueOf(m);
		}

		return hh + ":" + mm + last;
	}

	/**
	 * 计算日期时间差
	 */
	public static long getTimeDifference(String dateTime) {
		if (dateTime == null || dateTime.equals("0") || dateTime.length() < 19) {
			return -1;
		}
		try {
			Date date = new Date();// 实例化日期
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date nowDate = df.parse(String.valueOf(df.format(date)));
			Date delay = df.parse(dateTime);// 要计算的日期

			long l = nowDate.getTime() - delay.getTime(); // 比较时间差
			long day = l / (24 * 60 * 60 * 1000);
			if (day <= 7) {
				long hour = (l / (60 * 60 * 1000) - day * 24);
				long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
				return min >= 0 ? min + (day * 1440) + (hour * 60) : -1;
			} else {
				return 10080;
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}

	}
   //计算两时间差多少分钟
	public static long getTimeDifference(String date1,String dete2){
		if(StringUtil.isNotEmpty(date1) && StringUtil.isNotEmpty(dete2)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				long time = (df.parse(dete2).getTime()-df.parse(date1).getTime())/(60*1000);
				return time;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		return 1000l;
	}
	
	public static void main(String[] args) {
		System.out.println(getTimeDifference("2014-01-01 10:10:00","2014-01-01 10:13:00"));
	}
}