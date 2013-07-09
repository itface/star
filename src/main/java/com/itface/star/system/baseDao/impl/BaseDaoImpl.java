package com.itface.star.system.baseDao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itface.star.system.baseDao.BaseDao;
@Repository
public class BaseDaoImpl<T> implements BaseDao<T>{

	@PersistenceContext
    private EntityManager em; 
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	@Override
	public void delete(T t) {
		// TODO Auto-generated method stub
		em.remove(t);
	}

	@Override
	public T find(Class<T> clazz, Serializable id) {
		// TODO Auto-generated method stub
		//em.getReference(clazz, id);延迟加载，关闭session后，对象只有id
		return em.find(clazz, id);
	}

	@Override
	public List<T> find(String jpql) {
		// TODO Auto-generated method stub
		return em.createQuery(jpql).getResultList();
	}
	@Override
	public List<T> findByPage(String jpql, Object[] param,int pageNumber,int rowsPerPage) {
		int start = (pageNumber-1)*rowsPerPage;
		int end = start+rowsPerPage;
		Query query = em.createQuery(jpql);
		if(param!=null){
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		query.setFirstResult(start);
		query.setMaxResults(end);
		return query.getResultList();
	}
	@Override
	public List<T> find(String jpql, Object[] param) {
		// TODO Auto-generated method stub
		Query query = em.createQuery(jpql);
		for (int i = 1; i <= param.length; i++) {
			query.setParameter(i, param[i - 1]);
		}
		return query.getResultList();
	}

	@Override
	public T persist(T t) {
		// TODO Auto-generated method stub
		em.persist(t);
		return t;
	}

	@Override
	public T update(T t) {
		// TODO Auto-generated method stub
		return em.merge(t);
	}
	@Override
	public T updateNotInContext(T t) {
		// TODO Auto-generated method stub
		EntityManager em2 = entityManagerFactory.createEntityManager();
		try{
			t = em2.merge(t);
		}finally{
			em2.close();
		}
		return t;
	}

	@Override
	public void deleteById(Class<T> clazz, Serializable id) {
		// TODO Auto-generated method stub
		T t = this.find(clazz, id);
		if(t!=null){
			this.delete(t);
		}
	}

	@Override
	public long findTotalCount(String jpql, Object[] param) {
		// TODO Auto-generated method stub
		Query query = em.createQuery(jpql);
		if(param!=null){
			//这个版本要求hql改成use named parameters or JPA-style positional parameters，以前的from Model where id=?这种语句要改成from Model where id=?0，设置参数要用org.hibernate.Query.setParameter("0", java.lang.Object)才行，否则升级完系统会有警告
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		return (Long) query.getSingleResult();
	}

	@Override
	public void saveList(List<T> list) {
		// TODO Auto-generated method stub
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Object obj = list.get(i);
				em.persist(obj);
			}
		}
	}

	@Override
	public void executeUpdate(String jpql, Object[] param) {
		// TODO Auto-generated method stub
		Query query = em.createQuery(jpql);
		if(param!=null){
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		query.executeUpdate();
	}

	@Override
	public void executeUpdateNotInContext(String jpql, Object[] param) {
		// TODO Auto-generated method stub
		EntityManager em2 = entityManagerFactory.createEntityManager();
		try{
			Query query = em2.createQuery(jpql);
			if(param!=null){
				for (int i = 1; i <= param.length; i++) {
					query.setParameter(i, param[i - 1]);
				}
			}
			query.executeUpdate();
		}finally{
			em2.close();
		}
		
	}
	@Override
	public List<T> findNotInContext(String jpql, Object[] param) {
		// TODO Auto-generated method stub
		List<T> list = null;
		Query query;
		EntityManager em2 = entityManagerFactory.createEntityManager();
		try {
			query = em2.createQuery(jpql);
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
			list = query.getResultList();
		}finally{
			em2.close();
		}
		return list;
	}

	@Override
	public T findSingleResult(String jpql, Object[] param) {
		// TODO Auto-generated method stub
		//在getSingleResult的源码里有这样一句： @throws EntityNotFoundException if there is no result也就是说，查不到结果时，它是抛异常的，不会返回null..
		List<T> list = this.find(jpql, param);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void deleteList(List<T> list) {
		// TODO Auto-generated method stub
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Object obj = list.get(i);
				this.delete((T)obj);
			}
		}
	}
}
