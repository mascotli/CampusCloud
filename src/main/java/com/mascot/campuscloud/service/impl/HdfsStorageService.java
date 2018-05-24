package com.mascot.campuscloud.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mascot.campuscloud.dao.HdfsDAO;
import com.mascot.campuscloud.dao.entity.FileDO;
import com.mascot.campuscloud.dao.entity.UserDO;
import com.mascot.campuscloud.manager.exception.StorageException;
import com.mascot.campuscloud.service.StorageService;

@Service(value = "hdfsStorageService")
public class HdfsStorageService implements StorageService {
		
	private @javax.annotation.Resource(name = "hdfsDAO") HdfsDAO hdfs;

	@Override
	public void init() {
		
	}

	@Override
	public void store(MultipartFile file) {
		
	}
	
	@Override
	public void store(File file) {
		
	}
	
	@Override
	public void store(InputStream file, String path) {
		
	}

	@Override
	public void store(InputStream is, FileDO file, UserDO user) {
		try {			
			hdfs.put(is, file, user);
		} catch (IllegalArgumentException | IOException e) {
			throw new StorageException("Faild to storage file: " + file.getMd5(), e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		return null;
	}

	@Override
	public Path load(String filename) {
		return null;
	}

	@Override
	public Resource loadAsResource(String filename) {
		return null;
	}

	@Override
	public void deleteAll() {

	}

}
