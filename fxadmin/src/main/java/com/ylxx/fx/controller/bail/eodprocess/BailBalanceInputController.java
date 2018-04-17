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
import com.ylxx.fx.service.bail.eodprocess.IBailBalanceInputService;

@Controller
//@RequestMapping("fx")
public class BailBalanceInputController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BailBalanceInputController.class);
	
	@Resource(name="bailBalanceInputService")
	private IBailBalanceInputService bailBalanceInputService;
	
	//查询保证金余额录入/保证金利息录入
	@RequestMapping(value="/queryBailBalanceList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryBailBalanceList(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setStartDate("20171102");
		kdVo.setTradeCode("M5715");*/
		return bailBalanceInputService.queryBailBalanceList(kdVo.getStartDate(), kdVo.getTradeCode());	
	}
	//保证金余额录入/保证金利息录入发送报文
	@RequestMapping(value="/sunyiluru.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String sunyiluru(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setUserKey("123");
		BailBalanceBean bailBalanceBean = new BailBalanceBean();
		bailBalanceBean.setBzdt("20171103");
		bailBalanceBean.setTrid("M5715");//bailBalanceBean.setTrid("M5716");
		//trid='M5715'
		bailBalanceBean.setPacm("10");
		bailBalanceBean.setKcam("2");
		bailBalanceBean.setTllx("5");
		//trid='M5716'
		bailBalanceBean.setComm("6");
		
		kdVo.setBailBalanceBean(bailBalanceBean);*/
		return bailBalanceInputService.sunyiluru(kdVo.getUserKey(), kdVo.getBailBalanceBean());	
	}
	
}
