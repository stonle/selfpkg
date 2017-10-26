package com.ls.service;

import java.util.List;

import com.ls.domain.City;

/**
 * @Description:
 * @author: cjw
 * @date: 2017年2月7日
 **/
public interface CityService {

	List<City> listCityByProvince(int provinceID);
	// 根据ID获取城市信息
	City getCityById(int id);

}
