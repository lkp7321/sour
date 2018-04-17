package com.ylxx.fx.service.person.fxipmonitor;

import java.util.List;
import java.util.Map;

import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.po.PereCustMonitorB;

public interface FcustMonitorService {
	
	public List<FxipMonitorVo> selAllPdt(String prcd);
	public List<PereCustMonitorB> selAllPdtP004();
}
