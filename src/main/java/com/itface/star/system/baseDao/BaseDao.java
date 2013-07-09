package com.itface.star.system.baseDao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T>{
	public T update(T t);
	public T persist(T t);
	public void delete(T t);
	public void deleteList(List<T> list);
	public void deleteById(Class<T> clazz, Serializable id);
	public T findSingleResult(String jpql, Object[] param);
	public List<T> findByPage(String jpql, Object[] param,int pageNumber,int rowsPerPage);
	public T find(Class<T> clazz, Serializable id);
	public List<T> find(String jpql);
	public List<T> find(String jpql, Object[] param);
	public long findTotalCount(String jpql,Object[] param);
	public void saveList(List<T> list);
	public void executeUpdate(String jpql, Object[] param);
	public List<T> findNotInContext(String jpql, Object[] param);
	public void executeUpdateNotInContext(String jpql, Object[] param);
	public T updateNotInContext(T t);
}
