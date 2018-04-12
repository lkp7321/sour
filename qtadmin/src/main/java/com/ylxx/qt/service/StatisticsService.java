package com.ylxx.qt.service;

import java.util.List;

import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;

/**
 * 
 * @author mengpeitong
 * 
 */
public interface StatisticsService {
	/**
	 * 获取交易周期
	 * 
	 * @param investorid
	 * @return
	 */
	public int getDays(List<String> investorid);

	/**
	 * 获取资金账户表信息
	 * 
	 * @param accountid
	 * @return
	 */
	public List<TradingAccountFiledBean> getAll(List<String> accountid);

	/**
	 * 获取本金和每日权益
	 * 
	 * @param accountid
	 * @return
	 */
	public List<TradingAccountFiledBean> getPrebalance(List<String> accountid);

	/**
	 * 获取盈亏信息
	 * 
	 * @param investorid
	 * @return List<TradeFieldBean>
	 */
	public List<TradeFieldBean> queryAll(List<String> investorid);

	/**
	 * 获取每月盈亏信息
	 * 
	 * @param acList
	 * @return
	 */
	public List<TradeFieldBean> getCloseProfit(List<String> acList);

	/**
	 * 每周盈亏
	 * 
	 * @param acList
	 * @return
	 */
	public List<TradeFieldBean> selectWeekProfitField(List<String> acList);

	/**
	 * 持仓偏好
	 * 
	 * @param list_str
	 * @param list_str2
	 * @return
	 */
	public List<PositionDetailBean> selectPositionPrefer(List<String> list_str,
			List<String> list_str2);

	/**
	 * 获取累计手续费
	 * 
	 * @param acList
	 * @return
	 */
	public Double getAllOperateFee(List<String> acList);

	/**
	 * 获取在最大静态权益后的最小静态权益
	 * 
	 * @param acList
	 * @param maxday
	 * @return
	 */
	public List<TradingAccountFiledBean> getMinPrebalance(List<String> acList,
			String maxday);

	/**
	 * 最大亏损率
	 * 
	 * @param acList
	 * @return
	 */
	public Double getMaxFaile(List<String> acList);

	/**
	 * 获取持仓表所有信息
	 * 
	 * @param day
	 * @param ctpidList
	 * @param beginTime
	 * @param endTime
	 * @param index
	 * @param pagecounts
	 * @return
	 */
	public List<PositionDetailBean> getPositionMessage(String day,
			List<String> ctpidList, String beginTime, String endTime,
			int pageIndex, int pagecounts) throws Exception;

	/**
	 * 获取持仓表所有信息数量
	 * 
	 * @param day
	 * @param ctpidList
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Integer getPositionMessageCounts(String day, List<String> ctpidList,
			String beginTime, String endTime);
	/**
	 * 获取累计净利润
	 * @param acList 账户集合
	 * @return
	 */
	public List<TradingAccountFiledBean> getAllNetPor(List<String> acList);
	
	/**
	 * 计算最大盈利率
	 * @param acList
	 * @return
	 */
	public double getMaxProfit(List<String> acList);
}
