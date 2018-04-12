package com.ylxx.qt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.TradePreferMapper;
import com.ylxx.qt.service.ITradePreferService;
import com.ylxx.qt.service.po.InstrumentFieldBean;
import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;

@Service("tradePreferService")
public class TradePreferImpl implements ITradePreferService {

	@Resource
	private TradePreferMapper tpm;
	
	
	// 累计净值
	@Override
	public List<TradingAccountFiledBean> selectCumulativeField(List<String> item) {
		List<TradingAccountFiledBean> list = tpm.selectCumulativeField(item);
		return list;
	}

	// 成交偏好
	@Override
	/*public List<InstrumentFieldBean> selectVarTradeField(String investor_id) {
		List<InstrumentFieldBean> list = tpm.selectVarTradeField(investor_id);
		return list;
	}*/
	public List<InstrumentFieldBean> selectVarTradeField(List<String> item) {
		List<InstrumentFieldBean> list = null;
		try {
			list = tpm.selectVarTradeField(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 持仓偏好
	@Override
	public List<PositionDetailBean> selectPositionPrefer(String investor_id,String investorid) {
		List<PositionDetailBean> list = tpm.selectPositionPrefer(investor_id,investorid);
		return list;
	}
	
	// 每周盈亏
	@Override
	public List<TradeFieldBean> selectWeekProfitField(String account_id) {
		List<TradeFieldBean> list = tpm.selectWeekProfitField(account_id);
		return list;
	}

	// 原始数据
	@Override
	public List<TradingAccountFiledBean> selectRowDataField(String month,List<String> item,String beginTime,String endTime,int page,int limit) {
		int index=(page-1)*limit;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("month", month);
		map.put("AList", item);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("indexpp", index);
		map.put("pagecounts", limit);
		List<TradingAccountFiledBean> list = tpm.selectRowDataField(map);
		return list;
	}
	// 原始数据数量
	@Override
	public Integer selectRowDataFieldCounts(String month,List<String> item, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("month", month);
		map.put("AList", item);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return tpm.selectRowDataFieldCounts(map);
	}
	
	// 成交详细信息
	@Override
	public List<TradeFieldBean> selectTradefield(String month,List<String> item,String beginTime,String endTime,int page,int limit) {
		/*int index=(page-1)*limit;
		List<TradeFieldBean> list = tpm.selectTradefield(day,ctpid,beginTime,endTime,index,limit);
		return list;*/
		int index=(page-1)*limit;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("month", month);
		map.put("AList", item);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("indexpp", index);
		map.put("pagecounts", limit);
		List<TradeFieldBean> list = tpm.selectTradefield(map);
		return list;
	}
	// 成交详细信息数量
	@Override
	public Integer selectTradefieldCounts(String month,List<String> item, String beginTime, String endTime) {		
//		return 	tpm.selectTradefieldCounts(day,item, beginTime, endTime);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("month", month);
		map.put("AList", item);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return tpm.selectTradefieldCounts(map);
	}

}
