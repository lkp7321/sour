package com.ylxx.fx.controller.person.businesspara;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.BusinessVo;
import com.ylxx.fx.service.person.businesspara.CalendarShowService;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.po.calendar.TradeCodeVO;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.calendar.CalUDPClient;
/**
 * 日历规则展示
 * @author lz130
 *
 */
@Controller
public class CalendarShowController {
	private static final Logger log = LoggerFactory.getLogger(CalendarShowController.class);
	@Resource(name="calendarShowService")
	private CalendarShowService calendarShowService;
	private CalUDPClient caludp = new CalUDPClient();
	
	/**
	 * 日历规则查询
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getCalendarShow.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCalendarShow(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		log.info("日历展示");
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String proid = curUser.getProd();
		String tradeCode = bsVo.getTradeCode();//"S1101";
		String caltime = bsVo.getCaltime();//"20171011";
		List<CalendarVO> list = calendarShowService.getcalRuleList(proid, tradeCode, caltime);
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "无符合条件记录");
		}
	}
	/**
	 * 获取交易码
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/allTradeCodeList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String allTradeCodeList(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String proid = curUser.getProd();
		List<TradeCodeVO> list = calendarShowService.getTradeCodeList(proid);
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_16.getCode(), "获取交易码错误");
		}
	}
	/**
	 * 生效
	 */
	@RequestMapping(value="/SendSocketPdtInfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public void SendSocketPdtInfo(){
		caludp.SendSocketPdtInfo();
	}
}
