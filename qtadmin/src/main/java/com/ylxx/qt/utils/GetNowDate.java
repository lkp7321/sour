package com.ylxx.qt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetNowDate {
	public static String getNowDate() {
		Date d =new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String s=sdf.format(d);
		return s;
	}
}
