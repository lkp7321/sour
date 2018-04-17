package com.ylxx.fx.service.person.businesspara;

import java.util.List;

import com.ylxx.fx.service.po.CK_handMxdetail;
import com.ylxx.fx.service.po.QutCmmPrice;

public interface PpbusinessService {
	//平盘交易查询
	public List<CK_handMxdetail> getdate(String apdt,String jsdt);
	//原油手工平盘
	public List<QutCmmPrice> selPrice();
	
	//doubleclick
	public String sendToCkServer(QutCmmPrice price,String userKey);
	
}
