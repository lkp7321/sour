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

package com.ylxx.fx.controller.user;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.UserVo;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.service.user.RoleService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCode;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 角色 && 权限管理
 * @author lz130
 *
 */
@Controller
public class RoleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	@Resource(name="roleService")
	private RoleService roleService;
	/**
	 * 到角色管理页面
	 * @return
	 */
	@RequestMapping("torole.do")
	public String torole(){
		return "personaloffer/user/rolemana";
	}
	
	/**
	 * 到角色复核页面
	 * @return
	 */
	@RequestMapping("toroleFg.do")
	public String toroleFg(){
		return "personaloffer/user/roleauditing";
	}
	
	/**
	 * 获取角色列表，success
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/roleList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String rolelist(@RequestBody String userKey){
		LOGGER.info("查询角色列表");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//usnm,prod
		List<RoleBean> list = roleService.listRole(curUser.getProd());
		for(int i=0;i<list.size();i++){
			if(list.get(i).getUsfg().equals("0")){
				list.get(i).setUsfg("启用");
			}else{
				list.get(i).setUsfg("停用");
			}
		}
		String json = JSON.toJSONString(list);
		return ResultDomain.getRtnMsg(ErrorCode.E_0031.getCode(), json);
	}
	
	/**
	 * 添加角色,不能添加已有的ptid的角色
	 * @param userVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addrole.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addRole(@RequestBody UserVo userVo)throws Exception{
		//usnm,prod
		String userKey =userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//ptid,ptnm,remk,usfg(0,1),prod
		RoleBean role = userVo.getRole();
		role.setProd(curUser.getProd());
		int b = roleService.hasRole(role.getPtid(), role.getPtnm(), role.getProd());
		if(b>0){
			return ResultDomain.getRtnMsg(ErrorCode.E_0035.getCode(), null);
		}else{
			boolean flag = roleService.addRoles(userKey, role);
			if(flag){
				return ResultDomain.getRtnMsg(ErrorCode.E_0031.getCode(), "success");
			}else{
				return ResultDomain.getRtnMsg(ErrorCode.E_0032.getCode(), null);
			}	
		}
	}
	
	/**
	 * 删除角色
	 * @param userVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleterole.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteRole(@RequestBody UserVo userVo) throws Exception{
		String userKey = userVo.getUserKey();
		//usnm,prod
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//ptid
		RoleBean role = userVo.getRole();
		
		boolean flag = roleService.deleteRole(userKey, role.getPtid(), curUser.getProd());
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCode.E_0031.getCode(), "success");
		}else{
			return ResultDomain.getRtnMsg(ErrorCode.E_0034.getCode(), null);
		}
	}
	
	/**
	 * 修改角色
	 * @param userVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editrole.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String editRole(@RequestBody UserVo userVo)throws Exception{
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//ptid,prod,ptnm,remk,usfg(0,1)
		RoleBean role = userVo.getRole();
		role.setProd(curUser.getProd());
		int a = roleService.getroles(role);
		if(a>0){
			return ResultDomain.getRtnMsg(ErrorCode.E_0036.getCode(), null);
		}else{
			boolean flag = roleService.upRole(userKey, role);
			if(flag){
				return ResultDomain.getRtnMsg(ErrorCode.E_0031.getCode(), "success");
			}else{
				return ResultDomain.getRtnMsg(ErrorCode.E_0033.getCode(), null);
			}
		}
	}
	
}
