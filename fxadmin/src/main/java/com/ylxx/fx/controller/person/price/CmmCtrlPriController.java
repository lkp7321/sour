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
import com.ylxx.fx.service.person.price.ICmmCtrlPriService;
import com.ylxx.fx.service.po.CmmCtrlpri;

@Controller
//@RequestMapping("fx")
public class CmmCtrlPriController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmmCtrlPriController.class);
	
	@Resource(name="cmmCtrlPriService")
	private ICmmCtrlPriService cmmCtrlPriService;
	
	//查询货币对
	@RequestMapping(value="/allExnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String allExnm() throws Exception {
		return cmmCtrlPriService.allExnm();	
	}
	//查询页面数据
	@RequestMapping(value="/allCmmCtrlPri.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String allCmmCtrlPri(@RequestBody String exnm) throws Exception {
		return cmmCtrlPriService.allCmmCtrlPri(exnm);	
	}
	//添加
	@RequestMapping(value="/addCmmCtrlpri.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addCmmCtrlpri(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setUserKey("123");
		CmmCtrlpri ctrlpri = new CmmCtrlpri();
		ctrlpri.setCtid("CC99");//干预ID
		ctrlpri.setCtnm("手工干预器");//干预器名称
		ctrlpri.setTpfg("FWD");//价格类型
		ctrlpri.setTerm("0");//期限
		ctrlpri.setExnm("EURUSD");//货币对名称
		ctrlpri.setExcd("3814");//货币对
		ctrlpri.setNebp("1");//买入价干预点差
		ctrlpri.setNesp("1");//卖出价干预点差
		ctrlpri.setCxfg("1");//钞汇标志
		ctrlpri.setStfg("0");//干预标志	
		ctrlpri.setBgtm("20171124 00:00:00");//起始时间
		ctrlpri.setEdtm("20171231 23:59:59");//截止时间
		//后台写死
		ctrlpri.setFabp("0");//远端买入价点差 visible="false" text="0"
		ctrlpri.setFasp("0");//远端卖出价点差visible="false" text="0"
		ctrlpri.setUsfg("0");
		pVo.setCmmCtrlpri(ctrlpri);
		return cmmCtrlPriService.addCmmCtrlpri(pVo.getUserKey(),pVo.getCmmCtrlpri());*/
		CmmCtrlpri ctrlpri = pVo.getCmmCtrlpri();
		ctrlpri.setFabp("0");//远端买入价点差 visible="false" text="0"
		ctrlpri.setFasp("0");//远端卖出价点差visible="false" text="0"
		ctrlpri.setUsfg("0");
		return cmmCtrlPriService.addCmmCtrlpri(pVo.getUserKey(),ctrlpri);	
	}
	//修改
	@RequestMapping(value="/updateCmmCtrlpri.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateCmmCtrlpri(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setUserKey("123");
		CmmCtrlpri ctrlpri = new CmmCtrlpri();
		//主键
		ctrlpri.setCtid("CC99");//干预ID
		ctrlpri.setTpfg("FWD");//价格类型
		ctrlpri.setTerm("0");//期限
		ctrlpri.setExnm("EURUSD");//货币对名称
		ctrlpri.setCxfg("1");//钞汇标志
		//可修改部分
		ctrlpri.setNebp("2");//买入价干预点差
		ctrlpri.setNesp("2");//卖出价干预点差
		ctrlpri.setStfg("1");//干预标志: 0启用(干预),1停用(不干预)	
		ctrlpri.setBgtm("20171101 00:00:00");//起始时间
		ctrlpri.setEdtm("20171231 23:59:59");//截止时间
		//写日志
		ctrlpri.setCtnm("手工干预器");//干预器名称		
	
		pVo.setCmmCtrlpri(ctrlpri);*/
		return cmmCtrlPriService.updateCmmCtrlpri(pVo.getUserKey(),pVo.getCmmCtrlpri());	
	}
	//启用/停用
	@RequestMapping(value="/upCmmCtrlPriStfg.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upCmmCtrlPriStfg(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setUserKey("123");
		pVo.setUsfg("停用");
		List<CmmCtrlpri> cmmCtrlpris = new ArrayList<CmmCtrlpri>();
		CmmCtrlpri cmmctrl1 = new CmmCtrlpri();
		cmmctrl1.setCtid("CC99");
		cmmctrl1.setTpfg("FWD");
		cmmctrl1.setTerm("0");
		cmmctrl1.setCxfg("1");
		cmmctrl1.setExnm("EURUSD");
		cmmctrl1.setCtnm("手工干预器");
		cmmCtrlpris.add(cmmctrl1);
		CmmCtrlpri cmmctrl = new CmmCtrlpri();
		cmmctrl.setCtid("CC99");
		cmmctrl.setTpfg("SPT");
		cmmctrl.setTerm("0");
		cmmctrl.setCxfg("2");
		cmmctrl.setExnm("EURUSD");
		cmmctrl.setCtnm("手工干预器");
		cmmCtrlpris.add(cmmctrl);
		pVo.setCmmCtrlpris(cmmCtrlpris);*/
		return cmmCtrlPriService.upCmmCtrlPriStfg(pVo.getUserKey(), pVo.getCmmCtrlpris(), pVo.getUsfg());	
	}
		
}
