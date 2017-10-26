/**   
* 
*/
package com.ls.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ls.cenum.DateEnum;
import com.ls.dao.SendPerDao;
import com.ls.domain.SendPerInfo;
import com.ls.util.Page;
import com.ls.util.StringUtil;

/** 
* @ClassName: SendPerDaoImpl 
* @Description: 收寄人员管理service
* @author huanglei
* @date 2017年9月1日 上午9:22:36 
* @version V1.0    
*/
@Repository
public class SendPerDaoImpl extends BaseDAOImpl<SendPerInfo, String> implements SendPerDao{
	@Override
	public List<SendPerInfo> querySendPerAndRecvInfo(String memberId,Page page) {
		    String hql = "from SendPerInfo where memberId=:memberId and status=:status order by createDate";
		    Query query =this.currentSession().createQuery(hql);
		    query.setString("memberId", memberId);
		    query.setInteger("status", DateEnum.NO_DELETE.getIndex());
			query.setFirstResult(page.getBeginIndex());
		    query.setMaxResults(page.getEveryPage());
		    @SuppressWarnings("unchecked")
			List<SendPerInfo> sendPerInfos = query.list();
		    return sendPerInfos;

	}

	@Override
	public int getSendPreTotalCount(final SendPerInfo sendPerInfo) {
	int cout = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
		@Override
		public Integer doInHibernate(Session session) throws HibernateException {
			Criteria criteria = session.createCriteria(SendPerInfo.class);
			if(StringUtil.isNotEmpty(sendPerInfo.getSendName())){
				criteria.add(Restrictions.like("sendName", sendPerInfo.getSendName(), MatchMode.ANYWHERE));
			}
			if(StringUtil.isNotEmpty(sendPerInfo.getSendPhone())){
				criteria.add(Restrictions.like("sendPhone", sendPerInfo.getSendPhone(), MatchMode.ANYWHERE));
			};
			if(StringUtil.isNotEmpty(sendPerInfo.getMemberId())){
				criteria.add(Restrictions.eq("memberId", sendPerInfo.getMemberId()));
			}
			criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
			criteria.setProjection(Projections.rowCount());
			Number number = (Number) criteria.uniqueResult();
			return number.intValue();
		}
		});
		return cout;
	}


	@Override
	public List<SendPerInfo> listSendPerByPage(final SendPerInfo sendPerInfo, final Page page) {
		List<SendPerInfo> sendPerInfos = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<SendPerInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<SendPerInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(SendPerInfo.class);
				if(StringUtil.isNotEmpty(sendPerInfo.getSendName())){
					criteria.add(Restrictions.like("sendName", sendPerInfo.getSendName(), MatchMode.ANYWHERE));
				}
				if(StringUtil.isNotEmpty(sendPerInfo.getSendPhone())){
					criteria.add(Restrictions.like("sendPhone", sendPerInfo.getSendPhone(), MatchMode.ANYWHERE));
				};
				if(StringUtil.isNotEmpty(sendPerInfo.getMemberId())){
					criteria.add(Restrictions.eq("memberId", sendPerInfo.getMemberId()));
				}
				criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				criteria.addOrder(Order.desc("createDate"));
				criteria.setFirstResult(page.getBeginIndex());
				criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return sendPerInfos;
	}
	@Override
	public int getTotalCount(final String memberId) {
		int cout = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(SendPerInfo.class);
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
