package com.ls.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ls.dao.BaseDAO;
import com.ls.dao.impl.BaseDAOImpl;
import com.ls.service.BaseService;

/**
 * @author cjw 2017-05-04
 * 
 */
@Service("baseServiceImpl")
public class BaseServiceImpl<T, PK> extends BaseDAOImpl<T, PK> implements BaseService<T, PK> {
	@Resource(name = "baseDaoImpl")
	private BaseDAO<T, PK> baseDao;

	public List<T> getAllObjects(Class<T> entityClass) {
		try {
			return baseDao.getAllObjects(entityClass);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public T getObjectById(Class<T> entityClass, PK id) {
		try {
			return baseDao.getObjectById(entityClass, id);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public T loadObjectById(Class<T> entityClass, PK id) {
		try {
			return baseDao.loadObjectById(entityClass, id);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void saveObject(T entity) {
		try {
			baseDao.saveObject(entity);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void deleteObject(T entity) throws RuntimeException {
		try {
			baseDao.deleteObject(entity);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void deleteObjectById(Class<T> entityClass, PK id) {
		try {
			baseDao.deleteObjectById(entityClass, id);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void updateObject(T entity) throws RuntimeException {
		try {
			baseDao.updateObject(entity);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void saveOrUpdateObject(T entity) throws RuntimeException {
		try {
			baseDao.saveOrUpdateObject(entity);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
