package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ls.util.DateTimeUtil;
import com.ls.util.StringUtil;

@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class User {
	private String userID;
	private String loginName;
	private String password;
	private String userName;
	private String cellphone;
	private String remark;
	private String createDate;
	private String lastLoginDate;
	private int userType;
	private int status;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getRemark() {
		return remark == null ? "" : remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateDate() {
		return DateTimeUtil.convertFormatDate(createDate);
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getLastLoginDate() {
		if(StringUtil.isNotEmpty(lastLoginDate)){
			return DateTimeUtil.convertFormatDate(lastLoginDate);
		}else{
			return lastLoginDate;
		}
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
