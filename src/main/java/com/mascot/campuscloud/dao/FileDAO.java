package com.mascot.campuscloud.dao;

import com.mascot.campuscloud.dao.entity.FileDO;

public interface FileDAO extends GenericDAO<FileDO> {
	FileDO getByMd5(String md5);
}
