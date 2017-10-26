package com.ls.service;

import java.util.List;
/**
 * @author cjw 2017-05-04
 * 
 */
public interface BaseService<T, PK> {
	List<T> getAllObjects(Class<T> entityClass);

	T getObjectById(Class<T> entityClass, PK id);

	T loadObjectById(Class<T> entityClass, PK id);

	void saveObject(T entity);

	void saveOrUpdateObject(T entity);

	void deleteObject(T entity);

	void deleteObjectById(Class<T> entityClass, PK id);

	void updateObject(T entity);
}