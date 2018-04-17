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

package com.ylxx.fx.controller.accumu.businesspara;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.AccInfoManageVo;
import com.ylxx.fx.core.domain.UserVo;
import com.ylxx.fx.service.accumu.businesspara.IAccInfoService;
import com.ylxx.fx.service.po.AccInfoManageBean;
import com.ylxx.fx.service.po.TrdOgcdBean;
import com.ylxx.fx.service.po.TrdOrganBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

/**
 * 积存金（P003）
 * 活动专户管理，
 * 活动专户查询
 * @author lz130
 *
 */
@Controller
public class AccInfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccInfoController.class);
	List<AccInfoManageBean> alist = null;
	
	
	@Resource(name="accInfoService")
	private IAccInfoService accInfoService;
	
	/**
	 * 查询活动专户管理
	 * @param userKey
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/getAccInfoList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAccInfoList(@RequestBody UserVo userVo){
		String result = "";
		// 分页
		Integer pageNo = userVo.getPageNo() == null ? 1 : userVo.getPageNo();
		Integer pageSize = userVo.getPageSize() == null ? 10 : userVo.getPageSize();
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			alist = accInfoService.searchAccInfoList();
			if (alist != null && alist.size()>0) {
				//用PageInfo对结果进行包装
				PageInfo<AccInfoManageBean> page = new PageInfo<AccInfoManageBean>(alist);
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
				LOGGER.info("条数为："+ alist.size());
			}else if (alist != null && alist.size() == 0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	
	/**
	 * 查询活动专户ByOrgn
	 * @param userKey
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/getAccInfoListByOrgn.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAccInfoListByOrgn(@RequestBody UserVo userVo){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userVo.getUserKey());
		String result = "";
		// 分页
		Integer pageNo = userVo.getPageNo() == null ? 1 : userVo.getPageNo();
		Integer pageSize = userVo.getPageSize() == null ? 10 : userVo.getPageSize();
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			String orgn = curUser.getOrgn();
			alist = accInfoService.searchAccInfoListByOrgn(orgn);
			if (alist != null && alist.size()>0) {
				//用PageInfo对结果进行包装
				PageInfo<AccInfoManageBean> page = new PageInfo<AccInfoManageBean>(alist);
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
				LOGGER.info("条数为："+ alist.size());
			}else if (alist != null && alist.size() == 0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	
	/**
	 * 插入活动专户
	 * @param og
	 * @param ptid
	 * @param ptname
	 * @return
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/doInsertAccInfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String doInsertAccInfo(@RequestBody AccInfoManageVo accInfoManageVo) throws Exception {
		Boolean insertResult = accInfoService.doInsertAccInfo(accInfoManageVo.getOgcd(), accInfoManageVo.getCunm());
		String result = ResultDomain.getRtnMsg(ErrorCodePrice.E_21.getCode(), null);
		if(insertResult){
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(), null);
		}
		return result;
	}	
	
	/**
	 * 为第一个机构下拉框赋值
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryOneOrganForAcc.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryOneOrganForAcc(@RequestBody String userkey) throws Exception{
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userkey);
//		String userorg = curUser.getOrgn();
//		String userorg = "2300";
		List<TrdOrganBean> TrdOrganBeanList = accInfoService.queryOneOrgan1();
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(TrdOrganBeanList));
	}
	
	/**
	 * 查询当前用户机构名称
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryUserOrganForAcc.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryUserOrganForAcc(@RequestBody String userkey) throws Exception{
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userkey);
		String userorg = curUser.getOrgn();
//		String userorg = "2300";
		List<TrdOgcdBean> TrdOgcdBeanList = accInfoService.queryUserOrganForAcc(userorg);
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(TrdOgcdBeanList));
	}
	
}
