package com.ylxx.fx.service.person.price;

import java.util.List;

import com.ylxx.fx.service.po.HandQuoteVoBean;

public interface IHandQuoteService {

	//手工报价配置表
	public String selectHandQuoteList(String ptid,String stfg) throws Exception;
	//修改手工报价录入表
	public String updateProduct(String userKey,String prod,HandQuoteVoBean hqVoBean) throws Exception;
	//修改手工报价状态
	public String updateHandQuoteState(String userKey,String prod,List<HandQuoteVoBean> hqVoList,String labnm) throws Exception;
	//手工报价的价格源公告板
	public String selectHandPriceAfficheList(String ptid) throws Exception;
	//启用
	public String openHandQuote(String prod,List<HandQuoteVoBean> hqVos) throws Exception;
	//停用
	public String closeHandQuote(String prod,List<HandQuoteVoBean> hqVos) throws Exception;
	//手工报价操作表
	public String queryHandPriceOperate(String prod,String usnm,String optm,Integer pageNo, Integer pageSize) throws Exception;
	/*//测试用
	public List<HandQuoteVoBean> getHandQuoteVoBeanlist(String ptid) throws Exception;*/
}

