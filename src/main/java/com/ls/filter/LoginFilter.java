package com.ls.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginFilter implements Filter {

	public LoginFilter() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setContentType("text/html;charset=UTF-8");// 设置编码
		req.setCharacterEncoding("UTF-8");

		// 获得请求的URL格式:http://localhost:8080/oa/general/index.jsp
		PrintWriter out = res.getWriter();// 创建输出对象
		// 获得session中的对象
		HttpSession session = req.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object>  login = (Map<String, Object>) session.getAttribute("session");
		// 使用endsWith()判断url结尾的字符串
		/*
		 * //
		 * System.out.println("------------------------->"+req.getServletPath(
		 * )); if(req.getServletPath().indexOf(".jsp") >= 0 ||
		 * req.getServletPath().indexOf(".JSP") >= 0){
		 * 
		 * }
		 */
		
		
		
		if (login == null) {// 不满足条件就跳转到其他页面
			System.out.println("无登录信息--------"+login);
			out.print("<script language>window.top.location.href='../'</script>");
			// ((HttpServletResponse) response).sendRedirect("../");
		} else {
			System.out.println("---------已登录--------"+login.toString());
			chain.doFilter(req, res);
		//	 System.out.println("--------"+login.get("group"));
			// 满足条件就继续执行
			//根据用户的菜单判断用户是否具有请求jsp页面的权限
//			1.获取用户的访问路径
//			2.查询当前访问的URL在菜单列表（2级）中是否存在
//				a.不存在，跳转到404错误页面
//				b.存在，判断是否具有访问权限
//					1）有：放行
//					2）没有：拦截，跳转到没有访问权限的页面
			
//			// 获得请求的URL格式:http://localhost:8080/oa/general/index.jsp
//			 String requrl=req.getRequestURL().toString();
//			 if(requrl.endsWith("/") || requrl.endsWith("welcome.jsp")){
//				 chain.doFilter(req, res);
//			 }
//			 requrl = requrl.substring(requrl.lastIndexOf("/")+1, requrl.length()-1);
//			 List<Menu> list = menuService.listLevelTwoMenu();//所有的
//			 List<Menu> userLevel2Menu = UserContext.getMenuLevel2();//用户的二级菜单
//			 boolean isExist = false;
//			 for (Menu menu : list) {
//				if(requrl.equals(menu.getMenuUrl())){
//					isExist = true;
//				}
//			 }
//			 if(isExist){//存在
//				 boolean flag = false;
//				 for (Menu menu : userLevel2Menu) {
//					 if(requrl.equals(menu.getMenuUrl())){
//						 flag = true;
//					 }
//				 }
//				 if(flag){//存在，放行
//					 chain.doFilter(req, res);
//				 }else{//没有权限，跳转到无访问权限页面
//					 
//					  ((HttpServletResponse) response).sendRedirect("../406.html");
//				 }
//			 }else{
//				 
//			 }
		}

	}

	public void destroy() {
		// System.out.println("OnlineFilter destroied");
	}

	/**
	 * 
	 * @see Filter#init(FilterConfig)
	 */

	public void init(FilterConfig fConfig) throws ServletException {
		// System.out.println("OnlineFilter initialized.");
	}
}