/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: Data 
* @Description: 
* @author huanglei
* @date 2017年9月8日 下午4:15:53 
* @version V1.0    
*/
public class HeartBeat {
	
	private String terminalNum;
    
	private String version;
	
	private String pkgId;
	
	public String getTerminalNum() {
		return terminalNum;
	}

	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPkgId() {
		return pkgId;
	}

	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}	
}
