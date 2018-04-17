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

package com.ylxx.fx.controller.bail.eodprocess;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.KondorVo;
import com.ylxx.fx.service.bail.eodprocess.IBailBalanceBingZhangService;

@Controller
//@RequestMapping("fx")
public class BailBalanceBingZhangController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BailBalanceBingZhangController.class);
	
	@Resource(name="bailBalanceBingZhangService")
	private IBailBalanceBingZhangService bailBalanceBingZhangService;
	
	//查询保证金余额并账/保证金利息并账
	@RequestMapping(value="/queryBailBalanceBZList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryBailBalanceBZList(@RequestBody KondorVo kdVo) throws Exception {
	    // startDate("20171103");
	    // tradeCode("M5711");*/
		return bailBalanceBingZhangService.queryBailBalanceBZList(kdVo.getStartDate(), kdVo.getTradeCode());	
	}
	//并账
	@RequestMapping(value="/balanceBZSave.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String balanceBZSave(@RequestBody KondorVo kdVo) throws Exception {
	    // bzdt("20171205");
	    // trid("M5711");
		return bailBalanceBingZhangService.checkInput(kdVo.getUserKey(), kdVo.getBailBalanceBean());	
	}
	
}
