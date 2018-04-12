package com.ylxx.qt.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ylxx.qt.service.po.InstrumentFieldBean;
import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;

public interface TradePreferMapper {

	// 累计净值
	public List<TradingAccountFiledBean> selectCumulativeField(List<String> AList);

	// 成交偏好
	public List<InstrumentFieldBean> selectVarTradeField(List<String> item);
//	public List<InstrumentFieldBean> selectVarTradeField(String investor_id);

	// 持仓偏好
	public List<PositionDetailBean> selectPositionPrefer(String investor_id,String investorid);
	
	// 每周盈亏
	public List<TradeFieldBean> selectWeekProfitField(String account_id);

	// 原始数据
	public List<TradingAccountFiledBean> selectRowDataField1(@Param("month")String month,@Param("AList") List<String> AList,
			@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("index") int index,
			@Param("pagecounts") int pagecounts);
		public List<TradingAccountFiledBean> selectRowDataField(Map<String,Object> map);
	
	// 原始数据数量
	public Integer selectRowDataFieldCounts1(@Param("month")String month,@Param("AList") List<String> AList,
				@Param("beginTime") String beginTime, @Param("endTime") String endTime);
	public Integer selectRowDataFieldCounts(Map<String,Object> map);

	// 成交详细信息
	/*public List<TradeFieldBean> selectTradefield(@Param("day")String day,@Param("ctpid") String ctpid, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("index") int index, @Param("pagecounts") int pagecounts);*/
	public List<TradeFieldBean> selectTradefield1(@Param("month")String month,@Param("AList") List<String> Alist, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("index") int index, @Param("pagecounts") int pagecounts);
	public List<TradeFieldBean> selectTradefield(Map<String,Object> map);
	// 成交详细信息数量
	public Integer selectTradefieldCounts1(@Param("month")String day,@Param("AList") List<String> AList, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);
	public Integer selectTradefieldCounts(Map<String,Object> map);
}
