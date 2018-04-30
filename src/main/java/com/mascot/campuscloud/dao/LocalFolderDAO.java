package com.mascot.campuscloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mascot.campuscloud.dao.entity.LocalFolderDO;

public interface LocalFolderDAO extends GenericDAO<LocalFolderDO> {
	List<LocalFolderDO> listByParent(Long parent);

	List<LocalFolderDO> listRootContents(@Param("parent") Long parent, @Param("userID") Long userID);

	List<LocalFolderDO> listByName(@Param("userID") Long userID, @Param("name") String name);
}
