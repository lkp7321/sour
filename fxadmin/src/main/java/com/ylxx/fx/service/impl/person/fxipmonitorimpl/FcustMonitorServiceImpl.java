package com.ylxx.fx.service.impl.person.fxipmonitorimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.core.mapper.person.fxipmonitor.FcustMonitorMapper;
import com.ylxx.fx.service.person.fxipmonitor.FcustMonitorService;
import com.ylxx.fx.service.po.PereCustMonitorB;

@Service("fcustMonitorService")
public class FcustMonitorServiceImpl implements FcustMonitorService{
	private static final Logger log = LoggerFactory.getLogger(FcustMonitorServiceImpl.class);
	@Resource
	private FcustMonitorMapper fcustMonitorMap;
	public List<FxipMonitorVo> selAllPdt(String prcd) {
		log.info("\n客户价格监控:"+prcd);
		List<FxipMonitorVo> list = null;
		try {
			list = fcustMonitorMap.selectAllPdt(prcd);
			if(list!=null&&list.size()>0){
				log.info("\n客户价格监控数据:"+list.size());
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setMknm(list.get(i).getMknm().trim());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	public List<PereCustMonitorB> selAllPdtP004(){
		List<PereCustMonitorB> list =null;
		try {
			list = fcustMonitorMap.selectAllPdtP004();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
}
