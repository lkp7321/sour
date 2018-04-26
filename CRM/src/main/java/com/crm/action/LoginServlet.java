package com.crm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.entity.User;
import com.crm.service.LoginService;
import com.crm.service.impl.LoginServiceImpl;

public class LoginServlet extends HttpServlet{
@Override
protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	LoginService service=new LoginServiceImpl();
    String account=request.getParameter("account");
	System.out.println("account："+account);
    String password=request.getParameter("password");
    User user=service.Login(account, password);
    if(user!=null){
        request.getSession().setAttribute("username",user.getUserName());
		request.getSession().setAttribute("userId", user.getId());
		request.getRequestDispatcher("main.jsp").forward(request,response);
    }else{
        request.setAttribute("msg", "登录失败");
        request.getRequestDispatcher("login.jsp").forward(request,response);
    }
}
}
