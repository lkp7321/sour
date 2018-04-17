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

/**
 * 价格管理---报价频率设置
 */
package com.ylxx.fx.controller.person.price;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.QutFreqtVo;
import com.ylxx.fx.service.person.price.IQutFreqtService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;

@Controller
//@RequestMapping("fx")
public class QutFreqtController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QutFreqtController.class);
	
	@Resource(name="qutFreqtService")
	private IQutFreqtService qutFreqtService;
	
	/*String user = "xlj";
	String prod = "P001";
	LoginUser logUser = new LoginUser(user,prod);*/
	
	//查询报价频率参数
	@RequestMapping(value="/getQutFreqt.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getQutFreqt(@RequestBody String userKey) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return qutFreqtService.selectQutFreqt(curUser.getProd());
	}
	//修改报价频率参数
	@RequestMapping(value="/updateQutFreqt.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateQutFreqt(@RequestBody QutFreqtVo qFreqtVo) throws Exception{
		/*QutFreqtVo qFreqtVo = new QutFreqtVo();
		PdtinfoBean pdtinfo = new PdtinfoBean();
		pdtinfo.setPtid("P001");
		pdtinfo.setPtnm("个人实盘");
		pdtinfo.setQtty("SPT");
		pdtinfo.setFrqy("5");
		pdtinfo.setUsfg("0");
		qFreqtVo.setLogUser(logUser);
		qFreqtVo.setPdtinfo(pdtinfo);*/
		return qutFreqtService.updateQutFreqt(qFreqtVo.getUserKey(),qFreqtVo.getPdtinfo());
	}
}
