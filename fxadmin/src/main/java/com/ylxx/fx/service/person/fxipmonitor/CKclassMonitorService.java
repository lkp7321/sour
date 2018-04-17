package com.ylxx.fx.service.person.fxipmonitor;

import java.util.List;
import java.util.Map;

import com.ylxx.fx.service.po.FormuleBean;

public interface CKclassMonitorService {

	List<Map<String, String>> gettree(String prcd);

	List<FormuleBean> getSelectPrice(String prcd, String ckno);
	
	List<FormuleBean> getLJSelectPrice(String prcd, String ckno);
}
