package com.ylxx.qt.controller;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.qt.service.RevenueRankingService;
import com.ylxx.qt.service.StatisticsService;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;

/**
 * 收益排名
 * 
 * @author suimanman
 * 
 */
@Controller
@RequestMapping("/revenue")
public class RevenueRankingController {

	@Resource
	private StatisticsService stas;

	@Resource
	private RevenueRankingService rrs;

	// 从菜单栏跳转到收益排名页面
	@RequestMapping(value = "/toRevenueRank.do")
	public String toRevenueRank(HttpServletRequest request) {
		return "revenuerank/totalrank";
	}

	/**
	 * 
	 * 收益排名
	 * 
	 * @param model
	 * @param session
	 * @param requset
	 * @return
	 * @throws Exception
	 * @author suimanman
	 */
	@RequestMapping(value = "/revenuerank.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody
	String getRevenueRankData(Model model, HttpSession session,
			HttpServletRequest request) throws Exception {
		String result = "[";
		List<TradingAccountFiledBean> tradindAccoundList = null;
		List<TradeFieldBean> tradeFileList = null;
		DecimalFormat decimalFormatePrecent = new DecimalFormat("0.00%");

		String username = ""; 			// 投资者
		String ctpid = "";				// 账户
		Double totalAmount = 0.0; 		// 本金
		Double allNet = 0.0; 			// 累计净值
		Double customerRights = 0.0; 	// 客户权益
		Double allNetPor = 0.0; 		// 累计净利润
		Double allOperateFee = 0.0; 	// 累计手续费
		String allRate = ""; 			// 累计收益率
		String winRate = ""; 			// 胜率
		int winNum = 0; 				// 胜算笔数
		int failNum = 0; 				// 亏损笔数
		double maxFail = 0.00; 			// 最大亏损率
		String bigRetracement = null;	// 最大回撤

		tradindAccoundList = rrs.getAllRevenue();
		if (null != tradindAccoundList && tradindAccoundList.size() != 0) {
			int k = 0;
			for (TradingAccountFiledBean obj : tradindAccoundList) {
				// 投资者
				username = obj.getUsername();
				// 账户
				ctpid = obj.getCtp_id();
				String [] ctpidArr= ctpid.split(","); 			//将字符串转换成数组
				List<String> accList = Arrays.asList(ctpidArr);	//将数组转换成集合
				tradeFileList = stas.queryAll(accList);
				// 获取盈利笔数与亏损笔数
				for (int i = 0; i < tradeFileList.size(); i++) {
					if (!tradeFileList.get(i).getOffSet_flag().equals("0")) {
						if (tradeFileList.get(i).getProfit() > 0) {
							// 盈利笔数
							winNum++;
						} else if (tradeFileList.get(i).getProfit() < 0) {
							// 亏损笔数
							failNum++;
						}
					}
				}
				// 胜率=胜算 = 盈利笔数/(盈利笔数+亏损笔数)
				if (winNum == 0 && failNum == 0) {
					winRate = "0";
				} else {
					winRate = decimalFormatePrecent.format((double) winNum / (double) (winNum + failNum));
				}
				
				// 累计手续费
				allOperateFee = obj.getCommission();
				// 本金 = 初始金额+入金-出金
				totalAmount = obj.getTotalamount()+obj.getDeposit()-obj.getWithdraw();
				// 客户权益 = 今日客户权益（动态权益）=今日静态权益+今日平仓盈亏+今日浮动盈亏（持仓盈亏）+当日存取-手续费
				customerRights = obj.getFund();
				
				if(null != totalAmount && totalAmount !=0){
					// 累计净值 = 静态权益/本金
					allNet = obj.getPre_Balance()/totalAmount;
					// 累计收益率 = (客户权益- 本金)/本金*100%
					allRate = decimalFormatePrecent.format((customerRights-totalAmount)/totalAmount);
				}
				
				
				// 累计净利润 = 最新静态权益-入金+出金-账户初始金额
				allNetPor = obj.getPre_Balance()-totalAmount;
				// 最大回撤=最大亏损 = (初始金额 - 静态权益)/初始金额*100%
				maxFail = stas.getMaxFaile(accList);
				bigRetracement = decimalFormatePrecent.format(maxFail);
				
				result += "{\"username\":\"" + username + "\",\"allNet\":\""
						+ String.format("%.2f", allNet)
						+ "\",\"customerRights\":\""
						+ String.format("%.2f", customerRights)
						+ "\",\"allNetPor\":\""
						+ String.format("%.2f", allNetPor)
						+ "\",\"allOperateFee\":\""
						+ String.format("%.2f", allOperateFee)
						+ "\",\"allRate\":\"" + allRate
						+ "\",\"bigRetracement\":\""+bigRetracement
						+ "\",\"winRate\":\"" + winRate
						+ "\",\"accounts\":\"" + ctpid
						+ "\"}";
				k++;
				if (k < tradindAccoundList.size()) {
					result += ",";
				}
			}
		}
		return result + "]";
	}
}
