package com.ylxx.qt.service;

import java.util.List;

import com.ylxx.qt.service.po.InstruProductBean;
import com.ylxx.qt.service.po.ParameterdetailsBean;

/**
 * 
 * @author suimanman
 * 
 */
public interface CommonService {
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
	public List<ParameterdetailsBean> findParamByUseid(String userid,String result);
}
