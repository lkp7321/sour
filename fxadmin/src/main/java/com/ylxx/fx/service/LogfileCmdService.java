package com.ylxx.fx.service;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.jsh.JshPages;

public interface LogfileCmdService {
	/**
	 * 查询日志
	 * @param strusnm
	 * @return
	 */
	PageInfo<Logfile> selectMng_logAll(String strusnm, String usnm, String trdt, String endate, String handle, Integer pageNo, Integer pageSize);
	/**
	 *  写审计日志
	 * @param logobj
	 * @return
	 */
	boolean insertLog(Logfile logobj);
	
	/**
	 * 新
	 * 查询日志
	 */
	PageInfo<Logfile> selectLikeMngLog(JshPages<Logfile> logfile);
}
