package com.mascot.campuscloud.service;

import java.util.List;
import java.util.Map;

import com.mascot.campuscloud.dao.entity.LocalFolderDO;
import com.mascot.campuscloud.service.dto.LocalFileDTO;
import com.mascot.campuscloud.service.dto.LocalFolderDTO;
import com.mascot.campuscloud.service.dto.UserDTO;

public interface DiskService {
	Map<String, Object> getMenuContents(long userID, String menu);

	Map<String, Object> getFolderContents(long userID, long folderID, int sortType);

	Map<String, Object> search(long userID, String input);

	Map<String, Object> move(List<Long> folders, List<Long> files, long dest);

	LocalFolderDTO renameFolder(long folderID, String localName);

	LocalFileDTO renameFile(long fileID, String localName, String localType);

	LocalFolderDTO newFolder(LocalFolderDO unsaved);

	UserDTO shred(List<Long> folders, List<Long> files, long userID);
}
