package com.mascot.campuscloud.web.filter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mascot.campuscloud.dao.entity.TokenModelDO;
import com.mascot.campuscloud.manager.JWTUtil;
import com.mascot.campuscloud.manager.StringUtils;
import com.mascot.campuscloud.service.TokenManagerService;

import io.jsonwebtoken.ExpiredJwtException;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "redisTokenManagerService")
	TokenManagerService redisTokenManagerService;

	/** Token的类型 */
	private static final String TOKEN_TYPE_BEARER = "Bearer";

	// private static String[] NEED_TO_LOGIN_PAGES = { "/CampusCloud/home/",
	// "/CampusCloud/home" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		String token = null;
		if ((token = request.getParameter("token")) == null) {
			token = request.getHeader("Authorization");
		}

		if (token == null) {
			request.getRequestDispatcher("/").forward(request, response); // 未登录转发到首页登陆
			return false;
		}

		String username = null;
		boolean hasLogined = false;
		try {
			String[] tokenComponent = token.split(" ");
			if (!tokenComponent[0].equals(TOKEN_TYPE_BEARER)) {
				throw new IllegalArgumentException("Unsupported token type");
			}
			{
				username = JWTUtil.parseToken(tokenComponent[1]);
				hasLogined = redisTokenManagerService.checkToken(new TokenModelDO(username, tokenComponent[1]));
			}
		} catch (ExpiredJwtException e) {
			request.getRequestDispatcher("/").forward(request, response);
			return false;
		} catch (Exception e) {
			request.getRequestDispatcher("/").forward(request, response);
			return false;
		}

		if (StringUtils.isEmpty(username) || !hasLogined) {
			request.getRequestDispatcher("/").forward(request, response);
			return false; // 未登陆
		} else
			return true; // 登陆过，不检查是否超时

		// 已经登陆
		// if (request.getRequestURI().matches(NEED_TO_LOGIN_PAGES[0])
		// || request.getRequestURI().matches(NEED_TO_LOGIN_PAGES[1])) {
		// return true;
		// } else {
		// return false;
		// }
	}

}
