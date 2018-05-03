package com.mascot.campuscloud.service;

import java.io.File;

import com.mascot.campuscloud.dao.entity.LocalFileDO;

public interface FileService {
	/** 服务端保存所有文件的根路径 */
	String FILE_BASE = "C:/ProgramData/CampusCloud_Upload/" + File.separator;
	/** 所有上传文件URL的根 */
	String URL_ROOT = "http://localhost:8080/CampusCloud_Upload/";

	default String getFullFilename(LocalFileDO localFile) {
		return localFile.getLocalName() + "." + localFile.getLocalType();
	}
}
