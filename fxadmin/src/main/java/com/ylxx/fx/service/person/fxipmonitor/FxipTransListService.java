package com.ylxx.fx.service.person.fxipmonitor;

import java.util.List;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.utils.CurrUser;

public interface FxipTransListService {
	
	//public List<Tranlist> allTranList(CurrUser curUser, String time);
	
	public List<Tranlist> timeTranList(CurrUser curUser, String bTime, String eTime); 
	
	public String todayTranCount(CurrUser curUser, String curTime);
	
	public String todayTranUsam(CurrUser curUser, String curTime);
}
