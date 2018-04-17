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
import com.ylxx.fx.service.person.ppmagager.IPpAccountService;
/**
 * 不明交易处理
 * @author lz130
 *
 */
@Controller
public class PpAccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PpAccountController.class);
	
	@Resource(name="ppAccountService")
	private IPpAccountService PpAccountService;
	
	/**
	 * 查询所有不明交易
	 * @param lcno
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selPPAccount.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selPPAccount(@RequestBody String lcno) throws Exception {
		LOGGER.info("查询所有不明交易记录");
		return PpAccountService.selPPAccount(lcno);	
	}
	/**
	 * 查询UBS联系方式、我行标识
	 * @param paid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryPtpara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryPtpara(@RequestBody String paid) throws Exception {
		//String paid = "C001";
		return PpAccountService.queryPtpara(paid);	
	}
	/**
	 * 成功处理
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/onSucessManage.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String onSucessManage(@RequestBody MoZhangVo mzVo) throws Exception {
		/*MoZhangVo mzVo = new MoZhangVo();
		mzVo.setUserKey("123");
		Ck_ppresultdetail ppresult = new Ck_ppresultdetail();
		ppresult.setExnm("AUDUSD");//account.expdg.selectedItem.EXNM
		ppresult.setSlcy("USD");//卖出币种
		ppresult.setBamt(7.00);//买入金额  jyam.text
		ppresult.setSamt(21.00);//卖出金额  cjam.text
		ppresult.setBsfx("买入");//买卖方向
		ppresult.setPpno("2017102400007");//account.expdg.selectedItem.PPNO
		ppresult.setSeqn("2017102400007001");//本地平盘流水号
		ppresult.setDsno("LM153601972999");//UBS流水
		ppresult.setExpc(3.00);//成交汇率
		ppresult.setJgdt("20171110");//交割日期
		ppresult.setQxdt("20171101");//起息日期
		ppresult.setTrfl("普通平盘");//自动平盘标志    account.expdg.selectedItem.TRFL
		mzVo.setPpresult(ppresult);*/
		return PpAccountService.onSucessManage(mzVo.getUserKey(),mzVo.getPpresult());	
	}
	/**
	 * 失败处理
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/onFaultManage.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String onFaultManage(@RequestBody MoZhangVo mzVo) throws Exception {
		/*MoZhangVo mzVo = new MoZhangVo();
		mzVo.setUserKey("123");
		mzVo.setPpno("2017102400007");
		mzVo.setSeqn("2017102400007001");//本地平盘流水号
		mzVo.setTrfl("普通平盘");//自动平盘标志*/
		return PpAccountService.onFaultManage(mzVo.getUserKey(),mzVo.getPpno(),mzVo.getSeqn(),mzVo.getTrfl());	
	}
}
