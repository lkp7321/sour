package com.ylxx.fx.service.price.alarm;
import java.util.*;

import com.ylxx.fx.service.po.CmmAlarmCode;
import com.ylxx.fx.utils.CurrUser;
public interface AlarmCodeService {
	public List<Map<String,String>> getAllalarmCode();
	public boolean upAlarmCode(CurrUser curUser, CmmAlarmCode cmmAlarmCode, String ip);
	public boolean insAlarmCode(CurrUser curUser, CmmAlarmCode cmmAlarmCode, String ip);
	public boolean delAlarmCode(CurrUser curUser, String ercd, String ip);
 }
