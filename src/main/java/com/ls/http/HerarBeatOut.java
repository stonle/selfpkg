/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: HerarBeatOut 
* @Description: 
* @author huanglei
* @date 2017年9月16日 下午4:44:23 
* @version V1.0    
*/
public class HerarBeatOut {
	
	private String terminalNum;
	
	private int operType;
    // -1:默認值 0：成功 1：失敗
	private int wxPay;
	
	private String pkgId;
	public String getTerminalNum() {
		return terminalNum;
	}

	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}

	public int getOperType() {
		return operType;
	}

	public void setOperType(int operType) {
		this.operType = operType;
	}

	public int getWxPay() {
		return wxPay;
	}

	public void setWxPay(int wxPay) {
		this.wxPay = wxPay;
	}

	public String getPkgId() {
		return pkgId;
	}

	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}
}
