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

import com.ls.cenum.TaskExeEnum;
import com.ls.cenum.TaskRecStateEnum;
import com.ls.cenum.TaskTerOperEnum;
import com.ls.dao.TerTaskDao;
import com.ls.domain.TerTaskInfo;
import com.ls.util.Page;
import com.ls.util.StringUtil;

/** 
* @ClassName: TerTaskDaoImpl 
* @Description: 
* @author huanglei
* @date 2017年9月20日 下午3:42:39 
* @version V1.0    
*/
@Repository
public class TerTaskDaoImpl extends BaseDAOImpl<TerTaskInfo,Integer> implements TerTaskDao{

	@Override
	public TerTaskInfo getDevVersionByDevNum(final String devNum,final boolean flag) {
		TerTaskInfo dVersion = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<TerTaskInfo>() {
			@Override
			public TerTaskInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(TerTaskInfo.class);
				criteria.add(Restrictions.eq("deviceNum", devNum));
				criteria.add(Restrictions.eq("status", TaskExeEnum.NO_EXECUTED.getIndex()));
				if(flag){
					criteria.add(Restrictions.eq("isSend", TaskRecStateEnum.NO_RECEIVED.getIndex()));
				}else{
					criteria.add(Restrictions.eq("isSend", TaskRecStateEnum.RECEIVED.getIndex()));
				}
				@SuppressWarnings("unchecked")
				List<TerTaskInfo> tess = criteria.list();
				return tess.size()>0?tess.get(0):null;
			}
		});
		return dVersion;
	}
	
	@Override
	public TerTaskInfo getDevVersionByVersion(final String devNum,final String bVersion) {
		TerTaskInfo dVersion = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<TerTaskInfo>() {
			@Override
			public TerTaskInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(TerTaskInfo.class);
				criteria.add(Restrictions.eq("deviceNum", devNum));
				criteria.add(Restrictions.eq("bVersion", bVersion));
				criteria.add(Restrictions.eq("status", TaskExeEnum.EXECUTED.getIndex()));
				criteria.add(Restrictions.eq("isSend", TaskRecStateEnum.RECEIVED.getIndex()));
				return (TerTaskInfo) criteria.uniqueResult();
			}
		});
		return dVersion;
	}

	@Override
	public List<TerTaskInfo> queryTerConfigsByDevNum(final String devNum,final boolean flag) {
		List<TerTaskInfo> dVersion = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<TerTaskInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<TerTaskInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(TerTaskInfo.class);
				criteria.add(Restrictions.eq("deviceNum", devNum));
				criteria.add(Restrictions.eq("status", TaskExeEnum.NO_EXECUTED.getIndex()));
				criteria.add(Restrictions.eq("operType", TaskTerOperEnum.CONIFG_MODIFY.getIndex()));
				if(flag){
					criteria.add(Restrictions.eq("isSend", TaskRecStateEnum.NO_RECEIVED.getIndex()));
				}else{
					criteria.add(Restrictions.eq("isSend", TaskRecStateEnum.RECEIVED.getIndex()));
				}
				return criteria.list();
			}
		});
		return dVersion;
	}

	@Override
	public TerTaskInfo getDevVersionByDevNum(final String devNum) {
		TerTaskInfo dVersion = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<TerTaskInfo>() {
			@Override
			public TerTaskInfo doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(TerTaskInfo.class);
				criteria.add(Restrictions.eq("deviceNum", devNum));
				criteria.add(Restrictions.eq("status", TaskExeEnum.NO_EXECUTED.getIndex()));
				criteria.add(Restrictions.eq("isSend", TaskRecStateEnum.NO_RECEIVED.getIndex()));
				@SuppressWarnings("unchecked")
				List<TerTaskInfo> tess = criteria.list();
				return tess.size()>0?tess.get(0):null;
			}
		});
		return dVersion;
	}
	@Override
	public int getTotalCount(final TerTaskInfo terTaskInfo) {
		int count = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(TerTaskInfo.class);
				TerTaskExtracted(terTaskInfo, criteria);
				criteria.setProjection(Projections.rowCount());
				Number number =  (Number) criteria.uniqueResult();
				return number.intValue();
			}
		});
		return count;
	}

	@Override
	public List<TerTaskInfo> listTerTaskByPage(final Page page,final TerTaskInfo terTaskInfo) {
		List<TerTaskInfo> terList = this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<TerTaskInfo>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<TerTaskInfo> doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(TerTaskInfo.class);
				TerTaskExtracted(terTaskInfo, criteria);
				criteria.addOrder(Order.desc("createTime"));
				criteria.setFirstResult(page.getBeginIndex());
			    criteria.setMaxResults(page.getEveryPage());
				return criteria.list();
			}
		});
		return terList;
	}

	/**
	 * @param terTaskInfo
	 * @param criteria
	 */
	private void TerTaskExtracted(final TerTaskInfo terTaskInfo, Criteria criteria) {
		if(StringUtil.isNotEmpty(terTaskInfo.getCreateTime())){
			criteria.add(Restrictions.like("createTime",terTaskInfo.getCreateTime(), MatchMode.ANYWHERE));
		}
		if(terTaskInfo.getIsSend() != null){
			criteria.add(Restrictions.eq("isSend", terTaskInfo.getIsSend()));
		}
		if(terTaskInfo.getStatus() != null){
			criteria.add(Restrictions.eq("status", terTaskInfo.getStatus()));
		}
		if(terTaskInfo.getOperType() != null){
			criteria.add(Restrictions.eq("operType", terTaskInfo.getOperType()));
		}
		if(StringUtil.isNotEmpty(terTaskInfo.getDeviceNum())){
			criteria.add(Restrictions.like("deviceNum",terTaskInfo.getDeviceNum(), MatchMode.ANYWHERE));
		}
	}
}
