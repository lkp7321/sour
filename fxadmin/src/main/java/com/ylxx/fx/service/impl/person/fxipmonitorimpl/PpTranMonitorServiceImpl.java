package com.ylxx.fx.service.impl.person.fxipmonitorimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.person.fxipmonitor.PpTranMonitorMapper;
import com.ylxx.fx.service.person.fxipmonitor.PpTranMonitorService;
import com.ylxx.fx.service.po.Ck_ppresultdetail;
import com.ylxx.fx.utils.DataTimeClass;


@Service("ppTranMonitorService")
public class PpTranMonitorServiceImpl implements PpTranMonitorService{
	
	private static final Logger log = LoggerFactory.getLogger(PpTranMonitorServiceImpl.class);
	
	@Resource
	private PpTranMonitorMapper ppTranMonitorMap;

	/***
	 * 平盘交易查询
	 * 
	 * @param bTime
	 *            开始时间
	 * @param eTime
	 *            结束时间
	 * @return list 当前时间段的所有平盘交易
	 * **/
	public List<Ck_ppresultdetail> timePpDetailList(String bTime, String eTime) {
		String curTime = DataTimeClass.getNowDay();
		List<Ck_ppresultdetail> list = null;
		try {
			if(bTime.equals(eTime) && (bTime.equals(curTime)||eTime.equals(curTime))){
				list = ppTranMonitorMap.selectPpDeatil(curTime);
			}else{
				list = ppTranMonitorMap.selectTimeDetail(bTime, eTime);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
				
		return list;
	}

	/**
	 * 当天平盘交易总数
	 * */
	public String todayPpDetailCount(String curTime) {
		int a = 0;
		try {
			a = ppTranMonitorMap.selectTodayDetail(curTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return String.valueOf(a);
	}

	/**
	 * 当天平盘交易量
	 * */
	public String todayPpDetailAm(String curTime) {
		String curCount = null;
		try {
			curCount = ppTranMonitorMap.selectTodayUsam(curTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return curCount;
	}
}
