/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: PkgInfo 
* @Description: 称重
* @author huanglei
* @date 2017年9月9日 下午1:49:35 
* @version V1.0    
*/
public class PkgInfo extends BaseTerminal{
	private long height;
	private long width;
	private long length;
	private long weight;
	private String userId;
	public long getHeight() {
		return height;
	}
	public void setHeight(long height) {
		this.height = height;
	}
	public long getWidth() {
		return width;
	}
	public void setWidth(long width) {
		this.width = width;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public long getWeight() {
		return weight;
	}
	public void setWeight(long weight) {
		this.weight = weight;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
