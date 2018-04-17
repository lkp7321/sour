/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ylxx.fx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.utils.ErrorCode;
import com.ylxx.fx.utils.ResultDomain;

@Controller
//@RequestMapping("fx")
public class WelcomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);
	
	@RequestMapping("")
    public String Create(Model model) {
//		System.out.println("create........");
//        return "create";
		//System.out.println("to index........");
		LOGGER.debug("to index........");
		return "login";
    }
	
	/***
	 * 首页界面
	 * @param empUuid
	 * @param req
	 * @return
	 */
	@RequestMapping("/")
	public String toHome(HttpServletRequest req,Model model) {
        //System.out.println("to index........");
        LOGGER.debug("to index........");
        //1、收集参数、验证参数  
        //2、绑定参数到命令对象  
        //3、将命令对象传入业务对象进行业务处理  
        //4、选择下一个页面  
//        ModelAndView mv = new ModelAndView();  
        //添加模型数据 可以是任意的POJO对象  
        //mv.addObject("message", "Hello World!");  
        //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面  
//        mv.setViewName("index");  
//        return mv; 
		return "login";
	}
	
	/***
	 * 首页界面
	 * @param empUuid
	 * @param req
	 * @return
	 */
	@RequestMapping("/login.do")
	public String toHomePage(HttpServletRequest req,Model model) {
//		Enumeration<String> em = req.getSession().getAttributeNames();
//        while (em.hasMoreElements()) {
//        	req.getSession().removeAttribute(em.nextElement().toString());
//        }
//      req.getSession().invalidate();
//		req.getSession().setAttribute("loginFlag", "loginFlag");
        //System.out.println("to login.do........");
        LOGGER.debug("to login.do........");
		return "index";
	}

	@RequestMapping("toHomePage")
	public String toHomePage(String empUuid, HttpServletRequest req) {
		req.getSession().setAttribute("loginFlag", "loginFlag");
        System.out.println("toHomePage");
		return "redirect:/";
	}

	@RequestMapping("toUser.do")
	public String toUser() {
		//req.getSession().setAttribute("loginFlag", "loginFlag");
        //System.out.println("toHomePage");
		return "user/userinfo";
	}

	@RequestMapping("toIndex.do")
	public String toIndex() {
		//req.getSession().setAttribute("loginFlag", "loginFlag");
        //System.out.println("toHomePage");
		return "index";
	}
	/**
	 * 登出操作（用户点击退出系统链接 记录用户退出行为，并进行跳转）
	 */
	@RequestMapping(value = "/logOutOperate")
	public void logOutOperate(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		session.invalidate();
//		try {
//			response.sendRedirect(PropertiesUtil.getProperties(
//					"/config/application.properties", "cas.sso.url")
//					+ "/logout?service="
//					+ PropertiesUtil.getProperties(
//							"/config/application.properties", "cas.host.url"));
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@RequestMapping(value = "/hello")
	@ResponseBody
	public String hello() {
		System.out.println("test------------------");
		ResultDomain.getRtnMsg(ErrorCode.E_00.getCode(),null);
		return "hello";
	}
	
	@RequestMapping(value = "/hello2")
	@ResponseBody
	public String hello2() {
		System.out.println("test------------------");
		return ResultDomain.getRtnMsg(ErrorCode.E_01.getCode(),null);
	}
}
