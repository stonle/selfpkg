/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: HttpBase 
* @Description: 
* @author huanglei
* @date 2017年9月8日 下午4:12:59 
* @version V1.0    
*/
public class HttpBase {
	private int code;
	private int errorCode;
	private String  msg;
	private Object data;
	private String randStr;
	private String sign;
	private long timestamp;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getRandStr() {
		return randStr;
	}
	public void setRandStr(String randStr) {
		this.randStr = randStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
