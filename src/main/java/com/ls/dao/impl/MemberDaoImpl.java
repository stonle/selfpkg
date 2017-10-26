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
import com.ls.dao.MemberDao;
import com.ls.domain.MemberInfo;
import com.ls.util.Page;
import com.ls.util.StringUtil;
@Repository
public class MemberDaoImpl extends BaseDAOImpl<MemberInfo, String> implements MemberDao {

	@Override
	public List<MemberInfo> listPersonelByPage(final Page page, final MemberInfo personel) {
	    @SuppressWarnings("unchecked")
		List<MemberInfo> personels = (List<MemberInfo>) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
	            Criteria criteria = listPerExtracted(personel, session);
	        	criteria.addOrder(Order.desc("createDate"));
	        	criteria.setFirstResult(page.getBeginIndex());
	        	criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return personels;
	}

	@Override
	public int getTotalCount(final MemberInfo personel) {
		Long result = (Long) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = listPerExtracted(personel, session);
				criteria.setProjection(Projections.rowCount());
				return criteria.uniqueResult();
			}
		});
		return result.intValue();
	}  
	/**
	 * 手机号唯一性验证
	 */
	@Override
	public MemberInfo getMemberyInfoByMemberPhone(final String memberPhone) {
		MemberInfo memberInfo = (MemberInfo) this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<MemberInfo>() {
			@Override
			public MemberInfo doInHibernate(Session session) throws HibernateException {
				Criteria cri = session.createCriteria(MemberInfo.class);
				cri.add(Restrictions.eq("memberPhone", memberPhone));
				cri.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
				MemberInfo mInfo = (MemberInfo) cri.uniqueResult();
				return mInfo;
			}
		});
		return memberInfo;
	}

	/**
	 * @param personel
	 * @param session
	 * @return
	 */
	private Criteria listPerExtracted(final MemberInfo personel, Session session) {
		Criteria criteria = session.createCriteria(MemberInfo.class);
		if(personel!=null){
			if(StringUtil.isNotEmpty(personel.getMemberPhone())){
				criteria.add(Restrictions.like("memberPhone", personel.getMemberPhone(), MatchMode.ANYWHERE));
			}
			if(StringUtil.isNotEmpty(personel.getMemberName())){
				criteria.add(Restrictions.like("memberName", personel.getMemberName(), MatchMode.ANYWHERE));
			}
		}
		criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
		return criteria;
	}

}
