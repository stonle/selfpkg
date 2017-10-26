package com.ls.dao;

import java.util.List;

import com.ls.domain.District;

/**
* @Description:
* @author: cjw
* @date: 2016年2月7日
**/
public interface DistrictDAO extends BaseDAO<District, Integer>{

	List<District> listDistrictByCity(int cityID);

}
