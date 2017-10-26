/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: PaymentInfo 
* @Description: 
* @author huanglei
* @date 2017年9月9日 下午2:25:22 
* @version V1.0    
*/
public class PaymentInfo extends BaseTerminal {
	private int type;
	private double recv;
	private double back;
	private String userId;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getRecv() {
		return recv;
	}
	public void setRecv(double recv) {
		this.recv = recv;
	}
	public double getBack() {
		return back;
	}
	public void setBack(double back) {
		this.back = back;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}
