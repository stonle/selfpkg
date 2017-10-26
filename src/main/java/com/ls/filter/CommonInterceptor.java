package com.ls.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * @author cjw 2017-05-04
 *
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
	private final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

	/**
	 * 在请求处理之前执行，该方法主要是用于准备资源数据的，然后可以把它们当做请求属性放到WebRequest中
	 */

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.info("==============执行顺序: 1、preHandle================");
		String requestUri = request.getRequestURI();
		@SuppressWarnings("unused")
		String contextPath = request.getContextPath();
		String mapp = requestUri.substring(8, requestUri.length());
		//判断session是否存在用户
		@SuppressWarnings("unchecked")
		Map<String, Object>  login = (Map<String, Object>) request.getSession().getAttribute("session");
		
		if (login == null) {
			log.info("Interceptor：跳转到login页面！");
			System.out.println("未登录--------------->已拦截");
			// request.getRequestDispatcher("../").forward(request, response);
			response.sendRedirect("../");
			return false;
		}
		
		
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			//得到控制器名称
			String controllerName = handlerMethod.getBeanType().getName();
			//controllerName = controllerName.substring(0, controllerName.length()-10);
			//得到方法名称
			//String methodName = handlerMethod.getMethod().getName();
			//拼接资源名称
			String resourceName =  controllerName+"---"+mapp;
			System.out.println("请求资源名："+resourceName);
			
			
		}
		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("==============执行顺序: 2、postHandle================");
		/*if (modelAndView != null) { // 加入当前时间
			modelAndView.addObject("var", "测试postHandle");
		}*/
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("==============执行顺序: 3、afterCompletion================");
	}

}
