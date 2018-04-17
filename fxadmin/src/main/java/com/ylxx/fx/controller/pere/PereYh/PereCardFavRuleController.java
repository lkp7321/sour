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

package com.ylxx.fx.controller.pere.PereYh;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.PereCardFavRuleVo;
import com.ylxx.fx.service.pere.PereYh.IPereCardFavRuleService;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Controller
//@RequestMapping("fx")
public class PereCardFavRuleController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PereCardFavRuleController.class);

	@Resource(name = "pereCardFavRuleService")
	private IPereCardFavRuleService iPereCardFavRuleService;
	
	//
	@RequestMapping(value = "/addCardRuleByCard.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addCardRuleByCard(
			@RequestBody PereCardFavRuleVo pereCardFavRuleVo) throws Exception {

		return iPereCardFavRuleService.addCardRuleByCard(pereCardFavRuleVo);
	}

	/**
	 * @AUTHOR 韦任 修改卡bin优惠
	 * @PARAM PereCardFavRuleVo pereCardFavRuleVo
	 * @RETURN
	 */
	// String userKey, ArrayList chlist,String ogcd,String byds,String
	// slds,String beginDate,String endDate
	@RequestMapping(value = "/updateRuleByCards.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateRuleByCards(
			@RequestBody PereCardFavRuleVo pereCardFavRuleVo) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(
				pereCardFavRuleVo.getUserkey());

		String result = "";
		String flag = iPereCardFavRuleService.updateRuleByCards(
				pereCardFavRuleVo, curUser);
		if (flag.equals("true")) {
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(),
					"修改成功");

		} else {

			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),
					"修改失败");
		}
		return result;
	}

		/**@AUTHOR 韦任
		 * 查询机构优惠
		 * @PARAM  PereCardFavRuleVo pereCardFavRuleVo
		 * @RETURN
		 */
		@RequestMapping(value="/getCardFavRuleByOgcd.do",produces="application/json;charset=UTF-8")
		@ResponseBody
		public String  getCardFavRuleByOgcd(@RequestBody PereCardFavRuleVo pereCardFavRuleVo) {
		
//			PereCardFavRuleVo pereCardFavRuleVo  =new PereCardFavRuleVo();
//			pereCardFavRuleVo.setOgcd("0001");
			
			String result="";	
			String ogcd=pereCardFavRuleVo.getOgcd();
			Integer pageNo=pereCardFavRuleVo.getPageNo();
			Integer pageSize=pereCardFavRuleVo.getPageSize();
			pageNo = pageNo == null?1:pageNo;
			pageSize = pageSize == null?10:pageSize;
			PageHelper.startPage(pageNo,pageSize);
			try {
				List<Trd_FavruleOgcd> linkedList = iPereCardFavRuleService.getCardFavRuleByOgcd(ogcd);
				PageInfo<Trd_FavruleOgcd> page=new PageInfo<Trd_FavruleOgcd>(linkedList);
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}catch(Exception e) {
				e.printStackTrace();
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"无记录");
			}
			return result;	
		}
	@RequestMapping(value="/deleteCardFavRules.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteCardFavRules(@RequestBody PereCardFavRuleVo pereCardFavRuleVo) throws Exception {
//		ArrayList <Trd_FavruleOgcd> chlist =  new ArrayList<>();
//		Trd_FavruleOgcd favruleOgcd1=new Trd_FavruleOgcd();
//		
//		favruleOgcd1.setByds("33");
//		favruleOgcd1.setSlds("22");
//		favruleOgcd1.setStdt("20161102");
//		favruleOgcd1.setEddt("20171102");
//		favruleOgcd1.setExnm("SGDRMB");
//		favruleOgcd1.setCbin("1");
//		
//		chlist.add(favruleOgcd1);
//		
//		PereCardFavRuleVo deleteOrgFavrRuleVo=new PereCardFavRuleVo();
//		deleteOrgFavrRuleVo.setChlist(chlist);
//		
//		
//		deleteOrgFavrRuleVo.setOgcd("0001");	
		return iPereCardFavRuleService.deleteCardFavRules(pereCardFavRuleVo);	
	}

}
