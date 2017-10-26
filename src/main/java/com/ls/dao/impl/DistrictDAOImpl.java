package com.ls.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.ls.dao.DistrictDAO;
import com.ls.domain.District;

/**
* @Description:
* @author: cjw
* @date: 2017年2月7日
**/
@Repository("districtDAOImpl")
public class DistrictDAOImpl extends BaseDAOImpl<District, Integer> implements DistrictDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<District> listDistrictByCity(int cityID) {
		List<District> list = null;
		
		try {
			String hql = "from District as c where c.city.cityID=:cityID";
			String[] names = { "cityID" };          
			Object[] values = { cityID };
			list = (List<District>) this.getHibernateTemplate().findByNamedParam(hql, names, values);
			if(list.size() == 0){
				throw new RuntimeException("没有查询d对应的城市信息！");
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
		return list;
	}

}
