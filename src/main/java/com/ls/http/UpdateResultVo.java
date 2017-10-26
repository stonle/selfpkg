/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: UpdateResultVo 
* @Description: 更新结果Vo
* @author huanglei
* @date 2017年9月18日 上午10:39:53 
* @version V1.0    
*/
public class UpdateResultVo {
	
	private String terminalNum;
	
	private String version;
	
	private String url;
	
	private String hashCode;
    
	private String fileName;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	
}
