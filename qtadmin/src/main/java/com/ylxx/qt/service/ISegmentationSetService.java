package com.ylxx.qt.service;


import java.util.Map;

import com.ylxx.qt.service.po.ParameterdetailsBean;

public interface ISegmentationSetService {
	public Map<String,String> CreateSegmentSet(ParameterdetailsBean pb) throws Exception;
	public String BelongStalls(double nowprice,Map<String,String> map) throws Exception;
	public double GetNowPrice() throws Exception;
}
