package com.ylxx.fx.core.mapper.person.fxipmonitor;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Ck_ppresultdetail;


public interface PpTranMonitorMapper {

	public List<Ck_ppresultdetail> selectPpDeatil(@Param("time")String time);

	public List<Ck_ppresultdetail> selectTimeDetail(
			@Param("bTime")String bTime, @Param("eTime")String eTime);

	public int selectTodayDetail(@Param("curTime")String curTime);

	public String selectTodayUsam(@Param("curTime")String curTime);

	
}
