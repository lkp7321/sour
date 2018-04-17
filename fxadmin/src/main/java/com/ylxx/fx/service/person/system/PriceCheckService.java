package com.ylxx.fx.service.person.system;

import com.ylxx.fx.utils.CurrUser;

public interface PriceCheckService {
	public String allPriceCheck(CurrUser curUser);//获取所有数据
	public String getchkpara();//获取价格校验器状态
	public boolean closePriceStat(CurrUser curUser);//关闭价格源校验器状态
	public boolean opPriceStat(CurrUser curUser);//开启价格源校验器状态
	public boolean updatePriceStat(CurrUser curUser);//开启产品校验器
	
	public boolean refshPrice(CurrUser curUser);//刷新价格源校验器
	public boolean refshSptPrice(CurrUser curUser);//刷新外汇实盘
	public boolean refshCustPrices(CurrUser curUser,String prod);//新增根据客户价刷新价格校验器
	public boolean refshPrices(CurrUser curUser);
}
