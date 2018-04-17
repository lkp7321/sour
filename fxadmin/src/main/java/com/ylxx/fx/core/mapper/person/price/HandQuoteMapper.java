package com.ylxx.fx.core.mapper.person.price;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.HandPriceListBean;
import com.ylxx.fx.service.po.HandQuoteVoBean;

public interface HandQuoteMapper{
	
	//手工报价配置表
	public List<HandQuoteVoBean> selectHandQuoteList(@Param("ptid")String ptid,@Param("stfg")String stfg) throws Exception;
	//更新手工报价审核表
	public boolean updateProductVa(@Param("ptid")String ptid,@Param("hqVoBean")HandQuoteVoBean hqVoBean) throws Exception;
	//插入手工报价流水表
	public boolean insertHandQuoteList(@Param("hpList")HandPriceListBean hpList) throws Exception;
	//更新手工报价状态
	public void updateHandQuoteState(@Param("hqVoBeans")List<HandQuoteVoBean> hqVoBeans) throws Exception;
	//修改对应的手工报价公告板模板表
	public boolean updateHandprice(@Param("ptid")String ptid,@Param("hqVoBean")HandQuoteVoBean hqVoBean) throws Exception;
	//手工报价的价格源公告板
	public List<HandQuoteVoBean> selectHandPriceAfficheList(@Param("ptid")String ptid) throws Exception;
	//实盘、纸黄金开启/关闭手工报价
	public boolean updateHandpriceState(@Param("ptid")String ptid,@Param("hqVoBean")HandQuoteVoBean hqVoBean,@Param("ocfg")String ocfg) throws Exception;
	//手工报价操作表
	public List<HandQuoteVoBean> selectHandQuoteOperateList(@Param("prod")String prod,
			@Param("usnm")String usnm,@Param("optmfrom")String optmfrom,@Param("optmto")String optmto) throws Exception;
	
	//个人结售汇：手工报价启用：全部启用、全部停用
	//获得结售汇客户价互斥的产品编码
	public List<String> getUpdatePtid() throws Exception;
	//手工报价重置(非)价格加工类配置表
	public Boolean updateCmmpdtparaForDelete() throws Exception;
	//手工报价修改(非)价格加工类配置表
	public Boolean updateCmmpdtparaForSet(@Param("mkidc")String mkidc,@Param("mkidb")String mkidb) throws Exception;
	//获得结售汇客户价互斥的市场源编码
	public String getUpdateMkid() throws Exception;
	//手工报价重置市场信息表
	public Boolean updateMktinfoForDelete() throws Exception;
	//手工报价修改市场信息表
	public Boolean updateMktinfoForSet(@Param("mkid")String mkid) throws Exception;
	//结售汇开启/关闭手工报价
	public Boolean updateHandpriceState2(@Param("ptid")String ptid,@Param("ocfg")String ocfg) throws Exception;
}