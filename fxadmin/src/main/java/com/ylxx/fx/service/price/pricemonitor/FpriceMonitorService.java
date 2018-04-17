package com.ylxx.fx.service.price.pricemonitor;

import java.util.List;
import java.util.Map;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.core.domain.price.AllMakPrice;
public interface FpriceMonitorService {
	public List<AllMakPrice> getAllMaketPrice(String type, String name);
	
	public List<Map<String,String>> getAllMk();
	public List<Map<String,String>> getAllexnm();
	/**
	 * 报价平台产品源监控
	 * @param prcd
	 * @return
	 */
	List<FxipMonitorVo> selectAllPdtprod(String prcd);
}
