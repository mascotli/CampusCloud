package com.mascot.campuscloud.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mascot.campuscloud.manager.JWTUtil;
import com.mascot.campuscloud.manager.StringUtils;

import io.jsonwebtoken.ExpiredJwtException;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	/** Token的类型 */
	private static final String TOKEN_TYPE_BEARER = "Bearer";

	// private static String[] NEED_TO_LOGIN_PAGES = { "/CampusCloud/home/", "/CampusCloud/home" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = null;
		if ((token = request.getParameter("token")) == null) {
			token = request.getHeader("Authorization");
		}

		if (token == null) {
			return false;
		}

		String username = null;
		try {
			String[] tokenComponent = token.split(" ");
			if (!tokenComponent[0].equals(TOKEN_TYPE_BEARER)) {
				throw new IllegalArgumentException("Unsupported token type");
			}
			username = JWTUtil.parseToken(tokenComponent[1]);
		} catch (ExpiredJwtException e) {
			return false;
		} catch (Exception e) {
			return false;
		}

		if (StringUtils.isEmpty(username))
			return false; // 未登陆
		else
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
