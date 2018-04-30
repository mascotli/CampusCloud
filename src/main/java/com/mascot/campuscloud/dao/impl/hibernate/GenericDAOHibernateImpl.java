package com.mascot.campuscloud.dao.impl.hibernate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mascot.campuscloud.dao.GenericDAO;

public abstract class GenericDAOHibernateImpl<T> implements GenericDAO<T> {

	@Autowired
	protected SessionFactory sessionFactory;

	private Class<T> type;

	public GenericDAOHibernateImpl(Class<T> type) {
		this.type = type;
	}

	@Override
	public void save(T obj) {
		Session session = sessionFactory.getCurrentSession();
		long id = (long) session.save(obj);
		try {
			Method setId = type.getDeclaredMethod("setId", Long.class);
			setId.invoke(obj, id);
		} catch (Exception e) {
			throw new RuntimeException("GenericDAO save method encounter a problem");
		}
	}

	@Override
	public T get(long id) {
		Session session = sessionFactory.getCurrentSession();
		T obj = session.get(type, id);
		return obj;
	}

	@Override
	public void update(T obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
		session.flush();
	}

	@Override
	public void remove(T obj) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(obj);
	}

	@Override
	public T get(Map<String, Object> params) {
		List<T> result = list(params);
		if (result.size() == 1) {
			return result.get(0);
		} else if (result.isEmpty()) {
			return null;
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public List<T> list(Map<String, Object> params) {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(type);
		Root<T> root = cq.from(type);
		List<Predicate> predicateList = new ArrayList<>();
		for (Entry<String, Object> entry : params.entrySet()) {
			Predicate condition = cb.equal(root.get(entry.getKey()), entry.getValue());
			predicateList.add(condition);
		}
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<T> query = session.createQuery(cq);
		List<T> result = query.getResultList();

		return result;
	}
}
