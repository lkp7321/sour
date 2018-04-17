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

package com.ylxx.fx.controller.accex.tradeMananger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylxx.fx.core.domain.OrderInfoFromViewVo;
import com.ylxx.fx.service.accex.tradeMananger.IAccexPromotionListService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.Table;

/**
 * 账户交易
 * 营销查询
 */
@Controller
//@RequestMapping("fx")
public class AccexPromotionListController {

	private static final Logger log = LoggerFactory.getLogger(AccexPromotionListController.class);
	
	@Resource(name="iaccexPromotionListService")
	private IAccexPromotionListService iAccexPromotionListService;
	
	/**
	 * 账户交易营销查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="accex/selectPromotion.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectPromotion(HttpServletRequest req, @RequestBody OrderInfoFromViewVo orderInfoFromViewVo) throws Exception {
		log.info("营销流水查询");
		String userKey = orderInfoFromViewVo.getUserKey();
		String startDate = orderInfoFromViewVo.getStrateDate();
		String endDate = orderInfoFromViewVo.getEndDate();
		Integer pageNo = orderInfoFromViewVo.getPageNo();
		Integer pageSize = orderInfoFromViewVo.getPageSize();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return iAccexPromotionListService.selectPromotion(pageNo, pageSize,endDate, curUser.getProd(), startDate);	
	}
	
	@RequestMapping(value = "accex/selectPromotionExcel.do")
    public void selectPromotionExcel(OrderInfoFromViewVo orderInfoFromViewVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = orderInfoFromViewVo.getTableName();//前台传的  表名
        List<Table> tableList = orderInfoFromViewVo.getTableList();//前台传的  表头，及Key
		String userKey = orderInfoFromViewVo.getUserKey();//查询所需要的条件
		String startDate = orderInfoFromViewVo.getStrateDate();
		String endDate = orderInfoFromViewVo.getEndDate();
		String prod = LoginUsers.getLoginUser().getCurrUser(userKey).getProd();
		 List<Map<String, Object>> list = iAccexPromotionListService.selectAllPromotion(endDate, prod, startDate);
        PoiExcelExport<Map<String, Object>> pee = new PoiExcelExport<Map<String, Object>>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
