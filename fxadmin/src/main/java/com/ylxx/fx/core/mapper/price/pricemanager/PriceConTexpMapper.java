package com.ylxx.fx.core.mapper.price.pricemanager;

import java.util.List;

import com.ylxx.fx.core.domain.price.CmmFiltrate;
import com.ylxx.fx.core.domain.price.Countexp;

public interface PriceConTexpMapper {
	public List<Countexp> selContexpPrice();
	public List<CmmFiltrate> selPriceFile();
	public List<Countexp> selectAccExPrice();
}
