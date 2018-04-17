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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.UserVo;
import com.ylxx.fx.service.po.Nrlrgt;
import com.ylxx.fx.service.po.NrlrgtUp;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.service.user.NrlrgtService;
import com.ylxx.fx.service.user.RoleService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCode;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 权限管理
 * @author lz130
 *
 */
@Controller
public class NrlrgtController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NrlrgtController.class);
	
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="nrlrService")
	private NrlrgtService nrlrService;
	
	
	/**
	 * 添加
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/addNr.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addNr(@RequestBody UserVo userVo){
		LOGGER.info("权限添加：");
		//ptid,loginuser,Nrlist
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		RoleBean role = userVo.getRole();
		String ptid = role.getPtid();
		List<NrlrgtUp> list = userVo.getList();
		String nrgtNames = "";
		for(int i=0;i<list.size();i++){
			list.get(i).setPtid(ptid);
			nrgtNames += list.get(i).getMnid()+"||";
		}
		boolean b = nrlrService.addNrlrgts(curUser, list, nrgtNames);
		if(b){
			return ResultDomain.getRtnMsg(ErrorCode.E_0040.getCode(), "success");
		}else{
			return ResultDomain.getRtnMsg(ErrorCode.E_0041.getCode(), null);
		}
	}
	
	/**
	 * 权限删除
	 * @param userVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delNr.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delNr(@RequestBody UserVo userVo)throws Exception{
		//ptid,loginuser,list
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		RoleBean role = userVo.getRole();
		List<NrlrgtUp> list = userVo.getList();//mnid,mamn,sbmn
		boolean flag = nrlrService.deleteThisNrgt(curUser, list, role);
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCode.E_0040.getCode(), "success");
		}else{
			return ResultDomain.getRtnMsg(ErrorCode.E_0042.getCode(), null);
		}
		
	}
	
	/**
	 * 权限复核操作复核未通过
	 * @param userVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/roleNrNo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String roleNrNo(@RequestBody UserVo userVo)throws Exception{
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		RoleBean role = userVo.getRole();
		List<NrlrgtUp> list = userVo.getList();
		List<Nrlrgt> list2=new ArrayList<Nrlrgt>();
		Nrlrgt nr=null;
		for(int i=0;i<list.size();i++){
			nr = new Nrlrgt();
			nr.setMnid(list.get(i).getMnid());
			nr.setPtid(role.getPtid());
			list2.add(nr);
		}
		boolean flag=nrlrService.roleNrlrNo(curUser, list2);
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCode.E_0040.getCode(), "success");
		}else{
			return ResultDomain.getRtnMsg(ErrorCode.E_0044.getCode(), null);
		}
	}
	/**
	 * 权限复核  通过
	 * @param userVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/roleNrlrgtFg.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String roleNrlrgtFg(@RequestBody UserVo userVo) throws Exception{
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		RoleBean role = userVo.getRole();
		List<NrlrgtUp> list = userVo.getList();
		User user = new User();
		user.setUsnm(curUser.getUsnm());
		user.setProd(curUser.getProd());
		
		List<Nrlrgt> list2=new ArrayList<Nrlrgt>();
		Nrlrgt nr=null;
		for(int i=0;i<list.size();i++){
			nr = new Nrlrgt();
			nr.setMnid(list.get(i).getMnid());
			nr.setPtid(role.getPtid());
			nr.setSqno(curUser.getProd());
			nr.setClas(list.get(i).getMamn());
			list2.add(nr);
		}
		boolean flag = nrlrService.insertPrice(curUser,list2);
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCode.E_0040.getCode(), "success");
		}
		else{
			return ResultDomain.getRtnMsg(ErrorCode.E_0043.getCode(), null);
		}
	}
	
	/**
	 * 添加权限的查看
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/addlistNr.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addlistNr(@RequestBody UserVo userVo){
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		RoleBean role = userVo.getRole();
		String prod = curUser.getProd();
		String ptid = role.getPtid();
		Integer pageNo = userVo.getPageNum();
		Integer pageSize = userVo.getPageSize();
		PageInfo<NrlrgtUp> page =  nrlrService.addpage(prod,ptid,pageNo,pageSize);
		return ResultDomain.getRtnMsg(ErrorCode.E_0040.getCode(), page);
	}
	
	/**
	 * 删除权限的查看
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/dellistNr.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String dellistNr(@RequestBody UserVo userVo){
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		RoleBean role = userVo.getRole();
		String prod = curUser.getProd();
		String ptid = role.getPtid();
		Integer pageNo = userVo.getPageNum();
		Integer pageSize = userVo.getPageSize();
		PageInfo<NrlrgtUp> page =  nrlrService.delpage(prod,ptid,pageNo,pageSize);
		return ResultDomain.getRtnMsg(ErrorCode.E_0040.getCode(), page);
	}
	
	/**
	 * 复核权限的分页
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/fglistNr.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String fglistNr(@RequestBody UserVo userVo){
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		RoleBean role = userVo.getRole();
		String prod = curUser.getProd();
		String ptid = role.getPtid();
		Integer pageNo = userVo.getPageNum();
		Integer pageSize = userVo.getPageSize();
		PageInfo<NrlrgtUp> page =  nrlrService.fgpage(prod,ptid,pageNo,pageSize);
		return ResultDomain.getRtnMsg(ErrorCode.E_0040.getCode(), page);
	}
	
	/**
	 * 权限查看
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/sellistNr.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String sellistNr(@RequestBody UserVo userVo){
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		RoleBean role = userVo.getRole();
		String prod = curUser.getProd();
		String ptid = role.getPtid();
		Integer pageNo = userVo.getPageNum();
		Integer pageSize = userVo.getPageSize();
		PageInfo<NrlrgtUp> page =  nrlrService.selpage(prod,ptid,pageNo,pageSize);
		return ResultDomain.getRtnMsg(ErrorCode.E_0040.getCode(), page);
	}
}
