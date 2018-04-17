package com.ylxx.fx.service.person.fxipmonitor;

import java.util.List;

import com.ylxx.fx.service.po.Ck_ppresultdetail;


public interface PpTranMonitorService {
	public List<Ck_ppresultdetail> timePpDetailList(String bTime, String eTime);
	public String todayPpDetailCount(String curTime);
	public String todayPpDetailAm(String curTime);
}
