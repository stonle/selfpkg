package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
* @Description:
* @author: cjw
* @date: 2017年2月6日
**/
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Province {
	private int provinceID;
	private String provinceName;


	public int getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(int provinceID) {
		this.provinceID = provinceID;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Override
	public String toString() {
		return "Province [provinceID=" + provinceID + ", provinceName=" + provinceName + "]";
	}

}
