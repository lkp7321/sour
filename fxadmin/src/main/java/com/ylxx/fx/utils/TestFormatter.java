package com.ylxx.fx.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFormatter {
	private static final Logger log = LoggerFactory.getLogger(TestFormatter.class);
	/*
	 * 格式化
	 * 124.233.222.12
	 */
	public  String getFormatter(String str) {
		NumberFormat n = NumberFormat.getNumberInstance();
		double d;
		String outStr = null;
		try {
			d = Double.parseDouble(str);
			outStr = n.format(d);
		} catch (Exception e) {
			 log.error(e.getMessage(),e);
		}
		return outStr;
	}

	/*
	 * 按格式化输出
	 * 固定小数点
	 */
	public  String getDecimalFormat(String str) {
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00000");
		String outStr = null;
		double d;
		try {
			d = Double.parseDouble(str);
			outStr = fmt.format(d);
		} catch (Exception e) {
		}
		return outStr;
	}

	//
	/*
	 * 将数据按金额格式化
	 * $12.342.234.45
	 */
	public  String getCurrency(String str) {
		NumberFormat n = NumberFormat.getCurrencyInstance();
		double d;
		String outStr = null;
		try {
			d = Double.parseDouble(str);
			outStr = n.format(d);
		} catch (Exception e) {
			 log.error(e.getMessage(),e);
		}
		return outStr;
	}

	/*
	 * 价格有用到
	 * 价格有效位数
	 */
	public  String getDecimalFormat(String str, int numid) {

		DecimalFormat fmt = new DecimalFormat("####0.0000");
		if (numid == 2) {
			fmt = new DecimalFormat("####0.00");
		}else if (numid == 3) {
			fmt = new DecimalFormat("####0.000");
		}else if (numid == 4) {
			fmt = new DecimalFormat("####0.0000");
		}else if (numid == 0) {
			fmt = new DecimalFormat("####0");
		}else if (numid == 6){
			fmt = new DecimalFormat("####0.000000");
		}
		String outStr = null;
		double d;
		try 
		{
			d = Double.parseDouble(str);
			outStr = fmt.format(d);
			
		} catch (Exception e)
		{
		}
		return outStr;
	}

	/*
	 * 
	 * 价格干预
	 * int1 干预点差
	 * int2 有效位数
	 */
	public  float douFloat(Double dou1, int int1, int int2) {
		float f = (float) (dou1 + (int1 / Math.pow(10, int2) * 1.0));
		BigDecimal b = new BigDecimal(f);
		float f1 = b.setScale(int2, BigDecimal.ROUND_HALF_UP).floatValue();
		return f1;
	}

}
