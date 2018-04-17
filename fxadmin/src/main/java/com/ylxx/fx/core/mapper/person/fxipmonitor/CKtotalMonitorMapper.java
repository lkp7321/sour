package com.ylxx.fx.core.mapper.person.fxipmonitor;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.FormuleVo;
import com.ylxx.fx.service.po.price.ExposureBean;

public interface CKtotalMonitorMapper {
	//个人实盘，轧差
	public List<FormuleVo> selectPrice(@Param("prcd")String prcd);
	//个人实盘，累加
	public List<FormuleVo> selectCKLJPrice(@Param("prcd")String prcd);
	
	//外汇交易,外币
	public List<FormuleVo> selectPriceData(@Param("prcd1")String prcd1,@Param("prcd2")String prcd2);
	
	//结售汇
	public List<ExposureBean> selExpoTotalList(@Param("prod")String prod);
	public List<ExposureBean> selFxExpoTotalList(@Param("prod")String prod);
	
}
