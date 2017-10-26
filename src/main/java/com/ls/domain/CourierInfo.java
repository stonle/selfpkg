/**   
* 
*/
package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ls.util.DateTimeUtil;

/** 
* @ClassName: CourierInfo 
* @Description: 快递员实体信息表
* @author huanglei
* @date 2017年9月5日 上午9:04:44 
* @version V1.0    
*/
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class CourierInfo {
    private String couId;
    private String couName;
    private String couPhone;
    private String couCard;
    private String createTime;
    private String lastLoginTime;
    private String pwd;
    private String salt;
    private Integer status;
    private CompanyInfo companyInfo;
	public String getCouId() {
		return couId;
	}
	public void setCouId(String couId) {
		this.couId = couId;
	}
	public String getCouName() {
		return couName;
	}
	public void setCouName(String couName) {
		this.couName = couName;
	}
	public String getCouPhone() {
		return couPhone;
	}
	public void setCouPhone(String couPhone) {
		this.couPhone = couPhone;
	}
	public String getCouCard() {
		return couCard;
	}
	public void setCouCard(String couCard) {
		this.couCard = couCard;
	}
	public String getCreateTime() {
		return DateTimeUtil.convertFormatDate(createTime);
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastLoginTime() {
		return DateTimeUtil.convertFormatDate(lastLoginTime);
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
	@Override
	public String toString() {
		return "CourierInfo [couId=" + couId + ", couName=" + couName + ", couPhone=" + couPhone + ", couCard="
				+ couCard + ", createTime=" + createTime + ", lastLoginTime=" + lastLoginTime + ", pwd=" + pwd
				+ ", salt=" + salt + ", status=" + status + ", companyInfo=" + companyInfo + "]";
	}
}
