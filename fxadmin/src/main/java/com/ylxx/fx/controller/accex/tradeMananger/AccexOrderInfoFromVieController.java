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
import com.ylxx.fx.service.accex.tradeMananger.IAccexOrderInfoFromVieService;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.Table;


@Controller
//@RequestMapping("fx")
public class AccexOrderInfoFromVieController {

	private static final Logger log = LoggerFactory.getLogger(AccexOrderInfoFromVieController.class);
	
	@Resource(name="accexOrderInfoFromVieService")
	private IAccexOrderInfoFromVieService iaccexOrderInfoFromVieService;
	
	/**
	 * 获取持仓表视图数据   
	 * @param OrderInfoFromViewVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="accex/getOrderListFromVie.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getOrderListFromVie(@RequestBody OrderInfoFromViewVo OrderInfoFromViewVo) throws Exception {
		log.info("持仓查询：");
		return iaccexOrderInfoFromVieService.getOrderListFromVie(OrderInfoFromViewVo.getPageNo(),OrderInfoFromViewVo.getPageSize(),OrderInfoFromViewVo.getEndDate(), OrderInfoFromViewVo.getProd(), OrderInfoFromViewVo.getStrateDate(), OrderInfoFromViewVo.getUserKey());	
	}
	/**
	 * 持仓查询 导出Excel
	 * @param OrderInfoFromViewVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException 
	 */
	@RequestMapping(value = "accex/getOrderListFromVieExcel.do")
    public void showImgCode(OrderInfoFromViewVo OrderInfoFromViewVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = OrderInfoFromViewVo.getTableName();//前台传的  表名
        List<Table> tableList = OrderInfoFromViewVo.getTableList();//前台传的  表头，及Key
		String userKey = OrderInfoFromViewVo.getUserKey();//查询所需要的条件
		List<Map<String, Object>> list = iaccexOrderInfoFromVieService.getAllOrderListFromVie(OrderInfoFromViewVo.getEndDate(), OrderInfoFromViewVo.getProd(), OrderInfoFromViewVo.getStrateDate(), userKey);
        PoiExcelExport<Map<String, Object>> pee = new PoiExcelExport<Map<String, Object>>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}