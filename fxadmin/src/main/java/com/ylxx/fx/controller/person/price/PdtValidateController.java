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

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.PriceVo;
import com.ylxx.fx.service.person.price.IPdtValidateService;
import com.ylxx.fx.service.po.PdtValidateBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;

@Controller
//@RequestMapping("fx")
public class PdtValidateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdtValidateController.class);
	
	@Resource(name="pdtValidateService")
	private IPdtValidateService pdtValidateService;
	/*String user = "xlj";
	String prod = "P001";
	LoginUser logUser = new LoginUser(user,prod);*/
	
	//查询产品均价校验器列表
	@RequestMapping(value="/getProductVaList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getProductVaList(@RequestBody PriceVo pVo) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtValidateService.selectProductVaList(pVo.getProd());
		}else {
			return pdtValidateService.selectProductVaList(curUser.getProd());
		}
	}
	//获取价格类型列表
	@RequestMapping(value="/getTpfgList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getTpfgList() throws Exception{
		return pdtValidateService.getTpfg();
	}
	//查询货币对列表
	@RequestMapping(value="/getExnmList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getExnmList(@RequestBody PriceVo pVo) throws Exception{
		//测试用
		/*PriceVo pVo = new PriceVo();
		pVo.setProd("P001");
		pVo.setTpfg("FWD");//FWD
		pVo.setTerm("0");
		pVo.setCxfg("2");*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtValidateService.selectExnm(pVo.getProd(), pVo.getTpfg(), 
					pVo.getTerm(), pVo.getCxfg());
		}else {
			return pdtValidateService.selectExnm(curUser.getProd(), pVo.getTpfg(), 
					pVo.getTerm(), pVo.getCxfg());
		}
	}
	//添加校验器
	@RequestMapping(value="/addProductVa.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addProductVa(@RequestBody PriceVo pVo) throws Exception{
		/*PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		PdtValidateBean pdtVa = new PdtValidateBean();
		pdtVa.setTpfg("SPT");
		pdtVa.setTerm("0");
		pdtVa.setExnm("EURUSD");
		pdtVa.setExcd("3814");//查询货币对列表时获得
		pdtVa.setCxfg("1");
		pdtVa.setMxdt(0);
		pdtVa.setUsfg("0");
		pVo.setPdtVa(pdtVa);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtValidateService.addProductVa(pVo.getUserKey(), pVo.getProd(),pVo.getPdtVa());
		}else {
			return pdtValidateService.addProductVa(pVo.getUserKey(), curUser.getProd(),pVo.getPdtVa());
		}		
		//TODO ut.SendSocketB1();
	}
	//修改校验器
	@RequestMapping(value="/updateProductVa.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateProductVa(@RequestBody PriceVo pVo) throws Exception{
		//测试用
		/*PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		PdtValidateBean pdtVa = new PdtValidateBean();
		//不可修改
		pdtVa.setTpnm("即期");//SPT
		String tpfg = pdtValidateService.getTpfgByTpnm(pdtVa.getTpnm());
		LOGGER.info("查询价格类型成功!");
		pdtVa.setTpfg(tpfg);
		
		pdtVa.setTerm("0");
		pdtVa.setExnm("GBPUSD");
		pdtVa.setCxfg("2");
		//pdtVa.setExcd("1214");//查询货币对列表时获得
		
		//可修改
		pdtVa.setMxdt(1);//浮动点差
		pdtVa.setUsfg("0");//启用标志（0.启用 1.停用）
		pVo.setPdtVa(pdtVa);
		return pdtValidateService.updateProductVa(pVo.getLogUser().getUsnm(), pVo.getLogUser().getProd(),
				pVo.getPdtVa());*/
		
		//与前台交互时用
		PdtValidateBean pdtVa = pVo.getPdtVa();
		String tpfg = pdtValidateService.getTpfgByTpnm(pdtVa.getTpnm());
		LOGGER.info("查询价格类型成功!");
		pdtVa.setTpfg(tpfg);
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtValidateService.updateProductVa(pVo.getUserKey(), pVo.getProd(),pdtVa);
		}else {
			return pdtValidateService.updateProductVa(pVo.getUserKey(), curUser.getProd(),pdtVa);
		}
		//TODO ut.SendSocketB1();
	}
	//删除校验器
	@RequestMapping(value="/deleteProductVa.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteProductVa(@RequestBody PriceVo pVo) throws Exception{
		//测试用
		/*PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		PdtValidateBean pdtVa = new PdtValidateBean();
		
		pdtVa.setTpnm("即期");
		String tpfg = pdtValidateService.getTpfgByTpnm(pdtVa.getTpnm());
		LOGGER.info("查询价格类型成功!");
		pdtVa.setTpfg(tpfg);
		pdtVa.setTerm("0");
		pdtVa.setExnm("GBPUSD");
		pdtVa.setCxfg("2");
		
		pVo.setPdtVa(pdtVa);
		return pdtValidateService.deleteProductVa(pVo.getLogUser().getUsnm(), pVo.getLogUser().getProd(),
				pVo.getPdtVa());*/
		
		//与前台交互使用
		PdtValidateBean pdtVa = pVo.getPdtVa();
		String tpfg = pdtValidateService.getTpfgByTpnm(pdtVa.getTpnm());
		LOGGER.info("查询价格类型成功!");
		pdtVa.setTpfg(tpfg);
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtValidateService.deleteProductVa(pVo.getUserKey(), pVo.getProd(),pdtVa);
		}else {
			return pdtValidateService.deleteProductVa(pVo.getUserKey(), curUser.getProd(),pdtVa);
		}
		//TODO ut.SendSocketB1();
	}
	//启用
	@RequestMapping(value="/openUsfg.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String openUsfg(@RequestBody PriceVo pVo) throws Exception{
		//测试用
		/*PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		List<PdtValidateBean> pdtVas = new ArrayList<PdtValidateBean>();
		PdtValidateBean pdtVa1 = new PdtValidateBean();
		PdtValidateBean pdtVa2 = new PdtValidateBean();
		pdtVa1.setTpnm("远期");
		String tpfg1 = pdtValidateService.getTpfgByTpnm(pdtVa1.getTpnm());
		LOGGER.info("查询价格类型成功!");
		pdtVa1.setTpfg(tpfg1);
		pdtVa1.setTerm("0");
		pdtVa1.setExnm("GBPUSD");
		pdtVa1.setCxfg("1");
		pdtVa1.setMxdt(0);
		pdtVas.add(pdtVa1);
		pdtVa2.setTpnm("即期");
		String tpfg2 = pdtValidateService.getTpfgByTpnm(pdtVa2.getTpnm());
		LOGGER.info("查询价格类型成功!");
		pdtVa2.setTpfg(tpfg2);
		pdtVa2.setTerm("0");
		pdtVa2.setExnm("GBPUSD");
		pdtVa2.setCxfg("1");
		pdtVa2.setMxdt(0);
		pdtVas.add(pdtVa2);
		pVo.setPdtVas(pdtVas);
		return pdtValidateService.openUsfg(pVo.getLogUser().getUsnm(), pVo.getLogUser().getProd(),
				pVo.getPdtVas());*/
		
		//与前台交互时用
		List<PdtValidateBean> pdtVas = pVo.getPdtVas();
		for (int i = 0; i < pdtVas.size(); i++) {
			String tpfg = pdtValidateService.getTpfgByTpnm(pdtVas.get(i).getTpnm());
			pdtVas.get(i).setTpfg(tpfg);
		}
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		String prod = null;
		if (curUser.getProd().equals("P999")){
			prod = pVo.getProd();
		}else {
			prod = curUser.getProd();
		}
		return pdtValidateService.openUsfg(pVo.getUserKey(), prod, pdtVas);
		//TODO ut.SendSocketB1();
	}
	//停用
	@RequestMapping(value="/closeUSFG.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String closeUSFG(@RequestBody PriceVo pVo) throws Exception{
		/*PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		List<PdtValidateBean> pdtVas = new ArrayList<PdtValidateBean>();
		PdtValidateBean pdtVa1 = new PdtValidateBean();
		PdtValidateBean pdtVa2 = new PdtValidateBean();
		pdtVa1.setTpnm("远期");
		String tpfg1 = pdtValidateService.getTpfgByTpnm(pdtVa1.getTpnm());
		LOGGER.info("查询价格类型成功!");
		pdtVa1.setTpfg(tpfg1);
		pdtVa1.setTerm("0");
		pdtVa1.setExnm("GBPUSD");
		pdtVa1.setCxfg("1");
		pdtVa1.setMxdt(0);//浮动点差
		//pdtVa1.setUsfg("0");//启用标志（0. 启用 1.停用）
		pdtVas.add(pdtVa1);
		pdtVa2.setTpnm("即期");
		String tpfg2 = pdtValidateService.getTpfgByTpnm(pdtVa2.getTpnm());
		LOGGER.info("查询价格类型成功!");
		pdtVa2.setTpfg(tpfg2);
		pdtVa2.setTerm("0");
		pdtVa2.setExnm("GBPUSD");
		pdtVa2.setCxfg("1");
		pdtVa2.setMxdt(0);//浮动点差
		//pdtVa2.setUsfg("0");//启用标志（0. 启用 1.停用）
		pdtVas.add(pdtVa2);
		pVo.setPdtVas(pdtVas);
		return pdtValidateService.closeUSFG(pVo.getLogUser().getUsnm(), pVo.getLogUser().getProd(),
				pVo.getPdtVas());*/
		
		//与前台交互时用
		List<PdtValidateBean> pdtVas = pVo.getPdtVas();
		for (int i = 0; i < pdtVas.size(); i++) {
			String tpfg = pdtValidateService.getTpfgByTpnm(pdtVas.get(i).getTpnm());
			pdtVas.get(i).setTpfg(tpfg);
		}
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		String prod = null;
		if (curUser.getProd().equals("P999")){
			prod = pVo.getProd();
		}else {
			prod = curUser.getProd();
		}
		return pdtValidateService.closeUSFG(pVo.getUserKey(), prod, pdtVas);
		//TODO ut.SendSocketB1();
	}
}
