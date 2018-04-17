package com.ylxx.fx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ReadStringCodeConfig {
	private  String stringCode = "";
	private static Properties p = new Properties();
	private static InputStream in;
	private static Logger log = LoggerFactory.getLogger(ReadStringCodeConfig.class);
	
	public  String getStringCode() {
		return stringCode;
	}
	static {
		try {
			in =  ReadStringCodeConfig.class.getClassLoader().getResourceAsStream(getAPP_HOME() + "props/stringcode.properties");
			p.load(in);
		} catch (IOException e) {
			 log.error(e.getMessage(),e);
		}
	}
	/**
	 * 读取配置文件中的字符串编码信息设置
	 */
	public void readStringCodeProperties(String strcode){
	
		stringCode = p.getProperty(strcode);
	}
	public static String getAPP_HOME() {

		String appHomePath = "";
		String path = "";
		path = System.getProperty("APP_HOME");
		if (path != null) {
			return path;
		} else
			return appHomePath;

	}
}
