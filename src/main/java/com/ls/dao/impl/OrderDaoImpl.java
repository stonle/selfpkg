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
import com.ls.cenum.PKGStatusEnum;
import com.ls.dao.OrderDao;
import com.ls.domain.OrderInfo;
import com.ls.util.Page;
import com.ls.util.StringUtil;

/** 
* @ClassName: OrderDaoImpl 
* @Description: 
* @author huanglei
* @date 2017年9月2日 上午11:18:17 
* @version V1.0    
*/
@Repository
public class OrderDaoImpl extends BaseDAOImpl<OrderInfo, String> implements OrderDao{

	@Override
	public List<OrderInfo> queryOrderInfos(final String memberId,final Page page) {
	    List<OrderInfo> orderInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<OrderInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<OrderInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(OrderInfo.class,"or");
				criteria.createCriteria("memberInfo", "me");
				criteria.createCriteria("sendPerInfo", "se");
				criteria.createCriteria("recvPerInfo", "re");
				criteria.add(Restrictions.eq("me.memberId", memberId));
				criteria.addOrder(Order.desc("or.createTime"));
				criteria.setFirstResult(page.getBeginIndex());
				criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return orderInfo;
	}
	@Override
	public int getTotalCount(final OrderInfo packageVo,final String phone) {
		int cout = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(OrderInfo.class,"o");
				if(StringUtil.isNotEmpty(packageVo.getPkgNum())){
					criteria.add(Restrictions.like("o.pkgNum", packageVo.getPkgNum(), MatchMode.ANYWHERE));
				}
				//criteria.add(Restrictions.not(Restrictions.eq("status", 2)));
				criteria.createCriteria("sendPerInfo","s");
				criteria.createCriteria("recvPerInfo","r");
				//Disjunction dis = Restrictions.disjunction();
				if(StringUtil.isNotEmpty(phone)){
					criteria.add(Restrictions.like("s.sendPhone", phone, MatchMode.ANYWHERE));
					//dis.add(Restrictions.like("r.recvPhone", phone, MatchMode.ANYWHERE));
				}
				//criteria.add(dis);
				//criteria.addOrder(Order.desc("o.createTime"));
				criteria.setProjection(Projections.rowCount());
				Number number = (Number) criteria.uniqueResult();
				return number.intValue();
			}
		});
		return cout;
	}

	@Override
	public List<OrderInfo> listPackageByPage(final Page page,final OrderInfo packageVo,final String phone) {
		List<OrderInfo> orderInfos = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<OrderInfo>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<OrderInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(OrderInfo.class,"o");
				if(StringUtil.isNotEmpty(packageVo.getPkgNum())){
					criteria.add(Restrictions.like("o.pkgNum", packageVo.getPkgNum(), MatchMode.ANYWHERE));
				}
				//criteria.add(Restrictions.not(Restrictions.eq("status", 2)));
				criteria.createCriteria("sendPerInfo","s");
				criteria.createCriteria("recvPerInfo","r");
				//Disjunction dis = Restrictions.disjunction();
				if(StringUtil.isNotEmpty(phone)){
					criteria.add(Restrictions.like("s.sendPhone", phone, MatchMode.ANYWHERE));
					//dis.add(Restrictions.like("r.recvPhone", phone, MatchMode.ANYWHERE));
				}
				//criteria.add(dis);
				criteria.addOrder(Order.desc("o.createTime"));
				criteria.setFirstResult(page.getBeginIndex());
				criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return orderInfos;
	}
	@Override
	public OrderInfo ckeckOrder(final String phone,final String pickCode) {
		OrderInfo orderInfo = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<OrderInfo>() {
			@Override
			public OrderInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(OrderInfo.class,"od");
				criteria.add(Restrictions.eq("od.sendCode", pickCode));
				criteria.add(Restrictions.eq("od.status", PKGStatusEnum.NO_INPUT.getIndex()));
				criteria.createCriteria("memberInfo", "me");
				criteria.add(Restrictions.eq("me.memberPhone", phone));
				criteria.add(Restrictions.eq("me.status", DateEnum.NO_DELETE.getIndex()));
				return (OrderInfo) criteria.uniqueResult();
			}
		});
		return orderInfo;
	}

	@Override
	public int getTotalCount(final String memberId) {
		int cout = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(OrderInfo.class,"or");
				criteria.createCriteria("memberInfo", "me");
				criteria.add(Restrictions.eq("me.memberId", memberId));
				criteria.setProjection(Projections.rowCount());
				Number number = (Number) criteria.uniqueResult();
				return number.intValue();
			}
		});
		return cout;
	}
    /**
     * 根据手机号获取订单信息
     */
	@Override
	public int getTotalCountByPhone(final String phone) {
		int cout = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(OrderInfo.class,"o");
				criteria.createCriteria("memberInfo","m");
				criteria.createCriteria("sendPerInfo","s");
				criteria.createCriteria("recvPerInfo","r");
				if(StringUtil.isNotEmpty(phone)){
					criteria.add(Restrictions.eq("m.memberPhone", phone));
				}
				criteria.setProjection(Projections.rowCount());
				Number number = (Number) criteria.uniqueResult();
				return number.intValue();
			}
		});
		return cout;
	}

	@Override
	public List<OrderInfo> listPackageByPhone(final String phone,final Page page) {
		List<OrderInfo> orderInfos = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<OrderInfo>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<OrderInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(OrderInfo.class,"o");
				criteria.createCriteria("memberInfo","m");
				criteria.createCriteria("sendPerInfo","s");
				criteria.createCriteria("recvPerInfo","r");
				if(StringUtil.isNotEmpty(phone)){
					criteria.add(Restrictions.eq("m.memberPhone", phone));
				}
				criteria.addOrder(Order.desc("o.createTime"));
				criteria.setFirstResult(page.getBeginIndex());
				criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return orderInfos;
	}

}
