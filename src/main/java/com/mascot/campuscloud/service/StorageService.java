package com.mascot.campuscloud.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.mascot.campuscloud.dao.entity.FileDO;
import com.mascot.campuscloud.dao.entity.UserDO;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);
    
    void store(File file);
    
    void store(InputStream file, String path);
    
    void store(InputStream is, FileDO file, UserDO user);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}
