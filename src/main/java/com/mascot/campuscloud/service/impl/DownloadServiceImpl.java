package com.mascot.campuscloud.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.mascot.campuscloud.dao.FileDAO;
import com.mascot.campuscloud.dao.LocalFileDAO;
import com.mascot.campuscloud.dao.LocalFolderDAO;
import com.mascot.campuscloud.dao.entity.FileDO;
import com.mascot.campuscloud.dao.entity.LocalFileDO;
import com.mascot.campuscloud.dao.entity.LocalFolderDO;
import com.mascot.campuscloud.service.DownloadService;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class DownloadServiceImpl implements DownloadService {

	@Autowired
	private FileDAO fileDAO;
	@Autowired
	private LocalFileDAO localFileDAO;
	@Autowired
	private LocalFolderDAO localFolderDAO;
	/** key是文件在客户端存储的路径，value是文件在服务器存储的路径 */
	private Map<String, String> filePathMap = new HashMap<>();
	/** 空文件夹路径，用于压缩zip文件时生成空文件夹 */
	private List<String> emptyFolderList = new ArrayList<>();

	@Override
	@Transactional(readOnly = true)
	public String generateZipFilename(List<Long> files, List<Long> folders) {
		StringBuilder filename = new StringBuilder();
		if (!folders.isEmpty()) {
			LocalFolderDO localFolder = localFolderDAO.get(folders.get(0));
			filename.append(localFolder.getLocalName());
		} else {
			LocalFileDO localFile = localFileDAO.get(files.get(0));
			filename.append(getFullFilename(localFile));
		}

		int size = files.size() + folders.size();
		if (size > 1) {
			filename.append("等" + size + "个文件");
		}

		filename.append(".zip");

		return filename.toString();
	}

	@Override
	@Transactional(readOnly = true)
	public void download(List<Long> files, List<Long> folders, OutputStream out) throws IOException {
		/* 生成zip压缩文件内的目录结构 */
		String parentPath = "";// 初始路径为空
		for (Long folderID : folders) {
			LocalFolderDO folder = localFolderDAO.get(folderID);
			generateFolderPath(parentPath, folder);
		}
		for (Long fileID : files) {
			LocalFileDO file = localFileDAO.get(fileID);
			generateFilePath(parentPath, file);
		}

		ZipOutputStream zos = null;
		FileInputStream fis = null;
		try {
			zos = new ZipOutputStream(out);
			for (Entry<String, String> entry : filePathMap.entrySet()) {
				zos.putNextEntry(new ZipEntry(entry.getKey()));
				File file = new File(entry.getValue());
				try {
					fis = new FileInputStream(file);
					int hasRead;
					byte[] buffer = new byte[102400];
					while ((hasRead = fis.read(buffer)) != -1) {
						zos.write(buffer, 0, hasRead);
					}
				} finally {
					if (fis != null) {
						fis.close();
					}
				}
			}
			/* 往zip里添加空文件夹 */
			for (String emptyFolder : emptyFolderList) {
				zos.putNextEntry(new ZipEntry(emptyFolder));
			}
		} finally {
			zos.close();
		}
	}

	/**
	 * 生成当前文件夹路径，若文件夹为空，将其路径加入emptyFolderList
	 * 
	 * @param parentPath
	 *            上一级路径
	 * @param folder
	 *            当前文件夹实体对象
	 */
	private void generateFolderPath(String parentPath, LocalFolderDO folder) {
		if (parentPath.length() > 0) {
			parentPath = parentPath + File.separator + folder.getLocalName();
		} else {
			parentPath = folder.getLocalName();
		}

		boolean emptyFolder = true;

		List<LocalFolderDO> subfolders = localFolderDAO.listByParent(folder.getId());
		for (LocalFolderDO subfolder : subfolders) {
			emptyFolder = false;
			generateFolderPath(parentPath, subfolder);
		}

		List<LocalFileDO> subfiles = localFileDAO.listByParent(folder.getId());
		for (LocalFileDO subfile : subfiles) {
			emptyFolder = false;
			generateFilePath(parentPath, subfile);
		}

		if (emptyFolder) {
			parentPath += File.separator;// 需要在路径后面加一个分隔符才会生成空文件夹
			emptyFolderList.add(parentPath);
		}
	}

	/**
	 * 生成当前文件的本地路径并获取文件的服务器路径 把两个路径分别作为key（本地）,value（服务器）存入filePathMap中
	 * 
	 * @param parentPath
	 *            上一级路径
	 * @param localFile
	 *            当前文件实体对象
	 */
	private void generateFilePath(String parentPath, LocalFileDO localFile) {
		String filename = getFullFilename(localFile);
		String filePath;
		if (parentPath.length() > 0) {
			filePath = parentPath + File.separator + filename;
		} else {
			filePath = filename;
		}
		FileDO file = fileDAO.get(localFile.getFileID());
		filePathMap.put(filePath, FILE_BASE + file.getMd5());
	}
}
