package com.ylxx.fx.utils;

import java.util.List;

public class Pages<T> {
	private boolean isFirstPage;
	private boolean isLastPage;
	private boolean hasNextPage;
	private boolean hasPrePage;
	private Integer beginRows;
	private Integer endRows;
	private Integer pageNo = 1;
	private Integer pageSize = 10;
	private Integer total;
	private List<T> list;
	private Integer pages;
	
	public Pages(Integer pageNo, Integer pageSize,List<T> list){
		if(list!=null && list.size()>0){
			this.total = list.size();
			this.beginRows = (pageNo-1)*pageSize+1;
			this.endRows = (pageNo*pageSize);
			this.pages = list.size()/pageSize + ((list.size()%pageSize==0)?0:1); 
			if(pageNo==1 && pages == 1){
				this.isFirstPage = true;
				this.isLastPage = false;
				this.hasPrePage = false;
				this.hasNextPage = false;
			}
			else if(pageNo==1 && pages != 1){
				this.isFirstPage = true;
				this.isLastPage = false;
				this.hasPrePage = false;
				this.hasNextPage = true;
			}else if(pageNo==1 && pages != 1){
				
			}
		}
	}
}
