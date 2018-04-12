package com.ylxx.qt.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylxx.qt.service.IQTCountService;
import com.ylxx.qt.service.ITradePreferService;
import com.ylxx.qt.service.ITradingAccountFieldService;
import com.ylxx.qt.service.StatisticsService;
import com.ylxx.qt.service.po.InstrumentFieldBean;
import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.PositionPreferBean;
import com.ylxx.qt.service.po.ProductProfitBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;
import com.ylxx.qt.utils.GetNowDate;
import com.ylxx.qt.utils.ProductColorUtils;

/**
 * 账户统计
 * 
 * @author mengpeitong,zouhang
 * 
 */
@Controller
@RequestMapping("/basic")
public class StatisticsController {

	@Resource
	private ITradingAccountFieldService itaService;

	@Resource
	private StatisticsService stas;

	@Resource
	private IQTCountService qtcs; // 用到该类的有：每天持仓

	@Resource
	private ITradePreferService tps; // 用到该类的有：成交信息

	@Resource
	private ITradingAccountFieldService tafs; // 用到该类的有：每天仓位（上）

	/**
	 * 
	 * 概况页面获取值
	 * 
	 * @param model
	 * @param session
	 * @param requset
	 * @return
	 * @throws Exception
	 * @author mengpeitong
	 */
	@RequestMapping(value = "/survey.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String getData(Model model, HttpSession session, HttpServletRequest request) throws Exception {

		// 交易笔数
		int tradeNum = 0;
		// 交易周期
		int tradeCycle = 0;
		// 盈利笔数
		int winNum = 0;
		// 亏损笔数
		int failNum = 0;
		// 胜算
		String winPrecent = "";
		// 盈亏比
		String profit_Loss = "";
		// 累计手续费
		Double allOperateFee = 0.0;
		// 累计净利润
		Double allNetPor = 0.0;
		// 平均每笔盈利
		Double avgPorfit = 0.0;
		// 平均每笔亏损
		Double avgFail = 0.0;
		// 平均每笔费用
		Double avgFee = 0.0;
		// 费用占比
		String feePrecent = null;
		int maxFailNum = 0; // 最大连续亏损次数
		int maxProFitNum = 0; // 最大连续盈利次数

		double maxProfit = 0.00; // 最大盈利率
		double maxFail = 0.00; // 最大亏损率

		String maxProfitPrecent = null;
		String maxFailPrecent = null;

		DecimalFormat decimalFromate = new DecimalFormat("0.00");
		DecimalFormat decimalFormatePrecent = new DecimalFormat("0.00%");
		// 创建交易信息集合
		List<TradeFieldBean> tradeFileList = null;
		// 资金账户信息集合，用来计算累计净利润
		List<TradingAccountFiledBean> tradeAccountList = null;
		// 获取账户
		String[] account = getAccounts(request);
		// 总盈利金额
		Double allWinCount = 0.00;
		// 总亏损金额
		Double allFailCount = 0.0;
		// 如果传入的账户不为空，则按照账户统计
		if (account != null) {
			List<String> acList = Arrays.asList(account);
			tradeCycle = stas.getDays(acList);
			// 获取盈亏信息
			tradeFileList = stas.queryAll(acList);
			// 交易笔数
			tradeNum = tradeFileList.size();

			// 累计手续费
			allOperateFee = stas.getAllOperateFee(acList);
			if (allOperateFee == null) {
				allOperateFee = 0.0;
			}
			for (int i = 0; i < tradeFileList.size(); i++) {
				if (!tradeFileList.get(i).getOffSet_flag().equals("0")) {
					if (tradeFileList.get(i).getProfit() > 0) {
						// 盈利笔数
						winNum++;
						// 总盈利金额
						allWinCount += tradeFileList.get(i).getProfit(); // 成交盈亏
					} else if (tradeFileList.get(i).getProfit() < 0) {
						// 亏损笔数
						failNum++;
						// 总亏损金额
						allFailCount += tradeFileList.get(i).getProfit();
					}
				}
			}

			tradeAccountList = stas.getAllNetPor(acList);

			// 获取资金账户表信息
			if (tradeAccountList.size() != 0) {
				int t = tradeAccountList.size() - 1;
				// 净利润
				allNetPor = tradeAccountList.get(t).getPre_Balance() - tradeAccountList.get(t).getDeposit()
						+ tradeAccountList.get(t).getWithdraw() - tradeAccountList.get(t).getCapital();
			}
			// 最大连续亏损次数
			int temp = 0; // 当前次数
			for (int i = 0; i < tradeFileList.size(); i++) {
				if (!tradeFileList.get(i).getOffSet_flag().equals("0")) {
					if (tradeFileList.get(i).getProfit() < 0) {
						temp++;
					} else {
						if (temp > maxFailNum) {
							maxFailNum = temp;
						}
						temp = 0;
					}
				}
			}
			if (temp > maxFailNum) {
				maxFailNum = temp;
			}

			// 最大连续盈利次数
			int temp1 = 0;
			for (int i = 0; i < tradeFileList.size(); i++) {
				if (!tradeFileList.get(i).getOffSet_flag().equals("0")) {
					if (tradeFileList.get(i).getProfit() > 0) {
						temp1++;
					} else {
						if (temp1 > maxProFitNum) {
							maxProFitNum = temp1;
						}
						temp1 = 0;
					}
				}
			}

			if (temp1 > maxProFitNum) {
				maxProFitNum = temp1;
			}
			// 计算最大亏损率
			maxFail = stas.getMaxFaile(acList);
			// 最大亏损率
			maxFailPrecent = decimalFormatePrecent.format(maxFail);
			// 计算最大盈利率
			maxProfit = stas.getMaxProfit(acList);
			// 最大盈利率
			maxProfitPrecent = decimalFormatePrecent.format(maxProfit);
			// 胜算
			if (winNum == 0 && failNum == 0) {
				winPrecent = "0";
			} else {
				winPrecent = decimalFormatePrecent.format((double) winNum / (double) (winNum + failNum));
			}

			// 盈亏比
			if (winNum == 0 && failNum == 0) {
				profit_Loss = "0:0";
			} else if (winNum == 0 && failNum != 0) {
				profit_Loss = "0:" + failNum;
			} else {
				profit_Loss = "1:" + String.valueOf(decimalFromate.format(((double) failNum / (double) winNum)));
			}

			// 平均每笔盈利
			if (winNum == 0) {
				avgPorfit = 0.0;
			} else {
				avgPorfit = allWinCount / winNum;
			}

			// 平均每笔亏损
			if (failNum == 0) {
				avgFail = 0.0;
			} else {
				avgFail = allFailCount / failNum;
			}

			// 平均每笔费用
			if (tradeNum == 0) {
				avgFee = 0.0;
			} else {
				avgFee = allOperateFee / tradeNum;
			}

			if ((allOperateFee + allNetPor) != 0) {
				// 费用占比
				feePrecent = decimalFormatePrecent
						.format((Math.abs(allOperateFee) / (Math.abs(allOperateFee) + Math.abs(allNetPor))));
			} else {
				feePrecent = decimalFormatePrecent.format(0.00);
			}
		} else {
			// 当账户为空值时,显示默认值
			profit_Loss = "0:0";// 盈亏比
			winPrecent = "0"; // 胜算
			feePrecent = "0"; // 费用占比
			maxProfitPrecent = "0"; // 最大盈利
			maxFailPrecent = "0"; // 最大亏损
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", tradeNum + ""); // 交易笔数
		map.put("c", tradeCycle + ""); // 交易周期
		map.put("p", winNum + ""); // 盈利笔数
		map.put("d", failNum + ""); // 亏损笔数
		map.put("pr", winPrecent); // 胜算
		map.put("pdr", profit_Loss); // 盈亏比
		map.put("acp", decimalFromate.format(allOperateFee));// 累计手续费
		map.put("anp", decimalFromate.format(allNetPor)); // 累计净利润
		map.put("ap", decimalFromate.format(avgPorfit)); // 平均每笔盈利
		map.put("ad", decimalFromate.format(Math.abs(avgFail))); // 平均每笔亏损
		map.put("ae", decimalFromate.format(avgFee));// 平均每笔费用
		map.put("ep", feePrecent); // 费用占比
		map.put("mda", maxFailNum + ""); // 最大连续亏损次数
		map.put("mpa", maxProFitNum + ""); // 最大连续盈利次数
		map.put("mpr", maxProfitPrecent);// 最大盈利率
		map.put("mdr", maxFailPrecent); // 最大亏损率

		return mapper.writeValueAsString(map);
	}

	/**
	 * 周期盈亏
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 * @author mengpeitong
	 */
	@RequestMapping(value = "/profit")
	public @ResponseBody String getDate(HttpSession session, HttpServletRequest request) throws Exception {
		// 用来接收获取到的盈亏信息
		List<TradeFieldBean> weekList = new ArrayList<TradeFieldBean>();
		List<TradeFieldBean> monthList = new ArrayList<TradeFieldBean>();
		// 用来封装返回值
		List<String> wklist = new ArrayList<String>();
		List<String> mList = new ArrayList<String>();
		// 获取前端页面传入的账号
		String[] account = getAccounts(request);
		// 如果传入的账户为空，按照用户id或默认账号进行统计
		if (account != null) {
			List<String> acList = Arrays.asList(account);
			monthList = stas.getCloseProfit(acList);
			weekList = stas.selectWeekProfitField(acList);
			// 每周盈亏
			for (int j = 0; j < weekList.size(); j++) {
				// 周数
				String week = weekList.get(j).getWeek();
				// 盈亏值
				Double weekAccount = weekList.get(j).getWeek_account();
				wklist.add("{\"weekcount\":\"" + week + "\"");
				wklist.add("\"weekvalue\":\"" + weekAccount + "\"}");
			}
			// 每月盈亏
			for (int j = 0; j < monthList.size(); j++) {
				// 月份
				String date = monthList.get(j).getTd();
				// 值
				Double value = monthList.get(j).getSum();
				mList.add("{\"monthcount\":\"" + date.substring(0, 4) + "-" + date.substring(4, 6) + "\"");
				mList.add("\"monthvalue\":\"" + value + "\"}");
			}
		}
		return "{\"week\":" + wklist.toString() + ",\"month\":" + mList.toString() + "}";
	}

	/**
	 * 持仓偏好
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @author mengpeitong
	 */
	@RequestMapping(value = "/prefer")
	public @ResponseBody String getDate(HttpServletRequest request, HttpSession session) throws Exception {
		// 获取传入的账户集合
		String[] account = getAccounts(request);
		// 创建持仓集合
		List<PositionDetailBean> list = null;
		// JSON转换器
		ObjectMapper mapper = new ObjectMapper();
		// 获取productColor.properties文件地址
		String URL = ResourceUtils.getFile("classpath:productColor.properties").getPath();
		String color = "";
		// 获取配置文件中的品种颜色
		ProductColorUtils pcu = ProductColorUtils.getInstance(URL);

		List<PositionPreferBean> maplist = new ArrayList<PositionPreferBean>();
		// 如果有选择账户，则进行多账号查询
		if (account != null) {
			List<String> list_str = Arrays.asList(account);
			list = stas.selectPositionPrefer(list_str, list_str);
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).getTradeSum() != 0) {
					PositionPreferBean ppf = new PositionPreferBean();
					ppf.setName(list.get(j).getProductID());
					ppf.setValue(list.get(j).getTradeSum());
					color = pcu.getProductColor(list.get(j).getProductID());
					ppf.setColor(color);
					maplist.add(ppf);
				}
			}
		}
		return mapper.writeValueAsString(maplist);
	}

	/**
	 * 成交偏好最新改动
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tradeprefer")
	public @ResponseBody String getTradePrefer(HttpServletRequest request, HttpSession session) throws Exception {
		// 获取传入的账户集合
		String[] account = getAccounts(request);
		// 账户处理
		try {
			if (account == null) {
				return "{\"name\":\"0\",\"value\":\"0\",\"color\":\"0\"}";
			} else {
				account = getAccounts(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 将账户数组转化为列表
		List<String> list_str = Arrays.asList(account);
		// 创建成交列表
		List<InstrumentFieldBean> tradeList = null;
		tradeList = tps.selectVarTradeField(list_str);
		// 用来返回JSON数据的trademap集合
		Map<String, Double> trademap = new HashMap<String, Double>();

		// 循环遍历
		for (InstrumentFieldBean obj : tradeList) {
			trademap.put(obj.getProductID(), obj.getTradeSum());
		}
		ObjectMapper mapper = new ObjectMapper();
		String URL = ResourceUtils.getFile("classpath:productColor.properties").getPath();

		List<PositionPreferBean> maplist = new ArrayList<PositionPreferBean>();
		String color = "";
		// 获取配置文件中的品种颜色
		ProductColorUtils pcu = ProductColorUtils.getInstance(URL);

		for (Entry<String, Double> entry : trademap.entrySet()) {
			PositionPreferBean ppf = new PositionPreferBean();
			ppf.setName(entry.getKey());
			ppf.setValue(entry.getValue());
			color = pcu.getProductColor(entry.getKey());
			ppf.setColor(color);
			maplist.add(ppf);
		}
		return mapper.writeValueAsString(maplist);

	}

	/**
	 * 多空盈亏
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @author suimanman
	 */
	@RequestMapping(value = "/dk", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getDKProfit(HttpServletRequest request, HttpSession session) throws Exception {
		DecimalFormat df = new DecimalFormat("0.00");
		List<TradeFieldBean> list = null;

		String name1 = "多单盈利";
		String name2 = "多单亏损";
		String name3 = "空单盈利";
		String name4 = "空单亏损";

		Double d1 = 0.0; // 多单盈利
		Double d2 = 0.0; // 多单亏损
		Double k1 = 0.0; // 空单盈利
		Double k2 = 0.0; // 空单亏损

		String s = "[";
		// 获取传入的账户集合
		String[] account = getAccounts(request);

		if (null != account) {
			List<String> acList = Arrays.asList(account);
			list = stas.queryAll(acList);

			for (int i = 0; i < list.size(); i++) { // 判断是否为空单盈利
				if (!list.get(i).getOffSet_flag().equals("0")) {
					if (list.get(i).getDirection().equals("0")) {
						if (list.get(i).getProfit() > 0) {
							k1 += list.get(i).getProfit();
						} else if (list.get(i).getProfit() < 0) {// 盈利小于0，空单亏损
							k2 += list.get(i).getProfit();
						}
					} else if (list.get(i).getDirection().equals("1")) {
						if (list.get(i).getProfit() > 0) { // 多单盈利
							d1 += list.get(i).getProfit();
						} else if (list.get(i).getProfit() < 0) { // 多单亏损
							d2 += list.get(i).getProfit();
						}
					}
				}
			}
		}
		if (d1 == 0 && d2 == 0 && k1 == 0 && k2 == 0) {
			s = "";
		} else {
			s = "[[{" + "\"value\":" + df.format(Math.abs(d1)) + "," + "\"name\":\"" + name1 + "\"}," + "{\"value\":"
					+ df.format(Math.abs(k1)) + "," + "\"name\":\"" + name3 + "\"}," + "{\"value\":"
					+ df.format(Math.abs(d1)) + "," + "\"name\":\"" + name1 + "\"}," + "{\"value\":"
					+ df.format(Math.abs(d2)) + "," + "\"name\":\"" + name2 + "\"}]," + "[{\"value\":"
					+ df.format(Math.abs(d2)) + "," + "\"name\":\"" + name2 + "\"}," + "{\"value\":"
					+ df.format(Math.abs(k2)) + "," + "\"name\":\"" + name4 + "\"}," + "{\"value\":"
					+ df.format(Math.abs(k1)) + "," + "\"name\":\"" + name3 + "\"}," + "{\"value\":"
					+ df.format(Math.abs(k2)) + "," + "\"name\":\"" + name4 + "\"}]]";
		}
		return s;
	}

	/**
	 * 持仓表统计信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @author suimanman
	 */
	@RequestMapping(value = "/pmsg")
	public @ResponseBody String getPostionMessage(int page, int limit, String beginTime, String endTime,
			HttpSession session, HttpServletRequest request) throws Exception {
		String bT = null;
		String eT = null;
		Integer counts = 0;
		String day = "7";// 默认查询7天数据
		if (beginTime != null && beginTime != "") {
			bT = beginTime.replaceAll("-", "");
			day = null;
		} else {
			bT = "";
		}
		if (endTime != null && endTime != "") {
			eT = endTime.replaceAll("-", "");
			day = null;
		} else {
			eT = GetNowDate.getNowDate();
		}
		DecimalFormat df = new DecimalFormat("0.00");
		List<PositionDetailBean> posList = null;

		String id = null;

		String date = null;
		String cid = null;
		String insid = null;
		String dir = null;
		Double price = 0.0;
		int pos = 0;
		int ypos = 0;
		int tpos = 0;
		int nz = 0;
		Double pct = 0.0;
		Double oct = 0.0;
		Double act = 0.0;
		int vm = 0;
		String hde = null;
		int cv = 0;
		Double cp = 0.0;
		Double op = 0.0;
		Double pp = 0.0;
		Double cs = 0.0;
		Double mg = 0.0;
		Timestamp ut = null;
		Timestamp it = null;
		Double cpt = 0.0;
		Double lsp = 0.0;

		// 获取传入的账户集合
		String[] account = getAccounts(request);
		String s = "";
		if (null != account) {
			List<String> list = Arrays.asList(account);
			List<String> idList = null;
			try {
				id = (String) session.getAttribute("uid");
				idList = Arrays.asList(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (id != null) {
				posList = stas.getPositionMessage(day, idList, bT, eT, page, limit);
				counts = stas.getPositionMessageCounts(day, idList, bT, eT);
			} else {
				posList = stas.getPositionMessage(day, list, bT, eT, page, limit);
				counts = stas.getPositionMessageCounts(day, list, bT, eT);
			}

			s = "{\"code\":0,\"msg\":\"\",\"count\":" + counts + ",\"data\":[";
			if (posList.size() != 0) {
				for (int i = 0; i < posList.size(); i++) {
					date = posList.get(i).getTrade_day(); // 交易起日
					cid = posList.get(i).getCtp_id();
					insid = posList.get(i).getInstrument_id(); //
					dir = posList.get(i).getDirection();
					price = posList.get(i).getPrice();
					pos = posList.get(i).getPosition();
					ypos = posList.get(i).getYd_position();
					tpos = posList.get(i).getTd_position();
					nz = posList.get(i).getNo_frozen();
					pct = posList.get(i).getPosition_cost();
					oct = posList.get(i).getOpen_cost();
					act = posList.get(i).getAvg_cost();
					vm = posList.get(i).getVolumemultiple();
					hde = posList.get(i).getHedge();
					cv = posList.get(i).getClose_volume();
					cp = posList.get(i).getClose_profit();
					op = posList.get(i).getOpen_profit();
					pp = posList.get(i).getPosition_profit();
					cs = posList.get(i).getCommission();
					mg = posList.get(i).getMargin();
					ut = posList.get(i).getUpdatetime();
					it = posList.get(i).getInserttime();
					cpt = posList.get(i).getCloseprofit_bytrade();
					lsp = posList.get(i).getLast_settlementprice();

					if (i < posList.size() - 1) {
						s += "{\"date\":\"" + date + "\",\"cid\":\"" + cid + "\",\"insid\":\"" + insid + "\",\"dir\":\""
								+ dir + "\",\"price\":\"" + df.format(price) +"\",\"pos\":" + pos + ",\"ypos\":" + ypos
								+ ",\"tpos\":" + tpos + ",\"nz\":" + nz + ",\"pct\":\"" + df.format(pct) + "\",\"oct\":\""
								+ df.format(oct) + "\",\"act\":\"" + df.format(act) + "\",\"vm\":" + vm + ",\"hde\":\"" + hde
								+ "\",\"cv\":" + cv + ",\"cp\":\"" + df.format(cp) + "\",\"op\":\"" + df.format(op)
								+ "\",\"pp\":\"" + df.format(pp) + "\",\"cs\":\"" + df.format(cs) + "\",\"mg\":\"" + df.format(mg)
								+ "\",\"ut\":\"" + ut + "\",\"it\":\"" + it + "\",\"cpt\":\"" + df.format(cpt) + "\",\"lsp\":\""
								+ df.format(lsp) + "\"},";
					} else {
						s += "{\"date\":\"" + date + "\",\"cid\":\"" + cid + "\",\"insid\":\"" + insid + "\",\"dir\":\""
								+ dir + "\",\"price\":\""+ df.format(price) +"\",\"pos\":" + pos + ",\"ypos\":" + ypos
								+ ",\"tpos\":" + tpos + ",\"nz\":" + nz + ",\"pct\":\"" + df.format(pct) + "\",\"oct\":\""
								+ df.format(oct) + "\",\"act\":\"" + df.format(act) + "\",\"vm\":" + vm + ",\"hde\":\"" + hde
								+ "\",\"cv\":" + cv + ",\"cp\":\"" + df.format(cp) + "\",\"op\":\"" + df.format(op)
								+ "\",\"pp\":\"" + df.format(pp) + "\",\"cs\":\"" + df.format(cs) + "\",\"mg\":\"" + df.format(mg)
								+ "\",\"ut\":\"" + ut + "\",\"it\":\"" + it + "\",\"cpt\":\"" + df.format(cpt) + "\",\"lsp\":\""
								+ df.format(lsp) + "\"}]}";
					}
				}
			} else {
				s += "]}";
			}
		}
		return s;
	}

	/**
	 * 仓位信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/spaceinfo")
	public @ResponseBody String getSpace(HttpServletRequest request) {
		// 获取账户信息
		String[] account = getAccounts(request);
		// 账户处理
		try {
			if (account == null) {
				return "{\"date\":\"0\",\"space\":\"0\"}";
			} else {
				account = getAccounts(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 将账户数组转化为列表
		List<String> List_str = Arrays.asList(account);
		// 创建相应列表
		List<TradingAccountFiledBean> list = null;
		List<PositionDetailBean> lpbp = null;
		// 每天持仓
		lpbp = qtcs.getPosition(List_str);
		// 每天仓位
		list = tafs.listSpace(List_str);

		// 仓位MAP
		Map<String, Double> spaceMap = new TreeMap<String, Double>();
		// 持仓MAP
		Map<String, String> positionMap = new TreeMap<String, String>();

		// 循环遍历每天仓位
		for (TradingAccountFiledBean obj : list) {
			spaceMap.put(obj.getTrading_day(), obj.getSpace());
		}

		// 循环遍历每天持仓
		for (int m = 0; m < lpbp.size() - 1; m++) {
			if (lpbp.get(m).getTrading_day().equals(lpbp.get(m + 1).getTrading_day())) {
				String value3 = "";
				Double value1 = lpbp.get(m).getSum_margin();
				Double value2 = lpbp.get(m + 1).getSum_margin();
				String date1 = lpbp.get(m).getTrading_day().toString();
				m = m + 1;
				boolean contains = positionMap.containsKey(date1); // 通过containsKey判断是否有需要的键值
				if (contains) {
					value1 = value1 + Double
							.parseDouble(positionMap.get(date1).substring(0, positionMap.get(date1).indexOf("-")));
					value2 = value2 + Double.parseDouble(positionMap.get(date1)
							.substring(positionMap.get(date1).indexOf("-") + 1, positionMap.get(date1).length()));

					value3 = value1 + "" + "-" + value2 + "";
					positionMap.put(date1, value3);

				} else {
					value3 = value1 + "" + "-" + value2 + "";
					positionMap.put(date1, value3);
				}
			} else {
				if (lpbp.get(m).getDirection().equals("0")) {
					Double value1 = lpbp.get(m).getSum_margin();
					Double value2 = 0.0;
					String date1 = lpbp.get(m).getTrading_day().toString();
					boolean contains = positionMap.containsKey(date1); // 通过containsKey判断是否有需要的键值
					if (contains) {
						value1 = value1 + Double
								.parseDouble(positionMap.get(date1).substring(0, positionMap.get(date1).indexOf("-")));
						value2 = value2 + Double.parseDouble(positionMap.get(date1)
								.substring(positionMap.get(date1).indexOf("-") + 1, positionMap.get(date1).length()));
						String value3 = "";
						value3 = value1 + "" + "-" + value2 + "";
						positionMap.put(date1, value3);
					} else {
						String value3 = "";
						value3 = value1 + "" + "-" + value2 + "";
						positionMap.put(date1, value3);
					}

				} else if (lpbp.get(m).getDirection().equals("1")) {
					Double value2 = lpbp.get(m).getSum_margin();
					Double value1 = 0.0;
					String date1 = lpbp.get(m).getTrading_day().toString();
					boolean contains = positionMap.containsKey(date1); // 通过containsKey判断是否有需要的键值
					if (contains) {
						value1 = value1 + Double
								.parseDouble(positionMap.get(date1).substring(0, positionMap.get(date1).indexOf("-")));
						value2 = value2 + Double.parseDouble(positionMap.get(date1)
								.substring(positionMap.get(date1).indexOf("-") + 1, positionMap.get(date1).length()));
						String value3 = "";
						value3 = value1 + "" + "-" + value2 + "";
						positionMap.put(date1, value3);
					} else {
						String value3 = "";
						value3 = value1 + "" + "-" + value2 + "";
						positionMap.put(date1, value3);
					}

				}

			}

		}

		List<String> s1 = new ArrayList<String>(); // 每天仓位
		List<String> s2 = new ArrayList<String>(); // “0”对应 多单 “1”对应空单

		for (Entry<String, Double> u : spaceMap.entrySet()) {
			s1.add("{\"date\":\"" + u.getKey() + "\"");
			s1.add("\"space\":\"" + u.getValue() + "\"}");
		}

		for (Entry<String, String> v : positionMap.entrySet()) {
			s2.add("{\"date1\":\"" + v.getKey() + "\"");
			s2.add("\"value1\":\"" + v.getValue().substring(0, v.getValue().indexOf("-")) + "\"");
			s2.add("\"value2\":\"" + v.getValue().substring(v.getValue().indexOf("-") + 1, v.getValue().length())
					+ "\"}");
		}

		return "{\"s1\":" + s1.toString() + ",\"s2\":" + s2.toString() + "}";
	}

	/**
	 * 获取账户信息
	 * 
	 * @param request
	 * @return String[]
	 */
	public String[] getAccounts(HttpServletRequest request) {
		String[] account = null;
		try {
			account = request.getParameterValues("account[]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
	}

	/**
	 * 日结数据查询
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "/row")
	public @ResponseBody String getData(int page, int limit, String beginTime, String endTime, Model model,
			HttpServletRequest request, HttpSession session) throws Exception {
		String bT = null;
		String eT = null;
		int counts = 0;
		String month = "6";// 默认6个月
		String[] ac = getAccounts(request);
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df1 = new DecimalFormat("0.0000");
		DecimalFormat df2 = new DecimalFormat("0.00%");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (beginTime != null && beginTime != "") {
			bT = beginTime.replaceAll("-", "");
			month = null;
		} else {
			Date bT1 = new Date();
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(bT1);
			rightNow.add(Calendar.YEAR, 0);
			rightNow.add(Calendar.MONTH, -6);
			rightNow.add(Calendar.DAY_OF_YEAR, 0);
			bT1 = rightNow.getTime();
			bT = sdf.format(bT1);
		}
		if (endTime != null && endTime != "") {
			eT = endTime.replaceAll("-", "");
			month = null;
		} else {
			eT = GetNowDate.getNowDate();
		}
		List<TradingAccountFiledBean> list = null;

		String date = null; // 日期
		Double preBalance = 0.0; // 静态权益
		Double accountRight = 0.0; // 客户权益
		Double deposit = 0.0; // 当日入金
		Double withdraw = 0.0; // 当日出金
		Double mortgage = 0.0; // 质押金
		Double closeProfit = 0.0; // 平仓盈亏
		Double currMargin = 0.0; // 保证金占用
		Double commission = 0.0; // 手续费
		Double available = 0.0; // 可用资金
		Double pwmc = 0.0; // 本日结存
		Double cdp = 0.0; // 风险率
		Double pp = 0.0; // 浮动盈亏
		Double adc = 0.0; // 追加保证金
		Double dcp = 0.0; // 净值
		Double captical = 0.0;// 本金

		String s = "";
		List<String> list_ac = Arrays.asList(ac);
		/* 判断是否选择了账户 */
		String s1 = "";
		if (ac != null) {
			list = tps.selectRowDataField(month, list_ac, bT, eT, page, limit);
			counts = tps.selectRowDataFieldCounts(month, list_ac, bT, eT);
			int i = 0;
			if (list.size() != 0 && counts != 0) {
				for (i = 0; i < list.size(); i++) {
					date = list.get(i).getTrading_day();
					/*
					 * if (i == list.size() - 1) { preBalance =
					 * list.get(i).getFund(); } else { preBalance =
					 * list.get(i+1).getFund(); }
					 */
					captical = list.get(i).getCapital() + list.get(i).getDeposit() - list.get(i).getWithdraw();// 本金
					accountRight = list.get(i).getFund();// 客户权益=动态权益fund
					if (i == list.size() - 1) {
						preBalance = list.get(i).getPre_Balance();
					} else {
						preBalance = list.get(i + 1).getPre_Balance();// 上日结存取prebalance
					}
					deposit = list.get(i).getDeposit();// 入金
					withdraw = list.get(i).getWithdraw();// 出金
					mortgage = list.get(i).getMortgate();// 质押金
					closeProfit = list.get(i).getClose_profit();// 平仓盈亏
					currMargin = list.get(i).getCurr_margin();// 当前保证金
					commission = list.get(i).getCommission();// 手续费
					available = list.get(i).getAvailable();// 可用资金
					pp = list.get(i).getPosition_profit();// 持仓盈亏=浮动盈亏
					// pwmc = accountRight-pp;//本日结存=客户权益-浮动盈亏
					pwmc = list.get(i).getFund();// 本日结存=本日动态权益fund
					cdp = list.get(i).getCurr_margin() / pwmc;// 风险率=保证金占用/动态权益
					dcp = list.get(i).getPre_Balance() / captical;// 净值=静态权益/本金
					s1 += "{\"date\":\"" + date + "\",\"preb\":\"" + df.format(preBalance) +"\",\"avac\":\""
							+ df.format(accountRight) + "\",\"de\":\"" + df.format(deposit) + "\",\"wd\":\""
							+ df.format(withdraw) + "\",\"mg\":\"" + df.format(mortgage) + "\",\"cp\":\""
							+ df.format(closeProfit) + "\",\"cm\":\"" + df.format(currMargin) + "\",\"cms\":\""
							+ df.format(commission) + "\",\"ava\":\"" + df.format(available) + "\",\"pwmc\":\""
							+ df.format(pwmc) + "\",\"cdp\":\"" + df2.format(cdp) + "\",\"pp\":\"" + df.format(pp) + "\",\"adc\":\""
							+ df.format(adc) + "\",\"dcp\":\"" + df1.format(dcp) + "\"},";
				}
				s = "{\"code\":0,\"msg\":\"\",\"count\":" + counts + ",\"data\":[";
				s = s + s1.substring(0, s1.length() - 1) + "]}";
			} else {
				s += "{\"code\":0,\"msg\":\"\",\"count\":0,\"data\":[]}";
			}
		}
		return s;
	}

	/**
	 * 权益信息和累计净值
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @author zhangfeng
	 */
	@RequestMapping(value = "/lative")
	public @ResponseBody String getDate(HttpServletRequest request) throws Exception {
		List<TradingAccountFiledBean> list = null;
		List<TradingAccountFiledBean> list2 = null;
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df1 = new DecimalFormat("0.0000");
		String s = "[";
		String s1 = "[";
		// String id = null;
		String[] ac = getAccounts(request);
		ArrayList<Object> list1 = new ArrayList<Object>();
		Double amount = 0.0; // 本金
		Map<String, Double> map1 = new LinkedHashMap<String, Double>();
		try {
			/*
			 * id = (String) session.getAttribute("uid"); ac = (String)
			 * session.getAttribute("ac");
			 */
			if (getAccounts(request) == null) {
				return "{\"everyCumula\":[{\"date\":\"0\", \"value\":\"0\", \"amount\":\"0\"}],\"netWorth\":[{\"tradingDay\":\"0\", \"cumulative\":\"0\"}]}";

			} else {
				ac = getAccounts(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> list_ac = Arrays.asList(ac);
		list2 = qtcs.getAll(list_ac);
		list = tps.selectCumulativeField(list_ac);
		String tradingDay = null; // 交易日期
		Double preBalance = 0.0;
		Double totalAmount = 0.0;
		/* 获取累计净值 */
		if (list != null && list2 != null) {
			for (TradingAccountFiledBean obj : list) {
				totalAmount = obj.getTotalamount() + obj.getDeposit()
						- obj.getWithdraw();// 本金=初始金额+入金-出金
				preBalance = obj.getPre_Balance();// 静态权益
				tradingDay = obj.getTrading_day().toString();
				s1 += "{\"tradingDay\":\"" + tradingDay
						+ "\",\"cumulative\":\"" + df1.format(preBalance / totalAmount) + "\"},";
			}

			/* 获取每日权益 */
			Double value = 0.0; // 每日权益值
			String date = null; // 日期
			for (TradingAccountFiledBean obj : list2) {
				date = obj.getTrading_day();// 日期
				value = obj.getFund();// 每日权益=动态权益
				date = obj.getTrading_day().toString();
				amount = obj.getCapital() + obj.getDeposit()
						- obj.getWithdraw();// 本金
				if (!map1.containsKey(date)) {
					map1.put(date, value);
				} else {
					map1.put(date, map1.get(date) + value);
				}
			}
		} else {
			s1 = "";
		}
		for (String in : map1.keySet()) {
			double d2 = map1.get(in);// 得到每个key多对用value的值
			s += "{\"tradingDay\":\"" + in + "\",\"value\":\"" + df.format(d2)
					+ "\",\"amount\":\"" + df.format(amount) + "\"},";
		}
		s = s.substring(0, s.length() - 1);
		s1 = s1.substring(0, s1.length() - 1);
		if ((s.equals("") || s == null) && (s1.equals("") || s1 == null)) {
			return "";
		}
		if ((list == null || list2.size() == 0) && s.equals("[")) {
			return "";
		} else {
			// * everyCumula:每日权益；netWorth：累计净值
			// * 将集合拼接成json格式, 将每日权益和累计净值同时返回
			return "{\"everyCumula\":" + s + "]" + ",\"netWorth\":" + s1 + "]}";
		}
	}

	/**
	 * 累计盈亏
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "/sum")
	public @ResponseBody String getSum(HttpSession session, HttpServletRequest request) {
		List<TradingAccountFiledBean> list = null;
		String[] ac = getAccounts(request);
		DecimalFormat df = new DecimalFormat("0.00");
		String s = "{\"sum\":[";
		String s1 = "";
		if (ac == null) {
			return "{\"sum\":[{\"tradeDays\":\"0\",\"sum\":\"0\",\"coms\":\"0\"}]}";
		} else {
			ac = getAccounts(request);
		}
		List<String> list_ac = Arrays.asList(ac);
		list = itaService.listSum(list_ac);
		double count = 0;
		double day_count = 0;
		String tradeDays = null;
		double sum = 0; // 静态权益-本金(初始金额+入金-出金) -累计手续费 即为累计盈亏
		double cprofit = 0;
		if (list.size() != 0) {
			for (TradingAccountFiledBean obj : list) {
				count += obj.getCommission();// 累计手续费
				day_count = obj.getCommission();// 每日手续费
				cprofit = obj.getCprofit();// 静态权益
				tradeDays = obj.getTrading_day().toString();
				sum = cprofit - day_count - obj.getCapital() - obj.getDeposit()
						+ obj.getWithdraw();// 累计盈亏		
				s1 += "{\"tradeDays\":\"" + tradeDays + "\",\"sum\":\"" + df.format(sum)
						+ "\",\"coms\":\"" + df.format(count) + "\"},";
			}
			s1 = s1.substring(0, s1.length() - 1) + "]}";
		} else {
			s = "";
			s1 = "";
		}
		return s + s1;
	}

	/**
	 * 品种盈亏
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "/ft")
	public @ResponseBody String getFTProfit(HttpServletRequest request) throws Exception {
		List<TradeFieldBean> list = null;
		List<ProductProfitBean> ppList = null;
		DecimalFormat df = new DecimalFormat("0.00");
		String[] ac = getAccounts(request);
		String s1 = "[";
		String s2 = "[";
		try {
			if (ac == null) {
				return "{\"psFt\":[{\"pid\":\"0\",\"psProfit\":\"0\"}],\"smFt\":[{\"pid\":\"0\", \"smProfit\":\"0\"}]}";
			} else {
				ac = getAccounts(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * if(id!=null){ list = qtcs.getFTProfit(id); }else{ list =
		 * qtcs.getFTProfit(ac); }
		 */

		Double sumProfit = 0.0;
		Map<String, Double> map1 = new LinkedHashMap<String, Double>();
		List<String> list_str = Arrays.asList(ac);
		//品种盈亏信息
		list = qtcs.getFTProfit(list_str);
		//品种持仓盈亏
		ppList = qtcs.getPositionProfit(list_str);
		if (list != null && ppList != null) {
		for (int i =0;i<list.size();i++){
			//profit 中存放的是手续费
				s1 += "{\"pid\":\"" +list.get(i).getProduct_id() + "\",\"psProfit\":"
						+ df.format(list.get(i).getSumc()-list.get(i).getProfit()) + "},";
				map1.put(list.get(i).getProduct_id(), list.get(i).getSumc()-list.get(i).getProfit());
			}
			Iterator<Entry<String, Double>> it1 = map1.entrySet().iterator();
			while (it1.hasNext()) {
				Entry<String, Double> entry = it1.next();
			}
			double dd = 0.0;
			for (ProductProfitBean obj : ppList) {
				if (map1.containsKey(obj.getProductid())) {
					dd = map1.get(obj.getProductid()) + obj.getPositionProfit();
					map1.put(obj.getProductid(), dd);
				} else {
					map1.put(obj.getProductid(), obj.getPositionProfit());
				}
			}
		}
		for (String in1 : map1.keySet()) {
			BigDecimal b2 = new BigDecimal(map1.get(in1));
			sumProfit = b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			s2 += "{\"pid\":\"" + in1 + "\",\"smProfit\":" + sumProfit + "},";
		}
		s1 = s1.substring(0, s1.length() - 1) + "]";
		s2 = s2.substring(0, s2.length() - 1) + "]";
		if (s1.equals("]") && s2.equals("]"))
			return "";
		else
			return "{\"psFt\":" + s1 + ",\"smFt\":" + s2 + "}";
	}

	/**
	 * 成交信息统计信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @author zouhang
	 */

	@RequestMapping(value = "/allMsg", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String getTradeMessage(int page, int limit, String beginTime, String endTime,
			HttpSession session, HttpServletRequest request) throws Exception {
		String bT = null;
		String eT = null;
		Integer counts = 0;
		String month = "6";// 默认查询6个月数据
		String[] ac = getAccounts(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 后加

		if (beginTime != null && beginTime != "") {
			bT = beginTime.replaceAll("-", "");
			month = null;
		} else {
			Date bT1 = new Date();
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(bT1);
			rightNow.add(Calendar.YEAR, 0);
			rightNow.add(Calendar.MONTH, -6);
			rightNow.add(Calendar.DAY_OF_YEAR, 0);
			bT1 = rightNow.getTime();
			bT = sdf.format(bT1);
		}
		if (endTime != null && endTime != "") {
			eT = endTime.replaceAll("-", "");
			month = null;
		} else {
			eT = GetNowDate.getNowDate();
		}

		List<TradeFieldBean> list = null;
		// String cid = null; //交易账号

		String jybh = null; // 交易编号
		String hybh = null; // 合约编号
		String jysbm = null; // 交易所编号
		String d = null; // 买卖方向
		String mmfx = null; // 买卖方向
		Double p = null; // 价格
		String kp = null; // 开平标识
		String tb = null; // 投保
		String jyt = null; // 交易时间
		String jyd = null; // 交易日期
		String oid = null; // 委托订单
		String sid = null; // 系统编号
		String jydt = null; // 交易日期时间
		String ut = null; // 数据更新时间
		Integer v = null; // 手数
		String kccj = null; // 开仓成交号
		Double yk = null; // 成交号
		String s = "";

		List<String> list_ac = Arrays.asList(ac);
		// 判断是否选择账户
		list = tps.selectTradefield(month, list_ac, bT, eT, page, limit);
		counts = tps.selectTradefieldCounts(month, list_ac, bT, eT);

		s = "{\"code\":0,\"msg\":\"\",\"count\":" + counts + ",\"data\":[";
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {

				jybh = list.get(i).getTrade_id();
				hybh = list.get(i).getInstrument_id();
				jysbm = list.get(i).getExchange_id();
				d = list.get(i).getDirection();
				mmfx = null;
				if (d != null && d.equals("0")) {
					mmfx = "买";
				} else if (d != null && d.equals("1")) {
					mmfx = "卖";
				}
				p = list.get(i).getPrice();
				kp = list.get(i).getOffSet_flag();
				tb = list.get(i).getHedge();
				jyt = list.get(i).getTradeTime();
				jyd = list.get(i).getTradingDay();
				oid = list.get(i).getOrder_id();
				sid = list.get(i).getSys_id();
				jydt = list.get(i).getTradeDateTime();
				ut = list.get(i).getUpdateTime();
				v = list.get(i).getVolumn();
				kccj = list.get(i).getOpenTride_id();
				yk = list.get(i).getCloseProfit();

				if (i < list.size() - 1) {
					s += "{\"jybh\":\"" + jybh + "\",\"hybh\":\"" + hybh + "\",\"jysbm\":\"" + jysbm + "\",\"mmfx\":\""
							+ mmfx + "\",\"p\":" + p + ",\"kp\":\"" + kp + "\",\"tb\":\"" + tb + "\",\"jyt\":\"" + jyt
							+ "\",\"jyd\":\"" + jyd + "\",\"oid\":\"" + oid + "\",\"sid\":\"" + sid + "\",\"jydt\":\""
							+ jydt + "\",\"ut\":\"" + ut + "\",\"v\":" + v + ",\"kccj\":\"" + kccj + "\",\"yk\":" + yk
							+ "},";
				} else {
					s += "{\"jybh\":\"" + jybh + "\",\"hybh\":\"" + hybh + "\",\"jysbm\":\"" + jysbm + "\",\"mmfx\":\""
							+ mmfx + "\",\"p\":" + p + ",\"kp\":\"" + kp + "\",\"tb\":\"" + tb + "\",\"jyt\":\"" + jyt
							+ "\",\"jyd\":\"" + jyd + "\",\"oid\":\"" + oid + "\",\"sid\":\"" + sid + "\",\"jydt\":\""
							+ jydt + "\",\"ut\":\"" + ut + "\",\"v\":" + v + ",\"kccj\":\"" + kccj + "\",\"yk\":" + yk
							+ "}]}";
				}

			}
		} else {
			s += "]}";
		}
		return s;
	}

}
