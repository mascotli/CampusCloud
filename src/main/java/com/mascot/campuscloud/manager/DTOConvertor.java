package com.mascot.campuscloud.manager;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mascot.campuscloud.dao.FileDAO;
import com.mascot.campuscloud.dao.entity.FileDO;
import com.mascot.campuscloud.dao.entity.LocalFileDO;
import com.mascot.campuscloud.dao.entity.LocalFolderDO;
import com.mascot.campuscloud.dao.entity.UserDO;
import com.mascot.campuscloud.service.dto.LocalFileDTO;
import com.mascot.campuscloud.service.dto.LocalFolderDTO;
import com.mascot.campuscloud.service.dto.UserDTO;

/**
 * 将DO转换为DTO的工具类
 */
@Component
public class DTOConvertor {

	@Autowired
	private ModelMapper modelMapper;

	public UserDTO convertToDTO(UserDO user) {
		return modelMapper.map(user, UserDTO.class);
	}

	public LocalFolderDTO convertToDTO(LocalFolderDO entity) {
		return modelMapper.map(entity, LocalFolderDTO.class);
	}

	@Autowired
	private FileDAO fileDAO;

	@Transactional(readOnly = true)
	public LocalFileDTO convertToDTO(LocalFileDO localFileEntity, FileDO fileEntity) {
		LocalFileDTO dto = modelMapper.map(localFileEntity, LocalFileDTO.class);
		if (fileEntity == null) {
			fileEntity = fileDAO.get(localFileEntity.getFileID());
		}
		dto.setSize(fileEntity.getSize());
		dto.setUrl(fileEntity.getUrl());
		return dto;
	}

	public List<LocalFileDTO> convertFileList(List<LocalFileDO> entityList) {
		if (entityList != null) {
			List<LocalFileDTO> dtoList = new ArrayList<>();
			for (LocalFileDO entity : entityList) {
				LocalFileDTO dto = convertToDTO(entity, null);
				dtoList.add(dto);
			}
			return dtoList;
		} else {
			return null;
		}
	}

	public List<LocalFolderDTO> convertFolderList(List<LocalFolderDO> entityList) {
		if (entityList != null) {
			List<LocalFolderDTO> dtoList = new ArrayList<>();
			for (LocalFolderDO entity : entityList) {
				LocalFolderDTO dto = convertToDTO(entity);
				dtoList.add(dto);
			}
			return dtoList;
		} else {
			return null;
		}
	}

}
