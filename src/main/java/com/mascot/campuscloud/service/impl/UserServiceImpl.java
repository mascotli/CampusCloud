package com.mascot.campuscloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mascot.campuscloud.dao.UserDAO;
import com.mascot.campuscloud.dao.entity.UserDO;
import com.mascot.campuscloud.manager.DTOConvertor;
import com.mascot.campuscloud.manager.JWTUtil;
import com.mascot.campuscloud.manager.exception.IncorrectPasswordException;
import com.mascot.campuscloud.manager.exception.NoSuchUserException;
import com.mascot.campuscloud.service.UserService;
import com.mascot.campuscloud.service.dto.UserDTO;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private DTOConvertor convertor;

	@Override
	@Transactional(readOnly = true)
	public String login(String username, String password) throws NoSuchUserException, IncorrectPasswordException {
		UserDO user = userDAO.getByUsername(username);

		if (user == null) {
			throw new NoSuchUserException();
		} else if (user.getPassword().equals(password)) {
			return JWTUtil.generateToken(username);
		} else {
			throw new IncorrectPasswordException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO getUser(String username) {
		UserDO user = userDAO.getByUsername(username);
		if (user == null) {
			return null;
		} else {
			return convertor.convertToDTO(user);
		}
	}
}
