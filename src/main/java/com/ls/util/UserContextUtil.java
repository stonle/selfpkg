package com.ls.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ls.domain.User;

public class UserContextUtil {
	public static HttpServletRequest getRequest() {
		// 1、从RequestContextHolder中，获取ServletRequestAttributes
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		// 2、从ServletRequestAttributes对象中，取出request对象
		HttpServletRequest request = requestAttributes.getRequest();
		return request;
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 获取session（缓存）中的用户ID
	 */
	public static String getUserID() {
		@SuppressWarnings("unchecked")
		Map<String, Object> userInfo = (Map<String, Object>) getSession().getAttribute("session");
		return (String) userInfo.get("uid");
	}

	/**
	 * 把用户，放入session(缓存)
	 */
	public static void setUser(User user) {
		getSession().setAttribute("user", user);
	}
	
	/**
	 * 获取session（缓存）中的用户
	 */
	public static User getUser() {
		return (User) getSession().getAttribute("user");
	}
	
}
