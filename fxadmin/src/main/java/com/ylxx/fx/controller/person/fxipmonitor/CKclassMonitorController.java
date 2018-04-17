package com.ylxx.fx.controller.person.fxipmonitor;


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

import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.person.fxipmonitor.CKclassMonitorService;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * P001
 * 分类敞口监控
 * @author lz130
 *
 */
@Controller
//@RequestMapping("fx")
public class CKclassMonitorController {
	private static final Logger log = LoggerFactory.getLogger(CKclassMonitorController.class);
	@Resource(name="ckclassMonitorService")
	private CKclassMonitorService ckclassMonitorService;
	
	@RequestMapping(value="/getCknoTree.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCknoTree(HttpServletRequest req,@RequestBody String userKey){
		log.info("获取敞口树");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd=curUser.getProd();
		List<Map<String,String>> list = ckclassMonitorService.gettree(prcd);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * 轧差 分类敞口
	 * @param req
	 * @param fxMonitorVo
	 * @return
	 */
	@RequestMapping(value="/getSelPrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getSelPrice(HttpServletRequest req,@RequestBody FxipMonitorVo fxMonitorVo){
		String userKey = fxMonitorVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ckno = fxMonitorVo.getCkno();
		String prcd = curUser.getProd();
		List<FormuleBean> list = ckclassMonitorService.getSelectPrice(prcd,ckno);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	//导出Excel
	@RequestMapping(value = "/CkclassOutputExcel.do")
	public void CkclassOutputExcel(HttpServletRequest req, FxipMonitorVo fxMonitorVo, HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException{
		String userKey = fxMonitorVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ckno = fxMonitorVo.getCkno();
		String prcd = curUser.getProd();
		String fileName = fxMonitorVo.getTableName();//前台传的  表名
        List<Table> tableList = fxMonitorVo.getTableList();//前台传的  表头，及Key
        //查询数据
        List<FormuleBean> list = ckclassMonitorService.getSelectPrice(prcd,ckno);
        //封装数据
        PoiExcelExport<FormuleBean> pee = new PoiExcelExport<FormuleBean>(fileName, tableList, list, resp);
        pee.exportExcel();
	}
	
	/**
	 * 累加  分类敞口
	 * @param req
	 * @param fxMonitorVo
	 * @return
	 */
	@RequestMapping(value="/getLJSelPriceTree.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getLJSelPriceTree(HttpServletRequest req,@RequestBody FxipMonitorVo fxMonitorVo){
		String userKey = fxMonitorVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ckno = fxMonitorVo.getCkno();
		String prcd = curUser.getProd();
		List<FormuleBean> list = ckclassMonitorService.getLJSelectPrice(prcd,ckno);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	
	@RequestMapping(value = "/LJSelOutputExcel.do")
	public void LJSelOutputExcel(HttpServletRequest req, FxipMonitorVo fxMonitorVo, HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException{
		String userKey = fxMonitorVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ckno = fxMonitorVo.getCkno();
		String prcd = curUser.getProd();
		String fileName = fxMonitorVo.getTableName();//前台传的  表名
        List<Table> tableList = fxMonitorVo.getTableList();//前台传的  表头，及Key
        //查询数据
        List<FormuleBean> list = ckclassMonitorService.getLJSelectPrice(prcd,ckno);
        //封装数据
        PoiExcelExport<FormuleBean> pee = new PoiExcelExport<FormuleBean>(fileName, tableList, list, resp);
        pee.exportExcel();
	}

}
