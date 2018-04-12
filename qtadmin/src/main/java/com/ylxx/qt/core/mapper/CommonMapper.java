package com.ylxx.qt.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.qt.service.po.InstruProductBean;
import com.ylxx.qt.service.po.ParameterdetailsBean;

/**
 * 
 * @author suimanman
 * 
 */
public interface CommonMapper {

	/**
	 * 全局搜索：获取合约代码、品种名称列表
	 * 
	 * @return
	 */
	public List<InstruProductBean> findInsProdList(String result);

	/**
	 * 全局搜索：根据用户id、搜索内容，模糊查询出信息 
	 * 
	 * @return
	 */
	public List<ParameterdetailsBean> findParamByUseid(@Param("userid")String userid,@Param("result")String result);
	
}
