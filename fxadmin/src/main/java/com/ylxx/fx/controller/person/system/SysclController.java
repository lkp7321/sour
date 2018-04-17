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

package com.ylxx.fx.controller.person.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.SystemVo;
import com.ylxx.fx.service.person.system.SysclService;
import com.ylxx.fx.service.po.Sysctl;
import com.ylxx.fx.service.po.Testtrac;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.LoginUsers;
/**
 * 系统管理，系统状态
 * @author lz130
 *
 */
@Controller
public class SysclController {
	
	private static final Logger log = LoggerFactory.getLogger(SysclController.class);
	
	@Resource(name="sysclService")
	private SysclService sysclService;
	/**
	 * 系统状态所有的数据
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getAllTesttrac.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllTesttrac(HttpServletRequest req, @RequestBody String userKey){
		log.info("查询系统状态");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return sysclService.getAllTesttrac(curUser);
	}
	/**
	 * 系统状态  添加
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/addTesttrac.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addTesttrac(HttpServletRequest req, @RequestBody SystemVo sysVo){
		log.info("系统状态添加");
		String userKey = sysVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Testtrac testtrac = sysVo.getTesttrac();//cuac:卡号，usfg:状态0/1
		return sysclService.addTesttrac(curUser, testtrac);
	}
	/**
	 * 系统状态  修改
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/upTesttrac.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upTesttrac(HttpServletRequest req, @RequestBody SystemVo sysVo){
		log.info("系统状态修改");
		String userKey = sysVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Testtrac testtrac = sysVo.getTesttrac();//cuac:卡号，usfg:状态0/1
		return sysclService.updateTesttrac(curUser, testtrac);
	}
	/**
	 * 系统状态  删除
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/delTesttrac.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delTesttrac(HttpServletRequest req,@RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String cuac = sysVo.getCuac();//卡号
		return sysclService.deleteTesttrac(curUser, cuac);
	}
	/**
	 * 获取系统 栏  参数
	 * @param loginuser
	 * @return
	 */
	@RequestMapping(value="/initcon.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String initcon(HttpServletRequest req, @RequestBody String userKey){
		log.info("获取系统栏参数");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return sysclService.initCon(curUser);
	}
	
	@RequestMapping(value="/upPpStatcon.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upPpStatcon(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Sysctl sysctl = sysVo.getSysctl();//valu;0/1/2:开启，关闭，测试
		return sysclService.upPpcon(curUser, sysctl);
	}
	
	@RequestMapping(value="/upSyscon.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upSyscon(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Sysctl sysctl = sysVo.getSysctl();//usfg;0/1/2:开启，关闭，测试
		sysctl.setOpno("admin");
		sysctl.setTrtm(DataTimeClass.getCurDateTime());
		return sysclService.upCon(curUser, sysctl);
	}
}
