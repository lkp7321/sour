package com.ylxx.fx.service.person.businesspara;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.po.calendar.OriginalVO;
import com.ylxx.fx.service.po.calendar.TradeCodeVO;
import com.ylxx.fx.service.po.calendar.TradeProCalVO;
import com.ylxx.fx.utils.CurrUser;

public interface TradeProCalendarService {
	public PageInfo<TradeProCalVO> getAllProCalendarVo(String ip,
			String prod, String prod1, String prod2,
			String calendarID, String levelTy,
			Integer pageNo, Integer pageSize);
	public boolean delTradeProRule(CurrUser curUser, TradeProCalVO tradeProCalVo,String ip);
	public boolean saveTradeProRule(CurrUser curUser,TradeProCalVO tradeProCalVo, String ip);
	public boolean updateTradeProRule(CurrUser curUser, TradeProCalVO tradeProCalVo, String ip);
	public List<CalendarVO> selCalendarLeve(String levelty);
	public List<TradeCodeVO> seProTrade(String prod);
	public boolean checkoutCalRule(CurrUser curUser,List<OriginalVO> calList,String ip);
	public String selLevel(String calID);
	
}
