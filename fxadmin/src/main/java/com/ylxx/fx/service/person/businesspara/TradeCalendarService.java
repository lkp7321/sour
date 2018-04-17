package com.ylxx.fx.service.person.businesspara;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.utils.CurrUser;
/**
 * 日历规则
 * @author lz130
 *
 */
public interface TradeCalendarService {
	/**
	 * 查询数据
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo<CalendarVO> getAllCalendarVo(
			Integer pageNo, Integer pageSize);
	/**
	 * 修改
	 * @param curUser
	 * @param calVo
	 * @param ip
	 * @return
	 */
	boolean updateCalendarRule(CurrUser curUser, CalendarVO calVo, String ip);
	/**
	 * 删除
	 * @param curUser
	 * @param calendarId
	 * @param ip
	 * @return
	 */
	boolean delCalendarRules(CurrUser curUser, String calendarId, String ip);
	/**
	 * 添加
	 * @param curUser
	 * @param calVo
	 * @param ip
	 * @return
	 */
	boolean  saveCalendarRules(CurrUser curUser, CalendarVO calVo, String ip);
}
