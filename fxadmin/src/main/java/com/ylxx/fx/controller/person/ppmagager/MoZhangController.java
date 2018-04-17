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
import com.ylxx.fx.service.person.ppmagager.IMoZhangService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
/**
 * 手工敞口抹账
 * @author lz130
 *
 */
@Controller
public class MoZhangController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MoZhangController.class);
	
	@Resource(name="moZhangService")
	private IMoZhangService moZhangService;
	
	/**
	 * 获取所有抹账数据
	 * @param userKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectAllMxDetail.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectAllMxDetail(@RequestBody String userKey) throws Exception {
		LOGGER.info("查询所有抹账数据");
		//String prod = "P003";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return moZhangService.selectAllMxDetail(curUser.getProd());	
	}
	/**
	 * 条件查询
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectMxDetail.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectMxDetail(@RequestBody MoZhangVo mzVo) throws Exception {
		/*MoZhangVo mzVo = new MoZhangVo();
		mzVo.setProd("P003");
		mzVo.setDateApdt("20130730");
		mzVo.setDataEndInput("20130730");*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(mzVo.getUserKey());
		return moZhangService.selectMxDetail(curUser.getProd(),mzVo.getDateApdt(), mzVo.getDateEndInput());	
	}
	/**
	 * 抹账操作
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mozhang.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mozhang(@RequestBody MoZhangVo mzVo) throws Exception {
		/*MoZhangVo mzVo = new MoZhangVo();
		CkHandMxDetail ckno = new CkHandMxDetail();
		ckno.setSgno("00000008");//手工登记流水号
		ckno.setApdt("20120807");//登记日期
		mzVo.setMxdetail(ckno);*/
		return moZhangService.mozhang(mzVo.getUserKey(),mzVo.getMxdetail());	
	}
}
