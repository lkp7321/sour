package com.ylxx.fx.core.mapper.price.alarm;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CmmAlarmNotify;
public interface AlarmFsMapper {
	public List<CmmAlarmNotify> selectAlarmNotifyAll();
	
	public List<Map<String,String>> selectAlarmStcd();
	public List<Map<String,String>> selectAlarmLeveAll();
	public List<Map<String,String>> selectAlarmERR();
	
	public int selectAlarmNotify(@Param("notify")CmmAlarmNotify notify);
	public int insertAlarmNotify(@Param("notify")CmmAlarmNotify notify);
	public int upAlarmNotify(@Param("notify")CmmAlarmNotify notify);
	public int deleteAlarmNotify(@Param("ercd")String ercd, @Param("usid")String usid);
}
