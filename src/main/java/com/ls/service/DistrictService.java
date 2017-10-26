package com.ls.service;

import java.util.List;

import com.ls.domain.District;

/**
 * @Description:
 * @author: cjw
 * @date: 2017年2月7日
 **/
public interface DistrictService {

	// 指定城市下属区县
	List<District> listDistrictByCity(int cityID);

	// 根据区县ID获取District对象
	District getDistrictById(int id);

}
