package com.ylxx.fx.controller.price.pricesource;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.price.PriceMngVo;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 审计管理
 */
@Controller
//@RequestMapping("fx")
public class PriceMngController {
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	/**
	 * 审计管理
	 * @param priceMngVo
	 * @return
	 */
	@RequestMapping(value="price/mng_logall.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mng_logall(@RequestBody PriceMngVo priceMngVo){
		String userKey = priceMngVo.getUserKey(); 
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Integer pageNo = priceMngVo.getPageNo();
		Integer pageSize = priceMngVo.getPageSize();
		PageInfo<Logfile> page = logfileCmdService
				.selectMng_logAll(curUser.getUsnm(),priceMngVo.getUser(),priceMngVo.getData(),priceMngVo.getEndata(),priceMngVo.getHandle(),pageNo,pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
}
