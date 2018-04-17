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

/**
 * 客户管理---客户级别管理
 */
package com.ylxx.fx.controller.person.custom;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.CustVo;
import com.ylxx.fx.service.person.custom.ICustLevelService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
/**
 * 客户管理
 * 客户级别管理
 * @author lz130
 *
 */
@Controller
public class CustLevelController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustLevelController.class);	
	
	@Resource(name="custLevelService")
	private ICustLevelService custLevelService;
	
	/**
	 * 获取所有客户级别信息
	 * @param userKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getCustLevel.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCustLevel(@RequestBody String userKey) throws Exception {
		//String prod = "P002";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return custLevelService.getCustLevel(curUser.getProd());
	}
	/**
	 * 添加客户级别信息
	 * @param cv
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/CustLevelAdd.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String CustLevelAdd(@RequestBody CustVo cv) throws Exception{
		return custLevelService.CustLevelAdd(cv.getCustLevel(),cv.getUserKey());
	}
	/**
	 * 修改客户级别信息
	 * @param cv
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/CustLevelUpdate.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String CustLevelUpdate(@RequestBody CustVo cv) throws Exception{
		return custLevelService.CustLevelUpate(cv.getCustLevel(),cv.getUserKey());
	}
	/**
	 * 删除客户级别信息
	 * @param cv
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/CustLevelDelete.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String CustLevelDelete(@RequestBody CustVo cv) throws Exception{
		return custLevelService.CustLevelDelete(cv.getCustLevel(),cv.getUserKey());
	}
}
