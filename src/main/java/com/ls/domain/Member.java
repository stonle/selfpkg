package com.ls.domain;

/**
 * 收件人人员注册表
 * @author Administrator
 *
 */
public class Member {
	/**
	 * 成员Id
	 */
    private String userId;
    /**
     * 登陆名
     */
    private String loginName;
    /**
     * 姓名 
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 密匙
     */
    private String salt;
    /**
     * 手机号，一般用手机号注册，徐进行唯一性验证
     */
    private String cellPhone;
    /**
     * 注册时间
     */
    private String createDate;
    /**
     * 最后登录时间
     */
    private String lostLoginDate;
    /**
     * 被准
     */
    private String remark;
    /**
     * 状态：0：冻结，删除  1：活跃
     */
    private Integer status;
    /**
     * 成员类型       0:管理员；1：普通成员
     */
    private Integer userType;
    /**
     * 终端设备Id，管理员注册需要设备Id
     */
    private String devId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLostLoginDate() {
		return lostLoginDate;
	}
	public void setLostLoginDate(String lostLoginDate) {
		this.lostLoginDate = lostLoginDate;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
	@Override
	public String toString() {
		return "Member [userId=" + userId + ", loginName=" + loginName + ", userName=" + userName + ", passWord="
				+ passWord + ", salt=" + salt + ", cellPhone=" + cellPhone + ", createDate=" + createDate
				+ ", lostLoginDate=" + lostLoginDate + ", remark=" + remark + ", status=" + status + ", userType="
				+ userType + ", devId=" + devId + "]";
	}  
}
