package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.dao.CityDAO;
import com.ls.domain.City;
import com.ls.service.CityService;

/**
 * @Description:
 * @author: cjw
 * @date: 2017年2月7日
 **/
@Service("cityServiceImpl")
public class CityServiceImpl extends BaseServiceImpl<City, Integer> implements CityService {
	
	@Autowired
	private CityDAO cityDAO;
	
	/**
	 * 获取指定省份下属城市
	 * 
	 * */
	public List<City> listCityByProvince(int provinceID){
		return cityDAO.listCityByProvince(provinceID);
	}
	
	/**
	 * 根据城市ID获取City
	 * 
	 * @param int
	 *            id
	 * @retyrn City Obj
	 */
	public City getCityById(int id) {

		return id == 0 ? null : this.getObjectById(City.class, id);
	}
}
