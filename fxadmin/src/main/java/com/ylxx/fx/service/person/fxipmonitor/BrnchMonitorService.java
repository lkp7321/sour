package com.ylxx.fx.service.person.fxipmonitor;

import java.util.*;

import com.ylxx.fx.core.domain.FxipMonitorVo;

public interface BrnchMonitorService {
	public List<FxipMonitorVo> selAllBrnchPrice(String prcd);
	public List<Map<String,Object>> selbrnchcom();
}
