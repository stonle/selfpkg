package com.ls.domain;

/**
 * view 层，用于页面展示vo
 * @author Administrator
 *
 */
public class PackageVo {
    /**
     *包裹编号
     */
	private String packageNum;
	/**
	 * 取件人手机号码
	 */
	private String packagePhone;
	/**
	 * 包裹存放地址
	 */
	private String packageAddress;
	/**
	 * 包裹投放地址
	 */
	private String devAddress;
	/**
	 * 取件码
	 */
	private String pickupCode;
	/**
	 *包裹投放时间
	 */
	private String deliverDate;
	/**
	 * 取件时间
	 */
	private String pickupDate;
	/**
	 * 状态
	 */
	private Integer status;
	public String getPackageNum() {
		return packageNum;
	}
	public void setPackageNum(String packageNum) {
		this.packageNum = packageNum;
	}
	public String getDevAddress() {
		return devAddress;
	}
	public void setDevAddress(String devAddress) {
		this.devAddress = devAddress;
	}
	public String getPickupCode() {
		return pickupCode;
	}
	public void setPickupCode(String pickupCode) {
		this.pickupCode = pickupCode;
	}
	public String getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPackagePhone() {
		return packagePhone;
	}
	public void setPackagePhone(String packagePhone) {
		this.packagePhone = packagePhone;
	}
	public String getPackageAddress() {
		return packageAddress;
	}
	public void setPackageAddress(String packageAddress) {
		this.packageAddress = packageAddress;
	}
	@Override
	public String toString() {
		return "PackageVo [packageNum=" + packageNum + ", packagePhone=" + packagePhone + ", packageAddress="
				+ packageAddress + ", devAddress=" + devAddress + ", pickupCode=" + pickupCode + ", deliverDate="
				+ deliverDate + ", pickupDate=" + pickupDate + ", status=" + status + "]";
	}

	
}
