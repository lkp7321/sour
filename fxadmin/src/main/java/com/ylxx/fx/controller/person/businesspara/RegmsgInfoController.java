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
import com.ylxx.fx.service.person.businesspara.RegmsgInfoService;
import com.ylxx.fx.service.po.RegmsgBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 客户签约信息
 * @author lz130
 *
 */
@Controller
public class RegmsgInfoController {
	private static final Logger log = LoggerFactory.getLogger(RegmsgInfoController.class);
	@Resource(name="regmsgnfoService")
	private RegmsgInfoService regmsgnfoService;
	/**
	 * 客户签约信息查询
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getRegmsgInfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getRegmsgInfo(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		log.info("查询客户签约信息");
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String userProd=curUser.getProd();//产品
		String strcuno=bsVo.getStrcuno();//客户号
		String strcuac=bsVo.getStrcuac();//卡号
		String comaogcd=bsVo.getComaogcd();//"A000";//A000
		String combogcd=bsVo.getCombogcd();//"all";//all
		Integer pageNo=bsVo.getPageNo();
		Integer pageSize=bsVo.getPageSize();
		PageInfo<RegmsgBean> page = regmsgnfoService.getAllRegmsgInfo(
				userProd, strcuno, strcuac, comaogcd, combogcd, pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(),page);
	}
}
