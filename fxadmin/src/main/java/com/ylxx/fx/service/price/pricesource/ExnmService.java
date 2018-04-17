package com.ylxx.fx.service.price.pricesource;

import java.util.*;

import com.ylxx.fx.service.po.CmmPrice;

public interface ExnmService {
	public List<CmmPrice> getCMMPRICE(String mkinfo);
	public List<Map<String,String>> getPdtinfo();
}
