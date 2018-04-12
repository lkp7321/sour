package com.ylxx.qt.controller;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.qt.service.IQTCountService;
import com.ylxx.qt.service.ITradingAccountFieldService;
import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;

@Controller
@RequestMapping("/vv")
public class TradingAccountFieldController {

	@Resource
	private ITradingAccountFieldService itaService;	
	@Resource
	private IQTCountService qtcs;

	/*@RequestMapping(value = "sum")
	public @ResponseBody
	String getSum(HttpSession session,HttpServletRequest request) {
		List<TradingAccountFiledBean> list = null;
		String[] ac = new String[20];
		String s = "{\"sum\":[";
		String s1 = "";
		Map<String,Double> map1 = new TreeMap<String,Double>();
		Map<String,Double> map2 = new TreeMap<String,Double>();
		if(request.getParameterValues("account[]")==null){
			return "{\"sum\":[{\"tradeDays\":\"0\",\"sum\":\"0\",\"coms\":\"0\"}]}";
		}
		else{
			ac = request.getParameterValues("account[]");
		}		
		

		for (int i = 0; i < ac.length; i++) {
			double count = 0;
			String tradeDays = null;
			double sum = 0;										//静态权益--本金 -累计手续费即为累计盈亏
			double cprofit = 0;
			list = itaService.listSum(ac[i]);
			for (int j = 0; j < list.size(); j++) {
				count += list.get(j).getCommission();//累计手续费
				cprofit = list.get(j).getCprofit();//静态权益
				tradeDays = list.get(j).getTrading_day().toString();
				sum = cprofit - count - list.get(j).getCapital();//累计盈亏
				if(!map1.containsKey(tradeDays)){
					map1.put(tradeDays, sum);
					map2.put(tradeDays, count);
					//s1 += "{\"tradeDays\":\""+tradeDays+"\",\"sum\":\""+sum+"\",\"coms\":\""+count+"\"},";
				}else{
					map1.put(tradeDays, sum+map1.get(tradeDays));
					map2.put(tradeDays, count+map2.get(tradeDays));
					//s1 += "{\"tradeDays\":\""+tradeDays+"\",\"sum\":\""+sum+map1.get(tradeDays)+"\",\"coms\":\""+count+"\"},";
				}				
			}
		}		
		
		for (String in : map1.keySet()) {
			Double sumValue = map1.get(in);
			Double comsCount = 0.0;
			comsCount = map2.get(in);
			s1 += "{\"tradeDays\":\""+in+"\",\"sum\":\""+sumValue+"\",\"coms\":\""+comsCount+"\"},";
		}
		
		s1 = s1.substring(0, s1.length()-1)+"]}";
		return s+s1;
		List<TradingAccountFiledBean> list = null;
		String id = null;
	    String ac = null;
		try {
			id = (String) session.getAttribute("uid");
			ac = (String) session.getAttribute("ac");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(id!=null){
			list = itaService.listSum(id);
		}else{
			list = itaService.listSum(ac);
		}
		ArrayList list1 = new ArrayList();
		double count = 0;
		String tradedays = null;
		double sum = 0;
		double cprofit = 0;
		for (int i = 0; i < list.size(); i++) {
			count += list.get(i).getCommission();
			cprofit = list.get(i).getCprofit();
			sum = cprofit + count - list.get(i).getCapital();
			tradedays = list.get(i).getTrading_day().toString();

			list1.add("['" + tradedays + "'");
			list1.add("'" + sum + "'");
			list1.add("'" + count + "']");
		}
		
		if(list1==null||list1.size()==0){
			return "";
		}else{
			return list1.toString();
		}
		
	}*/

	@RequestMapping(value = "space")
	public @ResponseBody
	String getSpace(HttpSession session) {
		List<TradingAccountFiledBean> list = null;
		List<PositionDetailBean> lpbp = null;
		String id = null;
	    String ac = null;
		try {
			id = (String) session.getAttribute("uid");
			ac = (String) session.getAttribute("ac");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(id!=null){
	/*		lpbp = qtcs.getPosition(id);
			list = itaService.listSpace(id);*/
		}else{
	/*		lpbp = qtcs.getPosition(ac);
			list = itaService.listSpace(ac);*/
		}
		String date = null;
		double space = 0.00;
		String s = "[";
		for (int i = 0; i < list.size(); i++) {
			date = list.get(i).getTrading_day().toString();
			space = list.get(i).getSpace();
			if (i < list.size() - 1) {
//				s += "['" + date + "'," + space +"],";
				s +=  "{\"date\":\"" + date +"\",\"space\":"+space+"},";
			} else {
//				s += "['" + date + "'," + space +"]]";
				s +=  "{\"date\":\"" + date +"\",\"space\":"+space+"}]";
			}
		}

		
		String date1 = null;
		Double value1 = 0.0;
		Double value2 = 0.0;
		String date0 = null;
		Double value10 = 0.0;
		Double value20 = 0.0;
		String s1 = "[";
		
		for(int i=0;i<lpbp.size()-1;i++){
			if(lpbp.get(i).getTrading_day().equals(lpbp.get(i+1).getTrading_day())){
				value1 = lpbp.get(i).getSum_margin();
				value2 = lpbp.get(i+1).getSum_margin();
				date1 = lpbp.get(i).getTrading_day();
				i = i+1;
			}else {
				if(lpbp.get(i).getDirection().equals("0")){
					value1 = lpbp.get(i).getSum_margin();
					value2 = 0.0;
					date1 = lpbp.get(i).getTrading_day();
				}else if(lpbp.get(i).getDirection().equals("1")){
					value2 = lpbp.get(i).getSum_margin();
					value1 = 0.0;
					date1 = lpbp.get(i).getTrading_day();
				}
			}
			
			if(lpbp.get(lpbp.size()-2).getTrading_day().equals(lpbp.get(lpbp.size()-1).getTrading_day())){
				if(i<lpbp.size()-2){
//					s1 += "["+"'"+date1+"'"+",'"+value1+"','"+value2+"'],";
					s1 +=  "{\"date1\":\"" + date1 +"\",\"value1\":\""+value1+"\",\"value2\":"+value2+"},";
				}else{
//					s1 += "["+"'"+date1+"'"+",'"+value1+"','"+value2+"']]";
					s1 +=  "{\"date1\":\"" + date1 +"\",\"value1\":\""+value1+"\",\"value2\":"+value2+"}]";
				}
			}else{
//				s1 += "["+"'"+date1+"'"+",'"+value1+"','"+value2+"'],";
				s1 +=  "{\"date1\":\"" + date1 +"\",\"value1\":\""+value1+"\",\"value2\":"+value2+"},";
				
			}
			
		}
		if(lpbp.size()>1){
			if(!lpbp.get(lpbp.size()-2).getTrading_day().equals(lpbp.get(lpbp.size()-1).getTrading_day())){
				if(lpbp.get(lpbp.size()-1).getDirection().equals("0")){
					value10 = lpbp.get(lpbp.size()-1).getSum_margin();
					value20 = 0.0;
					date0 = lpbp.get(lpbp.size()-1).getTrading_day();
				}else if(lpbp.get(lpbp.size()-1).getDirection().equals("1")){
					value20 = lpbp.get(lpbp.size()-1).getSum_margin();
					value10 = 0.0;
					date0 = lpbp.get(lpbp.size()-1).getTrading_day();
				}
//				s1 += "["+"'"+date0+"'"+",'"+value10+"','"+value20+"']]";
				s1 +=  "{\"date1\":\"" + date0 +"\",\"value1\":\""+value10+"\",\"value2\":"+value20+"}]";
			}
		}
		
//		if(s.equals("[")&&s1.equals("[")){
		if(s.equals("{")&&s1.equals("{")){
			return "";
		}else{
//			return "["+s+","+s1+"]";
			return "{\"s\":" + s.toString() +",\"s1\":"+ s1.toString() +"}";
		}
	}
}

class MyComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		String id1 = (String) o1;
		String id2 = (String) o2;
		return id2.compareTo(id1);
	}
}
