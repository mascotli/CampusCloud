package com.mascot.campuscloud.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mascot.campuscloud.config.RootConfig;
import com.mascot.campuscloud.config.WebConfig;
import com.mascot.campuscloud.dao.LocalFileDAO;
import com.mascot.campuscloud.dao.LocalFolderDAO;
import com.mascot.campuscloud.dao.UserDAO;
import com.mascot.campuscloud.dao.entity.LocalFolderDO;
import com.mascot.campuscloud.manager.DTOConvertor;
import com.mascot.campuscloud.service.dto.LocalFileDTO;
import com.mascot.campuscloud.service.dto.LocalFolderDTO;
import com.mascot.campuscloud.service.dto.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class,RootConfig.class } )
@SuppressWarnings("unused")
public class ManagerTest {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LocalFileDAO localFileDAO;
    @Autowired
    private LocalFolderDAO localFolderDAO;
    @Autowired
    private DTOConvertor convertor;
    
    @Transactional @Test
    public void testDataConvertor() {
        UserDTO userDTO = convertor.convertToDTO(userDAO.get(1L));
        assertEquals("admin", userDTO.getUsername());
        
        LocalFolderDTO localFolderDTO = convertor.convertToDTO(localFolderDAO.get(1L));
        System.out.println(localFolderDTO);
        
        LocalFileDTO localFileDTO = convertor.convertToDTO(localFileDAO.get(2L), null);
        System.out.println(localFileDTO);
    }
}








