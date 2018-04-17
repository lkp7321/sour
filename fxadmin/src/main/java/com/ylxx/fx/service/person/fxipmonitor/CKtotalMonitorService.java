package com.ylxx.fx.service.person.fxipmonitor;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.FormuleVo;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.service.po.FormuleBeanForAcc;
import com.ylxx.fx.service.po.price.ExposureBean;

public interface CKtotalMonitorService{
	//轧差敞口监控，总敞口监控
	public List<FormuleBean> fxipExceptionData(String prcd);
	//累加敞口监控，总敞口监控
	public List<FormuleBean> fxipCKData(String prcd);
	//账户交易，外币
	public List<FormuleBeanForAcc> selectPriceData(String prcd1,String prcd2);
	//结售汇，总敞口监控
	public List<ExposureBean> pereFxipExceptionData(String prcd);
}
