/**   
* 
*/
package com.ls.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ls.cenum.DateEnum;
import com.ls.dao.CompanyDao;
import com.ls.domain.CompanyInfo;
import com.ls.util.Page;

/** 
* @ClassName: CompanyDaoImpl 
* @Description: 
* @author huanglei
* @date 2017年9月4日 上午10:40:36 
* @version V1.0    
*/
@Repository
public class CompanyDaoImpl extends BaseDAOImpl<CompanyInfo, Integer> implements CompanyDao {
	@Override
	public int getTotalCount() {
		int cout = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(CompanyInfo.class);
				criteria.setProjection(Projections.rowCount());
     			Number number =  (Number) criteria.uniqueResult();
				return number.intValue();
			}
		});
		return cout;
	}
	@Override
	public List<CompanyInfo> listCompanyByPage(final Page page) {
		List<CompanyInfo> conpanyInfos = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<CompanyInfo>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<CompanyInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(CompanyInfo.class);
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				criteria.setFirstResult(page.getBeginIndex());
				criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return conpanyInfos;
	}

}
