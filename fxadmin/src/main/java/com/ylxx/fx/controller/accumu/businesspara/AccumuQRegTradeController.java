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
import com.ylxx.fx.service.accumu.businesspara.IAccumuQRegTradeService;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.Table;
/**
 * 积存金：签约流水查询
 */
@Controller
//@RequestMapping("fx")
public class AccumuQRegTradeController {
	private static final Logger log = LoggerFactory.getLogger(AccumuQRegTradeController.class);
	
	@Resource(name="accumuQRegTradeService")
	private IAccumuQRegTradeService accumuQRegTradeService;
	
	//条件获得对应的数据
	@RequestMapping(value="accum/selectAccumuRegTrade.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectAccumuTranlist(HttpServletRequest req, @RequestBody AccumuBusinessParaVo accumuBusinessParaVo) throws Exception {
		log.info("查询签约流水：");
		return accumuQRegTradeService.selectAccumuTranlist(
				accumuBusinessParaVo.getComdata1(), accumuBusinessParaVo.getComdata3(),
				accumuBusinessParaVo.getStrcuac(), accumuBusinessParaVo.getTrdtbegin(), 
				accumuBusinessParaVo.getTrdtend(), accumuBusinessParaVo.getStrcuno(),
				accumuBusinessParaVo.getComaogcd(), accumuBusinessParaVo.getCombogcd(),
				accumuBusinessParaVo.getPageNo(),accumuBusinessParaVo.getPageSize());
	}
	//导出Excel
	@RequestMapping(value = "accum/selectAccumuTranlistExcel.do")
    public void showImgCode(AccumuBusinessParaVo accumuBusinessParaVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = accumuBusinessParaVo.getTableName();//前台传的  表名
        List<Table> tableList = accumuBusinessParaVo.getTableList();//前台传的  表头，及Key
		List<Map<String, Object>> list = accumuQRegTradeService.selectAllAccumuTranlist(
				accumuBusinessParaVo.getComdata1(), accumuBusinessParaVo.getComdata3(),
				accumuBusinessParaVo.getStrcuac(), accumuBusinessParaVo.getTrdtbegin(), 
				accumuBusinessParaVo.getTrdtend(), accumuBusinessParaVo.getStrcuno(),
				accumuBusinessParaVo.getComaogcd(), accumuBusinessParaVo.getCombogcd());
        PoiExcelExport<Map<String, Object>> pee = new PoiExcelExport<Map<String, Object>>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}