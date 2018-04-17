package com.ylxx.fx.core.mapper.person.fxipmonitor;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Tranlist;

public interface FxipTranListMapper {

	public List<Tranlist> selectTran(@Param("prod")String prod, @Param("time")String time);

	public List<Tranlist> selectTimeTran(@Param("prod")String prod, @Param("bTime")String bTime, @Param("eTime")String eTime);

	public int selectTimeCount(@Param("prod")String prod, @Param("curTime")String curTime);

	public String selectUsamCount(@Param("prod")String prod, @Param("curTime")String curTime);
	
	
}
