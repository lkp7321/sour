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
import com.ylxx.fx.service.kondor.kondorrv.IKonCashTradeService;

@Controller
//@RequestMapping("fx")
public class KonCashTradeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(KonCashTradeController.class);
	
	@Resource(name="konCashTradeService")
	private IKonCashTradeService konCashTradeService;
	
	//查询页面数据
	@RequestMapping(value="/selAllCashList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selAllCashList(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setStartDate(" ");//
		kdVo.setEndDate(" ");//
		kdVo.setRpcNo(" ");//
		kdVo.setPageNo(null);
		kdVo.setPageSize(null);*/
		return konCashTradeService.selAllCashList(kdVo.getStartDate(), kdVo.getEndDate(), 
				kdVo.getRpcNo(), kdVo.getPageNo(), kdVo.getPageSize());	
	}
	
}
