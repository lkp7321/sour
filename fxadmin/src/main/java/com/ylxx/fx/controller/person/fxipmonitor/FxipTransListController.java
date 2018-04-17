package com.ylxx.fx.controller.person.fxipmonitor;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.person.fxipmonitor.FxipTransListService;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 成交流水监控
 * @author lz130
 *
 */
@Controller
public class FxipTransListController {
	private static final Logger log = LoggerFactory.getLogger(FxipTransListController.class);
	@Resource(name="fxipTransListService")
	private FxipTransListService fxipTransListService;
	
	/**
	 * 当日成交总数
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/todayTranCount.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String todayTranCount(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String curTime = DataTimeClass.getNowDay();
		String count = fxipTransListService.todayTranCount(curUser, curTime);
		if(count == null || count.equals("")){
			count = "0";
		}
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), count);
	}
	/**
	 * 当日成交比量
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/todayTranUsam.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String todayTranUsam(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String curTime = DataTimeClass.getNowDay();
		String toUsam = fxipTransListService.todayTranUsam(curUser, curTime);
		if(toUsam == null || toUsam.equals("")){
			toUsam = "0";
		}
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), toUsam);
	}
	/**
	 * 查询成交记录
	 * @param req
	 * @param fxipMonVo
	 * @return
	 */
	@RequestMapping(value="/queryTranList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryTranList(HttpServletRequest req, @RequestBody FxipMonitorVo fxipMonVo){
		log.info("查询成交流水");
		String userKey = fxipMonVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String bTime = fxipMonVo.getBtime();
		String eTime = fxipMonVo.getEtime();
		List<Tranlist> list = fxipTransListService.timeTranList(curUser, bTime, eTime);
		if(list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前无符合条件记录");
		}
	}
}
