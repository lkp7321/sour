package com.ylxx.fx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeClass {
	/**
	 * 日期时间
	 * @return 当前日期时间
	 */
	public static String getCurDateTime() {
		Date currentDate = new Date();
		SimpleDateFormat formatdate   = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String curDate = formatdate.format(currentDate);
		return curDate;
	}
	
	public static String getFormatCurDateTime() {
		Date currentDate = new Date();
		SimpleDateFormat formatdate   = new SimpleDateFormat("yyyyMMddHHmmss");
		String curDate = formatdate.format(currentDate);
		return curDate;
	}
		  
	public static String getCurTime() {
		Date currentDate = new Date();
		SimpleDateFormat formatdate   = new SimpleDateFormat("HH:mm:ss");
		String curTime = formatdate.format(currentDate);
		return curTime;
	}
	public static String getNowDay(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	public static void main(String []args){
		
	}
}

