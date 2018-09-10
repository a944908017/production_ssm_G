package com.wangdao.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * 判断用户权限的Spring MVC的拦截器
 */
public class AuthorizedInterceptor implements HandlerInterceptor {

	/** 定义不需要拦截的请求 */
	private static final String[] IGNORE_URI = {"/loginForm", "/login","/404.html"};


	/**
	 * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用，
	 * 当preHandle的返回值为false的时候整个请求就结束了。
	 * 如果preHandle的返回值为true，则会继续执行postHandle和afterCompletion。
	 */
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		return true;
		//登录标志,默认未登录
//		boolean flag=false;
//		//获取请求path
//		String servletPath = httpServletRequest.getServletPath();
//		//判断是否需要拦截
//		for (String s:IGNORE_URI){
//			if (servletPath.contains(s)){
//				flag=true;
//				break;
//			}
//		}
//		//拦截请求
//		if (!flag){
//			//获取session中的user对象
//			User user = (User) httpServletRequest.getSession().getAttribute(HrmConstants.USER_SESSION);
//			//判断user是否存在
//			if (user==null){
//				//如果不存在跳转到登录页面
//				httpServletRequest.setAttribute("message","请先登录!");
//				httpServletRequest.getRequestDispatcher(HrmConstants.LOGIN).forward(httpServletRequest,httpServletResponse);
//				return flag;
//			}else{
//				flag=true;
//			}
//		}
//		return flag;
	}


	/**
	 * 这个方法在preHandle方法返回值为true的时候才会执行。
	 * 执行时间是在处理器进行处理之 后，也就是在Controller的方法调用之后执行。
	 */
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}


	/**
	 * 该方法需要preHandle方法的返回值为true时才会执行。
	 * 该方法将在整个请求完成之后执行，主要作用是用于清理资源。
	 */
	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}








}
