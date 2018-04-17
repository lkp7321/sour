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
import com.ylxx.fx.service.kondor.kondorrv.IKonSpotTradeService;

@Controller
//@RequestMapping("fx")
public class KonSpotTradeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(KonSpotTradeController.class);
	
	@Resource(name="konSpotTradeService")
	private IKonSpotTradeService konSpotTradeService;
	
	//查询页面数据
	@RequestMapping(value="/selAllSpotList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selAllSpotList(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setStartDate(" ");//20130610
		kdVo.setEndDate(" ");//20130710
		kdVo.setTradeCode("All");
		kdVo.setRpcNo(" ");//41
		kdVo.setPageNo(null);
		kdVo.setPageSize(null);*/
		return konSpotTradeService.selAllSpotList(kdVo.getStartDate(), kdVo.getEndDate(), 
				kdVo.getTradeCode(),kdVo.getRpcNo(), kdVo.getPageNo(), kdVo.getPageSize());	
	}
	
}
