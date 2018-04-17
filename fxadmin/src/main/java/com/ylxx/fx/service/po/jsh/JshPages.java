package com.ylxx.fx.service.po.jsh;

import java.util.List;

import com.ylxx.fx.utils.Table;

/**
 * 工具类
 * @author lz130
 * @param <T>
 */
public class JshPages<T>{
	/**
	 * Excel表名
	 */
	private String tableName;
	/**
	 * Excel表，列名及对应的id
	 */
	private List<Table> tableList;
	/**
	 * 当前页码
	 */
	private Integer pageNo;
	/**
	 * 条数/每页
	 */
	private Integer pageSize;
	/**
	 * 操作用户
	 */
	private String userKey;
	/**
	 * 封装对象
	 */
	private T entity;
	/**
	 * 批量的封装对象
	 */
	private List<T> t_list;
	
	public JshPages() {
		super();
	}
	public JshPages(Integer pageNo, Integer pageSize, T entity, List<T> t_list, String userKey, String tableName, List<Table> tableList) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.entity = entity;
		this.t_list = t_list;
		this.userKey = userKey;
		this.tableName = tableName;
		this.tableList = tableList;
	}
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Table> getTableList() {
		return tableList;
	}
	public void setTableList(List<Table> tableList) {
		this.tableList = tableList;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public List<T> getT_list() {
		return t_list;
	}
	public void setT_list(List<T> t_list) {
		this.t_list = t_list;
	}
	
}
