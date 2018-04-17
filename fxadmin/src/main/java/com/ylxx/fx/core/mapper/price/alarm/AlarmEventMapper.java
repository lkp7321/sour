package com.ylxx.fx.core.mapper.price.alarm;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Cmmalarm;
public interface AlarmEventMapper {
	List<Cmmalarm> selectAlarm1(@Param("bedate")String bedate, @Param("ercd")String ercd);
	List<Map<String, String>> selectAlarmErty();
}
