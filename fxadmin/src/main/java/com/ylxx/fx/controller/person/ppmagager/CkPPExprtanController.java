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
import com.ylxx.fx.service.person.ppmagager.ICkPPExprtanService;
/**
 * 异常平盘处理
 * @author lz130
 *
 */
@Controller
public class CkPPExprtanController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CkPPExprtanController.class);
	
	@Resource(name="ckPPExprtanService")
	private ICkPPExprtanService ckPPExprtanService;
	
	/**
	 * 获得全部异常交易的数据/根据流水号条件查询
	 * @param trac
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryExprtan.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryExprtan(@RequestBody String trac) throws Exception {
		LOGGER.info("查询异常平盘处理数据");
		return ckPPExprtanService.queryExprtan(trac);	
	}
	/**
	 * 处理操作
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateExprtan.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateExprtan(@RequestBody MoZhangVo mzVo) throws Exception {
		/*String user = "xlj";
		String prod = "P001";
		String ppno = "2017102400007";
		MoZhangVo mzVo = new MoZhangVo();
		mzVo.setUser(user);
		mzVo.setProd(prod);
		mzVo.setSeqn(ppno);*/
		return ckPPExprtanService.updateExprtan(mzVo.getUserKey(),mzVo.getSeqn());	
	}
}
