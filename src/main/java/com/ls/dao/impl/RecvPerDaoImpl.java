/**   
* 
*/
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
import com.ls.dao.RecvPerDao;
import com.ls.domain.RecvPerInfo;
import com.ls.util.Page;
import com.ls.util.StringUtil;

/** 
* @ClassName: RecvPerDaoImpl 
* @Description: 
* @author huanglei
* @date 2017年9月1日 下午5:19:46 
* @version V1.0    
*/
@Repository
public class RecvPerDaoImpl extends BaseDAOImpl<RecvPerInfo,String> implements RecvPerDao {
	@Override
	public int getRecvPreTotalCount(final RecvPerInfo recvInfo) {
		int cout = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = recvperExtracted(recvInfo, session);
				criteria.setProjection(Projections.rowCount());
				Number number =  (Number) criteria.uniqueResult();
				return number.intValue();
			}
		});
		return cout;
	}

	@Override
	public List<RecvPerInfo> listRecvPerByPage(final Page page, final RecvPerInfo recvInfo) {
		List<RecvPerInfo> recvPerInfos = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<RecvPerInfo>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<RecvPerInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = recvperExtracted(recvInfo, session);
				criteria.addOrder(Order.desc("createDate"));
	        	criteria.setFirstResult(page.getBeginIndex());
	        	criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return recvPerInfos;
	}

	@Override
	public List<RecvPerInfo> listRecvPerInfo(final String memberId,final Page page) {
        List<RecvPerInfo> recvPerInfos = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<RecvPerInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<RecvPerInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(RecvPerInfo.class);
				criteria.add(Restrictions.eq("memberId", memberId));
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				criteria.addOrder(Order.desc("createDate"));
				criteria.setFirstResult(page.getBeginIndex());
	        	criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return recvPerInfos;
	}

	/**
	 * @param recvInfo
	 * @param session
	 * @return
	 */
	private Criteria recvperExtracted(final RecvPerInfo recvInfo, Session session) {
		Criteria criteria = session.createCriteria(RecvPerInfo.class);
		if(recvInfo!=null){
			if(StringUtil.isNotEmpty(recvInfo.getRecvName())){
				criteria.add(Restrictions.like("recvName", recvInfo.getRecvName(), MatchMode.ANYWHERE));
			}
			if(StringUtil.isNotEmpty(recvInfo.getRecvPhone())){
				criteria.add(Restrictions.like("recvPhone", recvInfo.getRecvPhone(), MatchMode.ANYWHERE));
			}
			if(StringUtil.isNotEmpty(recvInfo.getMemberId())){
				criteria.add(Restrictions.eq("memberId", recvInfo.getMemberId()));
			}
		}
		criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
		return criteria;
	}
	@Override
	public int getTotalCount(final String memberId) {
		int cout = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(RecvPerInfo.class);
				criteria.add(Restrictions.eq("memberId", memberId));
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				criteria.setProjection(Projections.rowCount());
				Number number = (Number) criteria.uniqueResult();
				return number.intValue();
			}
		});
		return cout;
	}

}
