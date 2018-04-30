package com.mascot.campuscloud.service;

import com.mascot.campuscloud.manager.exception.IncorrectPasswordException;
import com.mascot.campuscloud.manager.exception.NoSuchUserException;
import com.mascot.campuscloud.service.dto.UserDTO;

public interface UserService {

	/**
	 * @return JSON web token if login successfully.
	 * @throws NoSuchUserException
	 *             if username does not exist.
	 * @throws IncorrectPasswordException
	 *             if password is incorrect.
	 */
	String login(String username, String password) throws NoSuchUserException, IncorrectPasswordException;

	/**
	 * 根据username获取用户信息
	 * 
	 * @return 包含用户基本信息的UserDTO对象
	 */
	UserDTO getUser(String username);
}
