package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
* @Description:
* @author: cjw
* @date: 2017年2月6日
**/
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class City {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -2805284943658356093L;
	private int cityID;
	private String cityName;
	private String zipCode;
	@JsonIgnore
	private Province province;
	
	private int provinceId;
	
	public int getCityID() {
		return cityID;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public int getProvinceId() {
		return province.getProvinceID();
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	@Override
	public String toString() {
		return "City [cityID=" + cityID + ", cityName=" + cityName + ", zipCode=" + zipCode + ", province=" + province
				+ ", provinceId=" + provinceId + "]";
	}
   
}
