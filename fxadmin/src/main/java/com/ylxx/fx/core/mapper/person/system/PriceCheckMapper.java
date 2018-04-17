package com.ylxx.fx.core.mapper.person.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.PdtCust;

public interface PriceCheckMapper {
	
	public List<PdtCust> allCustPrice(@Param("prod")String prod);
	//开启和关闭价格校验器
	public int opPriceStat();
	public int closePriceStat();
	//开启产品校验器
	public int upStatPrice(@Param("ptid")String ptid);
	
	//刷新价格源校验器1
	public List<String> allMkid(@Param("prod")String prod);
	public int refshUp(@Param("mkid")String mkid);
	//刷新价格源校验器
	public List<String> allMkidNo();
	public int refshUp1(@Param("mkid")String mkid);
	//刷新外汇实盘校验器
	public int refshSptPrice();
	//刷新价格源校验器2
	public int refshGoldPrice(@Param("mkid")String mkid);
	//刷新纸黄金校验器
	public int refshGoldsPrice();
	
	//定时刷新产品校验器
	public List<String> selectPrice();
	public int refshCustPrice(@Param("prod")String prod);
	public int refshCustPriceForAcc(@Param("prod")String prod);
	//获取价格源校验器的状态
	public int getchkpara();
	
}
