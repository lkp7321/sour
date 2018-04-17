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

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.ProductDoms;
import com.ylxx.fx.core.mapper.LoginMapper;
import com.ylxx.fx.core.mapper.MenuMapper;
import com.ylxx.fx.service.LoginService;
import com.ylxx.fx.service.MenuService;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DesService;
import com.ylxx.fx.utils.ErrorCode;
import com.ylxx.fx.utils.GetIp;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.Menu;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 登录
 * @author lz130
 *
 */
@Controller
public class LoginController {
	
	private DesService des = new DesService();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	@Resource(name="loginServicer")
	private LoginService loginServicer;
	
	@Resource(name="menuService")
	private MenuService menuService;
	
	@Resource
	private LoginMapper logimap;
	@Resource
	private MenuMapper menumap;
	 
	/**
	 * 获取产品
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/getPro.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPro(@RequestBody User user){
		LOGGER.info("【获取产品】："+"用户："+user.getUsnm());
		String jsons = null;
		List<ProductDoms> list1=loginServicer.findProduct(user.getUsnm());
		if(list1.size()>0&&list1!=null){
			if(user.getUsnm().equals("Administrator")){
				jsons=JSON.toJSONString(list1);
				LOGGER.info("【获取产品】"+" 管理员："+user.getUsnm()+" 成功");
				return ResultDomain.getRtnMsg(ErrorCode.E_000.getCode(), jsons);
			}else{
				List<ProductDoms> list2 = loginServicer.findProduct1(user.getUsnm());
				if(list2.size()>0&&list2!=null){
					LOGGER.info("【获取产品】"+" 普通用户："+user.getUsnm()+" 获取产品成功");
					jsons=JSON.toJSONString(list2);
					return ResultDomain.getRtnMsg(ErrorCode.E_000.getCode(), jsons);
				}else{
					LOGGER.error("【获取产品】"+" 普通用户："+user.getUsnm()+" 用户名未复核");
					return ResultDomain.getRtnMsg(ErrorCode.E_002.getCode(), null);
				}
			}
		}else{
			LOGGER.error("【获取产品】"+" 用户："+user.getUsnm()+" 不存在");
			return ResultDomain.getRtnMsg(ErrorCode.E_001.getCode(), null);
		}
	}
	
	/**
	 * 获取登陆验证信息
	 * @param req
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/doLogin.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String doLogin(HttpServletRequest req,@RequestBody User user){
		LOGGER.info("【进行登录验证】"+" 用户："+user.getUsnm()+",产品："+user.getProd());
		if(user.getUsnm()!=null && !user.getUsnm().equals("")&&
				user.getProd()!=null && !user.getProd().equals("")&&
				user.getPswd()!=null && !user.getPswd().equals("")) {
			return returnLogin(user, req);
		}else {
			return ResultDomain.getRtnMsg(ErrorCode.E_0010.getCode(), "用户名和密码不能为空");
		}
		
	}
	public String returnLogin(User user, HttpServletRequest req) {
		String password = des.encrypt(user.getPswd());
		user.setPswd(password);
		String message = loginServicer.login(user, req);
		if(message.equals("success")){
			User user1 = loginServicer.getUser(user);
			Menu menu = menuService.getM("admin", user1);
			if(menu.getMenuList() == null || menu.getMenuList().size()<=0){
				LOGGER.info("用户通过登陆验证，却没有任何功能权限！");
				return ResultDomain.getRtnMsg(ErrorCode.E_0010.getCode(), null);
			}else{
				String ip = GetIp.getIpAddr(req);
				CurrUser curUser = new CurrUser();
				curUser.setUsnm(user1.getUsnm());
				curUser.setUspt(user1.getUspt());
				curUser.setProd(user1.getProd());
				curUser.setCstn(user1.getCstn());
				curUser.setOrgn(user1.getCstn());
				curUser.setCurIP(ip);
				String key = LoginUsers.getLoginUser().saveCurUser(curUser);
				menu.setUserKey(key);
				HttpSession session = req.getSession();
				session.setAttribute(key,key);
				session.setAttribute("id", key);
				session.setMaxInactiveInterval(30*60);
				return ResultDomain.getRtnMsg(ErrorCode.E_000.getCode(), JSON.toJSONString(menu));
			}
		}else{
			return ResultDomain.getRtnMsg(message, null);
		}
	}
	
	/**
	 * 跳转主页
	 * @param userKey
	 * @param session
	 * @return
	 */
	@RequestMapping("toindex.do")
	public String toindex(String userKey, HttpSession session){
		if(userKey == null){
			return "redirect:/toHomePage";
		}else if(LoginUsers.getLoginUser().getCurrUser(userKey) == null || session.getAttribute(userKey) == null){
			session.removeAttribute(userKey);
			LoginUsers.getLoginUser().deleteCurUser(userKey);
			return "redirect:/toHomePage";
		}else{
			return "index";
		}
	}
	/**
	 * 退出
	 * @param userKey
	 * @param session
	 * @return
	 */
	@RequestMapping("logingout.do")
	public String logingout(String userKey, HttpSession session){
		session.removeAttribute(userKey);
		LoginUsers.getLoginUser().deleteCurUser(userKey);
		return "redirect:/toHomePage";
	}
	/**
	 * 登录修改原密码
	 * @param req
	 * @param usnm:用户名
	 * @param oldPass:旧密码，
	 * @param newPass:新密码，
	 * @param prod:产品
	 * @return
	 */
	@RequestMapping(value="/updateLoginPswd.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updatePassBefore(HttpServletRequest req, String usnm, String prod, String oldPass, String newPass) {
		if(usnm!=null&&!"".equals(usnm) && oldPass!=null &&!"".equals(oldPass) && newPass!=null && !"".equals(newPass) && prod!=null && !"".equals(prod)) {
			String oldPassWord = des.encrypt(oldPass);
			String newPassWord = des.encrypt(newPass);
			return loginServicer.findPswd(usnm, prod, oldPassWord, newPassWord, req);
		}else {
			return null;
		}
	}
}
