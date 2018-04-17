package com.ylxx.fx.service.price.alarm;
import java.util.*;
import com.ylxx.fx.service.po.CmmAlarmNotify;
import com.ylxx.fx.utils.CurrUser;
public interface AlarmFsService {
	public List<CmmAlarmNotify> getAllAlarmNotify();
	
	public List<Map<String, String>> getAlarmStcd();
	public List<Map<String, String>> selectAlarmLeveAll();
	public List<Map<String, String>> selectAlarmERR();
	
	public boolean addAlarmfs(CurrUser user, CmmAlarmNotify notify, String ip);
	public boolean modifyAlarmfs(CurrUser user, CmmAlarmNotify notify, String ip);
	public boolean delAlarmfs(CurrUser user, CmmAlarmNotify notify, String ip);
}
