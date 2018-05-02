package com.mascot.campuscloud.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mascot.campuscloud.dao.entity.TokenModelDO;
import com.mascot.campuscloud.manager.exception.IncorrectPasswordException;
import com.mascot.campuscloud.manager.exception.NoSuchUserException;
import com.mascot.campuscloud.manager.exception.TokenErrorException;
import com.mascot.campuscloud.service.TokenManagerService;
import com.mascot.campuscloud.service.UserService;
import com.mascot.campuscloud.web.reqbody.LoginReqBody;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json", consumes = "application/json")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Resource(name = "redisTokenManagerService")
	TokenManagerService redisTokenManagerService;

	/**
	 * POST api/v1/authentication <br />
	 * 检查用户名与密码，若用户名与密码匹配，返回一个该用户的Token， 该Token可用于访问该用户的资源
	 * @throws TokenErrorException 服务器出错
	 * @throws NoSuchUserException
	 * @throws IncorrectPasswordException
	 */
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	public Map<String, Object> authentication(@RequestBody @Valid LoginReqBody reqBody)
			throws NoSuchUserException, IncorrectPasswordException, TokenErrorException {
		String token = userService.login(reqBody.getUsername(), reqBody.getPassword());

		// 保存 token和userid 实现在线上人数统计/单点登录/登出/...
		{
			redisTokenManagerService.setToken(new TokenModelDO(reqBody.getUsername(), token));
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("token", token);
		return result;
	}

	// @ExceptionHandler(NoSuchUserException.class)
	// public ResponseEntity<Error> noSuchUser(NoSuchUserException e) {
	// Error error = new Error("username not fonund");
	// return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	// }
	//
	// @ExceptionHandler(IncorrectPasswordException.class)
	// public ResponseEntity<Error> incorrectPassword(IncorrectPasswordException e)
	// {
	// Error error = new Error("incorrect password");
	// return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
	// }
}
