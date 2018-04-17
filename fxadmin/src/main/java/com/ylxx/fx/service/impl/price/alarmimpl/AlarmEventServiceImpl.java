package com.ylxx.fx.service.impl.price.alarmimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.price.alarm.AlarmEventMapper;
import com.ylxx.fx.service.po.Cmmalarm;
import com.ylxx.fx.service.price.alarm.AlarmEventService;
@Service("alarmEventService")
public class AlarmEventServiceImpl implements AlarmEventService{
	@Resource
	private AlarmEventMapper alarmEventMap;
	private static final Logger log = LoggerFactory.getLogger(AlarmEventServiceImpl.class);
	/**
	 * 查询告警事件
	 */
	public PageInfo<Cmmalarm> getAlarmEvent(String bedate,
			String ercd, Integer pageNo, Integer pageSize) {
		List<Cmmalarm> list =null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			list = alarmEventMap.selectAlarm1(bedate, ercd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new PageInfo<Cmmalarm>(list);
	}

	@Override
	public List<Map<String, String>> notifyAlarmEvent() {
		List<Map<String, String>> list =null;
		try {
			list = alarmEventMap.selectAlarmErty();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
}
