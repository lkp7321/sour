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
/*报价平台-价格源管理-点差干预上限设置*/
package com.ylxx.fx.controller.person.price;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.PriceVo;
import com.ylxx.fx.service.person.price.ICmmCtrlswhService;
import com.ylxx.fx.service.po.CmmCtrlswh;

@Controller
//@RequestMapping("fx")
public class CmmCtrlswhController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmmCtrlswhController.class);
	
	@Resource(name="cmmCtrlswhService")
	private ICmmCtrlswhService cmmCtrlswhService;
	
	//加载页面数据
	@RequestMapping(value="/selectCtrlSwh.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectCtrlSwh() throws Exception {
		return cmmCtrlswhService.selectCtrlSwh();	
	}
	//查询币别对
	@RequestMapping(value="/curExnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String curExnm() throws Exception {
		return cmmCtrlswhService.curExnm();	
	}
	//查询价格类型
	@RequestMapping(value="/curTpnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String curTpnm() throws Exception {
		return cmmCtrlswhService.selectPrice();	
	}
	//保存
	@RequestMapping(value="/addCmmCtrlSwh.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addCmmCtrlSwh(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setUserKey("123");
		CmmCtrlswh cmmctrl = new CmmCtrlswh();
		cmmctrl.setTpfg("SPT");
		cmmctrl.setTerm("0");
		cmmctrl.setCxfg("2");
		cmmctrl.setExnm("USDKRW");
		cmmctrl.setExcd("1419");//USDKRW
		cmmctrl.setUsfg("0");
		cmmctrl.setNbup("0");
		cmmctrl.setNsup("0");
		//写死
		cmmctrl.setNblw("0");
		cmmctrl.setNslw("0");
		cmmctrl.setFbup("0");
		cmmctrl.setFblw("0");
		cmmctrl.setFsup("0");
		cmmctrl.setFslw("0");
		pVo.setCmmCtrl(cmmctrl);*/
		CmmCtrlswh cmmctrl = pVo.getCmmCtrl();
		cmmctrl.setNblw("0");
		cmmctrl.setNslw("0");
		cmmctrl.setFbup("0");
		cmmctrl.setFblw("0");
		cmmctrl.setFsup("0");
		cmmctrl.setFslw("0");
		return cmmCtrlswhService.addCmmCtrlSwh(pVo.getUserKey(),cmmctrl);	
	}
	//修改
	@RequestMapping(value="/upCmmCtrlSwh.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upCmmCtrlSwh(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setUserKey("123");
		CmmCtrlswh cmmctrl = new CmmCtrlswh();
		cmmctrl.setTpfg("SPT");
		cmmctrl.setTerm("0");
		cmmctrl.setCxfg("0");
		cmmctrl.setExnm("USDKRW");
		cmmctrl.setUsfg("1");
		cmmctrl.setNbup("1");
		cmmctrl.setNsup("1");
		pVo.setCmmCtrl(cmmctrl);*/
		return cmmCtrlswhService.upCmmCtrlSwh(pVo.getUserKey(),pVo.getCmmCtrl());	
	}
	//删除
	@RequestMapping(value="/delCmmCtrl.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delCmmCtrl(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setUserKey("123");
		List<CmmCtrlswh> cmmCtrlswhs = new ArrayList<CmmCtrlswh>();
		CmmCtrlswh cmmctrl = new CmmCtrlswh();
		cmmctrl.setTpfg("SPT");
		cmmctrl.setTerm("0");
		cmmctrl.setCxfg("0");
		cmmctrl.setExnm("USDKRW");
		cmmCtrlswhs.add(cmmctrl);
		pVo.setCmmCtrls(cmmCtrlswhs);*/
		return cmmCtrlswhService.delCmmCtrl(pVo.getUserKey(),pVo.getCmmCtrls());	
	}
	//启用/停用
	@RequestMapping(value="/updateCmmCtrl.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateCmmCtrl(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setUserKey("123");
		pVo.setUsfg("启用");
		List<CmmCtrlswh> cmmCtrlswhs = new ArrayList<CmmCtrlswh>();
		CmmCtrlswh cmmctrl = new CmmCtrlswh();
		cmmctrl.setTpfg("SPT");
		cmmctrl.setTerm("0");
		cmmctrl.setCxfg("0");
		cmmctrl.setExnm("USDKRW");
		cmmCtrlswhs.add(cmmctrl);
		pVo.setCmmCtrls(cmmCtrlswhs);*/
		return cmmCtrlswhService.updateCmmCtrl(pVo.getUserKey(),pVo.getUsfg(),pVo.getCmmCtrls());	
	}
	
}
