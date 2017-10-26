package com.ls.dao;

import java.util.List;

import com.ls.domain.City;

/**
* @Description:
* @author: cjw
* @date: 2016年2月7日
**/
public interface CityDAO extends BaseDAO<City, Integer> {

	List<City> listCityByProvince(int provinceID);

}
