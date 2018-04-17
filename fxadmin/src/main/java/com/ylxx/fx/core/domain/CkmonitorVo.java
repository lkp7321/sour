package com.ylxx.fx.core.domain;

import com.ylxx.fx.service.po.CkTotalBean;
import com.ylxx.fx.service.po.Trd_ogcd;

public class CkmonitorVo {
	private Integer pageNo;
	private Integer pageSize;
	private String ckno;
	private String userKey;
	private String date;
	private String dateBegin;
	private String dataBegin;
	private String dateEnd;
	private CkTotalBean ckTotalBean;
	private Trd_ogcd trd_ogcd;
	
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

	public Trd_ogcd getTrd_ogcd() {
		return trd_ogcd;
	}
	
	public String getDataBegin() {
		return dataBegin;
	}

	public void setDataBegin(String dataBegin) {
		this.dataBegin = dataBegin;
	}

	public void setTrd_ogcd(Trd_ogcd trd_ogcd) {
		this.trd_ogcd = trd_ogcd;
	}
	public String getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCkno() {
		return ckno;
	}
	public void setCkno(String ckno) {
		this.ckno = ckno;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public CkTotalBean getCkTotalBean() {
		return ckTotalBean;
	}
	public void setCkTotalBean(CkTotalBean ckTotalBean) {
		this.ckTotalBean = ckTotalBean;
	}
	
}
