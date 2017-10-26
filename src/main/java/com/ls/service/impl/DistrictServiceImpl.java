package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.dao.DistrictDAO;
import com.ls.domain.District;
import com.ls.service.DistrictService;

/**
* @Description:
* @author: cjw
* @date: 2017年2月7日
**/
@Service("districtServiceImpl")
public class DistrictServiceImpl extends BaseServiceImpl<District, Integer> 
	implements DistrictService {
	
	@Autowired
	private DistrictDAO districtDAO;
	
	public List<District> listDistrictByCity(int cityID){
		return districtDAO.listDistrictByCity(cityID);
	}
	
	/**
	 * 根据区域ID获取District
	 * @param int id
	 * @return District Obj
	 * */
	public District getDistrictById(int id) {
		return id == 0 ? null : this.getObjectById(District.class, id);
	}

}
