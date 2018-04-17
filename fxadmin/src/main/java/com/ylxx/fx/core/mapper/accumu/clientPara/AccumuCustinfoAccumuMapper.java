package com.ylxx.fx.core.mapper.accumu.clientPara;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AccumuCustinfoAccumuMapper{
	
	//获得全部数据String userProd,
	
		public List<HashMap<String, String>> queryRegmsgInfoList(@Param("prod")String prod,@Param("strcuno")String strcuno,@Param("strcuac")String strcuac,@Param("comaogcd")String comaogcd,@Param("combogcd")String combogcd) throws Exception;
	//获得对应的数据
		public List<HashMap<String, String>> selectTrac(String trac) throws Exception;
}
