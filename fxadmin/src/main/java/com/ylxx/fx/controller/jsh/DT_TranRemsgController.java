package com.ylxx.fx.controller.jsh;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.jsh.DT_TranRemsgService;
import com.ylxx.fx.service.po.jsh.DT_Price;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.service.po.jsh.TbTrd_RegMsgList;
import com.ylxx.fx.service.po.jsh.TbTrd_TranList;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 定投签约，流水查询
 * @author lz130
 *
 */
@Controller
public class DT_TranRemsgController {
	private static final Logger log = LoggerFactory.getLogger(DT_TranRemsgController.class);
	@Resource(name="dt_TranRemsgService")
	private DT_TranRemsgService dt_TranRemsgService;
	/**
	 * DT Ptpara
	 * @return
	 */
	@RequestMapping("/toDTPtpara.do")
	public String toDTPtpara() {
		return "youli/DT/dtpara";
	}
	/**
	 * DT Signing
	 * @return
	 */
	@RequestMapping("/toDTQian.do")
	public String toDTQian() {
		return "youli/DT/dtqian";
	}
	/**
	 * DT Trans
	 * @return
	 */
	@RequestMapping("/toDTTran.do")
	public String toDTTran() {
		return "youli/DT/tdtran";
	}
	/**
	 * DT Price
	 * @return
	 */
	@RequestMapping("/toDTPrice.do")
	public String toDTPrice() {
		return "youli/DT/dtprice";
	}
	
	//------PriceManager---------
	/**
	 * custPrice
	 * @return
	 */
	@RequestMapping("/toPriceCust.do")
	public String toPriceCust() {
		return "youli/priceManage/customerPrice";
	}
	/**
	 * brnchPrice
	 * @return
	 */
	@RequestMapping("/toPriceFen.do")
	public String toPriceFen() {
		return "youli/priceManage/branchPrice";
	}
	/**
	 * pricePtpara
	 * @return
	 */
	 @RequestMapping("/toPricePtpara.do")
	public String toPricePtpara() {
		return "youli/priceManage/priceParameter";
	}
	/**
	 * preorTrans
	 * @return
	 */
	@RequestMapping("/toCuoHe.do")
	public String toCuoHe() {
		return "youli/priceManage/cuohe";
	}
	
	//------WG manager---------
    /**
     * WG login
     * @return
     */
	@RequestMapping("/toGuiYuan.do")
	public String toGuiYuan() {
		return "youli/foreignAffairs/tellerManage";
	}
	/**
	 * WG country
	 * @return
	 */
	@RequestMapping("/toCountry.do")
	public String toCountry() {
		return "youli/foreignAffairs/differentCountries";
	}
	/**
	 * WG alarm   
	 * @return
	 */
	@RequestMapping("/toErrorAlarm.do")
	public String toErrorAlarm() {
		return "youli/foreignAffairs/alarmManage";
	}
    /**
     * WG translist
     * @return
     */
	@RequestMapping("/toTransList.do")
	public String toTransList() {
		return "youli/foreignAffairs/tradeWater";
	}
	/**
	 * WG focusName
	 * @return
	 */
	@RequestMapping("/toFocusName.do")
	public String toFocusName() {
		return "youli/foreignAffairs/blacklist";
	}
	/**
	 * WG usdPrice
	 * @return
	 */
	@RequestMapping("/toLiLv.do")
	public String toLiLv() {
		return "youli/foreignAffairs/rateManage";
	}
	//================SystemManager=====================
	/**
	 * system paramter
	 * @return
	 */
	@RequestMapping("/toSysCon.do")
	public String toSysCon() {
		return "youli/DT/dtpara";
	}
	/**
	 * system errcode
	 * @return
	 */
	@RequestMapping("/toErrorCode.do")
	public String toErrorCode() {
		return "youli/DT/errorcode";
	}
	/**
	 * system Cymsg
	 * @return
	 */
	@RequestMapping("/toCymsg.do")
	public String toCymsg() {
		return "youli/system/cuurencymana";
	}
	/**
	 * system Currmsg
	 * @return
	 */
	@RequestMapping("/toCurrmsg.do")
	public String toCurrmsg() {
		return "youli/system/cuurpairmana";
	}
	//System log
	@RequestMapping("/toMngLog.do")
	public String toMngLog() {
		return "youli/system/logmanage";
	}	
	/**
	 * My Test page
	 * @return
	 */
	@RequestMapping("two.do")
	public String two() {
		return "jav/two";
	}
	/**
	 * MyTest 
	 * @param req
	 * @param pageNo
	 * @param pageSize
	 * @param startDate
	 * @return
	 */
	@RequestMapping(value="/jsh/getMyTest.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String sagsdg(HttpServletRequest req, int pageNo,int pageSize, String startDate) {
		System.out.println(pageNo);
		System.out.println(pageSize);
		System.out.println(startDate);
		return "哦哟";
	}
	/**
	 * 定投签约信息查询
	 * @param tbTrd_RegMsgList
	 * @return
	 */
	@RequestMapping(value="/jsh/getDtTranRemsgs.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getDtTranRemsgs(@RequestBody JshPages<TbTrd_RegMsgList> tbTrd_RegMsgList) {
		log.info("开始查询定投签约信息：");
		PageInfo<Map<String, Object>> page = dt_TranRemsgService.selectDtTranRemsgList(tbTrd_RegMsgList);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 定投签约，
	 * 导出Excel
	 * @param tableName：表名
	 * @param rgtp：签约/解约
	 * @param rgdt：起始日期
	 * @param crdt：终止日期
	 * @param exnm：标准币别对
	 * @param cuac：卡号
	 * @param tbTrd_RegMsgList：键值
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	 
	@RequestMapping(value = "/dtRegmsgListExcel.do")
    public void dtRegmsgListExcel(String tableName, String rgtp, String rgdt, String crdt, String exnm, String cuac,JshPages<TbTrd_RegMsgList> tbTrd_RegMsgList, HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		log.info("导出Excel");
		List<Map<String, Object>> list = dt_TranRemsgService.selectAllDtTranRemsgList(rgtp, rgdt, crdt, exnm, cuac);
        PoiExcelExport<Map<String, Object>> pee = new PoiExcelExport<Map<String, Object>>(tableName, tbTrd_RegMsgList.getTableList(), list, resp);
        pee.exportExcel();
    }
	/**
	 * 定投流水信息查询
	 * @param tbTrd_TranList
	 * @return
	 */
	@RequestMapping(value="/jsh/getDtTranList.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getDtTranLists(@RequestBody JshPages<TbTrd_TranList> tbTrd_TranList) {
		log.info("开始查询定投流水信息：");
		PageInfo<Map<String, Object>> page = dt_TranRemsgService.selectDtTransList(tbTrd_TranList);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 定投流水,导出Excel
	 * @param trdt：起始日期
	 * @param trtm：终止日期
	 * @param cuac：卡号
	 * @param fonm：外币币种
	 * @param tableName：Excel表名
	 * @param trans：键值
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/dtTranListExcel.do")
    public void dtTranListExcel(String trdt, String trtm, String cuac, String fonm, String tableName, JshPages<TbTrd_TranList> trans, HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		log.info("导出Excel");
		List<Map<String, Object>> list = dt_TranRemsgService.selectAllDtTransList(trdt, trtm, cuac, fonm);
        PoiExcelExport<Map<String, Object>> pee = new PoiExcelExport<Map<String, Object>>(tableName, trans.getTableList(), list, resp);
        pee.exportExcel();
    }
	
	/**
	 * 定投价格查询
	 * @param dT_Price
	 * @return
	 */
	@RequestMapping(value="/jsh/getDtPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getDtPrices(@RequestBody JshPages<DT_Price> dT_Price) {
		log.info("开始查询定投价格信息：");
		PageInfo<Map<String, Object>> page = dt_TranRemsgService.selectDtPriceList(dT_Price);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 定投价格 
	 * 导出Excel
	 * @param trdt:起始日期
	 * @param trtm:终止日期
	 * @param exnm:标准币别对
	 * @param tableName:Excel表名
	 * @param dT_Price:存储excel表头的键值对
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/dtPriceListExcel.do")
    public void dtPriceListExcel(String trdt, String trtm, String exnm,String tableName, JshPages<DT_Price> dT_Price, HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		log.info("导出Excel");
		List<Map<String, Object>> list = dt_TranRemsgService.selectAllDtPriceList(trdt, trtm, exnm);
        PoiExcelExport<Map<String, Object>> pee = new PoiExcelExport<Map<String, Object>>(tableName, dT_Price.getTableList(), list, resp);
        pee.exportExcel();
    }
}
