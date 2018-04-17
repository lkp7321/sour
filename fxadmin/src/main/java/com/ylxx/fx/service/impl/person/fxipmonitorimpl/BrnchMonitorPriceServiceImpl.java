package com.ylxx.fx.service.impl.person.fxipmonitorimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.core.mapper.person.fxipmonitor.BrnchMonitorMapper;
import com.ylxx.fx.service.person.fxipmonitor.BrnchMonitorService;
@Service("brnchMonitorService")
public class BrnchMonitorPriceServiceImpl implements BrnchMonitorService {
	private static final Logger log = LoggerFactory.getLogger(BrnchMonitorPriceServiceImpl.class);
	@Resource
	private BrnchMonitorMapper brnchMonitorMap;
	//分行价格监控
	public List<FxipMonitorVo> selAllBrnchPrice(String prcd) {
		List<FxipMonitorVo> list = null;
		try {
			if(prcd.equals("P007")){
				list = brnchMonitorMap.selectAccExAllPdt(prcd);
			}else{
				list = brnchMonitorMap.selectAllPdt(prcd);
			}
			log.info("\n分行价格监控数据："+list.size());
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setMknm(list.get(i).getMknm().trim());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("\n获取分行价格监控错误");
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> selbrnchcom() {
		// TODO Auto-generated method stub
		return brnchMonitorMap.selbrnchcom();
	}
		
	
}
