package com.mascot.campuscloud.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mascot.campuscloud.dao.LocalFolderDAO;
import com.mascot.campuscloud.dao.entity.LocalFolderDO;

@Repository
public class LocalFolderDAOHibernateImpl extends GenericDAOHibernateImpl<LocalFolderDO> implements LocalFolderDAO {

	public LocalFolderDAOHibernateImpl() {
		super(LocalFolderDO.class);
	}

	@Override
	public List<LocalFolderDO> listByParent(Long parent) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<LocalFolderDO> query = session.createQuery("from LocalFolderDO folder where folder.parent = :parent");
		query.setParameter("parent", parent);
		List<LocalFolderDO> result = query.list();
		return result;
	}

	@Override
	public List<LocalFolderDO> listRootContents(Long parent, Long userID) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<LocalFolderDO> query = session
				.createQuery("from LocalFolderDO folder where folder.parent = :parent and folder.userID = :userID");
		query.setParameter("parent", parent);
		query.setParameter("userID", userID);
		List<LocalFolderDO> result = query.list();
		return result;
	}

	@Override
	public List<LocalFolderDO> listByName(Long userID, String name) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<LocalFolderDO> query = session.createQuery(
				"from LocalFolderDO folder where folder.parent!=2 and folder.parent!=3 and folder.userID=:userID and folder.localName like :name");
		query.setParameter("userID", userID);
		query.setParameter("name", "%" + name + "%");
		List<LocalFolderDO> result = query.list();
		return result;
	}

}
