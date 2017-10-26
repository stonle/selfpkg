/**   
* 
*/
package com.ls.domain;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: BaseAddress 
* @Description:数据库地址数据 
* @author huanglei
* @date 2017年9月2日 下午2:33:46 
* @version V1.0    
*/
public class BaseAddress {
    private List<City> citys = new ArrayList<City>();
    private List<Province> provinces = new ArrayList<Province>();
    private List<District> districts = new ArrayList<District>();
	public List<City> getCitys() {
		return citys;
	}
	public void setCitys(List<City> citys) {
		this.citys = citys;
	}
	public List<Province> getProvinces() {
		return provinces;
	}
	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}
	public List<District> getDistricts() {
		return districts;
	}
	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}
   
}
