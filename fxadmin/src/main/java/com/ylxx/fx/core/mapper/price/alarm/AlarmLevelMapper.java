package com.ylxx.fx.core.mapper.price.alarm;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CmmAlarmLevel;
public interface AlarmLevelMapper {
	public List<CmmAlarmLevel> selectAlarmLeveAll();
	public int upAlarmLeve(@Param("cmmAlarmLevel")CmmAlarmLevel cmmAlarmLevel);
	public int insertAlarmLeve(@Param("cmmAlarmLevel")CmmAlarmLevel cmmAlarmLevel);
	public int deleteAlarmLeve(@Param("alid")String alid);
	
}
