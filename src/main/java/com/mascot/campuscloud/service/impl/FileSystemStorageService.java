package com.mascot.campuscloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mascot.campuscloud.dao.entity.FileDO;
import com.mascot.campuscloud.dao.entity.UserDO;
import com.mascot.campuscloud.manager.constant.StorageProperties;
import com.mascot.campuscloud.manager.exception.StorageException;
import com.mascot.campuscloud.manager.exception.StorageFileNotFoundException;
import com.mascot.campuscloud.service.StorageService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service(value = "fileSystemStorageService")
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

	@Override
	public void store(File file) {
        try {
            if (!file.exists()) {
            	throw new StorageFileNotFoundException("Could not read file: " + file.getAbsolutePath());
            }
            Files.copy(new FileInputStream(file), this.rootLocation.resolve(file.getPath()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getAbsolutePath(), e);
        }		
	}

	@Override
	public void store(InputStream is, String path) {
        try {
            if (null == is) {
            	throw new StorageException("Could not read from null stream");
            }
            Files.copy(is, this.rootLocation.resolve(path));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + path, e);
        }			
	}

	@Override
	public void store(InputStream is, FileDO file, UserDO user) {
		// TODO Auto-generated method stub
		
	}
}
