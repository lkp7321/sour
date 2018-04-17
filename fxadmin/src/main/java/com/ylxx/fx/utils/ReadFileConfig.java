package com.ylxx.fx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadFileConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadFileConfig.class); 
	private static Properties p = new Properties();
	private static InputStream in;
	private  String filePath;
	
	
	static {
		try {
			in =  ReadFileConfig.class.getClassLoader().getResourceAsStream(getAPP_HOME() + "props/filePath.properties");
			p.load(in);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}
	
	
	
	public String getFilePath() {
		return filePath;
	}
	/**
	 * 读取配置文件中的信息设置
	 */
	public void readProperties(String realPath){
		filePath = p.getProperty(realPath);
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
