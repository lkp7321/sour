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
import com.ylxx.fx.service.kondor.kondorrv.IKonBondTradeService;

@Controller
//@RequestMapping("fx")
public class KonBondTradeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(KonBondTradeController.class);
	
	@Resource(name="konBondTradeService")
	private IKonBondTradeService konBondTradeService;
	
	/**
	 * 查询国债流水页面数据
	 * @param kdVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selAllBondList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selAllBondList(@RequestBody KondorVo kdVo) throws Exception {
		LOGGER.info("国债流水查询");
		return konBondTradeService.selAllBondList(kdVo.getStartDate(), kdVo.getEndDate(), 
				kdVo.getRpcNo(), kdVo.getPageNo(), kdVo.getPageSize());	
	}
	
}
