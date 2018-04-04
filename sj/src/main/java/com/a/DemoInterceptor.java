package com.a;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse rep, Object handler)throws Exception {
        long startTime = System.currentTimeMillis();
        req.setAttribute("stime",startTime);
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse rep, Object handler, ModelAndView modelAndView) throws Exception {
        Long startTime = (Long)req.getAttribute("stime");
        req.removeAttribute("stime");
        long endTime = System.currentTimeMillis();
        System.out.println("本次请求处理时间为："+new Long(endTime-startTime)+"ms");
        req.setAttribute("handlingTime", endTime-startTime);
    }
}
