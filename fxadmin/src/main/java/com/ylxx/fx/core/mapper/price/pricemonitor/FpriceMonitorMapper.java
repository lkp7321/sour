package com.ylxx.fx.core.mapper.price.pricemonitor;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.core.domain.price.AllMakPrice;
public interface FpriceMonitorMapper {
	public List<AllMakPrice> selectAllMaket(@Param("mkid")String mkid);
	public List<String> selMike();
	public List<AllMakPrice> selMike2(@Param("sqel")String sqel);
	
	public List<Map<String,String>> selAllMk();
	public List<Map<String,String>> selAllexnm();
	
	/**
	 * 产品源监控
	 * @param prcd
	 * @return
	 */
	List<FxipMonitorVo> selectAllPdtprod(@Param("prcd")String prcd);
}
