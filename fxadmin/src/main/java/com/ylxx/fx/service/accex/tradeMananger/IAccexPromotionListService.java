package com.ylxx.fx.service.accex.tradeMananger;

import java.util.List;
import java.util.Map;

public interface IAccexPromotionListService {

	public String selectPromotion(Integer pageNo, Integer pageSize, String endDate, String prod, String strateDate);
	public List<Map<String,Object>> selectAllPromotion(String endDate, String prod, String strateDate);

}	

