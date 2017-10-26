/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: QueryListVo 
* @Description: 列表查询条件vo
* @author huanglei
* @date 2017年9月11日 下午3:58:06 
* @version V1.0    
*/
public class QueryListVo {
	private String terminalNum;
	private String phoneNum;
	private int curPage;
	private int everyPage;
	
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getEveryPage() {
		return everyPage;
	}
	public void setEveryPage(int everyPage) {
		this.everyPage = 5;
	}
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
	
}
