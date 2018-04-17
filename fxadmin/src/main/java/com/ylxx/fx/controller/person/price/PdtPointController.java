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

package com.ylxx.fx.controller.person.price;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.PriceVo;
import com.ylxx.fx.service.person.price.IPdtPointService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;

@Controller
//@RequestMapping("fx")
public class PdtPointController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdtPointController.class);
	
	@Resource(name="pdtPointService")
	private IPdtPointService pdtPointService;
	
	//String prod = "P001";
	
	//查询产品点差列表
	@RequestMapping(value="/getPointList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPointList(@RequestBody PriceVo pVo) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		String prod = null;
		if (curUser.getProd().equals("P999")) {
			prod = pVo.getProd();
		}else {
			prod = curUser.getProd();
		}
		return pdtPointService.selectAllPdtPoint(prod);	
	}
	
}
