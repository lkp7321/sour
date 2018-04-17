package com.ylxx.fx.core.mapper.person.price;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.PdtinfoBean;

public interface QutFreqtMapper{
	//查询报价频率参数
	public PdtinfoBean selectQutFreqt(@Param("ptid")String ptid) throws Exception;
	//修改报价频率参数
	public boolean upQutFreqt(@Param("freqt")PdtinfoBean freqt) throws Exception;
	//写审计日志
	public boolean insertLogger(@Param("logfileBean")LogfileBean logfileBean) throws Exception;
}