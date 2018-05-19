package com.mascot.campuscloud.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mascot.campuscloud.config.RootConfig;
import com.mascot.campuscloud.config.WebConfig;
import com.mascot.campuscloud.dao.FileDAO;
import com.mascot.campuscloud.dao.HdfsDAO;
import com.mascot.campuscloud.dao.LocalFileDAO;
import com.mascot.campuscloud.dao.LocalFolderDAO;
import com.mascot.campuscloud.dao.UserDAO;
import com.mascot.campuscloud.dao.entity.FileDO;
import com.mascot.campuscloud.dao.entity.LocalFileDO;
import com.mascot.campuscloud.dao.entity.LocalFolderDO;
import com.mascot.campuscloud.dao.entity.UserDO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class,RootConfig.class } )
public class HdfsDAOTest {
    
    @Autowired
    private HdfsDAO hdfsDAO;
    
    @Transactional @Test
    public void testHdfsDAO () {
    	FileDO file = new FileDO();
		UserDO user = new UserDO();
		file.setId(01L);
		file.setLdtCreate(LocalDateTime.now());
		file.setLdtModified(LocalDateTime.now());
		file.setMd5("031440222");
		file.setSize(676745L);
		file.setType("video");
		file.setUrl("hdfs://godfather.mascot.com.cn:9000/CampusCloudStorage/31440222/video/");
		user.setId(031440222L);
		user.setLdtCreate(LocalDateTime.now());
		user.setLdtModified(LocalDateTime.now());
		user.setNickname("Mascot.Lee");
		user.setPassword("365346458768");
		user.setPhotoURL("hdfs://godfather.mascot.com.cn:9000/CampusCloudStorage/31440222/photo/head.png");
		user.setUsername("Mascot.Lee");
		user.setUsedCapacity(0L);
		try {
			hdfsDAO.mkDir(file, user);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
    }
    
}
