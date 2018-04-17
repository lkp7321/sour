package com.ylxx.fx.service.price.pricecontxp;

import java.util.List;

import com.ylxx.fx.core.domain.price.CmmFiltrate;
import com.ylxx.fx.core.domain.price.Countexp;

public interface PriceContxpService {
	public List<Countexp> getContexpPrice();
	public List<CmmFiltrate> getPriceFile();
	public List<Countexp> getContexpPrice1();
}
