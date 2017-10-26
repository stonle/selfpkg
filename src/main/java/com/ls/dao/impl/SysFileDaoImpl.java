/**   
* 
*/
package com.ls.dao.impl;

import java.util.ArrayList;
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
import com.ls.dao.SysFileDao;
import com.ls.domain.SysFileInfo;
import com.ls.util.Page;

/** 
* @ClassName: Sys 
* @Description: 
* @author huanglei
* @date 2017年9月19日 上午9:30:18 
* @version V1.0    
*/
@Repository
public class SysFileDaoImpl extends BaseDAOImpl<SysFileInfo,Integer> implements SysFileDao{
    
	@Override
	public SysFileInfo getsysFileInfoByVersion(final String sysVersion) {
		SysFileInfo sysFileInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<SysFileInfo>() {
			@Override
			public SysFileInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(SysFileInfo.class);
				criteria.add(Restrictions.eq("fileVersion", sysVersion));
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				@SuppressWarnings("unchecked")
				List<SysFileInfo> list = criteria.list();
				return list.size()>0?list.get(0):null;
			}
		});
		return sysFileInfo;
	}
	@Override
	public SysFileInfo getSysFileInfoByMD5(final String md5) {
		SysFileInfo sysFileInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<SysFileInfo>() {
			@Override
			public SysFileInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(SysFileInfo.class);
				criteria.add(Restrictions.eq("fileMD5", md5));
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				@SuppressWarnings("unchecked")
				List<SysFileInfo> list = criteria.list();
				return list.size()>0?list.get(0):null;
			}
		});
		return sysFileInfo;
	}

	@Override
	public int getTotallCount() {
        int count = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(SysFileInfo.class);
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				criteria.addOrder(Order.desc("createTime"));
				criteria.setProjection(Projections.rowCount());
				Number number = (Number) criteria.uniqueResult();
				return number.intValue();
			}
		});
		return count;
	}

	@Override
	public List<SysFileInfo> listSysFileByPages(final Page page) {
		List<SysFileInfo> sysFileInfos = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<SysFileInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<SysFileInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(SysFileInfo.class);
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				criteria.addOrder(Order.desc("createTime"));
				criteria.setFirstResult(page.getBeginIndex());
				criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return sysFileInfos;
	}

	@Override
	public List<SysFileInfo> querySysFileInfoByIds(final String[] strs) {
		List<SysFileInfo> sysLists = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<SysFileInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<SysFileInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(SysFileInfo.class);
				List<Integer> ids = new ArrayList<Integer>();
				for(String str:strs){
					ids.add(Integer.parseInt(str));	
				}
				criteria.add(Restrictions.in("id", ids));
				return criteria.list();
			}
		});
		return sysLists;
	}

	@Override
	public SysFileInfo getNweSysFileInfo() {
		SysFileInfo sysFileInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<SysFileInfo>() {
			@SuppressWarnings("unchecked")
			@Override
			public SysFileInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(SysFileInfo.class);
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				criteria.add(Restrictions.like("fileName", "apk", MatchMode.END));
				criteria.addOrder(Order.desc("fileVersion"));
				List<SysFileInfo> sysFileInfos = criteria.list();
				return sysFileInfos.size()>0?sysFileInfos.get(0):null;
			}
		});
		return sysFileInfo;
	}


}
