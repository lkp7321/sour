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
import com.ylxx.fx.service.person.price.IPdtCtrlSwhService;
import com.ylxx.fx.service.po.PdtCtrlSwhBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;

@Controller
//@RequestMapping("fx")
public class PdtCtrlSwhController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PdtCtrlSwhController.class);
	
	@Resource(name="pdtCtrlSwhService")
	private IPdtCtrlSwhService pdtCtrlSwhService;
	
	/*String user = "xlj";
	String ptid = "P001";
	String exnm ="EURUSD";
	LoginUser logUser = new LoginUser(user,ptid);*/
	
	//得到当前所有干预器列表及状态
	@RequestMapping(value="/getPdtCtrlSwhList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPdtCtrlSwhList(@RequestBody PriceVo pVo)throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtCtrlSwhService.selectPrice(pVo.getProd());
		}else {
			return pdtCtrlSwhService.selectPrice(curUser.getProd());
		}
		
		//TODO ut.SendSocketB1();
	}
	//取货币对名称
	@RequestMapping(value="/getPriceUSD.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPriceUSD(@RequestBody PriceVo pVo) throws Exception {
		//原系统：得到所有币种信息,原系统返回xml串
		//取货币对名称(添加、修改的处理不同)
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtCtrlSwhService.selectPriceUSD(pVo.getProd());
		}else {
			return pdtCtrlSwhService.selectPriceUSD(curUser.getProd());
		}
		//取产品名称
		//String ptnm = pdtCtrlSwhService.selectObjPrice(ptid).getPtnm();
	}
	//保存添加/修改结果
	@RequestMapping(value="/saveRole.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String saveRole(@RequestBody PriceVo pVo) throws Exception {		
		/*//取所有干预器列表和状态时，会查出所有字段，修改时初始化页面可从此处取数据。
		//添加时，文本框有默认值，visible="false"的数据读取默认值插入数据库表
		PdtCtrlSwhBean pdtCtrlSwhBean = new PdtCtrlSwhBean();
		pdtCtrlSwhBean.setExnm("EURUSD");
		pdtCtrlSwhBean.setExcd("3814");				//在取货币对时查询出
		pdtCtrlSwhBean.setNbup("1");//买入价干预上限
		pdtCtrlSwhBean.setNblw("0");				//默认值
		pdtCtrlSwhBean.setNsup("1");//卖出价干预上限
		pdtCtrlSwhBean.setNslw("0");				//默认值
		pdtCtrlSwhBean.setFbup("0");				//默认值
		pdtCtrlSwhBean.setFblw("0");				//默认值
		pdtCtrlSwhBean.setFsup("0");				//默认值
		pdtCtrlSwhBean.setFslw("0");				//默认值
		pdtCtrlSwhBean.setUsfg("1");//总控开关(0启用1停用)
		
		PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		pVo.setPdtCtrlSwh(pdtCtrlSwhBean);*/
		
		PdtCtrlSwhBean pdtCtrlSwhBean = pVo.getPdtCtrlSwh();
		pdtCtrlSwhBean.setNblw("0");				//默认值
		pdtCtrlSwhBean.setNslw("0");				//默认值
		pdtCtrlSwhBean.setFbup("0");				//默认值
		pdtCtrlSwhBean.setFblw("0");				//默认值
		pdtCtrlSwhBean.setFsup("0");				//默认值
		pdtCtrlSwhBean.setFslw("0");				//默认值
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtCtrlSwhService.pdtAddCtrlList(pVo.getUserKey(), pVo.getProd(),
					pdtCtrlSwhBean);
		}else {
			return pdtCtrlSwhService.pdtAddCtrlList(pVo.getUserKey(), curUser.getProd(),
					pdtCtrlSwhBean);
		}
	}
	//删除
	@RequestMapping(value = "/deleteRole.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteRole(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		pVo.setExnm(exnm);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtCtrlSwhService.delCtrl(pVo.getUserKey(), pVo.getProd(),pVo.getExnm());
		}else {
			return pdtCtrlSwhService.delCtrl(pVo.getUserKey(), curUser.getProd(),pVo.getExnm());
		}
	}
	//启用/停用: 更新成功后需刷新页面
	@RequestMapping(value = "/updateCtrl.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateCtrl(@RequestBody PriceVo pVo) throws Exception {
		/*String usfg = "启用";
		List<PdtCtrlSwhBean> pdtCtrls = new ArrayList<PdtCtrlSwhBean>();
		PdtCtrlSwhBean pdtctrl1 = new PdtCtrlSwhBean();
		PdtCtrlSwhBean pdtctrl2 = new PdtCtrlSwhBean();
		pdtctrl1.setExnm("USDJPY");
		pdtCtrls.add(pdtctrl1);
		pdtctrl2.setExnm("USDHKD");
		pdtCtrls.add(pdtctrl2);
		
		PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		pVo.setUsfg(usfg);
		pVo.setPdtCtrlSwhs(pdtCtrls);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtCtrlSwhService.updateCtrl(pVo.getUserKey(), pVo.getProd(),
					pVo.getUsfg(), pVo.getPdtCtrlSwhs());
		}else {
			return pdtCtrlSwhService.updateCtrl(pVo.getUserKey(), curUser.getProd(),
					pVo.getUsfg(), pVo.getPdtCtrlSwhs());
		}
	}
}