package com.ylxx.fx.controller.accumu.businesspara;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.AccumuBusinessParaVo;
import com.ylxx.fx.service.accumu.businesspara.IAccumuQTranTradeService;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.Table;
/**
 * 积存金：
 * 定期申购签约解约
 */
@Controller
//@RequestMapping("fx")
public class AccumuQTranTradeController {
	@Resource(name="accumuQTranTradeService")
	private IAccumuQTranTradeService accumuQTranTradeService;
	
	
	/**
	 * 定期申购签约解约查询 本地流水号LCNO..卡号CUAC..签约状态RGTP..签约日期RGDT..签约时间RGTM..解约日期CRDT..解约时间CRTM..定投频率MSFY..定投数量BYAM
	 * @param req
	 * @param accumuBusinessParaVo
	 * @return
	 */
	@RequestMapping(value="accum/queryTranTrade.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryTranTrade(HttpServletRequest req, @RequestBody AccumuBusinessParaVo accumuBusinessParaVo){
		return accumuQTranTradeService.selectAccumuTranTrade(accumuBusinessParaVo.getComdata1(),accumuBusinessParaVo.getComdata3(),accumuBusinessParaVo.getStrcuac(),accumuBusinessParaVo.getTrdtbegin(),accumuBusinessParaVo.getTrdtend(),accumuBusinessParaVo.getPageNo(),accumuBusinessParaVo.getPageSize());
	}
	
	/**
	 * 导出Excel
	 * @param accumuBusinessParaVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "accum/queryTranTradeoutputexcel.do")
    public void showImgCode(AccumuBusinessParaVo accumuBusinessParaVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = accumuBusinessParaVo.getTableName();//前台传的  表名
        List<Table> tableList = accumuBusinessParaVo.getTableList();//前台传的  表头，及Key
		List<Tranlist> list = accumuQTranTradeService.selAccumuTranTrade(accumuBusinessParaVo.getComdata1(),accumuBusinessParaVo.getComdata3(),accumuBusinessParaVo.getStrcuac(),accumuBusinessParaVo.getTrdtbegin(),accumuBusinessParaVo.getTrdtend());
        PoiExcelExport<Tranlist> pee = new PoiExcelExport<Tranlist>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
