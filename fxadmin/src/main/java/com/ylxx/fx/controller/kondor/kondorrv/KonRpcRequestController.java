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

package com.ylxx.fx.controller.kondor.kondorrv;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.KondorVo;
import com.ylxx.fx.service.kondor.kondorrv.IKonRpcRequestService;

@Controller
//@RequestMapping("fx")
public class KonRpcRequestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(KonRpcRequestController.class);
	
	@Resource(name="konRpcRequestService")
	private IKonRpcRequestService konRpcRequestService;
	
	//查询页面数据
	@RequestMapping(value="/selAllRpcList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selAllRpcList(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setTradeCode("BOND001");
		kdVo.setClstate(" ");//选All时传空
		kdVo.setRpcNo(" ");//41
		kdVo.setPageNo(null);
		kdVo.setPageSize(null);*/
		return konRpcRequestService.selAllRpcList(kdVo.getTradeCode(), kdVo.getClstate(), 
				kdVo.getRpcNo(), kdVo.getPageNo(), kdVo.getPageSize());	
	}
	//判断此交易是否需要修改
	@RequestMapping(value="/selInKondor.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selInKondor(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setTradeCode("BOND001");
		kdVo.setDownloadkey("BT090000013052510282");*/
		return konRpcRequestService.selInKondor(kdVo.getDownloadkey(), kdVo.getTradeCode());	
	}
	//保存修改数据
	@RequestMapping(value="/rpcUpdate.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String rpcUpdate(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setRetrytimes("2");
		kdVo.setClstate("1");
		kdVo.setRpcNo("12");*/
		return konRpcRequestService.rpcUpdate(kdVo.getRetrytimes(),kdVo.getClstate(),kdVo.getRpcNo());	
	}
}
