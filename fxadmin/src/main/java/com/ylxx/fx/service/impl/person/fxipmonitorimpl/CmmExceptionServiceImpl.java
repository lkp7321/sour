package com.ylxx.fx.service.impl.person.fxipmonitorimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.person.fxipmonitor.CmmExceptionMapper;
import com.ylxx.fx.service.person.fxipmonitor.CmmExceptionService;
import com.ylxx.fx.service.po.Cmmalarm;


@Service("cmmExceptionService")
public class CmmExceptionServiceImpl implements CmmExceptionService{
	
	private static final Logger log = LoggerFactory.getLogger(CmmExceptionServiceImpl.class);
	
	@Resource
	private CmmExceptionMapper cmmExceptionMap;

	@Override
	public List<Cmmalarm> fxipExceptionData(String curDate) {
		log.info("\n异常数据监控,\n日期："+curDate);
		List<Cmmalarm> list = null;
		try {
			list = cmmExceptionMap.selectExceptionList(curDate.trim());
			log.info("异常数据监控："+list.size()+"条");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	
}
