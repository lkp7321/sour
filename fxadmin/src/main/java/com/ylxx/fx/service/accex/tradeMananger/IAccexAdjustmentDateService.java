package com.ylxx.fx.service.accex.tradeMananger;

import java.util.List;
import java.util.Map;

public interface IAccexAdjustmentDateService {
	//页面加载的交易品种
	public String selCurrencyPair(String userKey);
	//保存
	public String addAdjust(String ostp,String osbs,String nstp,String nsbs,String orbs,String nrbs,String osas,String nsas,String oras,String nras,String exnm,String femd,String stat);
	//查询调整日配置信息
	public String selOilAdjustList(String beginDate,String endDate,String exnm,Integer pageNo, Integer pageSize);
	public List<Map<String, Object>> selAllOilAdjustList(String beginDate,String endDate,String exnm);
	//修改
	public String updateAdjust(String ostp,String osbs,String nstp,String nsbs,String orbs,String nrbs,String osas,String nsas,String oras,String nras,String exnm,String femd);
	//删除
	public String deleteAdjustByKey(String exnm,String femd);
}

