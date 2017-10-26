package com.ls.util;

import java.io.UnsupportedEncodingException;

public class EncodingUtil {
	/**
	 * 处理字符串编码为UTF-8，防止中文乱码
	 */
	public static String getUtf8String(String param) {
		String resultStr = "";
		try {
			if (param != null) {
				resultStr = new String(param.getBytes("ISO-8859-1"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			resultStr = "";
		}
		return resultStr;

	}

	/**
	 * 汉字转换Unicode编码
	 * */
	public static String gbEncoding(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}
}
