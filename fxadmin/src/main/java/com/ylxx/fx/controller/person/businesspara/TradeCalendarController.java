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
import com.ylxx.fx.service.person.businesspara.TradeCalendarService;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 日历规则
 * @author lz130
 *
 */
@Controller
public class TradeCalendarController {
	private static final Logger log = LoggerFactory.getLogger(TradeCalendarController.class);
	@Resource(name="tradeCalendarService")
	private TradeCalendarService tradeCalendarService;
	/**
	 * 日历规则查询
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getAllTradeCalendar.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllTradeCalendar(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		log.info("查询日历规则");
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		PageInfo<CalendarVO> page = tradeCalendarService.getAllCalendarVo(pageNo, pageSize);
		if(page.getList()!=null&&page.getList().size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(),null);
		}
	}
	/**
	 * 删除
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/delCalendarRule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delCalendarRule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = curUser.getCurIP();
		String calendarId = bsVo.getCalendarID();
		boolean flag = tradeCalendarService.delCalendarRules(curUser, calendarId, ip);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "删除日历规则成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_09.getCode(), "删除日历规则失败");
		}
	}
	/**
	 * 添加
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/saveCalendarRule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String saveCalendarRule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CalendarVO calVo = bsVo.getCalVo();
		String ip = curUser.getCurIP();
		//beginweek,endweek,begindate,enddate,calendarNmae,endtime,flag,isquantian leveltype
		boolean flag = tradeCalendarService.saveCalendarRules(curUser, calVo, ip);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "添加日历规则成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_11.getCode(),"添加日历规则失败");
		}
	}
	/**
	 * 修改
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/updateCalendarRules.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateCalendarRules(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CalendarVO calVo = bsVo.getCalVo();
		String ip = curUser.getCurIP();
		boolean flag = tradeCalendarService.updateCalendarRule(curUser, calVo, ip);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "日历规则修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_12.getCode(), "日历规则修改失败");
		}
	}
}
