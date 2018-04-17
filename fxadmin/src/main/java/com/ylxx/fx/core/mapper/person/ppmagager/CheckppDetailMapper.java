package com.ylxx.fx.core.mapper.person.ppmagager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CheckPpDetail;
import com.ylxx.fx.service.po.LogfileBean;

public interface CheckppDetailMapper{
	
	//条件查询
	public List<CheckPpDetail> selCondition(@Param("seqn")String seqn) throws Exception;
	//成功复核
	public boolean upSuccess(@Param("seqn")String seqn) throws Exception;
	//写审计日志
	public boolean insertMarkLogger(@Param("logfileBean")LogfileBean logfileBean) throws Exception;
	//失败复核
	public boolean upUnsuccess(@Param("seqn")String seqn) throws Exception;
}