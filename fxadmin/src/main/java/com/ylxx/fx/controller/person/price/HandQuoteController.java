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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylxx.fx.core.domain.PriceVo;
import com.ylxx.fx.service.person.price.IHandQuoteService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;

@Controller
//@RequestMapping("fx")
public class HandQuoteController {

	@Resource(name="handQuoteService")
	private IHandQuoteService handQuoteService;
	
	/*String user = "xlj";
	String prod = "P001";
	LoginUser logUser = new LoginUser(user,prod);
	//查询全部数据时用
	//String stfg = "";
	//条件查询手工报价数据时用
	String stfg = "0";//未审批:0;审批:1;未通过:2.
	String labnm = "复核";*/
	
	//筛选qut_handpriceconfig配置表各个产品的所有列表
	@RequestMapping(value="/selectProductVaList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectProductVaList(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setProd(prod);
		pVo.setStfg(stfg);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		return handQuoteService.selectHandQuoteList(curUser.getProd(), pVo.getStfg());	
	}
	//修改
	@RequestMapping(value="/updateProduct.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateProduct(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		HandQuoteVoBean hqVoBean = new HandQuoteVoBean();
		hqVoBean.setMkid("M9002");//市场编号(报价源)
		hqVoBean.setExcd("");//币别对编号
		hqVoBean.setNeby("510");//买入价
		hqVoBean.setNesl("545.2");//卖出价
		hqVoBean.setNemd("527.6000");//中间价
		hqVoBean.setTpfg("SPT");//价格类型
		hqVoBean.setTerm("0");//期限
		hqVoBean.setExnm("AUDRMB");//币别对名称
		hqVoBean.setCxfg("1");//钞汇标志
		hqVoBean.setBcfg("分行价");
		pVo.setHqVo(hqVoBean);
		return handQuoteService.updateProduct("123", "P004", 
				pVo.getHqVo());*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		return handQuoteService.updateProduct(pVo.getUserKey(), curUser.getProd(), 
				pVo.getHqVo());	
	}
	//提交、复核、未通过              未审批:0; 审批:1; 未通过:2  
	@RequestMapping(value="/updateHandQuoteState.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateHandQuoteState(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setLabnm("全部提交");
		List<HandQuoteVoBean> hqVoList = new ArrayList<HandQuoteVoBean>();
		HandQuoteVoBean hqVoBean1 = new HandQuoteVoBean();
		hqVoBean1.setMkid("M9002");//市场编号(报价源)
		hqVoBean1.setExcd("2901");//币别对编号
		hqVoBean1.setTpfg("SPT");//价格类型
		hqVoBean1.setTerm("0");//期限
		hqVoBean1.setExnm("AUDRMB");//币别对名称
		hqVoBean1.setCxfg("1");//钞汇标志
		hqVoBean1.setNeby("540.1");//买入价
		hqVoBean1.setNesl("545.2");//卖出价
		hqVoBean1.setNemd("542.65");//中间价
		hqVoBean1.setBcfg("分行价");
		hqVoList.add(hqVoBean1);
		HandQuoteVoBean hqVoBean2 = new HandQuoteVoBean();		
		hqVoBean2.setMkid("M9002");//市场编号(报价源)
		hqVoBean2.setTpfg("SPT");//价格类型
		hqVoBean2.setExcd("2801");//币别对编号
		hqVoBean2.setTerm("0");//期限
		hqVoBean2.setExnm("CADRMB");//币别对名称
		hqVoBean2.setCxfg("1");//钞汇标志
		hqVoBean2.setNeby("600.36");//买入价
		hqVoBean2.setNesl("605.14");//卖出价
		hqVoBean2.setNemd("602.75");//中间价
		hqVoBean2.setBcfg("分行价");
		hqVoList.add(hqVoBean2);
		pVo.setHqVoList(hqVoList);
		return handQuoteService.updateHandQuoteState("123", "P004", 
				pVo.getHqVoList(),pVo.getLabnm());*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		return handQuoteService.updateHandQuoteState(pVo.getUserKey(), curUser.getProd(), 
				pVo.getHqVoList(),pVo.getLabnm());
	}
	//手工报价启用开始
	//筛选qut_handprice_P00X表各个产品的所有列表
	@RequestMapping(value="/selectHandPriceList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectHandPriceList(@RequestBody String userKey) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return handQuoteService.selectHandPriceAfficheList(curUser.getProd());	
	}
	//启用
	@RequestMapping(value="/openHandQuote.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String openHandQuote(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		List<HandQuoteVoBean> hqVos = new ArrayList<HandQuoteVoBean>();
		HandQuoteVoBean hqVoBean1 = new HandQuoteVoBean();
		//前台隐藏
		hqVoBean1.setTpfg("SPT");//价格类型
		hqVoBean1.setExcd("2928");//币别对编号
		hqVoBean1.setMkid("M0199");//市场编号(报价源)
		//前台显示的
		hqVoBean1.setTerm("0");//期限
		hqVoBean1.setExnm("AUDCAD");//币别对名称
		hqVoBean1.setCxfg("2");//钞汇标志
		hqVos.add(hqVoBean1);
		HandQuoteVoBean hqVoBean2 = new HandQuoteVoBean();
		hqVoBean2.setTpfg("SPT");//价格类型
		hqVoBean2.setMkid("M0199");//市场编号(报价源)
		hqVoBean2.setExcd("2927");//币别对编号
		hqVoBean2.setTerm("0");//期限
		hqVoBean2.setExnm("AUDJPY");//币别对名称
		hqVoBean2.setCxfg("2");//钞汇标志		
		hqVos.add(hqVoBean2);
		pVo.setProd(prod);
		pVo.setHqVoList(hqVos);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		return handQuoteService.openHandQuote(curUser.getProd(),pVo.getHqVoList());
		
		//测试用：个人结售汇：全部启用
		/*PriceVo pVo = new PriceVo();
		List<HandQuoteVoBean> list = handQuoteService.getHandQuoteVoBeanlist("P004");
		pVo.setHqVoList(list);
		return handQuoteService.openHandQuote("P004",pVo.getHqVoList());*/
		
		//TODO ut.SendSocketHandPrice(ptid);
	}
	//停用
	@RequestMapping(value="/closeHandQuote.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String closeHandQuote(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		List<HandQuoteVoBean> hqVos = new ArrayList<HandQuoteVoBean>();
		HandQuoteVoBean hqVoBean1 = new HandQuoteVoBean();
		hqVoBean1.setTpfg("SPT");//价格类型
		hqVoBean1.setTerm("0");//期限
		hqVoBean1.setExnm("AUDCAD");//币别对名称
		hqVoBean1.setCxfg("2");//钞汇标志
		hqVoBean1.setExcd("2928");//币别对编号
		hqVoBean1.setMkid("M0199");//市场编号(报价源)
		hqVos.add(hqVoBean1);
		HandQuoteVoBean hqVoBean2 = new HandQuoteVoBean();
		hqVoBean2.setTpfg("SPT");//价格类型
		hqVoBean2.setTerm("0");//期限
		hqVoBean2.setExnm("AUDJPY");//币别对名称
		hqVoBean2.setCxfg("2");//钞汇标志
		hqVoBean2.setMkid("M0199");//市场编号(报价源)
		hqVoBean2.setExcd("2927");//币别对编号
		hqVos.add(hqVoBean2);
		pVo.setProd(prod);
		pVo.setHqVoList(hqVos);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		return handQuoteService.closeHandQuote(curUser.getProd(),pVo.getHqVoList());
		
		//测试用：个人结售汇：全部停用
		/*PriceVo pVo = new PriceVo();
		List<HandQuoteVoBean> list = handQuoteService.getHandQuoteVoBeanlist("P004");
		pVo.setHqVoList(list);
		return handQuoteService.closeHandQuote("P004",pVo.getHqVoList());*/
		
		//TODO ut.SendSocketHandPrice(ptid);
	}
	//查询手工报价操作表
	@RequestMapping(value="/HandPriceOperateList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String HandPriceOperateList(@RequestBody PriceVo pVo) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		return handQuoteService.queryHandPriceOperate(curUser.getProd(), pVo.getUsnm(),pVo.getOptm(),pVo.getPageNo(),pVo.getPageSize());	
	}
}
