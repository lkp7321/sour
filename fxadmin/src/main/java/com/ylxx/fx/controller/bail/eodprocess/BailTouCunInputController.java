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
import com.ylxx.fx.service.bail.eodprocess.IBailTouCunInputService;

@Controller
//@RequestMapping("fx")
public class BailTouCunInputController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BailTouCunInputController.class);
	
	@Resource(name="bailTouCunInputService")
	private IBailTouCunInputService bailTouCunInputService;
	
	//查询保证金头寸录入
	@RequestMapping(value="/queryBailTouCunList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryBailTouCunList(@RequestBody String hddate) throws Exception {
		//String hddate="20171106";
		return bailTouCunInputService.queryBailTouCunList(hddate);	
	}
	//录入:保证金头寸录入发送报文
	@RequestMapping(value="/toucunluru.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String toucunluru(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setUserKey("123");
		kdVo.setStartDate("20171205");//并账日期
		List<BailTouCunBean> bailTouCunBeans = new ArrayList<BailTouCunBean>();
		BailTouCunBean bailTouCunBean1 = new BailTouCunBean();
		BailTouCunBean bailTouCunBean2 = new BailTouCunBean();
		bailTouCunBean1.setExnm("AUDCAD");
		bailTouCunBean1.setLeft("-1");
		bailTouCunBean1.setRigh("2");
		bailTouCunBean2.setExnm("AUDJPY");
		bailTouCunBean2.setLeft("0");
		bailTouCunBean2.setRigh("0");
		bailTouCunBeans.add(bailTouCunBean1);
		bailTouCunBeans.add(bailTouCunBean2);
		kdVo.setBailTouCunBeans(bailTouCunBeans);*/
		return bailTouCunInputService.toucunluru(kdVo.getUserKey(), 
				kdVo.getStartDate(), kdVo.getBailTouCunBeans());	
	}
	
}
