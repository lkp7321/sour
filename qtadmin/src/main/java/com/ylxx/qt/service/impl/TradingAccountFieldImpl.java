package com.ylxx.qt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.TradingAccountFieldMapper;
import com.ylxx.qt.service.ITradingAccountFieldService;
import com.ylxx.qt.service.po.TradingAccountFiledBean;


@Service("ITradingAccountFieldService")
public class TradingAccountFieldImpl implements ITradingAccountFieldService{

	@Resource
	private TradingAccountFieldMapper tafMpper;
	
	@Override
	public List<TradingAccountFiledBean> listSum(List<String> item) {
		List<TradingAccountFiledBean> list2=tafMpper.listSum(item);
		return list2;
	}

	@Override
	public List<TradingAccountFiledBean> listSpace(List<String> item) {
		List<TradingAccountFiledBean>list3=tafMpper.listSpace(item);
		return list3;
	}

}
