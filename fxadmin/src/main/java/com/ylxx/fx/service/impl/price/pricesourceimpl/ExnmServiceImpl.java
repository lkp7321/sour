package com.ylxx.fx.service.impl.price.pricesourceimpl;

import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.controller.user.UserController;
import com.ylxx.fx.core.mapper.price.pricesource.ExnmMapper;
import com.ylxx.fx.service.po.CmmPrice;
import com.ylxx.fx.service.price.pricesource.ExnmService;
@Service("exnmService")
public class ExnmServiceImpl implements ExnmService {

	@Resource
	private ExnmMapper exnmMap;
	private static final Logger log = LoggerFactory.getLogger(ExnmServiceImpl.class);
	public List<CmmPrice> getCMMPRICE(String mkinfo) {
		List<CmmPrice> list =null;
		try {
			list = exnmMap.selectCMMPRICE(mkinfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(list!= null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setCxfg(list.get(i).getCxfg().equals("1")?"汇":"钞");
			}
		}
		return list;
	}
	
	public List<Map<String,String>> getPdtinfo(){
		return exnmMap.selectPdtinfo();
	}
	
}
