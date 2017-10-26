/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: UpdateConfigVo 
* @Description: 更新配置文件vo
* @author huanglei
* @date 2017年9月18日 上午11:01:44 
* @version V1.0    
*/
public class UpdateConfigVo {
	
	private String confgKey;
	
	private String confgValue;
	
	private int type;

	public String getConfgKey() {
		return confgKey;
	}

	public void setConfgKey(String confgKey) {
		this.confgKey = confgKey;
	}

	public String getConfgValue() {
		return confgValue;
	}

	public void setConfgValue(String confgValue) {
		this.confgValue = confgValue;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
