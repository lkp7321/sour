package com.crm.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtil {
	// 用于读取Java配置文件
	private static Properties prop=new Properties();
	private static Connection conn=null;
	private static PreparedStatement ps=null;
	private static ResultSet  rs=null;
	static{
		InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("config/jdbc.properties");
		try {
			prop.load(is);//将属性字符流装载到属性对象
		} catch (IOException e) {
			System.out.println("文件加载失败！");
			e.printStackTrace();
		}
		try {
            System.out.println("driver：" + prop.getProperty("driver"));
            Class.forName(prop.getProperty("driver"));
		} catch (ClassNotFoundException e) {
			System.out.println("加载驱动失败");
			e.printStackTrace();
		}
	}

	/**
	 * 连接数据库
	 * @return
	 */
	public static Connection getConnection(){
		try {
			conn=DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("user"),prop.getProperty("password"));
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
	 */
	public static void close(){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 进行结果集操作
	 * @param sql
	 * @param params
	 * @return
	 */
	public static ResultSet getResult(String sql,Object[] params){
		try {
            System.out.println("sql："+sql);
            ps=getConnection().prepareStatement(sql);
			if(params!=null){
				for(int i=0;i<params.length;i++){
                    System.out.println(params[i]);
                    ps.setObject(i+1,params[i]);
				}
			}
			rs=ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 反射为PreparedStatement赋值
	 * @param sql
	 * @param params
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T>List<T> getObjectList(String sql,Object[] params,Class<?> clazz){
		List<T> list=new ArrayList();
		// 获取结果集
		rs=getResult(sql,params);
		int columnCount=0;
		try {
			// 获取表结构
			ResultSetMetaData rsmd = rs.getMetaData();
			// 获取表的字段的个数
			columnCount = rsmd.getColumnCount();
			while(rs.next()){
				// 根据bean的class创建一个对象
				Object obj = clazz.newInstance();
				for(int i = 0; i < columnCount; i++){
					// 遍历 表的字段
					String columnName = rsmd.getColumnName(i+1);
					// 获取class对象的属性 不包括父类
					Field field = clazz.getDeclaredField(columnName);
					// 设置访问权限
					field.setAccessible(true);
					// 获取字段的值
					Object value = rs.getObject(columnName);
					// 值放入对象中
					field.set(obj, value);
				}
				list.add((T)obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			close();
		}
		return list;
	}

	/**
	 * 增删改操作
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int execute(String sql,Object[] params){
		int rows=0;
		try {
			ps=getConnection().prepareStatement(sql);
			if(params!=null){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1,params[i]);
				}
			}
		rows=ps.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		// 返回影响条数
		return rows;
	}

	/**
	 * 测试连接
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn=DBUtil.getConnection();
		System.out.println(conn);
	}
}
