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

package com.ylxx.fx.controller.person.price;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.PriceVo;
import com.ylxx.fx.service.person.price.IHandQuickStopService;

@Controller
//@RequestMapping("fx")
public class HandQuickStopController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandQuickStopController.class);
	
	@Resource(name="handQuickStopService")
	private IHandQuickStopService handQuickStopService;
	
	//加载市场
	@RequestMapping(value="/queryMktinfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryMktinfo() throws Exception {	   
		return handQuickStopService.queryMktinfo();	
	}
	//加载币别对
	@RequestMapping(value="/queryhqsExnms.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryhqsExnms() throws Exception {	   
		return handQuickStopService.queryhqsExnms();	
	}
	//加载停牌数据
	@RequestMapping(value="/selecthqsCmmStopers.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selecthqsCmmStopers() throws Exception {	   
		return handQuickStopService.selecthqsCmmStopers();	
	}
	//条件加载停牌数据
	@RequestMapping(value="/selecthqsCmmStoper.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selecthqsCmmStoper(@RequestBody PriceVo pVo) throws Exception {	   
		/*String mkid = "all";
		String excd = "6814";*/
		return handQuickStopService.selecthqsCmmStopers(pVo.getMkid(),pVo.getExcd());	
	}
	//开牌
	@RequestMapping(value="/open.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String open(@RequestBody PriceVo pVo) throws Exception {	   
		/*PriceVo pVo = new PriceVo();
		pVo.setUserKey("123");
		List<CmmStoper> cmms = new ArrayList<CmmStoper>();
		CmmStoper cmm1 = new CmmStoper();
		cmm1.setStid("SC99");
		cmm1.setMkid("M0101");//
		cmm1.setExcd("6814");//
		cmm1.setTpfg("SPT");//
		cmm1.setTerm("0");
		
		cmm1.setMknm("UBS1");
		cmm1.setExnm("OAUUSD");
		cmm1.setStnm("手工停牌器");
		CmmStoper cmm2 = new CmmStoper();
		cmm2.setStid("SC99");
		cmm2.setMkid("M0102");
		cmm2.setExcd("6814");
		cmm2.setTpfg("SPT");
		cmm2.setTerm("0");
		
		cmm2.setMknm("UBS2");
		cmm2.setExnm("OAUUSD");
		cmm2.setStnm("手工停牌器");
		
		cmms.add(cmm1);
		cmms.add(cmm2);
		pVo.setCmmStopers(cmms);*/
		return handQuickStopService.openChannel(pVo.getUserKey(),pVo.getCmmStopers());	
	}
	//停牌
	@RequestMapping(value="/close.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String close(@RequestBody PriceVo pVo) throws Exception {	   
		/*PriceVo pVo = new PriceVo();
		pVo.setUserKey("123");
		List<CmmStoper> cmms = new ArrayList<CmmStoper>();
		CmmStoper cmm1 = new CmmStoper();
		cmm1.setStid("SC99");
		cmm1.setMkid("M0101");//
		cmm1.setExcd("6814");//
		cmm1.setTpfg("SPT");//
		cmm1.setTerm("0");
		
		cmm1.setMknm("UBS1");
		cmm1.setExnm("OAUUSD");
		cmm1.setStnm("手工停牌器");
		CmmStoper cmm2 = new CmmStoper();
		cmm2.setStid("SC99");
		cmm2.setMkid("M0102");
		cmm2.setExcd("6814");
		cmm2.setTpfg("SPT");
		cmm2.setTerm("0");
		
		cmm2.setMknm("UBS2");
		cmm2.setExnm("OAUUSD");
		cmm2.setStnm("手工停牌器");
		
		cmms.add(cmm1);
		cmms.add(cmm2);
		pVo.setCmmStopers(cmms);*/
		return handQuickStopService.closeChannel(pVo.getUserKey(),pVo.getCmmStopers());	
	}
}
