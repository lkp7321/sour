package com.ylxx.fx.service.person.businesspara;

import java.util.List;

import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.po.calendar.TradeCodeVO;

public interface CalendarShowService {
	public List<CalendarVO> getcalRuleList(
			String proid,String tradeCode,String caltime);
	public List<TradeCodeVO> getTradeCodeList(String proid);
}
