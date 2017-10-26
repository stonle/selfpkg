/**   
* 
*/
package com.ls.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.StringUtils;

import com.ls.http.HttpBase;
import com.ls.http.HttpConstant;

import net.sf.json.JSONObject;

/**
 * @ClassName: Utils
 * @Description:
 * @author huanglei
 * @date 2017年9月2日 上午11:56:58
 * @version V1.0
 */
@SuppressWarnings("deprecation")
public class Utils {

	/**
	 * 读取文件内容
	 */
	public static String readFile(InputStream inputStream, String charsetName) throws Exception {
		try (InputStream inData = new BufferedInputStream(inputStream)) {
			ByteArrayOutputStream outData = new ByteArrayOutputStream();
			byte[] buffer = new byte[10240];
			for (int numRead; (numRead = inData.read(buffer)) > 0;) {
				outData.write(buffer, 0, numRead);
			}
			return outData.toString(charsetName);
		}
	}

	/**
	 * 读取文件内容
	 */
	public static String readFile(String file, String charsetName) throws Exception {
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			return readFile(fileInputStream, charsetName);
		}
	}

	/**
	 * 读取文件内容
	 */
	public static byte[] readFile(String file) throws Exception {
		try (InputStream inData = new BufferedInputStream(new FileInputStream(file))) {
			ByteArrayOutputStream outData = new ByteArrayOutputStream();
			byte[] buffer = new byte[10240];
			for (int numRead; (numRead = inData.read(buffer)) > 0;) {
				outData.write(buffer, 0, numRead);
			}
			return outData.toByteArray();
		}
	}

	/**
	 * 从左边开始计数，取指定长度字符串
	 */
	public static String left(String str, int length) {
		if (StringUtils.hasText(str)) {
			if (length < str.length()) {
				str = str.substring(length, str.length());
			}
		}
		return str;
	}

	/**
	 * 将空格替换为&nbsp;
	 */
	public static String space2nbsp(String str) {
		if (StringUtils.hasText(str)) {
			str = str.replace(" ", "&nbsp");
		}
		return str;
	}

	/**
	 * 将换行符替换为"＜ｂｒ　／＞"
	 */
	public static String crlf2br(String str) {
		if (StringUtils.hasText(str)) {
			str = str.replace("\r\n", "<br />");
			str = str.replace("\n", "<br />");
		}
		return str;
	}

	/**
	 * 获取指定长度字符串
	 */
	public static String len(String str, int length) {
		if (StringUtils.hasText(str)) {
			if (str.length() > length) {
				str = str.substring(0, length) + "...";
			}
		}
		return str;
	}

	/**
	 * HTML编码
	 */
	public static String htmlEncode(String str) {
		return StringEscapeUtils.escapeHtml4(str);
	}

	/**
	 * HTML解码
	 */
	public static String htmlDecode(String str) {
		return StringEscapeUtils.unescapeHtml4(str);
	}

	/**
	 * URL编码
	 */
	public static String urlEncode(String str) {
		String ret = "";
		try {
			ret = URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {

			System.out.println("url encode failed:" + str);	
			}	
		
		return ret;
	}

	/**
	 * URL解码
	 */
	public static String urlDecode(String str) throws Exception {
		return URLDecoder.decode(str, "UTF-8");
	}

	/**
	 * 获取MD5加密字符串
	 * @throws Exception 
	 */
	public static String getMD5(String _str) throws Exception {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] strTemp = _str.getBytes("UTF-8");
		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(strTemp);
		byte[] md = mdTemp.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}

	/**
	 * 计算文件Hash值
	 */
	public static String fileHash(String fileName) throws Exception {
		return fileHash(new FileInputStream(fileName));
	}

	/**
	 * 计算文件Hash值
	 */
	public static String fileHash(File file) throws Exception {
		return fileHash(new FileInputStream(file));
	}

	/**
	 * 计算文件Hash值
	 */
	public static String fileHash(InputStream is) throws Exception {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		InputStream fis = new BufferedInputStream(is);
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] buffer = new byte[10240];
		for (int numRead = 0; (numRead = fis.read(buffer)) > 0;) {
			md5.update(buffer, 0, numRead);
		}
		byte[] md = md5.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		fis.close();
		return new String(str).toUpperCase();
	}

	/**
	 * 获取随机字符串
	 */
	public static String getRandomString(int length) {
		String radStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder generateRandStr = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			int randNum = rand.nextInt(radStr.length() - 1);
			generateRandStr.append(radStr.substring(randNum, randNum + 1));
		}
		return generateRandStr.toString();
	}

	/**
	 * 获取随机数字
	 */
	public static String getRandomNum(int length) {
		String radStr = "0123456789";
		StringBuilder generateRandStr = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			int randNum = rand.nextInt(radStr.length() - 1);
			generateRandStr.append(radStr.substring(randNum, randNum + 1));
		}
		return generateRandStr.toString();
	}

	/**
	 * 根据一定范围获取随机数字
	 */
	public static long getRandomByRange(long min, long max) {
		return Math.round(Math.random() * (max - min) + min);
	}

	/**
	 * 将字节转换成带有单位显示的字符串
	 */
	public static String byte2String(long number) {

		DecimalFormat df = new DecimalFormat("###.##");

		float f;
		String value;

		if (number < 1024L * 1024L) {
			f = (float) ((float) number / (float) 1024L);
			value = ((df.format(new Float(f).doubleValue()) + " KB"));
		} else if (number < 1024L * 1024L * 1024L) {
			f = (float) ((float) number / (float) (1024L * 1024L));
			value = ((df.format(new Float(f).doubleValue()) + " MB"));
		} else if (number < 1024L * 1024L * 1024L * 1024L) {
			f = (float) ((float) number / (float) (1024L * 1024L * 1024L));
			value = ((df.format(new Float(f).doubleValue()) + " GB"));
		} else {
			f = (float) ((float) number / (float) (1024L * 1024L * 1024L * 1024L));
			value = ((df.format(new Float(f).doubleValue()) + " TB"));
		}

		return value;
	}

	/**
	 * IP转换成纯数字表示
	 */
	public static long ip2long(String ipaddress) {

		long[] ip = new long[4];

		// 先找到IP地址字符串中.的位置
		int position1 = ipaddress.indexOf(".");
		int position2 = ipaddress.indexOf(".", position1 + 1);
		int position3 = ipaddress.indexOf(".", position2 + 1);

		// 将每个.之间的字符串转换成整形
		ip[0] = Long.parseLong(ipaddress.substring(0, position1));
		ip[1] = Long.parseLong(ipaddress.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(ipaddress.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(ipaddress.substring(position3 + 1));

		long returnDate = (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];

		return returnDate;
	}

	/**
	 * 纯数字转换成IP地址表示
	 */
	public static String long2ip(long ipaddress) {

		StringBuilder sb = new StringBuilder("");

		// 直接右移24位
		sb.append(String.valueOf((ipaddress >>> 24)));
		sb.append(".");

		// 降高8位置0，然后右移16位
		sb.append(String.valueOf((ipaddress & 0x00FFFFFF) >>> 16));
		sb.append(".");

		// 将高16位置0，然后右移8位
		sb.append(String.valueOf((ipaddress & 0x0000FFFF) >>> 8));
		sb.append(".");

		// 将高24位置0
		sb.append(String.valueOf((ipaddress & 0x000000FF)));

		return sb.toString();
	}

	/**
	 * 获取当前时间
	 *
	 * @return 返回格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	/**
	 * 字符串转为时间（毫秒值）yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static long getDateLongByString(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long startTime;
		try {
			startTime = sdf.parse(str).getTime();
			return startTime;
		} catch (ParseException e) {

			// e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 字符串转为时间（毫秒值）yyyy-MM-dd
	 *
	 * @return
	 */
	public static long getDateLongByStrYMD(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long startTime;
		try {
			startTime = sdf.parse(str).getTime();
			return startTime;
		} catch (ParseException e) {

			// e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 当前月的第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfMonth() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return sdf.format(cal.getTime());
	}

	/**
	 * 下一个月的第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfNextMonth() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return sdf.format(cal.getTime());
	}

	/**
	 * 当前月的第一天
	 * 
	 * @return
	 */
	public static String getLastDayOfMonth() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return sdf.format(cal.getTime());
	}

	/**
	 * 指定月的第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return sdf.format(cal.getTime());
	}

	/**
	 * 指定月的最后一天
	 * 
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return sdf.format(cal.getTime());
	}

	/**
	 * 当前年、指定月的第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfMonth(int month) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return sdf.format(cal.getTime());
	}

	/**
	 * 当前年、指定月的最后倚天
	 * 
	 * @return
	 */
	public static String getLastDayOfMonth(int month) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return sdf.format(cal.getTime());
	}

	/**
	 * 日期字符串从一个格式转为另外一个格式的日期字符串 如果转化失败返回原始的字符串
	 *
	 * @param strDate
	 *            转换前的日期
	 * @param fromFt
	 *            转换前格式
	 * @param toFt
	 *            转换后格式
	 * @return 转换后的日期
	 */
	public static String convertDate(String strDate, String fromFt, String toFt) {
		try {
			SimpleDateFormat sdfFrom = new SimpleDateFormat(fromFt);
			SimpleDateFormat sdfTo = new SimpleDateFormat(toFt);
			return sdfTo.format(sdfFrom.parse(strDate));
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return strDate;
	}

	/**
	 * 获取当前日期
	 *
	 * @return 返回格式：yyyy-MM-dd
	 */
	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}

	/**
	 * 获取当前日期多少天之后的日期时间
	 * 
	 * @param days
	 * @return
	 */
	public static String getCurrentDateAssignDays(int days) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Date dt = calendar.getTime();
		calendar.setTime(dt);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取当前日期多少小时之后的日期时间
	 * 
	 * @param hours
	 * @return
	 */
	public static String getCurrentDateAssignHours(int hours) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Date dt = calendar.getTime();
		calendar.setTime(dt);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hours);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取指定日期时间多少天之前或多少天之后的日期时间
	 * 
	 * @param timeStr
	 * @param days
	 * @return
	 */
	public static String getDateTimeAssignDays(String timeStr, int days) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Date dt;
		try {
			dt = formatter.parse(timeStr);
		} catch (ParseException e) {
			dt = calendar.getTime();
		}
		calendar.setTime(dt);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取指定日期时间多少天之前或多少天之后的日期时间
	 * 
	 * @param timeStr
	 * @param days
	 * @return
	 */
	public static String getDateAssignDays(String timeStr, int days) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date dt;
		try {
			dt = formatter.parse(timeStr);
		} catch (ParseException e) {
			dt = calendar.getTime();
		}
		calendar.setTime(dt);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取指定月份后的日期
	 * 
	 * @param mounths
	 * @return
	 */
	public static String getDateAssignMonths(int mounths) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + mounths);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取指定年后的日期时间
	 * 
	 * @param years
	 * @return
	 */
	public static String getDateAssignYears(int years) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + years);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取当前日期
	 *
	 * @param day
	 *            以当天日期进行增加或减少
	 * @return 返回格式：yyyy-MM-dd
	 */
	public static String getCurrentDate(int day) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取时间戳（精确到日期）
	 *
	 * @return 返回格式：yyyyMMdd
	 */
	public static String getDatestamp() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(date);
	}

	/**
	 * 获取时间戳（精确到毫秒）
	 *
	 * @return 返回格式：yyyyMMddHHmmssSSS
	 */
	public static String getTimestamp() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return formatter.format(date);
	}

	/**
	 * 字符串转换为List
	 *
	 * @param str
	 *            传入字符串，格式：{3},{4},{5}
	 */
	public static List<String> string2List(String str) {
		List<String> list = new ArrayList<>();
		if (StringUtils.hasText(str)) {
			String _str[] = str.split(",");
			for (int i = 0; i < _str.length; i++) {
				String s = _str[i].replace("{", "").replace("}", "");
				list.add(s);
			}
		}
		return list;
	}

	/**
	 * List转换为字符串
	 *
	 * @param list
	 * @return 返回格式：{3},{4},{5}
	 */
	public static String list2String(List<String> list) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String s : list) {
			stringBuilder.append(",{");
			stringBuilder.append(s);
			stringBuilder.append("}");
		}
		if (stringBuilder.length() > 0) {
			stringBuilder.deleteCharAt(0).toString();
		}
		return stringBuilder.toString();
	}

	/**
	 * 字符串转换为Map
	 *
	 * @param s
	 *            传入字符串，格式 ：xxxx:200;yyy:500;Status:ok
	 */
	public static Map<String, String> string2Map(String s) {
		Map<String, String> result = new LinkedHashMap<>();
		if (StringUtils.hasText(s)) {
			String _a1[] = s.split(";");
			for (String b : _a1) {
				String _b1[] = b.split(":");
				if (_b1.length == 2) {
					result.put(_b1[0], _b1[1]);
				}
			}
		}
		return result;
	}

	/**
	 * QueryString转换为Map
	 *
	 * @param s
	 *            传入字符串，格式 ："xxxx＝200＆yyy＝500＆Status＝ok"
	 */
	public static Map<String, String> queryString2Map(String s) {
		Map<String, String> result = new HashMap<>();
		String _a1[] = s.split("&");
		for (String b : _a1) {
			String _b1[] = b.split("=");
			if (_b1.length == 2) {
				result.put(_b1[0], _b1[1]);
			}
		}
		return result;
	}

	/**
	 * 复制文件
	 */
	public static void copyFile(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		int BUFFER_SIZE = 16 * 1024;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len;
			while ((len = in.read(buffer, 0, BUFFER_SIZE)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Throwable t) {
			throw new RuntimeException(t.getMessage(), t);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (Throwable t) {
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (Throwable t) {
				}
			}
		}
	}

	/**
	 * 获取文件扩展名
	 */
	public static String getFileExtName(String fileName) {
		String ret = "";
		if (StringUtils.hasText(fileName)) {
			fileName = fileName.toLowerCase();
			int dot = fileName.lastIndexOf('.');
			if (dot != -1) {
				ret = fileName.substring(dot);
			}
		}
		return ret;
	}

	/**
	 * 去掉HTML标签
	 */
	public static String removeHtmlTag(String html) {
		html = html.replace("&nbsp;", "");
		html = html.replace("&amp;", "");
		html = html.replace("&lt;", "");
		html = html.replace("&gt;", "");
		html = html.replace("&mdash;", "");
		html = html.replace("&deg;", "");
		html = html.replace("&ldquo;", "");
		html = html.replace("&rdquo;", "");
		html = html.replace("&middot;", "");
		html = html.replace("&lsquo;", "");
		html = html.replace("&rsquo;", "");
		html = html.replace("&hellip;", "");
		return html.replaceAll("<[^>]*>", "");
	}

	/**
	 * 转换文本域为HTML
	 */
	public static String convertText2Html(String html) {
		html = htmlEncode(html);
		html = html.replace("\n", "<br>");
		html = html.replace("\r\n", "<br>");
		html = html.replace(" ", "&nbsp;");
		return html;
	}

	/**
	 * 将long行的秒数转化成分钟显示，如："90 ＝＝＞＞ 01:30"；
	 *
	 * @param sec
	 *            需要转换的长度
	 * @return 按照格式转换后的值
	 */
	public static String convertSec2Minutes(long sec) {
		String out = "00:00";
		SimpleDateFormat format1 = new SimpleDateFormat("ss");
		SimpleDateFormat format2 = new SimpleDateFormat("mm:ss");

		if (sec >= 12 * 3600) {
			return out;
		}
		if (sec >= 3600 && sec < 12 * 3600) {
			format2 = new SimpleDateFormat("hh:mm:ss");
		}

		try {
			out = format2.format(format1.parse(String.valueOf(sec)));
		} catch (ParseException e) {
			return out;
		}

		return out;
	}

	public static String dateAddDay(String date, int days) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(df.parse(date));
			calendar.add(Calendar.DAY_OF_MONTH, days);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return df.format(calendar.getTime());
	}

	public static String secToDayHourMinSec(long time) {
		long sec = time;
		long secRemainder = sec % 60;
		long min = time / 60;
		long minRemainder = min % 60;
		long hour = min / 60;
		long hourRemainder = hour % 24;
		long day = hour / 24;
		return day + "天" + hourRemainder + "时" + minRemainder + "分" + secRemainder + "秒";
	}

	/**
	 * 获取UNIX时间戳（UTC时间）
	 */
	public static long getUTCUnixTime() {

		// 取得本地时间
		final Calendar cal = Calendar.getInstance();

		// 取得时间偏移量
		final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

		// 取得夏令时差
		final int dstOffset = cal.get(Calendar.DST_OFFSET);

		// 从本地时间里扣除这些差量，即可以取得UTC时间
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		return cal.getTimeInMillis();
	}

	/**
	 * 获取UNIX时间戳（UTC时间）,并在原来的基础上加上day天数
	 */
	public static long getUTCUnixTime(int day) {

		// 取得本地时间
		final Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + day);
		// 取得时间偏移量
		final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

		// 取得夏令时差
		final int dstOffset = cal.get(Calendar.DST_OFFSET);

		// 从本地时间里扣除这些差量，即可以取得UTC时间
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		return cal.getTimeInMillis();
	}

	/**
	 * 获取UNIX时间戳（UTC时间）,并在原来的基础上加上day天数
	 */
	public static long getUTCUnixTimeSec(int seconds) {

		// 取得本地时间
		final Calendar cal = Calendar.getInstance();

		cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + seconds);
		// 取得时间偏移量
		final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

		// 取得夏令时差
		final int dstOffset = cal.get(Calendar.DST_OFFSET);

		// 从本地时间里扣除这些差量，即可以取得UTC时间
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		return cal.getTimeInMillis();
	}

	/**
	 * 获取指定日期的UTC时间
	 */
	public static long convert2UTCUnixTime(String date) {
		// 取得本地时间
		final Calendar cal = Calendar.getInstance();

		// 取得时间偏移量
		final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

		// 取得夏令时差
		final int dstOffset = cal.get(Calendar.DST_OFFSET);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date dateTime = null;
		try {
			dateTime = sdf.parse(date);
		} catch (ParseException e) {
			dateTime = new Date();
		}
		cal.setTime(dateTime);
		// 从本地时间里扣除这些差量，即可以取得UTC时间le
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTimeInMillis();
	}

	/**
	 * 把一个list转换成逗号分割的字符串，用于sql 中 in (?) 查询
	 */
	public static String list2String2(@SuppressWarnings("rawtypes") List list) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append(list.get(i) + ",");
				} else {
					sb.append(list.get(i));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 根据显示长度截取字符串（字符串中包括汉字，一个汉字等于两个字符）
	 *
	 * @param str
	 *            要截取的字符串
	 * @param limitLength
	 *            截取的长度
	 * @return 截取后的字符串
	 */
	public String disLen(String str, int limitLength) {
		String return_str = str;// 返回的字符串
		int temp_int = 0;// 将汉字转换成两个字符后的字符串长度
		int cut_int = 0;// 对原始字符串截取的长度
		byte[] b = str.getBytes();// 将字符串转换成字符数组

		for (int i = 0; i < b.length; i++) {
			if (b[i] >= 0) {
				temp_int = temp_int + 1;
			} else {
				temp_int = temp_int + 2;// 一个汉字等于两个字符
				i++;
			}
			cut_int++;

			if (temp_int >= limitLength) {
				if (temp_int % 2 != 0 && b[temp_int - 1] < 0) {
					cut_int--;
				}
				return_str = return_str.substring(0, cut_int) + "...";
				break;
			}
		}
		return return_str;
	}

	/**
	 * 获取年龄（X岁）
	 */
	public static int getAge(String dd) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());
		int nowYear = calendar.get(Calendar.YEAR);

		calendar.setTime(dateFormat.parse(dd));
		int year = calendar.get(Calendar.YEAR);

		return nowYear - year;
	}

	/**
	 * 根据业务代码和记录Id生成编号
	 */
	public static String genCode(String busCode, String key) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		Calendar calendar = Calendar.getInstance();
		String dateStr = dateFormat.format(calendar.getTime());
		StringBuilder result = new StringBuilder();
		result.append(busCode).append(dateStr.replace("-", "")).append(key);
		return result.toString();
	}

	public static String replace(String initStr, List<String> words) {
		StringBuilder message = new StringBuilder();
		if (StringUtils.hasText(initStr) && initStr.contains("[") && initStr.contains("]")) {
			message.append(initStr);
			for (String word : words) {
				int start = message.indexOf("[");
				int end = message.indexOf("]");
				message.replace(start, end + 1, word).toString();
			}

		}

		return message.toString();
	}

	public static String replace(String content, Map<String, Object> words) {
		if (content != null && StringUtils.hasText(content)) {
			for (Map.Entry<String, Object> entry : words.entrySet()) {
				if (content.contains(entry.getKey())) {
					Object value = entry.getValue();
					String strValue = value == null ? "" : value.toString();
					content = content.replace(entry.getKey(), strValue);
				}
			}
		}

		return content;
	}

	public static String getDate(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			date = new Date();
		}
		return formatter.format(date);
	}

	/**
	 * 返回处理后的数字形式的字符串，例如科学技术法的数字返回正常显示的数字形式，处理不成功则原样返回
	 * 
	 * @param str
	 * @return
	 */
	public static String numeric2String(String str) {
		DecimalFormat decimalFormat = new DecimalFormat("###");// 格式化设置
		try {
			BigDecimal value = new BigDecimal(str);
			return decimalFormat.format(value);
		} catch (NumberFormatException ex) {
		}

		return str;
	}
	
	
	
    
	/**
	 * 获取code码
	 * @param request
	 * @return
	 */
	public static Integer getCodeFromHttp(JSONObject obj) {
		return Integer.parseInt(obj.getString("code"));
	}
    
	/**
	 * 获取JSONObject
	 * @param request
	 * @return
	 */
	public static JSONObject getJSONObjectFromTer(HttpServletRequest request){
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonobject = JSONObject.fromObject(jb.toString());
		return jsonobject;
	}
	
	
	/**
	 * 鉴权；
	 * @return
	 */
	public static boolean authenticationSign(HttpBase httpBase){
		try {
			String str = httpBase.getRandStr()+httpBase.getTimestamp()+HttpConstant.SALT;
			if(httpBase.getSign().equals(getMD5(str))){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public static HttpBase getHttpDataForTer(HttpBase htttpBase,int code,int errorCode,String msg){
		htttpBase.setCode(code);
		htttpBase.setErrorCode(errorCode);
		htttpBase.setMsg(msg);
		String randomStr = getRandomString(8);
		long timestamp = System.currentTimeMillis()/1000;
		htttpBase.setRandStr(randomStr);
		htttpBase.setTimestamp(timestamp);
		try {
			htttpBase.setSign(getMD5(randomStr+timestamp+HttpConstant.SALT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htttpBase;
	}
	
	public static HttpBase respReturnError(HttpBase htttpBase){
		switch (htttpBase.getCode()) {
		case HttpConstant.REQ_CHECK_HERATBEAT:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_CHECK_HERATBEAT,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_LOGIN:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_LOGIN,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_AUTH:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_AUTH,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_PKG_CHEKC:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_PKG_CHEKC,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_PKG_WEIGH:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_PKG_WEIGH,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_PANYMENT:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_PANYMENT,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_CKECK_WEIGH:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_CKECK_WEIGH,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_PAY_CODE:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_PAY_CODE,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_QUERY_LIST:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_QUERY_LIST,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_GET_FIRST_VOLUME:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_GET_FIRST_VOLUME,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_PRO_UPDATE:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_PRO_UPDATE,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_UPDATE_CONFIG_FIELD:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_UPDATE_CONFIG_FIELD,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_UPDATE_RESULT:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_UPDATE_RESULT,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_RESTART:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_RESTART,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		case HttpConstant.REQ_POLLING_CHECK:
			return getHttpDataForTer(htttpBase,HttpConstant.RSP_POLLING_CHECK,HttpConstant.SIGN_AUTH_ERROR,HttpConstant.SIGN_AUTH_ERROR_MSG);
		default:			
			return htttpBase;
		}
	}
	public static void main(String[] args) throws Exception {
		//String str = "TUYmP89l"+1473314962+HttpConstant.SALT;
		//System.out.println(System.currentTimeMillis());
	}
}
