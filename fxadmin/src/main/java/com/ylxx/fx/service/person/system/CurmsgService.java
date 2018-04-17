package com.ylxx.fx.service.person.system;

import java.util.*;

import com.ylxx.fx.core.domain.price.CurrmsgForAcc;
import com.ylxx.fx.service.po.Currmsg;
import com.ylxx.fx.service.po.Cytp;

public interface CurmsgService {

	public String getAllCurrmsg(String prod);
	public String getAllCurrmsgP7();
	public List<Map<String, Object>> selExnminit();
	public boolean upCurrmsgPrice7(CurrmsgForAcc currmsg, String userKey);
	
	public List<Cytp> selCytpExnm(String prod);
	
	public String insCurrPrice(String userKey,Currmsg currmsg);
	public String upsCurrPrice(String userKey, Currmsg currmsg);
	public boolean delCurrPrice(String userKey,String exnm);
}
