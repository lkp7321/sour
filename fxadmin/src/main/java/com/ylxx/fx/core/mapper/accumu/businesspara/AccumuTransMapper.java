package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AccumuTransMapper{
	
	//查询买币种
	public List<String> querybuyexnm(@Param("prod")String prod) throws Exception;
	//查询交易码
	public List<HashMap<String, String>> querytrancode() throws Exception;
	//为第一个机构下拉框赋值
	public List<HashMap<String, String>> queryOneOrgan() throws Exception;
	//为第一个机构下拉框赋值
	public List<HashMap<String, String>> queryTwoOrgan(@Param("comaogcd")String comaogcd) throws Exception;
	//条件获得对应的数据String comdata1, String comdata3,String strcuac, String trdtbegin, String trdtend, String byexnm,String comaogcd, String combogcd
	public List<HashMap<String, String>> selectAccumuTranlist(@Param("comdata1")Integer comdata1,@Param("comdata3")String comdata3,@Param("strcuac")String strcuac,@Param("trdtbegin")String trdtbegin,@Param("trdtend")String trdtend,@Param("byexnm")String byexnm,@Param("comaogcd")String comaogcd,@Param("combogcd")String combogcd) throws Exception;
}																