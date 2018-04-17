package com.ylxx.fx.service.price.alarm;
import java.util.*;

import com.ylxx.fx.service.po.CmmAlarmLevel;
import com.ylxx.fx.utils.CurrUser;
public interface AlarmLevelService {
	public List<CmmAlarmLevel> getAllAlarmLevel();
	public boolean upAlarmLevel(CurrUser curUser, CmmAlarmLevel cmmAlarmLevel, String ip);
	public boolean insAlarmLevel(CurrUser curUser, CmmAlarmLevel cmmAlarmLevel, String ip);
	public boolean delAlarmLevel(CurrUser curUser, String alid, String ip);
	
}
