package com.ylxx.qt.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.StatisticsMapper;
import com.ylxx.qt.service.StatisticsService;
import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;

/**
 * 
 * @author mengpeitong
 * 
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
	// 账户统计服务
	@Resource
	private StatisticsMapper stmp;

	@Override
	public int getDays(List<String> investorid) {
		int days = 0;
		try {
			days = stmp.getDays(investorid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}

	@Override
	public List<TradingAccountFiledBean> getAll(List<String> accountid) {
		List<TradingAccountFiledBean> list = null;
		try {
			list = stmp.getAll(accountid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TradingAccountFiledBean> getPrebalance(List<String> accountid) {
		List<TradingAccountFiledBean> list = null;
		try {
			list = stmp.getPrebalance(accountid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TradeFieldBean> queryAll(List<String> investorid) {
		List<TradeFieldBean> list = null;
		try {
			list = stmp.queryAll(investorid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TradeFieldBean> getCloseProfit(List<String> investorid) {
		List<TradeFieldBean> list = null;
		try {
			list = stmp.getCloseProfit(investorid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TradeFieldBean> selectWeekProfitField(List<String> account_id) {
		List<TradeFieldBean> list = stmp.selectWeekProfitField(account_id);
		return list;
	}

	@Override
	public List<PositionDetailBean> selectPositionPrefer(List<String> investor_id, List<String> investorid) {
		List<PositionDetailBean> list = stmp.selectPositionPrefer(investor_id, investorid);
		return list;
	}

	@Override
	public Double getAllOperateFee(List<String> acList) {

		return stmp.getAllOperateFee(acList);
	}

	@Override
	public List<TradingAccountFiledBean> getMinPrebalance(List<String> acList, String maxday) {
		List<TradingAccountFiledBean> list = null;
		try {
			list = stmp.getMaxPrebalance(acList, maxday);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * 最大亏损率 计算 ：最大亏损率=（最小净值日期前的最大净值-最小净值）/最小净值日期前的最大净值
	 * 
	 * 暂时没有用到
	 * 
	 * 
	 */
	public Double getMaxFaile2(List<String> acList) {
		List<TradingAccountFiledBean> maxFileList = null;
		List<TradingAccountFiledBean> capitalAndPowerList = null;

		Double result = 0.0;
		try {
			capitalAndPowerList = stmp.getPrebalance(acList); // 计算每日净值
			for (int i = 0; i < capitalAndPowerList.size(); i++) {
				// 当前最新本金 =初始本金+入金-出金；
				Double cptal = capitalAndPowerList.get(i).getCapital() + capitalAndPowerList.get(i).getDeposit()
						- capitalAndPowerList.get(i).getWithdraw();
				// 每日净值=当日最新静态权益/最新本金
				Double allNet = capitalAndPowerList.get(i).getPre_Balance() / cptal;
				capitalAndPowerList.get(i).setCumulative(allNet);
			} // 对获取到数据进行排序
			Collections.sort(capitalAndPowerList, new Comparator<TradingAccountFiledBean>() {

				@Override
				public int compare(TradingAccountFiledBean o1, TradingAccountFiledBean o2) {
					return new Double(o1.getCumulative()).compareTo(new Double(o2.getCumulative()));
				}
			});

			if (capitalAndPowerList.size() < 2) {
				return result;
			} else {
				int j = 0;
				while (true) {
					Double minNet = capitalAndPowerList.get(j).getCumulative();
					String minday = capitalAndPowerList.get(j).getTrading_day();
					maxFileList = stmp.getMaxPrebalance(acList, minday);
					if (maxFileList.size() > 0) {
						for (int i = 0; i < maxFileList.size(); i++) {
							// 当前最新本金 =初始本金+入金-出金；
							Double cptal = maxFileList.get(i).getCapital() + maxFileList.get(i).getDeposit()
									- maxFileList.get(i).getWithdraw();
							Double allNet = maxFileList.get(i).getPre_Balance() / cptal;
							maxFileList.get(i).setCumulative(allNet);
						} // 对获取到的数据按照净值大小进行排序
						Collections.sort(maxFileList, new Comparator<TradingAccountFiledBean>() {

							@Override
							public int compare(TradingAccountFiledBean o1, TradingAccountFiledBean o2) {
								return new Double(o1.getCumulative()).compareTo(new Double(o2.getCumulative()));
							}
						}); // 最大亏损率=（最小净值日期前的最大净值-最小净值）/最小净值日期前的最大净值
						Double maxNet = maxFileList.get(maxFileList.size() - 1 - j).getCumulative();
						result = (maxNet - minNet) / maxNet;
						break;
					} else {
						j++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public List<PositionDetailBean> getPositionMessage(String day, List<String> list, String beginTime, String endTime,
			int page, int limit) throws Exception {
		// 获取持仓表所有信息
		List<PositionDetailBean> posList = null;
		try {
			int pageIndex = (page - 1) * limit;
			posList = stmp.getPositionMessage(day, list, beginTime, endTime, pageIndex, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return posList;
	}

	@Override
	public Integer getPositionMessageCounts(String day, List<String> list, String beginTime, String endTime) {
		// 获取持仓表所有信息数量
		return stmp.getPositionMessageCounts(day, list, beginTime, endTime);
	}

	@Override
	public List<TradingAccountFiledBean> getAllNetPor(List<String> acList) {
		List<TradingAccountFiledBean> list = null;
		try {
			list = stmp.getAllNetPor(acList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public double getMaxProfit(List<String> acList) {
		List<TradingAccountFiledBean> maxFileList = null;
		List<TradingAccountFiledBean> capitalAndPowerList = null;
		List<TradingAccountFiledBean> minProfitList = null;
		Double result = 0.0;
		Double maxNet = 0.00;
		Double minNet = 0.00;
		Double maxNet2 = 0.00;
		Double minNet2= 0.00;
		Double result1=0.00;
		Double result2=0.00;
		try {
			capitalAndPowerList = stmp.getPrebalance(acList);
			// 计算每日净值
			for (int i = 0; i < capitalAndPowerList.size(); i++) {
				// 当前最新本金 = 初始本金+入金-出金；
				Double cptal = capitalAndPowerList.get(i).getCapital() + capitalAndPowerList.get(i).getDeposit()
						- capitalAndPowerList.get(i).getWithdraw();
				Double allNet = capitalAndPowerList.get(i).getPre_Balance() / cptal;
				capitalAndPowerList.get(i).setCumulative(allNet);
			}
			// 对获取到的数据进行排序，按照净值大小排序
			Collections.sort(capitalAndPowerList, new Comparator<TradingAccountFiledBean>() {
				@Override
				public int compare(TradingAccountFiledBean o1, TradingAccountFiledBean o2) {
					return new Double(o1.getCumulative()).compareTo(new Double(o2.getCumulative()));
				}
			});
			if (capitalAndPowerList.size() < 2) {
				return result;
			} else {
				// 1、获取最大净值当天
				String maxday = capitalAndPowerList.get(capitalAndPowerList.size() - 1).getTrading_day();
				maxNet=capitalAndPowerList.get(capitalAndPowerList.size() - 1).getCumulative();
				//2、 获取最大净值天后的最小净值
				maxFileList = stmp.getMaxPrebalance(acList, maxday);
				if (maxFileList.size() > 0) {
					for (int i = 0; i < maxFileList.size(); i++) {
						// 当前最新本金 = 初始本金+入金-出金；
						Double cptal = maxFileList.get(i).getCapital() + maxFileList.get(i).getDeposit()
								- maxFileList.get(i).getWithdraw();
						Double allNet = maxFileList.get(i).getPre_Balance() / cptal;
						maxFileList.get(i).setCumulative(allNet);
					}
					// 对获取到的数据进行排序，按照净值大小排序
					Collections.sort(maxFileList, new Comparator<TradingAccountFiledBean>() {
						@Override
						public int compare(TradingAccountFiledBean o1, TradingAccountFiledBean o2) {
							return new Double(o1.getCumulative()).compareTo(new Double(o2.getCumulative()));
						}
					});
					// 获取到最小值
					minNet = maxFileList.get(0).getCumulative();
					//3、获取净值最小的那天，
					String minday = capitalAndPowerList.get(0).getTrading_day();
					minNet2=capitalAndPowerList.get(0).getCumulative();
					//4、向前找最大净值
					minProfitList = stmp.getAfterDayPro(acList, minday);
					if (minProfitList.size() > 0) {
						for (int i = 0; i < minProfitList.size(); i++) {
							// 当前最新本金 = 初始本金+入金-出金；
							Double cptal = minProfitList.get(i).getCapital() + minProfitList.get(i).getDeposit()
									- minProfitList.get(i).getWithdraw();
							Double allNet = minProfitList.get(i).getPre_Balance() / cptal;
							minProfitList.get(i).setCumulative(allNet);
						}
						// 对获取到的数据进行排序，按照净值大小排序
						Collections.sort(minProfitList, new Comparator<TradingAccountFiledBean>() {
							@Override
							public int compare(TradingAccountFiledBean o1, TradingAccountFiledBean o2) {
								return new Double(o1.getCumulative()).compareTo(new Double(o2.getCumulative()));
							}
						});
						//得到最大净值
						maxNet2 = minProfitList.get(minProfitList.size() - 1).getCumulative();
					}else{
						maxNet2=minNet2;
					}
				}
			}
			
			result1 = (maxNet - minNet) / maxNet;
			result2 = (maxNet2 - minNet2) / maxNet2;
			if(result1>=result2){
				result=result1;
			}else{
				result=result2;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 最大亏损率 改版： 判断（ 权益-本金）：先找最大值。然后向后找最小值（获取最小值） 再找最小值。然后向前找最大值（获取最大值）
	 * 最大亏损=（最大值-最小值）/最大值
	 */
	@Override
	public Double getMaxFaile(List<String> acList) {
		List<TradingAccountFiledBean> maxFileList = null;
		List<TradingAccountFiledBean> capitalAndPowerList = null;
		List<TradingAccountFiledBean> minFileList = null;
		Double result = 0.0;
		Double maxNet = 0.00;
		Double maxNet2 = 0.00;
		Double minNet = 0.00;
		Double minNet2 = 0.00;
		Double result1 = 0.00;
		Double result2 = 0.00;
		try {
			capitalAndPowerList = stmp.getPrebalance(acList);
			// 计算每日净值
			for (int i = 0; i < capitalAndPowerList.size(); i++) {
				// 当前最新本金 = 初始本金+入金-出金；
				Double cptal = capitalAndPowerList.get(i).getCapital() + capitalAndPowerList.get(i).getDeposit()
						- capitalAndPowerList.get(i).getWithdraw();
				// 每日净值=当日最新静态权益/最新本金
				Double allNet = capitalAndPowerList.get(i).getPre_Balance() / cptal;
				capitalAndPowerList.get(i).setCumulative(allNet);
			}
			// 对获取到数据进行排序
			Collections.sort(capitalAndPowerList, new Comparator<TradingAccountFiledBean>() {
				@Override
				public int compare(TradingAccountFiledBean o1, TradingAccountFiledBean o2) {
					return new Double(o1.getCumulative()).compareTo(new Double(o2.getCumulative()));
				}
			});

			if (capitalAndPowerList.size() < 2) {
				return result;
			} else {
				//1、先找最大值的那天
				String maxday = capitalAndPowerList.get(capitalAndPowerList.size()-1).getTrading_day();
				maxNet = capitalAndPowerList.get(capitalAndPowerList.size()-1).getCumulative();
				//2、向后找净值最小值
				maxFileList = stmp.getAfterDayPro(acList, maxday);
				if (maxFileList.size() > 0) {
					for (int i = 0; i < maxFileList.size(); i++) {
						// 当前最新本金 = 初始本金+入金-出金；
						Double cptal = maxFileList.get(i).getCapital() + maxFileList.get(i).getDeposit()
								- maxFileList.get(i).getWithdraw();
						Double allNet = maxFileList.get(i).getPre_Balance() / cptal;
						maxFileList.get(i).setCumulative(allNet);
					}
					// 对获取到的数据按照净值大小进行排序
					Collections.sort(maxFileList, new Comparator<TradingAccountFiledBean>() {
						@Override
						public int compare(TradingAccountFiledBean o1, TradingAccountFiledBean o2) {
							return new Double(o1.getCumulative()).compareTo(new Double(o2.getCumulative()));
						}
					});
					// 获取净值最小值
					minNet = maxFileList.get(0).getCumulative();
				}else{
					minNet=maxNet;
				}
			}
			result1=(maxNet-minNet)/maxNet;
			// ***************************找最大值******//
			//3、先获取到净值最小的那天。
			String minday = null;
			minday = capitalAndPowerList.get(0).getTrading_day();
			minNet2=capitalAndPowerList.get(0).getCumulative();
			//4、然后找净值最小那天前的最大值
			minFileList = stmp.getMaxPrebalance(acList, minday);
			if (minFileList.size() > 0) {
				for (int i = 0; i < minFileList.size(); i++) {
					// 当前最新本金 = 初始本金+入金-出金；
					Double cptal = minFileList.get(i).getCapital() + minFileList.get(i).getDeposit()
							- minFileList.get(i).getWithdraw();
					Double allNet = minFileList.get(i).getPre_Balance() / cptal;
					minFileList.get(i).setCumulative(allNet);
				}
				// 对获取到的数据按照净值大小进行排序
				Collections.sort(minFileList, new Comparator<TradingAccountFiledBean>() {
					@Override
					public int compare(TradingAccountFiledBean o1, TradingAccountFiledBean o2) {
						return new Double(o1.getCumulative()).compareTo(new Double(o2.getCumulative()));
					}
				});
				// 获取最大值
				maxNet2 = minFileList.get(minFileList.size()-1).getCumulative();
			}else{
				maxNet2=minNet2;
			}
			result2=(maxNet2-minNet2)/maxNet2;
			if(result1>=result2){
				result=result1;
			}else{
				result=result2;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
