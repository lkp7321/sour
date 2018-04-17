package com.ylxx.fx.service.impl.price.pricecontxpimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.price.CmmFiltrate;
import com.ylxx.fx.core.domain.price.Countexp;
import com.ylxx.fx.core.mapper.price.pricemanager.PriceConTexpMapper;
import com.ylxx.fx.service.price.pricecontxp.PriceContxpService;
@Service("priceContxpService")
public class PriceConxServiceImpl implements PriceContxpService{
	
	@Resource
	private PriceConTexpMapper priceContxpMap;

	//交叉盘计算1
	public List<Countexp> getContexpPrice() {
		
		return priceContxpMap.selContexpPrice();
	}

	//策略字典
	public List<CmmFiltrate> getPriceFile() {
		
		return priceContxpMap.selPriceFile();
	}
	
	//交叉盘计算2
	public List<Countexp> getContexpPrice1() {
		
		return priceContxpMap.selectAccExPrice();
	}
	
}
