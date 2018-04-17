package com.ylxx.fx.service.accumu.businesspara;

/**
 * 添加客户等级
 * @author lz130
 *
 */
public interface AccumuAddCutyService {
	/**
	 * 查询
	 * @param ptid
	 * @param gstp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	String doSearchList(String ptid,String gstp,Integer pageNo,Integer pageSize);
	String insertCuty(String gstp, String bp, String name,String ptid);
	String selMaxCuty(String tynm);
}
