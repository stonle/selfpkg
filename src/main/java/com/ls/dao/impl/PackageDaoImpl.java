package com.ls.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ls.cenum.DateEnum;
import com.ls.dao.PackageDao;
import com.ls.domain.PackageInfo;
import com.ls.util.Page;
import com.ls.util.StringUtil;

@Repository
public class PackageDaoImpl extends BaseDAOImpl<PackageInfo, Integer> implements PackageDao{
    
	@Override
	public List<PackageInfo> listPackageByPage( final Page page,final String pkgNum,final String deviceNum) {
		List<PackageInfo> packageInfos = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<PackageInfo>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<PackageInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(PackageInfo.class, "p");
				criteria.createCriteria("orderInfo", "o");
				criteria.createCriteria("device", "d");
				if(StringUtil.isNotEmpty(pkgNum)){
					criteria.add(Restrictions.like("o.pkgNum", pkgNum, MatchMode.ANYWHERE));
				}
				if(StringUtil.isNotEmpty(deviceNum)){
					criteria.add(Restrictions.like("d.devNum", deviceNum, MatchMode.ANYWHERE));
				}
				criteria.setFirstResult(page.getBeginIndex());
				criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return packageInfos;
	}

	@Override
	public int getTotalCount(final String pkgNum,final String deviceNum) {
		int cout = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(PackageInfo.class, "p");
				criteria.createCriteria("orderInfo", "o");
				criteria.createCriteria("device", "d");
				if(StringUtil.isNotEmpty(pkgNum)){
					criteria.add(Restrictions.like("o.pkgNum", pkgNum, MatchMode.ANYWHERE));
				}
				if(StringUtil.isNotEmpty(deviceNum)){
					criteria.add(Restrictions.like("d.devNum", deviceNum, MatchMode.ANYWHERE));
				}
				criteria.setProjection(Projections.rowCount());
				Number number = (Number) criteria.uniqueResult();
				return number.intValue();
			}
		});
		return cout;
	}
	@Override
	public PackageInfo getPckInfoByPkgNum(final String pkuNum) {
		PackageInfo packageInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<PackageInfo>() {
			@Override
			public PackageInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(PackageInfo.class,"pkg");
				criteria.createCriteria("orderInfo", "or");
				criteria.add(Restrictions.eq("pkg.status", DateEnum.NO_DELETE.getIndex()));
				criteria.add(Restrictions.eq("or.pkgNum", pkuNum));
				@SuppressWarnings("unchecked")
				List<PackageInfo> packageInfos = criteria.list();
				return packageInfos.size()>0?packageInfos.get(0):null;
			}
		});
		return packageInfo;
	}

	@Override
	public PackageInfo getPackageInfoByDevNum(final String devNum) {
		PackageInfo packageInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<PackageInfo>() {
			@SuppressWarnings("unchecked")
			@Override
			public PackageInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(PackageInfo.class,"pkg");
				criteria.createCriteria("orderInfo", "or");
				criteria.createCriteria("device","dev");
				criteria.add(Restrictions.eq("pkg.status", DateEnum.NO_DELETE.getIndex()));
				criteria.add(Restrictions.eq("dev.devNum", devNum));
				criteria.addOrder(Order.desc("pkg.createDate"));
				List<PackageInfo> packageInfos = criteria.list();
				return packageInfos.size()>0?packageInfos.get(0):null;
			}
		});
		return packageInfo;
	}


    
}
