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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.PriceVo;
import com.ylxx.fx.service.person.price.IPdtStoperUpService;
import com.ylxx.fx.service.po.PdtStoperBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;

@Controller
//@RequestMapping("fx")
public class PdtStoperUpController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdtStoperUpController.class);
	
	@Resource(name="pdtStoperUpService")
	private IPdtStoperUpService pdtStoperUpService;
	
	/*String user = "xlj";
	String prod = "P001";
	String excd = "14";//选中的币种的币种代码。14:USD
	String stid = "SC99";
	LoginUser logUser = new LoginUser(user,prod);*/
	
	//查询下拉框中的币种
	//一般，产品号为登录的产品
	//对报价平台，产品号为下拉列表选择的产品
	@RequestMapping(value="/queryExnms.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryExnms(@RequestBody PriceVo pVo) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtStoperUpService.selectEXNM(pVo.getProd());
		}else {
			return pdtStoperUpService.selectEXNM(curUser.getProd());
		}	
	}
	//加载停牌数据
	//一般，产品号为登录的产品
	//对报价平台，产品号为下拉列表选择的产品
	@RequestMapping(value="/selectCmmStoper.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectCmmStoper(@RequestBody PriceVo pVo) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtStoperUpService.selectAllCmmStopers(pVo.getProd());
		}else {
			return pdtStoperUpService.selectAllCmmStopers(curUser.getProd());
		}
	}
	//条件查询停牌数据
	@RequestMapping(value="/selectHandcaftStop.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectHandcaftStop(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setProd(prod);
		pVo.setExcd(excd);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtStoperUpService.selectAllCmmStoper(pVo.getProd(), pVo.getExcd());
		}else {
			return pdtStoperUpService.selectAllCmmStoper(curUser.getProd(), pVo.getExcd());
		}			
	}
	//启用停牌器
	@RequestMapping(value="/openChannel.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String openChannel(@RequestBody PriceVo pVo) throws Exception {
		//测试用
		/*PriceVo pVo = new PriceVo();
		List<PdtStoperBean> pdts = new ArrayList<PdtStoperBean>();
		PdtStoperBean pdt1 = new PdtStoperBean();
		PdtStoperBean pdt2 = new PdtStoperBean();
		//前台传过来
		pdt1.setStid(stid);
		pdt1.setExnm("AUDCAD");
		pdt2.setStid(stid);
		pdt2.setExnm("USDJPY");
		//后台查询
		String excd1 = pdtStoperUpService.getExcd(logUser.getProd(), pdt1.getExnm());
		String excd2 = pdtStoperUpService.getExcd(logUser.getProd(), pdt2.getExnm());
		LOGGER.info("查询货币对编号成功!");
		
		pdt1.setExcd(excd1);
		pdt2.setExcd(excd2);
		
		pdts.add(pdt1);
		pdts.add(pdt2);
		pVo.setLogUser(logUser);
		pVo.setPdtStopers(pdts);
		return pdtStoperUpService.openChannel(prod, pVo.getLogUser().getUsnm(), pVo.getPdtStopers());*/
		
		//与前台交互时用
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		List<PdtStoperBean> pdtStopers = pVo.getPdtStopers();
		List<PdtStoperBean> pdts = new ArrayList<PdtStoperBean>();
		PdtStoperBean pdt = null;
		String prod = null;
		if (curUser.getProd().equals("P999")) {
			prod = pVo.getProd();
		}else {
			prod = curUser.getProd();
		}
		for (int i = 0; i < pdtStopers.size(); i++) {
			pdt = pdtStopers.get(i);
			pdt.setExcd(pdtStoperUpService.getExcd(prod, pdt.getExnm()));
			pdts.add(pdt);
		}
		return pdtStoperUpService.openChannel(prod, pVo.getUserKey(), pdts);
		//TODO ut.SendSocketB1();
	}
	//停用停牌器
	@RequestMapping(value="/closeChannel.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String closeChannel(@RequestBody PriceVo pVo) throws Exception {
		//测试用
		/*PriceVo pVo = new PriceVo();
		List<PdtStoperBean> pdts = new ArrayList<PdtStoperBean>();
		PdtStoperBean pdt1 = new PdtStoperBean();
		PdtStoperBean pdt2 = new PdtStoperBean();
		//前台传过来
		pdt1.setStid(stid);
		pdt1.setExnm("AUDCAD");
		pdt2.setStid(stid);
		pdt2.setExnm("USDJPY");
		//后台查询
		String excd1 = pdtStoperUpService.getExcd(logUser.getProd(), pdt1.getExnm());
		String excd2 = pdtStoperUpService.getExcd(logUser.getProd(), pdt2.getExnm());
		LOGGER.info("查询货币对编号成功!");
		
		pdt1.setExcd(excd1);
		pdt2.setExcd(excd2);
		
		pdts.add(pdt1);
		pdts.add(pdt2);
		pVo.setLogUser(logUser);
		pVo.setPdtStopers(pdts);
		return pdtStoperUpService.closeChannel(pVo.getLogUser().getProd(), pVo.getLogUser().getUsnm(), 
				pVo.getPdtStopers());*/
		
		//与前台交互时用
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		List<PdtStoperBean> pdtStopers = pVo.getPdtStopers();
		List<PdtStoperBean> pdts = new ArrayList<PdtStoperBean>();
		PdtStoperBean pdt = null;
		String prod = null;
		if (curUser.getProd().equals("P999")) {
			prod = pVo.getProd();
		}else {
			prod = curUser.getProd();
		}
		for (int i = 0; i < pdtStopers.size(); i++) {
			pdt = pdtStopers.get(i);
			pdt.setExcd(pdtStoperUpService.getExcd(prod, pdt.getExnm()));
			pdts.add(pdt);
		}
		return pdtStoperUpService.closeChannel(prod, pVo.getUserKey(), pdts);
		//TODO ut.SendSocketB1();
	}
}
