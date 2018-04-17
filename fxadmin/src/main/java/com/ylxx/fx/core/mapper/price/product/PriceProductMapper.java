package com.ylxx.fx.core.mapper.price.product;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.PdtJGinfo;
import com.ylxx.fx.service.po.PdtRParaT;
import com.ylxx.fx.service.po.Pdtinfo;
import com.ylxx.fx.service.po.TradeOnOffBean;
public interface PriceProductMapper {
	/*
	 * 产品页面数据
	 */
	public List<Pdtinfo> selectAllPrice();
	/**
	 * 删除产品相关表，存储过程
	 * @param map
	 */
	public void dropPriceProd(Map<String, Object> map);
	/**
	 * 删除表中信息
	 * @param ptid
	 * @return
	 */
	public int deletePrice(@Param("ptid")String ptid);
	/**
	 * 修改表中信息
	 * @param pdtinfo
	 * @return
	 */
	public int updatePrice(@Param("pdtinfo")Pdtinfo pdtinfo);
	/**
	 * 添加新表，调用存储过程
	 * @param map
	 */
	public void createPriceProd(Map<String, Object> map);
	/**
	 * 添加表中信息
	 * @param pdtinfo
	 * @return
	 */
	public int increated(@Param("pdtinfo")Pdtinfo pdtinfo);
	
	public int selectPrice(@Param("ptid")String ptid);
	
	/**
	 * 货币对配置
	 */
	public List<Map<String, Object>> selectexnmprice();
	/*
	 * 页面数据
	 */
	public List<PdtRParaT> selectallExnmPrice(@Param("prod")String prod);
	
	/**
	 * 产品配置信息
	 */
	public List<PdtJGinfo> selectAllPmodPrice();
	public int deletePmodPrice(@Param("ptid")String ptid);
	public int selPmodPriceOn(@Param("ptid")String ptid);
	public int updatePmodPrice(@Param("pdtinfo")PdtJGinfo pdtinfo);
	public int insPmodPrice(@Param("pdtinfo")PdtJGinfo pdtinfo);
	
	//交易市场开闭市管理
	/**
	 * 产品下拉框对应的数据
	 * @param ptid
	 * @return
	 */
	public List<TradeOnOffBean> selectSysctls(@Param("ptid")String ptid);
	/**
	 * 保存
	 * @param usnm
	 * @param ptidcomb
	 * @param usfg
	 * @param time
	 * @return
	 */
	public int updateUsfg(@Param("usnm")String usnm,
			@Param("ptidcomb")String ptidcomb, 
			@Param("usfg")String usfg,
			@Param("time")String time);
}
