package com.ls.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ls.cenum.DateEnum;
import com.ls.dao.UserDAO;
import com.ls.domain.User;
import com.ls.util.EncodingUtil;
import com.ls.util.Page;

/**
 * @author cjw 2017-05-04
 * 
 */
@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDAOImpl<User, String> implements UserDAO {

	/**
	 * 分页条件查询User
	 * @param  Page page ，User user
	 * @return List<User>
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<User> listUserByPage(final Page page, final User user) {
		List<User> list = (List<User>) this.getHibernateTemplate()
				.executeWithNativeSession(new HibernateCallback<Object>() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = session.createCriteria(User.class);
						criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
						criteria.add(Restrictions.eq("userType", 1));
						if(user != null){
							if(user.getCellphone() != null){
								criteria.add(Restrictions.like("cellphone", user.getCellphone(),MatchMode.START));
							}
							if(user.getUserName() != null){
								criteria.add(Restrictions.eq("userName",EncodingUtil.getUtf8String(user.getUserName())));
							}
						}
						criteria.addOrder(Order.desc("createDate"));
						criteria.setFirstResult(page.getBeginIndex());
						criteria.setMaxResults(page.getEveryPage());
						return criteria.list();
					}
				});
		return list;
	}

	/**
	 * 获取分页条件查询时的记录总数，配合List<User> listUserByPage(Page page, User user)使用
	 * @param User user
	 * @return int
	 * */
	public int getTotalCount(final User user) {
		Long result = (Long) this.getHibernateTemplate()
				.executeWithNativeSession(new HibernateCallback<Object>() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = session.createCriteria(User.class);
						if(user != null){
							if(user.getCellphone() != null){
								criteria.add(Restrictions.like("cellphone", user.getCellphone(),MatchMode.START));
							}
							if(user.getUserName() != null){
								criteria.add(Restrictions.eq("userName",EncodingUtil.getUtf8String(user.getUserName())));
							}
						}
						criteria.add(Restrictions.eq("status", DateEnum.NO_DELETE.getIndex()));
						criteria.add(Restrictions.eq("userType", 1));
						criteria.setProjection(Projections.rowCount());
						return criteria.uniqueResult();
					}
				});

		return result.intValue();
	}

	
	/**
	 * 根据用户名获取用户信息,可用于用户登录信息验证
	 * @param String loginNam
	 * @return Object User
	 * */
	@SuppressWarnings("unchecked")
	public User getUserByLoginName(String loginName) {
		List<User> list = new ArrayList<User>();
		try {
			String hql = "from User as a where a.loginName=:loginName and a.status = 1 ";
			String[] names = { "loginName" };

			Object[] values = { loginName };
			list = (List<User>) this.getHibernateTemplate().findByNamedParam(
					hql, names, values);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
		return list.size() == 0 ? null : list.get(0);
	}

}
