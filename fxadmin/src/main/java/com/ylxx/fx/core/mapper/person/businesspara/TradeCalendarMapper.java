package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.calendar.CalendarVO;
/**
 * 日历规则
 * @author lz130
 *
 */
public interface TradeCalendarMapper {
	List<CalendarVO> selectCalenderRuleList();
	int updateCalendarRule(@Param("calVo")CalendarVO calVo);
	
	int deleteCalendaRule(@Param("calendarid") String calendarid);
	int deleteProCalendarRule(@Param("calendarid") String calendarid);
	int deletePriceRecCalendarRule(@Param("calendarid") String calendarid);
	int deletePriceJiaCalendarRule(@Param("calendarid") String calendarid);
	
	int insertCalendarRule(@Param("calVo")CalendarVO calVo);
	
	String selCalId(@Param("level")String level);
}
