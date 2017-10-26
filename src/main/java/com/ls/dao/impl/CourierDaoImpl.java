/**   
* 
*/
package com.ls.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ls.cenum.DateEnum;
import com.ls.dao.CourierDao;
import com.ls.domain.CourierInfo;
import com.ls.util.Page;
import com.ls.util.StringUtil;

/** 
* @ClassName: CourierDaoImpl 
* @Description: 
* @author huanglei
* @date 2017年9月5日 下午2:53:55 
* @version V1.0    
*/
@Repository
public class CourierDaoImpl extends BaseDAOImpl<CourierInfo, String> implements CourierDao {

	@Override
	public int getTotalCount(final CourierInfo courierInfo) {
		int cout = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(CourierInfo.class,"c");
				if(StringUtil.isNotEmpty(courierInfo.getCouName())){
					criteria.add(Restrictions.like("c.couName", courierInfo.getCouName(), MatchMode.ANYWHERE));
				}
				if(StringUtil.isNotEmpty(courierInfo.getCouPhone())){
					criteria.add(Restrictions.like("c.couPhone", courierInfo.getCouPhone(), MatchMode.ANYWHERE));			
				}
				criteria.add(Restrictions.eq("c.status", DateEnum.NO_DELETE.getIndex()));
				criteria.createCriteria("companyInfo","cm");
				if(courierInfo.getCompanyInfo() != null && StringUtil.isNotEmpty(courierInfo.getCompanyInfo().getCopName())){
					criteria.add(Restrictions.like("cm.copName", courierInfo.getCompanyInfo().getCopName(), MatchMode.ANYWHERE));
				}
				criteria.add(Restrictions.eq("cm.status", DateEnum.NO_DELETE.getIndex()));
				criteria.setProjection(Projections.rowCount());
				Number number = (Number) criteria.uniqueResult();
				return number.intValue();
			}
		});
		return cout;
	}

	@Override
	public List<CourierInfo> queryCourierByPage(final CourierInfo courierInfo,final Page page) {
        List<CourierInfo> courierInfos = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<CourierInfo>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<CourierInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(CourierInfo.class,"c");
				if(StringUtil.isNotEmpty(courierInfo.getCouName())){
					criteria.add(Restrictions.like("c.couName", courierInfo.getCouName(), MatchMode.ANYWHERE));
				}
				if(StringUtil.isNotEmpty(courierInfo.getCouPhone())){
					criteria.add(Restrictions.like("c.couPhone", courierInfo.getCouPhone(), MatchMode.ANYWHERE));			
				}
				criteria.add(Restrictions.eq("c.status", DateEnum.NO_DELETE.getIndex()));
				criteria.createCriteria("companyInfo","cm");
				if(courierInfo.getCompanyInfo() != null && StringUtil.isNotEmpty(courierInfo.getCompanyInfo().getCopName())){
					criteria.add(Restrictions.like("cm.copName", courierInfo.getCompanyInfo().getCopName(), MatchMode.ANYWHERE));
				}
				criteria.add(Restrictions.eq("cm.status", DateEnum.NO_DELETE.getIndex()));
				criteria.setFirstResult(page.getBeginIndex());
				criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return courierInfos;
	}

	@Override
	public CourierInfo queryCourierByPhone(final String phone) {
		CourierInfo courierInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<CourierInfo>() {
			@Override
			public CourierInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(CourierInfo.class);
				criteria.add(Restrictions.eq("couPhone", phone));
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				return (CourierInfo) criteria.uniqueResult();
			}
		});
		return courierInfo;
	}

}
