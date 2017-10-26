/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: OrderInfo 
* @Description:返回快递单号信息
* @author huanglei
* @date 2017年9月8日 下午5:07:48 
* @version V1.0    
*/
public class OrderInfoOut extends BaseTerminal{
    private String  recvAddr;
    private String  recvPhone;
    private String  recvName;
    private String  sendAddr;
    private String  sendPhone;
    private String  sendName;

	public String getRecvAddr() {
		return recvAddr;
	}
	public void setRecvAddr(String recvAddr) {
		this.recvAddr = recvAddr;
	}
	public String getRecvPhone() {
		return recvPhone;
	}
	public void setRecvPhone(String recvPhone) {
		this.recvPhone = recvPhone;
	}
	public String getRecvName() {
		return recvName;
	}
	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}
	public String getSendAddr() {
		return sendAddr;
	}
	public void setSendAddr(String sendAddr) {
		this.sendAddr = sendAddr;
	}
	public String getSendPhone() {
		return sendPhone;
	}
	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
}
