/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: BaseTerminal 
* @Description:基类
* @author huanglei
* @date 2017年9月9日 上午11:32:34 
* @version V1.0    
*/
public class BaseTerminal {
	private String terminalNum;
	private String pkgId;
	public String getTerminalNum() {
		return terminalNum;
	}
	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}
	public String getPkgId() {
		return pkgId;
	}
	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}
}
