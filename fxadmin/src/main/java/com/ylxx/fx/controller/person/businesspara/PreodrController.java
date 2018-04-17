package com.ylxx.fx.controller.person.businesspara;

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

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.BusinessVo;
import com.ylxx.fx.service.person.businesspara.PreodrService;
import com.ylxx.fx.service.po.Preodrlist;
import com.ylxx.fx.service.po.Trd_ogcd;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * 个人实盘 /纸黄金 /积存金
 * 挂单流水查询
 */
@Controller
public class PreodrController {
	private static final Logger log = LoggerFactory.getLogger(PreodrController.class);
	@Resource(name="preodrService")
	private PreodrService preodrService;
	
	/**
	 * 机构框1
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/comboxA.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String comboxA(HttpServletRequest req){
		List<Trd_ogcd> list = preodrService.getqueryOrgan1();
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取机构失败");
		}
	}
	/**
	 * 机构框2
	 * @param req
	 * @param comaogcd
	 * @return
	 */
	@RequestMapping(value="/comboxB.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String comboxB(HttpServletRequest req, @RequestBody String comaogcd){
		//String comaogcd = "A000";//第一个机构框的编号
		List<Trd_ogcd> list = preodrService.getqueryOrgan2(comaogcd);
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取机构失败");
		}
	}
	/**
	 * 挂单流水
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getAllPreodrList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllPreodrList(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		String strcuac = bsVo.getStrcuac(); //卡号
		String trdtbegin = bsVo.getTrdtbegin(); //
		String trdtend = bsVo.getTrdtend(); //
		String comaogcd = bsVo.getComaogcd();//机构一编号
		String combogcd = bsVo.getCombogcd();//all/
		log.info("个人实盘查询挂单流水");
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		PageInfo<Preodrlist> page = preodrService.getAllPreodrList(
				prod, strcuac, trdtbegin, trdtend, 
				comaogcd, combogcd, pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 导出Excel
	 * @param bsVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException 
	 */
	@RequestMapping(value = "/preodrOutputexcel.do")
    public void showImgCodePreodr(BusinessVo bsVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = bsVo.getTableName();//前台传的  表名
        List<Table> tableList = bsVo.getTableList();//前台传的  表头，及Key
        String strcuac = bsVo.getStrcuac(); //卡号
		String trdtbegin = bsVo.getTrdtbegin(); //
		String trdtend = bsVo.getTrdtend(); //
		String comaogcd = bsVo.getComaogcd();//机构一编号
		String combogcd = bsVo.getCombogcd();//all/
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		//这里是我自己查询数据的过程
        List<Preodrlist> list = preodrService.AllPreodrList(prod, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
        PoiExcelExport<Preodrlist> pee = new PoiExcelExport<Preodrlist>(fileName, tableList, list, resp);
        pee.exportExcel();
    }

}
