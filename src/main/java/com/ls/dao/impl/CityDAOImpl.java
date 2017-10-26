package com.ls.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ls.dao.CityDAO;
import com.ls.domain.City;

/**
* @Description:
* @author: cjw
* @date: 2017年2月7日
**/
@Repository("cityDaoImpl")
public class CityDAOImpl extends BaseDAOImpl<City, Integer> implements CityDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<City> listCityByProvince(int provinceID) {
		List<City> list = null;
		try {
			String hql = "from City as c where c.province.provinceID=:provinceID";
			String[] names = { "provinceID" };          
			Object[] values = { provinceID };
			list = (List<City>) this.getHibernateTemplate().findByNamedParam(hql, names, values);
			if(list.size() == 0){
				throw new RuntimeException("没有查询d对应的城市信息！");
			}
		} catch (RuntimeException e) {	
			throw new RuntimeException(e.getMessage());
		}
		
		return list;
	}

}
