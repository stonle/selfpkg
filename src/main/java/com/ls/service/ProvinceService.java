package com.ls.service;

import java.util.List;

import com.ls.domain.Province;

/**
 * @Description:
 * @author: cjw
 * @date: 2017年2月7日
 **/
public interface ProvinceService {
	List<Province> getAllProvince();
	// 根据省份ID获取省份信息
	Province getProvinceById(int id);
}
