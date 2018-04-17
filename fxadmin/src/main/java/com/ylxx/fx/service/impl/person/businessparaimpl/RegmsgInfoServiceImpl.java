package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.RegmsgInfoMapper;
import com.ylxx.fx.service.person.businesspara.RegmsgInfoService;
import com.ylxx.fx.service.po.RegmsgBean;
@Service("regmsgnfoService")
public class RegmsgInfoServiceImpl implements RegmsgInfoService{
	private static final Logger log = LoggerFactory.getLogger(RegmsgInfoServiceImpl.class);
	@Resource
	private RegmsgInfoMapper regmsginfoMap;
	
	public PageInfo<RegmsgBean> getAllRegmsgInfo(String userProd, 
			String strcuno, String strcuac, 
			String comaogcd, String combogcd,
			Integer pageNo, Integer pageSize ) {
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<RegmsgBean> list = null;
	    try {
	    	list = regmsginfoMap.selectRegmsgInfoList(
	  	    		userProd, strcuno, strcuac, comaogcd, combogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	    //用PageInfo对结果进行包装
	    PageInfo<RegmsgBean> page = new PageInfo<RegmsgBean>(list);
		return page;
	}
}
