package com.ylxx.qt.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {
static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	private static final String[] files = {"common_data"};
	private static Properties properties = new Properties();
	static{
		load();
	}
	/**
	 * 
	 * description: 加载配置文件
	 */
	private static void load(){
		InputStream in = null;
		for(String file : files){
			in= PropertiesUtil.class.getResourceAsStream("/"+ file + ".properties");	
			try {
				properties.load(in);
			} catch (Exception e) {
				logger.error("读取文件异常 ",e);
			}finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					in = null;
				}
			}
		}	
	}
	/*
	 * 从消息定义文件中取出code所对应的消息
	 */
	public static String getValue(String key){
		return trim(properties.getProperty(key));
	}
	/**
	 * 
	 * description: 从消息定义文件中取出code所对应的消息, args为占位符的实际值
	 * @param code
	 * @param args
	 * @return 消息
	 */
	public static String getValue(String key, Object[] args){
		return trim(MessageFormat.format(getValue(key),args));
	}
	
	/**
	 * 判断字符串是不是为空
	 * @param str
	 * @return 如果空返回true 不为空返回false
	 */
	public static boolean strIsNull(String str){
		if(null == str || "".equals(str.trim()))
			return true;
		return false;
	}
	
	public static String trim(String str) {
        return str == null ? "" : str.trim();
    }
}
