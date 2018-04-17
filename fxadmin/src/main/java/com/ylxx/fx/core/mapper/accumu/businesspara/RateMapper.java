package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.RateBean;
/**
 * 利率配置
 * @author lz130
 *
 */
public interface RateMapper{
	
	/**
	 * 查询利率配置数据
	 * @return
	 * @throws Exception
	 */
	List<RateBean> searchList() throws Exception;
	
	/**
	 * 向利率历史表里添加记录
	 * @param rateBean
	 * @return
	 * @throws Exception
	 */
	int doInsertHis(@Param("rateBean")RateBean rateBean) throws Exception;
	
	/**
	 * 向利率表里添加记录
	 * @param rateBean
	 * @return
	 * @throws Exception
	 */
	int doInsert(@Param("rateBean")RateBean rateBean) throws Exception;
	
	/**
	 * 删除利率历史表记录
	 * @param time
	 * @param pdtp
	 * @return
	 * @throws Exception
	 */
	int doDelete(@Param("time")String time, @Param("pdtp")String pdtp) throws Exception;
	
	/**
	 * 更新利率表里的记录
	 * @param rateBean
	 * @return
	 * @throws Exception
	 */
	int doUpdate(@Param("rateBean")RateBean rateBean) throws Exception;
	
}