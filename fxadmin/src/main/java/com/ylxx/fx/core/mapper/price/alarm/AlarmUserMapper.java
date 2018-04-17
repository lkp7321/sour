package com.ylxx.fx.core.mapper.price.alarm;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Cmmalarmuser;
public interface AlarmUserMapper {
	public List<Cmmalarmuser> selectAlarmn();
	public int upAlarm(@Param("cmmAlarmUser")Cmmalarmuser cmmAlarmUser);
	public int insertAlarm(@Param("cmmAlarmUser")Cmmalarmuser cmmAlarmUser);
	public int deleteAlarm(@Param("usid")String usid,@Param("usnm")String usnm);
	public String selAlarmUsid();
}
