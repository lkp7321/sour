package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.po.calendar.TradeCodeVO;

public interface CalendarShowMapper {
	public List<CalendarVO> selectRule(
			@Param("proId")String proId,@Param("tradeCode")String tradeCode,
			@Param("levelType")String levelType);
	public List<TradeCodeVO> selTradeCodeList(@Param("proid")String proid);
}
