package com.ylxx.qt.filters;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ylxx.qt.service.YQUserService;

public class LoginInterceptor implements HandlerInterceptor {

	@Resource
	public YQUserService user;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
			StringBuffer url=request.getRequestURL();
			String uri=request.getRequestURI();
			String XRequested =request.getHeader("X-Requested-With");
			//System.out.println("-----------"+url);
			if(url.indexOf("login")>0||url.indexOf(".js")>0) {
				return true;
			}
		  HttpSession session = request.getSession(); 
		  String userid = (String)session.getAttribute("userid"); 
		  //获取用户权限
		  String roleid=(String)session.getAttribute("role");
		  if(userid == null){
			  //判断是否是ajax的方式
			  if("XMLHttpRequest".equals(XRequested)){
			        response.sendRedirect(uri);
			      }else{
			        request.getRequestDispatcher("/page/newLogin.jsp").forward(request, response); 
			      }
			  return false;
		  }
		 
		  return true;
	}
}
