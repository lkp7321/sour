package com.ylxx.fx.controller.person.businesspara;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.BusinessVo;
import com.ylxx.fx.service.person.businesspara.PtparaService;
import com.ylxx.fx.service.po.Ptpara;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 业务参数设置
 * @author lz130
 *
 */
@Controller
public class PtparaController {
	private static final Logger log = LoggerFactory.getLogger(PtparaController.class);
	@Resource(name="ptparaService")
	private PtparaService ptparaService;
	
	/**
	 * 交易参数查询
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getAllPtpara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllPtpara(HttpServletRequest req,@RequestBody BusinessVo bsVo){
		log.info("获取交易参数设置数据");
		
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(bsVo.getUserKey());
		String prod = curUser.getProd();//产品
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		PageInfo<Ptpara> page = ptparaService.getptparalist(prod, pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 修改交易参数
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/upPtpara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upPtpara(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Ptpara ptpara = bsVo.getPtpara();
		//Ptpara ptpara = new Ptpara();//paid:编号,remk:说明,valu:参数值,stat:状态,
		boolean flag = ptparaService.updateptpara(curUser, ptpara);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "交易参数修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_08.getCode(), "交易参数修改失败");
		}
	}
}
