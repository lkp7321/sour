package com.ylxx.fx.service.price.pricesource;

import java.util.List;
import java.util.Map;

import com.ylxx.fx.core.domain.price.PriceJiaVo;
import com.ylxx.fx.service.po.calendar.CalendarVO;

public interface PriceSetService {
	public List<PriceJiaVo> getPriceJiaList();
	public List<Map<String,String>> getProList();
	public boolean savePriceJia(String userKey, PriceJiaVo priceJiaBean);
	public boolean delPriceJia(String userKey, PriceJiaVo priceJiaBean);
	public boolean upPriceJia(String userKey, PriceJiaVo priceJiaBean);
	
	public List<CalendarVO> getPriceJiacalRuleList(String userKey,String proid,String caltime);
}
