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

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.BusinessVo;
import com.ylxx.fx.service.person.businesspara.TradeProCalendarService;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.po.calendar.OriginalVO;
import com.ylxx.fx.service.po.calendar.TradeCodeVO;
import com.ylxx.fx.service.po.calendar.TradeProCalVO;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.GetIp;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 交易产品日历
 * @author lz130
 *
 */
@Controller
public class TradeProCalendarController {
	private static final Logger log = LoggerFactory.getLogger(TradeProCalendarController.class);
	@Resource(name="tradeProCalendarService")
	private TradeProCalendarService tradeProCalendarService;
	
	/**
	 * 交易产品日历查询
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getAllProTradeCalendar.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllProTradeCalendar(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		log.info("查询交易产品日历");
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		String calendarID = bsVo.getCalendarID(); 
		String levelTy = bsVo.getLevelTy();
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		PageInfo<TradeProCalVO> page = tradeProCalendarService.getAllProCalendarVo(GetIp.getIpAddr(req),
				prod, prod.toLowerCase(), prod.toUpperCase(), calendarID, levelTy, pageNo, pageSize);
		if(page.getList()!=null&&page.getList().size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), null);
		}
	}
	/**
	 * 删除
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/delTradeProRules.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delTradeProRules(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = curUser.getCurIP();
		TradeProCalVO tradeProCalVo = bsVo.getTradeProCalVo();
		//calendarID,trcd
		boolean flag = tradeProCalendarService.delTradeProRule(curUser, tradeProCalVo, ip);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "产品交易日历删除成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_13.getCode(),null);
		}
	}
	/**
	 * 交易产品日历检查 && 添加
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/checkCalRule.do",produces="application/json;charset=UTF-8")
	@ResponseBody 
	public String checkCalRule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		List<OriginalVO> calList = bsVo.getCalList();
		List<TradeCodeVO> codeList = bsVo.getCodeList();
		boolean flag = tradeProCalendarService.checkoutCalRule(curUser, calList, curUser.getCurIP());
		if(flag){
			TradeProCalVO tradeProCalVo = new TradeProCalVO();
			String bs1 = "";
			String bs2 = "";
			for (int i = 0; i < calList.size(); i++) {
				if(i==calList.size()-1){
					bs1 = bs1 + calList.get(i).getCalendarID();
					break;
				}
				bs1 = bs1 + calList.get(i).getCalendarID()+"&";
			}
			for (int j = 0; j < codeList.size(); j++) {
				if(j==codeList.size()-1){
					bs2 = bs2 + codeList.get(j).getTrcd();
					break;
				}
				bs2 = bs2 + codeList.get(j).getTrcd()+"&";
			}
			tradeProCalVo.setCalendarID(bs1);
			tradeProCalVo.setTrcd(bs2);
			tradeProCalVo.setPtid(curUser.getProd());
			boolean f = tradeProCalendarService.saveTradeProRule(curUser, tradeProCalVo, curUser.getCurIP());
			if(f){
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "添加产品交易日历成功");
			}else{
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_14.getCode(), null);
			}
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_21.getCode(), null);
		}
	}
	/**
	 * 交易产品日历修改
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/upTradeProRules.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	//tradeProCalVO:ptid(ptid,trcd,calendarId),calendarId,trcd
	public String upsTradeProRules(HttpServletRequest req,@RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = curUser.getCurIP();
		TradeProCalVO tradeProCalVo = bsVo.getTradeProCalVo();
		boolean flag = tradeProCalendarService.updateTradeProRule(curUser, tradeProCalVo, ip);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_15.getCode(), null);
		}
	}
	/**
	 * 根据等级获取日历规则
	 * @param req
	 * @param levelty
	 * @return
	 */
	@RequestMapping(value="getCalenLevel.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCalenLevel(HttpServletRequest req, @RequestBody String levelty){
		List<CalendarVO> list = tradeProCalendarService.selCalendarLeve(levelty);
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_18.getCode(), null);
		}
	}
	
	/**
	 * 获取相应产品的交易码
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="getProdTrade.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getProdTrade(HttpServletRequest req,@RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		List<TradeCodeVO> list = tradeProCalendarService.seProTrade(prod);
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_19.getCode(), null);
		}
	}
	/**
	 * 查询该交易的等级
	 * @param req
	 * @param calID
	 * @return
	 */
	@RequestMapping(value="selevel.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getProdTrade1(HttpServletRequest req, @RequestBody String calID){
		String level = tradeProCalendarService.selLevel(calID);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), level);
	}
}
