/**   
* 
*/
package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** 
* @ClassName: Order 
* @Description: 下单信息表
* @author huanglei
* @date 2017年9月1日 上午9:16:40 
* @version V1.0    
*/
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class OrderInfo {
    private String pkgNum;
    private String sendCode;
    private String createTime;
    private String pkgName;
    private Integer type;
    private Integer status;
    private String state;
    private Integer payMent;
    private MemberInfo memberInfo;
    private SendPerInfo sendPerInfo;
    private RecvPerInfo recvPerInfo;

    private double weight;
    private double payMoney;
    
    private String codeDate;
    
    private String payMentDate;
    
	public String getCodeDate() {
		return codeDate;
	}
	public void setCodeDate(String codeDate) {
		this.codeDate = codeDate;
	}
	public String getPayMentDate() {
		return payMentDate;
	}
	public void setPayMentDate(String payMentDate) {
		this.payMentDate = payMentDate;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public SendPerInfo getSendPerInfo() {
		return sendPerInfo;
	}
	public void setSendPerInfo(SendPerInfo sendPerInfo) {
		this.sendPerInfo = sendPerInfo;
	}
	public RecvPerInfo getRecvPerInfo() {
		return recvPerInfo;
	}
	public void setRecvPerInfo(RecvPerInfo recvPerInfo) {
		this.recvPerInfo = recvPerInfo;
	}
	public String getPkgNum() {
		return pkgNum;
	}
	public void setPkgNum(String pkgNum) {
		this.pkgNum = pkgNum;
	}
	public String getSendCode() {
		return sendCode;
	}
	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPkgName() {
		return pkgName;
	}
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    /*
	 * 0:未投入,下单 (手机下单，还没有投入揽收机) 
	 * 1：已投入 (该状态只对于户外有效) 
	 * 2：已取消 (手机取消下单) 
	 * 3：未揽收（户外即快递员未将包裹取回投递部,或自助揽收机包裹未揽收）
	 * 4：已揽收（户外即是快递员将包裹取回投递部,或自助揽收机包裹已揽收） 
	 * 5：已邮寄
	 * 6：已退回 
	 * 7：已签收
     */
	public String getState() {
		switch(this.status){
			case 0:
			   this.state = "未投入";
			   break;
			case 1:
				this.state = "已投入";
				   break;
			case 2:
				this.state = "已取消";
				   break;
			case 3:
				this.state = "未揽收";
				   break;
			case 4:
				this.state = "已揽收";
				   break;
			case 5:
				this.state = "已邮寄";
				   break;
			case 6:
				this.state = "已退回";
				   break;
			case 7:
				this.state = "已退回";
				   break;
			default:
				this.state = "已签收";
	   }
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public MemberInfo getMemberInfo() {
		return memberInfo;
	}
	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}
	public double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}
	public Integer getPayMent() {
		return payMent;
	}
	public void setPayMent(Integer payMent) {
		this.payMent = payMent;
	}
}
