package com.ylxx.fx.core.mapper.person.fxipmonitor;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CkTotalBean;

public interface GoldCKtotalMapper {
	//市场报价
	public List<CkTotalBean> ckTotalData();
	//总敞口
	public List<CkTotalBean> ckTotalYk();
	
	public double USDCNYNEMD();
	//更新对外损益
	public int updateCKZCYK(@Param("ckTotalBean")CkTotalBean ckTotalBean);
	
	public int insertTQTranList(@Param("usnm")String usnm,
			@Param("currDate")String currDate,@Param("currTime")String currTime,
			@Param("ckTotalBean")CkTotalBean ckTotalBean);
}
