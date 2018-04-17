package com.ylxx.fx.service.price.pricesource;
import java.util.*;

import com.ylxx.fx.core.domain.price.PriceRecVo;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.utils.CurrUser;
public interface PriceReceveService {
	public List<Map<String,String>> getMarketinfo();
	public List<PriceRecVo> getPriceRecLists(String mkcode,String exnm);
	public List<String> getBiBieDuiLists(String mkid);
	public boolean insertPriceRec(CurrUser curUser, PriceRecVo priceRecBean);
	public boolean upPriceRec(String userKey, PriceRecVo priceRecBean);
	public boolean deletePriceRec(String userKey,PriceRecVo priceRecBean);
	public boolean upsPriceRecList(String userKey, String calendarId, List<PriceRecVo> list);
	public List<CalendarVO> getPriceReccalRuleList(String userKey,String mkid,String exnmCode,String caltime);
}
