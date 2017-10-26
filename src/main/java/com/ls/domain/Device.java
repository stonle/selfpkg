package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ls.util.DateTimeUtil;

/**
 * 终端设备注册POJO
 * @author Administrator
 *
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Device {
    private String devNum;
    private String devName;
    private String  devAddr;
    private String cloudNum;
    private String videoNum;
    private String createDate;
    private String remark;
    private Integer status;
    @SuppressWarnings("unused")
	private String runStatus;
    private Long runDate;
    
    private CompanyInfo companyInfo;
    private CourierInfo courierInfo;
    private User user;
    private City city;
    private Province province;
    private District district;
    
    
    
	public String getRunStatus() {
		if(runDate == null){
			return "未接入";
		}
		//心跳时间目前需要修改
		return (System.currentTimeMillis()-runDate) <31*1000 ? "正常":"离线";
	}
	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public String getDevNum() {
		return devNum;
	}
	public void setDevNum(String devNum) {
		this.devNum = devNum;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public String getDevAddr() {
		return devAddr;
	}
	public void setDevAddr(String devAddr) {
		this.devAddr = devAddr;
	}
	public String getCreateDate() {
		return DateTimeUtil.convertFormatDate(createDate);
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public CourierInfo getCourierInfo() {
		return courierInfo;
	}
	public void setCourierInfo(CourierInfo courierInfo) {
		this.courierInfo = courierInfo;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getRunDate() {
		return runDate;
	}
	public void setRunDate(Long runDate) {
		this.runDate = runDate;
	}
	public String getCloudNum() {
		return cloudNum;
	}
	public void setCloudNum(String cloudNum) {
		this.cloudNum = cloudNum;
	}
	public String getVideoNum() {
		return videoNum;
	}
	public void setVideoNum(String videoNum) {
		this.videoNum = videoNum;
	}	
}
