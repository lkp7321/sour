package com.ylxx.fx.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.LogfileCmdMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.jsh.JshPages;
@Service("logfileCmdService")
//@Scope("prototype")
public class LogfileCmdServiceImpl implements LogfileCmdService{
	private static final Logger log = LoggerFactory.getLogger(LogfileCmdServiceImpl.class);
	@Resource
	private LogfileCmdMapper logfilecmdMap;
	/**
	 * 查询审计日志
	 */
	@Override
	public PageInfo<Logfile> selectMng_logAll(String strusnm,String usnm,String trdt,String endt,String handle,Integer pageNo, Integer pageSize) {
		List<Logfile> list = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			list = logfilecmdMap.selectMng_logAll(strusnm,usnm,trdt,endt,handle);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new PageInfo<Logfile>(list);
	}
	/**
	 * 录入审计日志
	 */
	@Override
	public boolean insertLog(Logfile logobj) {
		log.info("开始存入登陆日志信息");
		try {
			SimpleDateFormat df = new SimpleDateFormat(
					"yyyyMMddHH:mm:ss");
			Date d = new Date();
			String strdata = df.format(d);
			logobj.setRzdt(strdata.substring(0, 8));
			logobj.setRzsj(strdata.substring(8));
			logfilecmdMap.insertLog(logobj);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	/**
	 * 查询友利银行日志
	 */
	public PageInfo<Logfile> selectLikeMngLog(JshPages<Logfile> logfile){
		Integer pageNo = logfile.getPageNo();
		Integer pageSize = logfile.getPageSize();
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<Logfile> logList = null;
	    String rzdt = logfile.getEntity().getRzdt();//日期
	    String usem = logfile.getEntity().getUsem();//当前登录用户
	    String remk = logfile.getEntity().getRemk();//搜索字段
	    try {
	    	logList = logfilecmdMap.selectLikeMngLog(rzdt, usem, remk);
		} catch (Exception e) {
			log.error("查询日志信息出错");
			log.error(e.getMessage(), e);
		}
	    PageInfo<Logfile> page = new PageInfo<Logfile>(logList);
	    return page;
	}
}
