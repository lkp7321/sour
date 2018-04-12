package com.ylxx.qt.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ylxx.qt.service.AnalysisPriceInfoService;
import com.ylxx.qt.service.po.InstrumentPtrice;
@Service("analysispriceinfoservice")
public class AnalysisPriceInfoServiceImpl implements AnalysisPriceInfoService{
	//private static Logger logger = Logger.getLogger("AnalysisPriceInfoServiceImpl");
	@Override
	public InstrumentPtrice analyPriceInfo(String priceInfo) {
		JSONObject jsonobj = JSONObject.parseObject(priceInfo);
		InstrumentPtrice instrpri = new InstrumentPtrice();
		instrpri.setInstrumentId((String)jsonobj.get("InstrumentID"));
		instrpri.setNowprice((Double)jsonobj.get("LastPrice"));
		return instrpri;
	}
}
