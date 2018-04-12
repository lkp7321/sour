package com.ylxx.qt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.CommonMapper;
import com.ylxx.qt.service.CommonService;
import com.ylxx.qt.service.po.InstruProductBean;
import com.ylxx.qt.service.po.ParameterdetailsBean;

/**
 * 
 * @author suimanman
 * 
 */
@Service
public class CommonServiceImpl implements CommonService {

	@Resource
	private CommonMapper comm;

	@Override
	public List<InstruProductBean> findInsProdList(String result) {
		// 全局搜索：获取合约代码、品种名称列表
		List<InstruProductBean> list = new ArrayList<InstruProductBean>();
		try {
			list = comm.findInsProdList(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<ParameterdetailsBean> findParamByUseid(String userid,
			String result) {
		// 全局搜索：根据用户id、搜索内容，获取详细信息
		List<ParameterdetailsBean> list = new ArrayList<ParameterdetailsBean>();
		try {
			list = comm.findParamByUseid(userid, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
