package com.crm.util;

import java.util.List;

public class Pager<E> {
	// 当前第几页
	private int curPage;
	// 一页多少条
	private int pageSize;
	// 总记录条数
	private int allCount;
	private List<E> list;

	public Pager() {

	}
	public Pager(int curPage, int pageSize, int allCount, List<E> list) {
		super();
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.allCount = allCount;
		this.list = list;
	}



	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getAllPage() {
		return allCount;
	}
	public void setAllPage(int allPage) {
		this.allCount = allPage;
	}

	/**
	 * 获取总页数
	 * @return
	 */
	public int getPageSum(){
		return this.allCount%this.pageSize==0?this.allCount/this.pageSize:this.allCount/this.pageSize+1;
	}
}
