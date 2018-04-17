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
import com.ylxx.fx.core.domain.AccexAdjustmentDateVo;
import com.ylxx.fx.service.accex.tradeMananger.IAccexAdjustmentDateService;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.Table;

/**
 * 调整日配置信息查询
 */
@Controller
//@RequestMapping("fx")
public class AccexAdjustmentDateController {

	private static final Logger log = LoggerFactory.getLogger(AccexAdjustmentDateController.class);
	@Resource(name="iaccexAdjustmentDateService")
	private IAccexAdjustmentDateService iAccexAdjustmentDateService;
	
	//页面加载的交易品种 
	@RequestMapping(value="accex/selCurrencyPair.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selCurrencyPair(HttpServletRequest req, @RequestBody String userKey) throws Exception {
		log.info("获取交易币种：");
		return iAccexAdjustmentDateService.selCurrencyPair(userKey);	
	}
		
//	/**
//	 * 查询货币对和时间是否存在
//	 * @param req
//	 * @param accexAdjustmentDateVo
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value="accex/selectAdjustByKey.do",produces="application/json;charset=UTF-8")
//	@ResponseBody
//	public String selectAdjustByKey(HttpServletRequest req, @RequestBody AccexAdjustmentDateVo accexAdjustmentDateVo) throws Exception {
//		return iAccexAdjustmentDateService.selectAdjustByKey(accexAdjustmentDateVo.getExnm(),accexAdjustmentDateVo.getPemd());	
//	}
		
	//保存
	@RequestMapping(value="accex/addAdjust.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addAdjust(HttpServletRequest req, @RequestBody AccexAdjustmentDateVo accexAdjustmentDateVo) throws Exception {
//		exnm,货币对
//		femd,时间
//		ostp,上期结算价格
//		osbs,上期结算价格bid点差
//		osas,上期结算价格ask点差
//		nstp,下期结算价格
//		nrbs,下期市场价格bid点差
//		nsas,下期结算价格ask点差
//
//		orbs,上期市场bid点差
//		oras,上期市场ask点差
//		nsbs,下期市场bid点差
//		nras,下期市场ask点差
		//stat,W
		
		return iAccexAdjustmentDateService.addAdjust(accexAdjustmentDateVo.getOstp(),accexAdjustmentDateVo.getOsas(),accexAdjustmentDateVo.getNstp(),accexAdjustmentDateVo.getNsbs(),accexAdjustmentDateVo.getOrbs(),accexAdjustmentDateVo.getNrbs(),accexAdjustmentDateVo.getOsas(),accexAdjustmentDateVo.getNsas(),accexAdjustmentDateVo.getOras(),accexAdjustmentDateVo.getNras(),accexAdjustmentDateVo.getExnm(),accexAdjustmentDateVo.getFemd(),accexAdjustmentDateVo.getStat());	
		
	}
			
	// 分页查询调整日配置信息
	@RequestMapping(value="accex/selOilAdjustList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selOilAdjustList(HttpServletRequest req, @RequestBody AccexAdjustmentDateVo accexAdjustmentDateVo) throws Exception {
		return iAccexAdjustmentDateService.selOilAdjustList(
				accexAdjustmentDateVo.getBeginDate(),accexAdjustmentDateVo.getEndDate(),
				accexAdjustmentDateVo.getExnm(),accexAdjustmentDateVo.getPageNo(),
				accexAdjustmentDateVo.getPageSize());	
	}
			
	//修改
	@RequestMapping(value="accex/updateAdjust.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateAdjust(HttpServletRequest req,@RequestBody AccexAdjustmentDateVo accexAdjustmentDateVo) throws Exception {
		//OSTP ,OSBS ,NSTP ,NSBS ,ORBS ,NRBS ,OSAS ,NSAS ,ORAS ,NRAS ,EXNM ,FEMD ,STAT
		//OSTP,OSBS,NSTP,NSBS,ORBS,NRBS,OSAS,NSAS,ORAS,
		return iAccexAdjustmentDateService.updateAdjust(accexAdjustmentDateVo.getOstp(),accexAdjustmentDateVo.getOsas(),accexAdjustmentDateVo.getNstp(),accexAdjustmentDateVo.getNsbs(),accexAdjustmentDateVo.getOrbs(),accexAdjustmentDateVo.getNrbs(),accexAdjustmentDateVo.getOsbs(),accexAdjustmentDateVo.getNsas(),accexAdjustmentDateVo.getOras(),accexAdjustmentDateVo.getNras(),accexAdjustmentDateVo.getExnm(),accexAdjustmentDateVo.getFemd());	
	}
	//删除
	@RequestMapping(value="accex/deleteAdjustByKey.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteAdjustByKey(HttpServletRequest req, @RequestBody AccexAdjustmentDateVo accexAdjustmentDateVo) throws Exception {
		//OSTP ,OSBS ,NSTP ,NSBS ,ORBS ,NRBS ,OSAS ,NSAS ,ORAS ,NRAS ,EXNM ,FEMD ,STAT
		//OSTP,OSBS,NSTP,NSBS,ORBS,NRBS,OSAS,NSAS,ORAS,
		return iAccexAdjustmentDateService.deleteAdjustByKey(accexAdjustmentDateVo.getExnm(),accexAdjustmentDateVo.getFemd());				
	}
	
	//导出Excel
	@RequestMapping(value = "accex/selOilAdjustListExcel.do")
    public void showImgCode(AccexAdjustmentDateVo accexAdjustmentDateVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = accexAdjustmentDateVo.getTableName();//前台传的  表名
        List<Table> tableList = accexAdjustmentDateVo.getTableList();//前台传的  表头，及Key
		List<Map<String, Object>> list = iAccexAdjustmentDateService.selAllOilAdjustList(
				accexAdjustmentDateVo.getBeginDate(),accexAdjustmentDateVo.getEndDate(),
				accexAdjustmentDateVo.getExnm());
        PoiExcelExport<Map<String, Object>> pee = new PoiExcelExport<Map<String, Object>>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
