package com.ylxx.fx.controller.pere.presysmanager;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.ElecTellerManagerSelectVo;
import com.ylxx.fx.service.pere.presysmanager.IQueryDiscountRateService;
import com.ylxx.fx.service.po.QueryDiscountRate;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * 折算汇率查询
 */
@Controller
//@RequestMapping("fx")

public class QueryDiscountRateController {
private static final Logger LOGGER = LoggerFactory.getLogger(QueryDiscountRateController.class);
	
	@Resource(name="iQueryDiscountRateService")
	private IQueryDiscountRateService iQueryDiscountRateService;
	
	
	@RequestMapping(value="/selcDisRate.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selcDisRate(@RequestBody ElecTellerManagerSelectVo EtmsVo ,HttpServletRequest req) throws Exception {
		Integer pageNo = EtmsVo.getPageNo();
		Integer pageSize = EtmsVo.getPageSize();
		PageInfo<QueryDiscountRate>  page = iQueryDiscountRateService.pageSelcDisRate(pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);	
	}
	//导出
	@RequestMapping(value = "/selcDisRateExcel.do")
    public void selcDisRateExcel(ElecTellerManagerSelectVo EtmsVo,HttpServletRequest req, 
             HttpServletResponse resp) throws Exception {
		String fileName = EtmsVo.getTableName();//前台传的  表名
        List<Table> tableList = EtmsVo.getTableList();//前台传的  表头，及Key
        List<QueryDiscountRate> list = iQueryDiscountRateService.selcAllDisRate();
        PoiExcelExport<QueryDiscountRate> pee = new PoiExcelExport<QueryDiscountRate>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
	
	

}
