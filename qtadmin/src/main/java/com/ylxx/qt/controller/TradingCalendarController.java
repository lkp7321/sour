package com.ylxx.qt.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.qt.service.TradingCalendarService;
import com.ylxx.qt.service.po.TradingCalendarBean;
import com.ylxx.qt.service.po.TradingCalendarList;

@Controller
@RequestMapping("/calendar")
public class TradingCalendarController {

	@Resource
	private TradingCalendarService tcs;
	
	@RequestMapping(value = "/inserttradcalendar",produces="application/json;charset=UTF-8")
	public @ResponseBody String insertTradeCalendar(@RequestBody TradingCalendarList tcl,HttpServletRequest request)throws Exception{
		String January="";
		String February="";
		String March="";
		String April="";
		String May="";
		String June="";
		String July="";
		String August="";
		String September="";
		String October="";
		String November="";
		String December="";
		List<TradingCalendarBean> tcbList = new ArrayList<TradingCalendarBean>(); 
		List<String> nonTradTime = tcl.getList();
		Calendar cal=Calendar.getInstance();
		Integer year_Int = Integer.parseInt(String.valueOf(cal.get(Calendar.YEAR)))+1;//默认下一年
		String year = year_Int.toString();
		if(nonTradTime.size()>0){
			year = nonTradTime.get(0).substring(2, 6);
		}
		Date date = new Date();
    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
 	    String date_str = dateFormat.format(date);//时间
 	    
 	    Date start = dateFormat.parse(year+"-01-01") ;
 	    Date end = dateFormat.parse(year+"-12-31");
	 	Long spi = end.getTime() - start.getTime();
	 	Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数
	 	List<Date> dateList = new ArrayList<Date>();
	 	dateList.add(start);
	 	for (int i = 1; i <= step; i++) {
	 	      dateList.add(new Date(dateList.get(i - 1).getTime()
	 	              + (24 * 60 * 60 * 1000)));// 比上一天加一
	 	}
	 	for(int i=0;i<dateList.size();i++){
	 		String dayStr = dateFormat.format(dateList.get(i)).substring(5, 7);//月份
	 		Date day = dateList.get(i);
	 		String days = dateFormat.format(dateList.get(i));
	 		cal.setTime(day);
	 		boolean flag = true;
	 		if(dayStr.equals("01")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				January += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						January += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					January += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("02")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				February += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						February += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					February += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("03")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				March += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						March += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					March += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("04")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				April += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						April += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					April += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("05")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				May += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						May += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					May += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("06")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				June += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						June += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					June += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("07")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				July += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						July += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					July += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("08")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				August += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						August += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					August += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("09")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				September += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						September += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					September += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("10")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				October += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						October += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					October += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("11")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				November += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						November += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					November += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 		if(dayStr.equals("12")){
	 			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	 				December += "H";//节假日
	 			}else{
	 				for (String holdate : nonTradTime) {
	 					if(days.equals(holdate.substring(2, 12))){
	 						December += "H";//节假日
	 						flag = false;
	 						break;
	 					}
	 				}
	 				if(flag){
	 					December += "W";//工作日
	 				}
	 			}
	 			continue;
	 		}
	 	}
	 	for(int i=1;i<=12;i++){
	 		TradingCalendarBean tb = new TradingCalendarBean();
	 		tb.setYear(Integer.parseInt(year));
	 		tb.setMonth(i);
	 		if(i==1){
	 			tb.setMark(January);
	 		}
	 		if(i==2){
	 			tb.setMark(February);
	 		}
	 		if(i==3){
	 			tb.setMark(March);
	 		}
	 		if(i==4){
	 			tb.setMark(April);
	 		}
	 		if(i==5){
	 			tb.setMark(May);
	 		}
	 		if(i==6){
	 			tb.setMark(June);
	 		}
	 		if(i==7){
	 			tb.setMark(July);
	 		}
	 		if(i==8){
	 			tb.setMark(August);
	 		}
	 		if(i==9){
	 			tb.setMark(September);
	 		}
	 		if(i==10){
	 			tb.setMark(October);
	 		}
	 		if(i==11){
	 			tb.setMark(November);
	 		}
	 		if(i==12){
	 			tb.setMark(December);
	 		}
	 		tcbList.add(tb);
	 	}
	 	for (TradingCalendarBean aaa : tcbList) {
			System.out.println(aaa.toString());
		}
	 	if(! tcs.selectTradCalendar(year).isEmpty()){
	 		tcs.deleteTradCalendar(year);
	 	}
	 	tcs.insertTradCalendar(tcbList);
		return null;
	}
}
