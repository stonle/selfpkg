package com.ls.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ls.cenum.DateEnum;
import com.ls.dao.DeviceDao;
import com.ls.domain.Device;
import com.ls.util.Page;
import com.ls.util.StringUtil;

@Repository
public class DeviceDaoImpl extends BaseDAOImpl<Device, String> implements DeviceDao {
    
	/**
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Device> listDeviceByPage(final Page page, final Device device,final String couName) {
        List<Device> devices = (List<Device>) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>(
        		) {
					@Override
					public Object doInHibernate(Session session) throws HibernateException {
						Criteria criteria = exUtils(device, couName, session);
						criteria.addOrder(Order.desc("de.createDate"));
						criteria.setFirstResult(page.getBeginIndex());
						criteria.setMaxResults(page.getEveryPage());
						return criteria.list();
					}
		});
		return devices;
	}
    
	/**
	 * 查詢条数获取
	 */
	@Override
	public int getTotalCount(final Device device,final String couName) {
		
		Long result = (Long)this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = exUtils(device, couName, session);
				criteria.setProjection(Projections.rowCount());
				return criteria.uniqueResult();
			}
		});
		return result.intValue();
	}
	/**
	 * @param device
	 * @param couName
	 * @param session
	 * @return
	 */
	private Criteria exUtils(final Device device, final String couName, Session session) {
		Criteria criteria = session.createCriteria(Device.class,"de");
		criteria.createCriteria("companyInfo", "cp");
		criteria.createCriteria("user", "us");
		criteria.createCriteria("province", "pr");
		criteria.createCriteria("city", "ct");
		criteria.createCriteria("district", "di");
        criteria.createCriteria("courierInfo", "co", JoinType.LEFT_OUTER_JOIN);
        if(StringUtil.isNotEmpty(device.getDevNum())){
        	criteria.add(Restrictions.like("de.devNum", device.getDevNum(), MatchMode.ANYWHERE));
		}
		if(StringUtil.isNotEmpty(device.getDevName())){
			criteria.add(Restrictions.like("de.devName", device.getDevName(), MatchMode.ANYWHERE));				
		}
		if(StringUtil.isNotEmpty(couName)){
			criteria.add(Restrictions.eq("co.couName", couName));
		}
		criteria.add(Restrictions.eq("de.status", DateEnum.NO_DELETE.getIndex()));
		return criteria;
	}

}
