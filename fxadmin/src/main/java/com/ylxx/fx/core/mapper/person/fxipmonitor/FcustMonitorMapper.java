package com.ylxx.fx.core.mapper.person.fxipmonitor;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.po.PereCustMonitorB;

public interface FcustMonitorMapper {
	public List<FxipMonitorVo> selectAllPdt(@Param("prcd")String prcd);
	public List<PereCustMonitorB> selectAllPdtP004();
}
