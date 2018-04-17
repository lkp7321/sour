package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.po.calendar.TradeCodeVO;
import com.ylxx.fx.service.po.calendar.TradeProCalVO;
/**
 * 交易产品日历
 * @author lz130
 *
 */
public interface TradeProCalendarMapper {
	/**
	 * 查询交易产品日历
	 * @param proid
	 * @param proidl
	 * @param proidu
	 * @param calendarID
	 * @param levelTy
	 * @return
	 */
	List<TradeProCalVO> selectProCalender(@Param("proid")String proid,
			@Param("proidl")String proidl,@Param("proidu")String proidu,
			@Param("calendarID")String calendarID,@Param("levelTy")String levelTy);
	
	//删除
	public int deleteTradeProRule(@Param("tradeProCalVo")TradeProCalVO tradeProCalVo);
	
	//新增
	public int saveTradeProRule(@Param("ptid")String ptid,
			@Param("trcd")String trcd,@Param("caleId")String caleId);
	
	//修改
	public int updateTradeProRule(@Param("trcd")String trcd, @Param("calendarId")String calendarId, 
			@Param("keys0")String keys0, @Param("keys1")String keys1, @Param("keys2")String keys2);
	//根据等级查表
	public  List<CalendarVO> selcallevel(@Param("levelty")String levelty);
	
	//查询产品交易码
	public List<TradeCodeVO> selProTrade(@Param("prod")String prod);
	
	public List<CalendarVO> selCaleRuleList(@Param("calendarId")String calendarId);
	//查出相应的日历规则的等级
	public String selLevel(@Param("calendarId")String calendarId);
}
