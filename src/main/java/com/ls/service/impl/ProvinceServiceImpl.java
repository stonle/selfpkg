package com.ls.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ls.domain.Province;
import com.ls.service.ProvinceService;

/**
 * @Description:
 * @author: cjw
 * @date: 2017年2月7日
 **/
@Service("provinceServiceImpl")
public class ProvinceServiceImpl extends BaseServiceImpl<Province, Integer> implements ProvinceService {

	/**
	 * 获取全部省份信息
	 * 
	 * @param null
	 * @return List<Province>
	 */
	public List<Province> getAllProvince() {
		return this.getAllObjects(Province.class);
	}

	/**
	 * 根据省份的ID获取省份信息
	 * 
	 * @param int
	 *            ID
	 * @param Province
	 *            Obj
	 */
	public Province getProvinceById(int id) {
		return id == 0 ? null : this.getObjectById(Province.class, id);
	}
}
