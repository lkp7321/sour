package com.ylxx.qt.service;

import java.util.List;

import com.ylxx.qt.service.po.InstrumentFieldBean;
import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;

public interface ITradePreferService {

	/**
	 *  累计净值
	 * @param account_id
	 * @return
	 */
	public List<TradingAccountFiledBean> selectCumulativeField(List<String> item);

	/**
	 *  成交偏好
	 * @param investor_id
	 * @return
	 */
	public List<InstrumentFieldBean> selectVarTradeField(List<String> item);
//	public List<InstrumentFieldBean> selectVarTradeField(String investor_id);

	/**
	 * 持仓偏好
	 * @param investor_id
	 * @param investorid
	 * @return
	 */
	public List<PositionDetailBean> selectPositionPrefer(String investor_id,String investorid);
	
	/**
	 *  每周盈亏
	 * @param account_id
	 * @return
	 */
	public List<TradeFieldBean> selectWeekProfitField(String account_id);

	/**
	 *  原始数据
	 * @param month
	 * @param ctpid
	 * @param beginTime
	 * @param endTime
	 * @param index
	 * @param pagecounts
	 * @return
	 */
	public List<TradingAccountFiledBean> selectRowDataField(String month,List<String> item, String beginTime, String endTime, int index,
			int pagecounts);
	/**
	 *  原始数据数量
	 * @param month
	 * @param ctpid
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Integer selectRowDataFieldCounts(String month,List<String> item, String beginTime, String endTime);

	/**
	 *  成交详细信息
	 * @param day
	 * @param ctpid
	 * @param beginTime
	 * @param endTime
	 * @param index
	 * @param pagecounts
	 * @return
	 */
	/*public List<TradeFieldBean> selectTradefield(String day,String ctpid, String beginTime, String endTime, int index,
			int pagecounts);*/
	public List<TradeFieldBean> selectTradefield(String month,List<String> item, String beginTime, String endTime, int index,
			int pagecounts);

	/**
	 *  成交详细信息数量
	 * @param day
	 * @param ctpid
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
//	public Integer selectTradefieldCounts(String day,String ctpid, String beginTime, String endTime);
	public Integer selectTradefieldCounts(String month,List<String> item, String beginTime, String endTime);

}
