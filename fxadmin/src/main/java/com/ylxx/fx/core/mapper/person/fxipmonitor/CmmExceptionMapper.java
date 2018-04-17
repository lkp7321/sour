package com.ylxx.fx.core.mapper.person.fxipmonitor;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Cmmalarm;

public interface CmmExceptionMapper {

	List<Cmmalarm> selectExceptionList(@Param("curDate")String curDate);
	
}
