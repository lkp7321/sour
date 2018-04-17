package com.ylxx.fx.controller.person.businesspara;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylxx.fx.core.domain.price.MonitorVo;
import com.ylxx.fx.service.person.businesspara.PpbusinessService;
import com.ylxx.fx.service.po.CK_handMxdetail;
import com.ylxx.fx.service.po.QutCmmPrice;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * 账户交易-手工平盘
 * 平盘交易查询,原油手工平盘
 */
@Controller
public class PpbusinessController {
	@Resource(name="ppbusinessService")
	private PpbusinessService ppbusinessService;
	
	/**
	 * 平盘交易查询
	 * @param req
	 * @param monitVo
	 * @return
	 */
	@RequestMapping(value="/pphandsel.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String dgssdf(HttpServletRequest req, @RequestBody MonitorVo monitVo){
		String apdt = monitVo.getApdt();//登记日期
		String jsdt = monitVo.getJsdt();
		List<CK_handMxdetail> list = ppbusinessService.getdate(apdt, jsdt);
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无符合条件记录");
		}
	}
	/**
	 * 平盘交易导出Excel
	 * @param monitVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/pphandselExcel.do")
    public void pphandselExcel(MonitorVo monitVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = monitVo.getTableName();//前台传的  表名
        List<Table> tableList = monitVo.getTableList();//前台传的  表头，及Key
        String apdt = monitVo.getApdt();//登记日期
		String jsdt = monitVo.getJsdt();
		List<CK_handMxdetail> list = ppbusinessService.getdate(apdt, jsdt);
        PoiExcelExport<CK_handMxdetail> pee = new PoiExcelExport<CK_handMxdetail>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
	
	/**
	 * 原油手工平盘查询
	 * @return
	 */
	@RequestMapping(value="/oilhandsel.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String oilhandsel(){
		List<QutCmmPrice> list = ppbusinessService.selPrice();
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无符合条件记录");
		}
	}
	/**
	 * 双击事件
	 * doubleClick
	 * @param req
	 * @param monitVo
	 * @return
	 */
	@RequestMapping(value="/oilsendToCkServer.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String oilsendToCkServer(HttpServletRequest req, @RequestBody MonitorVo monitVo){
		QutCmmPrice price = monitVo.getPrice();
		String userKey = monitVo.getUserKey();
		String ss = ppbusinessService.sendToCkServer(price, userKey);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), ss);
	}
}
