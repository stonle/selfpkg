package com.ls.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.util.StringUtil;

/**
 * java拦截请求的方式有servlet、
 * filter、
 * listener三种方式，我们采用filter。
 * 在sso-client中新建LoginFilter.java类并实现Filter接口，在doFilter()方法中加入对未登录用户的拦截
 * @author Administrator
 *
 */
public class AppLoginFilter implements Filter{
    private static final String TOKEN = "37a26edd-65e5-4b49-8a07-ab8ef5ec23e5"; 
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String reUri = httpRequest.getRequestURI();
		String contentPath = httpRequest.getContextPath();
		String appRestMethond = reUri.substring(contentPath.length());
        if(appRestMethond.contains("/pages/app") && !appRestMethond.equals("/pages/appmeberLogin.do") && 
        		!appRestMethond.equals("/pages/appaddMember.do")){
        	String token = httpRequest.getHeader("token");
        	if(StringUtil.isNotEmpty(token) && TOKEN.equals(token)){
        		chain.doFilter(request, response);
        	}else{
	    		httpResponse.getWriter().print("No permissions!");
	        	return;
        	}
        }else{
        	chain.doFilter(request, response);
        }	
	}

	@Override
	public void destroy() {	
	}

}
