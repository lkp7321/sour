package com.ylxx.fx.controller.price.pricemonitor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.core.domain.price.AllMakPrice;
import com.ylxx.fx.core.domain.price.PriceMonitorVo;
import com.ylxx.fx.service.price.pricemonitor.FpriceMonitorService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;

@Controller
//@RequestMapping("fx")
public class FpriceMonitorController {
	@Resource(name="fpriceMonitorService")
	private FpriceMonitorService fpriceMonitorService;
	private String codeMessage="";
	//利率市场查询：
	@RequestMapping(value="price/getFpriceMonitor.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getFpriceMonitor(HttpServletRequest req){
		String type = "1";
		String name = "M0301";
		List<AllMakPrice> list = fpriceMonitorService.getAllMaketPrice(type, name);
		if(list!=null&&list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "暂无数据";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(),codeMessage);
		}
	}
	//导出Excel
	@RequestMapping(value = "price/liLvMarketExcel.do")
    public void liLvMarketExcel(PriceMonitorVo priceMonitorVo, HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = priceMonitorVo.getTableName();//前台传的  表名
        List<Table> tableList = priceMonitorVo.getTableList();//前台传的  表头，及Key
        String type = "1";
		String name = "M0301";
		List<AllMakPrice> list = fpriceMonitorService.getAllMaketPrice(type, name);
        PoiExcelExport<AllMakPrice> pee = new PoiExcelExport<AllMakPrice>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
	/**
	 * 价格源监控
	 * @param priceMonitorVo
	 * @return
	 */
	@RequestMapping(value="price/getffpriceMonitor.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getffpriceMonitor(@RequestBody PriceMonitorVo priceMonitorVo){
		String type = priceMonitorVo.getType();
		String name = priceMonitorVo.getName();
		List<AllMakPrice> list = fpriceMonitorService.getAllMaketPrice(type, name);
		if(list!=null&&list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "暂无数据";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(),codeMessage);
		}
	}
	/**
	 * 获取价格源市场树
	 * @return
	 */
	@RequestMapping(value="price/getMktree.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getMktree(){
		List<Map<String,String>> list = fpriceMonitorService.getAllMk();
		codeMessage = JSON.toJSONString(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
	}
	/**
	 * 获取价格元监控，货币对树
	 * @return
	 */
	@RequestMapping(value="price/getExnmtree.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getExnmtree(){
		List<Map<String,String>> list = fpriceMonitorService.getAllexnm();
		codeMessage = JSON.toJSONString(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
	}
	/**
	 * 报价平台，产品管理的 -产品源监控
	 * @param ptid
	 * @return
	 */
	@RequestMapping(value="price/getPriceProdse.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPriceProdse(@RequestBody String ptid){
		List<FxipMonitorVo> list = fpriceMonitorService.selectAllPdtprod(ptid);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
}
