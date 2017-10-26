package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
* @Description:
* @author: cjw
* @date: 2017年2月6日
**/
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class District {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -2805284943658356093L;
	private int districtID;
	private String districtName;
	@JsonIgnore
	private City city;
	
	private int cityId;
	
	public int getDistrictID() {
		return districtID;
	}
	public void setDistrictID(int districtID) {
		this.districtID = districtID;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public int getCityId() {
		return city.getCityID();
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	@Override
	public String toString() {
		return "District [districtID=" + districtID + ", districtName=" + districtName + ", city=" + city + ", cityId="
				+ cityId + "]";
	}
}
