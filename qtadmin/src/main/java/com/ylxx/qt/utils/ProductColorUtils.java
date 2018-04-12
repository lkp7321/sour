package com.ylxx.qt.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 对品种颜色进行设置
 * 
 * @author mengpeitong
 * 
 */
public class ProductColorUtils {
	Map<String, String> map = null;
	/* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
	private static ProductColorUtils instance = null;
	//public  final static  String  PATH=ProductColorUtils.class.getClass().getResource("/").getPath()+ "/WEB-INF/classes/productColor.properties";
	/* 静态工程方法，创建实例 */
	public static ProductColorUtils getInstance(String url) {
		if (instance == null) {
			instance = new ProductColorUtils(url);
		}
		return instance;
	}

	private ProductColorUtils(String url) {
		InputStream in = null;
		BufferedInputStream bis = null;
		try {
			Properties pp = new Properties();
			in = new FileInputStream(url);
			bis = new BufferedInputStream(in);
			pp.load(bis);
			Enumeration en = pp.propertyNames(); // 得到配置文件的名字
			map = new HashMap<String, String>();
			while (en.hasMoreElements()) {
				String strKey = (String) en.nextElement();
				String strValue = pp.getProperty(strKey);
				map.put(strKey, strValue);
			}
		} catch (Exception c) {
			c.printStackTrace();
		} finally {
			try {
				in.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 获取文件内容
	public String getProductColor(String Product) {
		// 如果找不到，那么就返回白色
		if (map.get(Product) == null) {
			return "#FFF";
		}
		return map.get(Product);
	}

	// 写入Properties信息
	public static void WriteProperties(String url,String pKey, String pValue) throws IOException {
		Properties pps = new Properties();

		InputStream in = new FileInputStream(url);
		// 从输入流中读取属性列表（键和元素对）
		pps.load(in);
		// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
		// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
		OutputStream out = new FileOutputStream(url);
		pps.setProperty(pKey, pValue);
		// 以适合使用 load 方法加载到 Properties 表中的格式，
		// 将此 Properties 表中的属性列表（键和元素对）写入输出流
		pps.store(out, "Update " + pKey + " name");
	}

}
