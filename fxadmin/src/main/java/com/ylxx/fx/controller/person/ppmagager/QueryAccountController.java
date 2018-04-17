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
import com.ylxx.fx.service.person.ppmagager.IQueryAccountService;
/**
 * 清算明细查询
 * @author lz130
 *
 */
@Controller
public class QueryAccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryAccountController.class);
	
	@Resource(name="queryAccountService")
	private IQueryAccountService queryAccountService;
	
	//获取页面数据列表
	@RequestMapping(value="/selectList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectList(@RequestBody MoZhangVo mzVo) throws Exception {
		/*String jgdt = "";//交割日期20120731
		String trdt = "";//交易日期20120728
		MoZhangVo mzVo = new MoZhangVo();
		mzVo.setTrdt(trdt);
		mzVo.setJgdt(jgdt);*/
		return queryAccountService.selectList(mzVo.getJgdt(), mzVo.getTrdt());
	}
}
