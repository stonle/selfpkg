/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: LoginData 
* @Description: 
* @author huanglei
* @date 2017年9月8日 下午5:11:46 
* @version V1.0    
*/
public class LoginData {
	//电话号码
	private String phoneNum;
	//寄件码
	private String  psw;
	private String terminalNum;
	public String getTerminalNum() {
		return terminalNum;
	}
	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	@Override
	public String toString() {
		return "LoginData [terminalNum=" + terminalNum + ", phoneNum=" + phoneNum + ", psw=" + psw + "]";
	}
	
}
