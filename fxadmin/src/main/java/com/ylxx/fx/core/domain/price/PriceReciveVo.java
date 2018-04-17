package com.ylxx.fx.core.domain.price;

import java.util.List;

import com.ylxx.fx.service.po.CmmChkpara;
import com.ylxx.fx.service.po.CmmStoper;
import com.ylxx.fx.service.po.calendar.OriginalVO;
/**
 * 价格接收设置及展示的VO
 */
public class PriceReciveVo {
	private String time;
	private String exnm;
	private String mkid;
	private String userKey;
	private List<OriginalVO> calList;
	private PriceRecVo pricerecBean;
	private String calendarId; 
	private List<PriceRecVo> priceRecList;
	private List<CmmChkpara> chlist;//校验器停用，启用
	private CmmChkpara cmmbean;//校验器修改
	
	
	
	public CmmChkpara getCmmbean() {
		return cmmbean;
	}
	public void setCmmbean(CmmChkpara cmmbean) {
		this.cmmbean = cmmbean;
	}
	public List<CmmChkpara> getChlist() {
		return chlist;
	}
	public void setChlist(List<CmmChkpara> chlist) {
		this.chlist = chlist;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getMkid() {
		return mkid;
	}
	public void setMkid(String mkid) {
		this.mkid = mkid;
	}
	public String getCalendarId() {
		return calendarId;
	}
	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}
	public List<PriceRecVo> getPriceRecList() {
		return priceRecList;
	}
	public void setPriceRecList(List<PriceRecVo> priceRecList) {
		this.priceRecList = priceRecList;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public List<OriginalVO> getCalList() {
		return calList;
	}
	public void setCalList(List<OriginalVO> calList) {
		this.calList = calList;
	}
	public PriceRecVo getPricerecBean() {
		return pricerecBean;
	}
	public void setPricerecBean(PriceRecVo pricerecBean) {
		this.pricerecBean = pricerecBean;
	}
	
}
