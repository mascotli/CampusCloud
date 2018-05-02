package com.mascot.campuscloud.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mascot.campuscloud.dao.entity.TokenModelDO;
import com.mascot.campuscloud.manager.JWTUtil;
import com.mascot.campuscloud.manager.StringUtils;
import com.mascot.campuscloud.manager.constant.TokenConst;
import com.mascot.campuscloud.manager.exception.TokenErrorException;
import com.mascot.campuscloud.service.TokenManagerService;

@Service("redisTokenManagerService")
public class RedisTokenManagerServiceImpl implements TokenManagerService {

	@Resource(name = "redistoken")
	private RedisTemplate<String, String> redis;

	@Override
	public TokenModelDO createToken(String username) {
		// 使用uuid作为源token
		// String token = UUID.randomUUID().toString().replace("-", "");
		String token = JWTUtil.generateToken(username);
		TokenModelDO model = new TokenModelDO(username, token);
		// 存储到redis并设置过期时间
		redis.boundValueOps(username).set(token, TokenConst.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
		return model;
	}

	public void setToken(TokenModelDO token) throws TokenErrorException {
		if (null == token || StringUtils.isEmpty(token.getToken()) || StringUtils.isEmpty(token.getUsername())) {
			throw new TokenErrorException("无效令牌");
		}
		redis.boundValueOps(token.getUsername()).set(token.getToken(), TokenConst.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
	}

	@Override
	public boolean checkToken(TokenModelDO model) {
		if (model == null) {
			return false;
		}
		String token = redis.boundValueOps(model.getUsername()).get();
		if (token == null || !token.equals(model.getToken())) {
			return false;
		}
		// 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
		redis.boundValueOps(model.getUsername()).expire(TokenConst.TOKEN_EXPIRES_HOUR_ADD, TimeUnit.HOURS);
		return true;
	}

	@Override
	public TokenModelDO getToken(String authentication) {
		if (authentication == null || authentication.length() == 0) {
			return null;
		}
		String[] param = authentication.split("_");
		if (param.length != 2) {
			return null;
		}
		// 使用userId和源token简单拼接成的token，可以增加加密措施
		// String username = Long.parseLong(param[0]);
		{
			String username = param[0];
			String token = param[1];
			return new TokenModelDO(username, token);
		}
	}

	@Override
	public void deleteToken(String username) {
		redis.delete(username);
	}

}
