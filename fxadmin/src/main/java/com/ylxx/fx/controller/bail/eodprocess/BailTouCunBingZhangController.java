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
import com.ylxx.fx.service.bail.eodprocess.IBailTouCunBingZhangService;

@Controller
//@RequestMapping("fx")
public class BailTouCunBingZhangController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BailTouCunBingZhangController.class);
	
	@Resource(name="bailTouCunBingZhangService")
	private IBailTouCunBingZhangService bailTouCunBingZhangService;
	
	//查询保证金头寸并账
	@RequestMapping(value="/queryBailTouCunBZList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryBailTouCunBZList(@RequestBody String hddate) throws Exception {
		//String hddate="20171106";
		return bailTouCunBingZhangService.queryBailTouCunBZList(hddate);	
	}
	//检查并账日期前是否有未并账的记录
	@RequestMapping(value="/checkInput.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String checkInput(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setUserKey("123");
		BailTouCunBean bailTouCunBean = new BailTouCunBean();
		bailTouCunBean.setBzdt("20171205");
		kdVo.setBailTouCunBean(bailTouCunBean);*/
		return bailTouCunBingZhangService.checkInput(kdVo.getUserKey(),
				kdVo.getBailTouCunBean());	
	}
	
}
