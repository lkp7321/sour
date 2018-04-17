package com.ylxx.fx.controller.person.fxipmonitor;


import java.io.IOException;
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

import com.ylxx.fx.core.domain.price.MonitorVo;
import com.ylxx.fx.service.person.fxipmonitor.CKtotalMonitorService;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.service.po.FormuleBeanForAcc;
import com.ylxx.fx.service.po.price.ExposureBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * P001
 * 总敞口监控
 * @author lz130
 *
 */
@Controller
public class CKtotalMonitorController {
	private static final Logger log = LoggerFactory.getLogger(CKtotalMonitorController.class);
	@Resource(name="cktotalMonitorService")
	private CKtotalMonitorService cktotalMonitorService;
	
	/**
	 *	查询轧差敞口监控的总敞口监控数据
	 * @return
	 */
	@RequestMapping(value="/getCktotalMonitor.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCktotalMonitor(HttpServletRequest req, @RequestBody String userKey){
		log.info("\n轧差敞口总敞口监控");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd = curUser.getProd();
		List<FormuleBean> list = cktotalMonitorService.fxipExceptionData(prcd);
		if(list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前暂无数据");
		}
	}
	
	@RequestMapping(value = "/CktotalPutputExcel.do")
	public void CktotalPutputExcel(HttpServletRequest req, MonitorVo monitorVo, HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException{
		String userKey = monitorVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd = curUser.getProd();
		String fileName = monitorVo.getTableName();//前台传的  表名
        List<Table> tableList = monitorVo.getTableList();//前台传的  表头，及Key
        //查询数据
        List<FormuleBean> list = cktotalMonitorService.fxipExceptionData(prcd);
        //数据封装
        PoiExcelExport<FormuleBean> pee = new PoiExcelExport<FormuleBean>(fileName, tableList, list, resp);
        pee.exportExcel();
	}
	
	/**
	 * 查询累加敞口监控的总敞口监控数据
	 * @return
	 */
	@RequestMapping(value="/getLjCktotalMonitor.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getLjCktotalMonitor(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd = curUser.getProd();
		List<FormuleBean> list = cktotalMonitorService.fxipCKData(prcd);
		if(list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前暂无数据");
		}
	}
	/**
	 * 导出Excel
	 * @param req
	 * @param monitorVo
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/LjCktotalPutputExcel.do")
	public void LjCktotalPutputExcel(HttpServletRequest req, MonitorVo monitorVo, HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException{
		String userKey = monitorVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd = curUser.getProd();
		String fileName = monitorVo.getTableName();//前台传的  表名
        List<Table> tableList = monitorVo.getTableList();//前台传的  表头，及Key
        //查询数据
        List<FormuleBean> list = cktotalMonitorService.fxipCKData(prcd);
        //数据封装
        PoiExcelExport<FormuleBean> pee = new PoiExcelExport<FormuleBean>(fileName, tableList, list, resp);
        pee.exportExcel();
	}
	
	/**
	 * 报价平台--监控管理
	 * 查询账户交易
	 * prcd,prcd1
	 * 外币：P072,P075
	 * 贵金属敞口监控：P073,""
	 * 结售汇敞口监控：P071,""
	 * 
	 */
	@RequestMapping(value="price/accexPriceMonitor.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String accexPriceMonitor(HttpServletRequest req, @RequestBody MonitorVo monitorVo){
		String prcd=monitorVo.getPrcd();
		String prcd1 = monitorVo.getPrcd1();
		List<FormuleBeanForAcc> list = cktotalMonitorService.selectPriceData(prcd, prcd1);
		if(list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前暂无数据");
		}
	}
	
	/**
	 * P004
	 * 总敞口监控
	 */
	@RequestMapping(value="pere/getsCktotalMonitor.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getsCktotalMonitor(HttpServletRequest req, @RequestBody String userKey){
		log.info("\n结售汇总敞口监控");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd = curUser.getProd();
		List<ExposureBean> list = cktotalMonitorService.pereFxipExceptionData(prcd);
		if(list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前暂无数据");
		}
	}
}
