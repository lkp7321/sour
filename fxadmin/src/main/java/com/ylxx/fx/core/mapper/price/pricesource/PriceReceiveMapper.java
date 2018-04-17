package com.ylxx.fx.core.mapper.price.pricesource;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.price.PriceRecVo;
import com.ylxx.fx.service.po.calendar.CalendarVO;
public interface PriceReceiveMapper {
	public List<Map<String,String>> selectMarketinfo();
	public List<PriceRecVo> getPriceRecList(@Param("mkcode")String mkcode,@Param("exnm")String exnm);
	public List<String> getBiBieDuiList(@Param("mkid")String mkid);
	public int insertPriceRec(@Param("mkid")String mkid,@Param("exnm")String exnm,@Param("calendarId")String calendarId);
	public int updatePriceRec(@Param("mkid")String mkid, @Param("exnm")String exnm, @Param("calendarId")String calendarId,
			@Param("mkpk1")String mkpk1, @Param("mkpk2")String mkpk2, @Param("mkpk3")String mkpk3);
	public int deletePriceRec(@Param("mkid")String mkid,@Param("exnm")String exnm,@Param("calendarID")String calendarID);
	public int batchUpPriceRecRule(@Param("calendarId")String calendarId,@Param("priceRecBean")PriceRecVo priceRecBean);
	public List<CalendarVO> selRule(@Param("mkid")String mkid,@Param("exnm")String exnm,@Param("levelType")String levelType);
}
