package com.ylxx.fx.service.price.alarm;
import java.util.*;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.Cmmalarm;
/**
 * 告警事件
 * @author lz130
 *
 */
public interface AlarmEventService {
	/**
	 * 告警事件查询
	 * @param betime
	 * @param endtime
	 * @param ercd
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo<Cmmalarm> getAlarmEvent(String betime,String ercd,Integer pageNo, Integer pageSize);
	public List<Map<String, String>> notifyAlarmEvent();
}
