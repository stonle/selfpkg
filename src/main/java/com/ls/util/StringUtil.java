package com.ls.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author cjw 2013-06-04
 * 
 */
public class StringUtil {

	/**
	 * 过滤字符串特殊字符
	 */
	public static String filterString(String str) throws PatternSyntaxException {
		if (str == null || str.equals("")) {
			return "";
		}
		// 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*<>+=|{}'\"￥‘”“’，、\f\t\n\r\\s]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
    
	/**
	 * 字符串为空判断
	 * @param str
	 * @return
	 */
	public static Boolean isNotEmpty(String str){
	    if(str ==null || "".equals(str))
	    	return false;
		return true;
	}
	  /** 
     * 通用判断 
     * @param telNum 
     * @return 
     */  
    public static boolean isMobiPhoneNum(String telNum){  
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";  
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(telNum);  
        return m.matches();  
    }  
}
