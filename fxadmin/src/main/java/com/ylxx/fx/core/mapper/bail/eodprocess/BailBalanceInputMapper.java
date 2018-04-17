package com.ylxx.fx.core.mapper.bail.eodprocess;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.BailBalanceBean;

public interface BailBalanceInputMapper{
	
	//选择保证金余额录入/保证金利息录入的所有数据
	public List<BailBalanceBean> selectBalanceInputList(@Param("hddate")String hddate,
			@Param("tradeCode")String tradeCode) throws Exception;
	//保证金渠道流水号中生成10位顺序号
	public String generatorseq1() throws Exception;
	//保证金渠道流水号中生成6位顺序号
	public String generatorseq2() throws Exception;
}