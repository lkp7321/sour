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

package com.ylxx.fx.controller.person.ppmagager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.MoZhangVo;
import com.ylxx.fx.service.person.ppmagager.ISelppDetailService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
/**
 * 平盘待处理查询
 * @author lz130
 *
 */
@Controller
public class SelppDetailController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SelppDetailController.class);
	
	@Resource(name="selppDetailService")
	private ISelppDetailService selppDetailService;
	
	/**
	 * 查询货币对列表
	 * @param userKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selUSDEXNM.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selUSDEXNM(@RequestBody String userKey) throws Exception {
		//String prod = "P001";
		return selppDetailService.selUSDEXNM(userKey);	
	}
	/**
	 * (条件)查询页面数据列表
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selAllList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selAllList(@RequestBody MoZhangVo mzVo) throws Exception {
		/*MoZhangVo mzVo = new MoZhangVo();
		String prod = "P001";
		String sartDate = "20110101";
		String endDate = "20130101";
		String strExnm = "";
		String strStat = "";
		mzVo.setProd(prod);
		mzVo.setSartDate(sartDate);
		mzVo.setEndDate(endDate);
		mzVo.setStrExnm(strExnm);
		mzVo.setStrStat(strStat);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(mzVo.getUserKey());
		return selppDetailService.selAllList(curUser.getProd(),mzVo.getSartDate(),mzVo.getEndDate(),
				mzVo.getStrExnm(),mzVo.getStrStat());	
	}
	
}
