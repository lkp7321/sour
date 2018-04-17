package com.ylxx.fx.core.mapper.person.fxipmonitor;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.FxipMonitorVo;

public interface BrnchMonitorMapper {
	
	public List<FxipMonitorVo> selectAllPdt(@Param("prcd")String prcd);
	public List<FxipMonitorVo> selectAccExAllPdt(@Param("prcd")String prcd);
	public List<Map<String,Object>> selbrnchcom();
	
}
