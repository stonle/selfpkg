package com.ls.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.ls.dao.BaseDAO;

/**
 * @author cjw 2017-05-04
 * 数据库通过方法BaseDAOImpl实现
 */
@Repository("baseDaoImpl")
public class BaseDAOImpl<T, PK> extends HibernateDaoSupport implements BaseDAO<T, PK> {

	@Resource(name = "sessionFactory")
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List<T> getAllObjects(Class<T> entityClass) {
		List<T> entities = null;
		try {
			entities = this.getHibernateTemplate().loadAll(entityClass);
		} catch (RuntimeException e) {

			throw new RuntimeException(e.getMessage());
		}
		return entities;
	}

	public T getObjectById(Class<T> entityClass, PK id) {
		T entities = null;
		try {
			HibernateTemplate ht = this.getHibernateTemplate();
			entities = (T) ht.get(entityClass, (Serializable) id);

		} catch (RuntimeException e) {

			throw new RuntimeException(e.getMessage());
		}
		return entities;
	}

	public T loadObjectById(Class<T> entityClass, PK id) {
		T entities = null;
		try {
			HibernateTemplate ht = this.getHibernateTemplate();
			entities = (T) ht.load(entityClass, (Serializable) id);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
		return entities;
	}

	public void saveObject(T entity) {

		try {
			this.getHibernateTemplate().save(entity);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void deleteObject(T entity) {

		try {
			this.getHibernateTemplate().delete(entity);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void deleteObjectById(Class<T> entityClass, PK id) {

		try {
			this.getHibernateTemplate().delete(this.getObjectById(entityClass, id));
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void updateObject(T entity) {
		try {
			this.getHibernateTemplate().update(entity);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void saveOrUpdateObject(T entity) {
		try {
			this.getHibernateTemplate().saveOrUpdate(entity);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void megreObject(T entity) {
		
		try {
			this.getHibernateTemplate().merge(entity);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}