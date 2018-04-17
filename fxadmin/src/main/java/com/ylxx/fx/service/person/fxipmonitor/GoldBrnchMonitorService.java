package com.ylxx.fx.service.person.fxipmonitor;

import java.util.List;
import java.util.Map;

import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.service.po.GckwebBean;

public interface GoldBrnchMonitorService {
	public List<FxipMonitorVo> getAllGoldBrnch();
	public List<FxipMonitorVo> getAllGoldCust();
	public List<GckwebBean> getAllGoldwebPrice();
	public List<Map<String,String>> getGoldCkno();
	public List<FormuleBean> getGoldclassCk(String ckno);
}
