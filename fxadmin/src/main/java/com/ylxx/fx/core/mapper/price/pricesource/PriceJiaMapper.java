package com.ylxx.fx.core.mapper.price.pricesource;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.price.PriceJiaVo;
import com.ylxx.fx.service.po.calendar.CalendarVO;
public interface PriceJiaMapper {
	public List<PriceJiaVo> selPriceJiaList();
	public List<Map<String,String>> getProList();
	public int savePriceJia(@Param("ptid")String ptid,@Param("calendarId")String calendarId);
	public int delPriceJia(@Param("ptid")String ptid,@Param("calendarId")String calendarId);
	public int upPriceJia(@Param("ptid")String ptid,@Param("calendarId")String calendarId,@Param("mkpk1")String mkpk1, @Param("mkpk2")String mkpk2);
	public List<CalendarVO> selcalPricesetRule(@Param("ptid")String ptid, @Param("levelType")String levelType);
}
