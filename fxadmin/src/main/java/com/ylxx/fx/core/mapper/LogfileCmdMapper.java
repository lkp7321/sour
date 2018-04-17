package com.ylxx.fx.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Logfile;

public interface LogfileCmdMapper {
	/**
	 * 查询审计管理
	 * @param strusnm
	 * @param usnm
	 * @param trdt
	 * @return
	 */
	List<Logfile> selectMng_logAll(
			@Param("strusnm")String strusnm, @Param("usnm")String usnm, 
			@Param("trdt")String trdt, @Param("endt")String endt, @Param("handle")String handle);
	/**
	 * 写审计日志
	 * @param logobj
	 * @return
	 */
	int insertLog(@Param("logobj")Logfile logobj);
	/**
	 * 包商银行
	 * @param rzdt
	 * @param usem
	 * @param search
	 * @return
	 */
	List<Logfile> selectLikeMngLog(@Param("rzdt")String rzdt, @Param("usem")String usem,@Param("search")String search);
}
