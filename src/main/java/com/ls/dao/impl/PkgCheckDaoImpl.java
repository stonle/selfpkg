package com.ls.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ls.cenum.DateEnum;
import com.ls.cenum.PkgCheckTypeEnum;
import com.ls.dao.PkgCheckDao;
import com.ls.domain.PkgCheckInfo;
import com.ls.http.BaseTerminal;

@Repository
public class PkgCheckDaoImpl extends BaseDAOImpl<PkgCheckInfo,Integer> implements PkgCheckDao{

	@Override
	public PkgCheckInfo getPkgCheckInfo(final BaseTerminal baseTerminal) {
		PkgCheckInfo PkgCheckInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<PkgCheckInfo>() {

			@Override
			public PkgCheckInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(PkgCheckInfo.class);
				criteria.add(Restrictions.eq("devNum", baseTerminal.getTerminalNum()));
				criteria.add(Restrictions.eq("pkgNum", baseTerminal.getPkgId()));
				criteria.addOrder(Order.desc("createDate"));
				@SuppressWarnings("unchecked")
				List<PkgCheckInfo> lsit = criteria.list();
				return lsit.size()>0?lsit.get(0):null;
			}
		});
		return PkgCheckInfo;
	}

	@Override
	public PkgCheckInfo getCheckInfoByPkgNum(final String pkgNum) {
		
		PkgCheckInfo PkgCheckInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<PkgCheckInfo>() {

			@Override
			public PkgCheckInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(PkgCheckInfo.class);
				criteria.add(Restrictions.eq("pkgNum", pkgNum));
				//criteria.add(Restrictions.not(Restrictions.eq("type", PkgCheckTypeEnum.SUCCESS.getIndex())));
				//criteria.add(Restrictions.not(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex())));
				criteria.addOrder(Order.desc("createDate"));
				@SuppressWarnings("unchecked")
				List<PkgCheckInfo> list = criteria.list();
				return list.size()>0?list.get(0):null;
			}
		});
		return PkgCheckInfo;
	}

	@Override
	public List<PkgCheckInfo> getPkgCheckInfos() {
		List<PkgCheckInfo> list = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<PkgCheckInfo>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<PkgCheckInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(PkgCheckInfo.class);
				criteria.add(Restrictions.not(Restrictions.eq("type", PkgCheckTypeEnum.SUCCESS.getIndex())));
				criteria.add(Restrictions.not(Restrictions.eq("type", PkgCheckTypeEnum.FAIL.getIndex())));
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				criteria.addOrder(Order.desc("createDate"));
				return criteria.list();
			}
		});
		return list;
	}

	@Override
	public PkgCheckInfo getPkgCheckInfoByPkgNumAndDevNum(final String pkgNum,final String devNum,final boolean flag) {
		PkgCheckInfo PkgCheckInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<PkgCheckInfo>() {
			@Override
			public PkgCheckInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(PkgCheckInfo.class);
				criteria.add(Restrictions.eq("pkgNum", pkgNum));
				criteria.add(Restrictions.eq("devNum", devNum));
				if(flag){
					criteria.add(Restrictions.eq("type", PkgCheckTypeEnum.NO_HANDLE.getIndex()));
				}
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				return (PkgCheckInfo) criteria.uniqueResult();
			}
		});
		return PkgCheckInfo;
	}

}
