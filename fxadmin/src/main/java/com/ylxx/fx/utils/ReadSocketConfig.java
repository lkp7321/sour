package com.ylxx.fx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadSocketConfig {
	private static Logger log = LoggerFactory.getLogger(ReadSocketConfig.class);
	private  String socketip = "";
	private  int socketport = 0;
	private static Properties p = new Properties();
	private static InputStream in;
	
	
	public  String getSocketip() {
		return socketip;
	}
	public  int getSocketport() {
		return socketport;
	}
	static {
		try {
			in =  ReadSocketConfig.class.getClassLoader().getResourceAsStream(getAPP_HOME() + "props/socket.properties");
			p.load(in);
		} catch (IOException e) {
			 log.error(e.getMessage(),e);
		}
	}
	/**
	 * 读取配置文件中的Socket信息设置
	 */
	public void readWainingProperties(String strip,String strport){
	
		socketip = p.getProperty(strip);
		socketport = Integer.parseInt(p.getProperty(strport));
	}
	public static String getAPP_HOME() {

		String appHomePath = "";
		String path = "";
		path = System.getProperty("APP_HOME");
//		log.info("path = " + path);
		if (path != null) {
			return path;
		} else
			return appHomePath;

	}
}
