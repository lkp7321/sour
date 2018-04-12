package com.ylxx.qt.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;

public interface StatisticsMapper {
	/**
	 * 获取盈亏信息
	 * 
	 * @param investorid
	 * @return
	 * @throws Exception
	 */
	public List<TradeFieldBean> queryAll(
			@Param("investorid") List<String> investorid) throws Exception;

	/**
	 * 获取交易周期
	 * 
	 * @param investorid
	 *            账号
	 * @return
	 * @throws Exception
	 */
	public int getDays(@Param("investorid") List<String> investorid)
			throws Exception;

	/**
	 * 获取资金账户表信息
	 * 
	 * @param accountid
	 * @return
	 * @throws Exception
	 */
	public List<TradingAccountFiledBean> getAll(
			@Param("accountid") List<String> accountid) throws Exception;

	/**
	 * 获取本金和每日权益
	 * 
	 * @param accountid
	 * @return
	 * @throws Exception
	 */
	public List<TradingAccountFiledBean> getPrebalance(
			@Param("accountid") List<String> accountid) throws Exception;

	/**
	 * 获取每月盈亏信息
	 * 
	 * @param investorid
	 * @return
	 * @throws Exception
	 */
	public List<TradeFieldBean> getCloseProfit(
			@Param("investorid") List<String> investorid) throws Exception;

	/**
	 * 每周盈亏
	 * 
	 * @param account_id
	 * @return
	 */
	public List<TradeFieldBean> selectWeekProfitField(
			@Param("account_id") List<String> account_id);

	/**
	 * 持仓偏好
	 * 
	 * @param investor_id
	 * @param investorid
	 * @return
	 */
	public List<PositionDetailBean> selectPositionPrefer(
			@Param("investor_id") List<String> investor_id,
			@Param("investorid") List<String> investorid);

	/**
	 * 获取累计手续费
	 * 
	 * @param acList
	 * @return
	 */
	public Double getAllOperateFee(@Param("acList") List<String> acList);

	/**
	 * 小于某天的每日权益和本金
	 * 
	 * @param acList账户
	 * @param maxday
	 *            最大静态权益当天
	 * @return
	 */
	public List<TradingAccountFiledBean> getMaxPrebalance(
			@Param("acList") List<String> acList, @Param("maxday") String maxday);

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
	public List<PositionDetailBean> getPositionMessage(
			@Param("day") String day, @Param("investor_id") List<String> list,
			@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("pageIndex") int pageIndex,
			@Param("pagecounts") int pagecounts) throws Exception;

	/**
	 * 获取持仓表所有信息数量
	 * 
	 * @param day
	 * @param ctpidList
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Integer getPositionMessageCounts(@Param("day") String day,
			@Param("investor_id") List<String> list,
			@Param("beginTime") String beginTime,
			@Param("endTime") String endTime);

	/**
	 * 获取累计净利润
	 * 
	 * @param acList
	 * @return
	 */
	public List<TradingAccountFiledBean> getAllNetPor(
			@Param("acList") List<String> acList);

	
	
	/**
	 * 大于某天的每日权益和本金
	 * @param acList
	 * @param maxday
	 * @return
	 */
	public List<TradingAccountFiledBean> getAfterDayPro(
			@Param("acList") List<String> acList, @Param("maxday") String maxday);

}
