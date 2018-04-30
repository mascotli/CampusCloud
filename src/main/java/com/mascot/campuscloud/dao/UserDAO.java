package com.mascot.campuscloud.dao;

import com.mascot.campuscloud.dao.entity.UserDO;

public interface UserDAO extends GenericDAO<UserDO> {
	UserDO getByUsername(String username);
}
