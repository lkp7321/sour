package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AccumuAddCutyMapper {
	List<HashMap<String,String>> doSearchList(@Param("ptid")String ptid,@Param("gstp")String gstp);
	String getCutyMax(@Param("tynm")String tynm,@Param("ptid")String ptid);
	void insertCuty(@Param("ptid")String ptid,@Param("level")String level,@Param("name")String name,@Param("gstp")String gstp,@Param("bp")String bp);
	String selMaxCuty(@Param("tynm")String tynm);
}
