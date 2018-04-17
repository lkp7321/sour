package com.ylxx.fx.core.mapper.price.alarm;

import java.util.*;

import org.apache.ibatis.annotations.Param;

public interface AlarmCodeMapper {
	public List<Map<String,String>> selectAlarmLeveAll();
	public int upAlarmCode(@Param("erms")String erms,@Param("ercd")String ercd);
	public int insertAlarmCod(@Param("erms")String erms,@Param("ercd")String ercd);
	public int deleteAlarmLeve(@Param("ercd")String ercd);
}
