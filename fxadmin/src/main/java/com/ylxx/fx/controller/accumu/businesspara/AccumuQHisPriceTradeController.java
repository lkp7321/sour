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

package com.ylxx.fx.controller.accumu.businesspara;

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

import com.ylxx.fx.core.domain.AccumuBusinessParaVo;
import com.ylxx.fx.service.accumu.businesspara.IAccumuQHisPriceTradeService;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.Table;

/**
 * 积存金：
 * 定期申购历史价格 
 */
@Controller
//@RequestMapping("fx")
public class AccumuQHisPriceTradeController {

	private static final Logger log = LoggerFactory.getLogger(AccumuQHisPriceTradeController.class);
	
	@Resource(name="iaccumuQHisPriceTradeService")
	private IAccumuQHisPriceTradeService iAccumuQHisPriceTradeService;
	
	/**
	 * 定期申购历史价格
	 * @param AccumuBusinessParaVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/accum/selectAccumuHisPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selbyexnm(@RequestBody AccumuBusinessParaVo AccumuBusinessParaVo) throws Exception {
		log.info("查询定期申购历史价格：");
		return iAccumuQHisPriceTradeService.selectAccumuHisPrice(AccumuBusinessParaVo.getTrdtbegin(),AccumuBusinessParaVo.getTrdtend(),AccumuBusinessParaVo.getPageNo(),AccumuBusinessParaVo.getPageSize());	
	}
	
	/**
	 * 导出定期申购历史价格
	 * @param AccumuBusinessParaVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException 
	 */
	@RequestMapping(value = "accum/selectAccumuHisPriceExcel.do")
    public void selectAccumuHisPriceExcel(AccumuBusinessParaVo AccumuBusinessParaVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		log.info("导出Excel");
		String fileName = AccumuBusinessParaVo.getTableName();//前台传的  表名
        List<Table> tableList = AccumuBusinessParaVo.getTableList();//前台传的  表头，及Key
		List<Map<String, Object>> list = iAccumuQHisPriceTradeService.selectAllAccumuHisPrice(AccumuBusinessParaVo.getTrdtbegin(),AccumuBusinessParaVo.getTrdtend());
        PoiExcelExport<Map<String, Object>> pee = new PoiExcelExport<Map<String, Object>>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
