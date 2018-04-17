package com.ylxx.fx.service.accumu.businesspara;

import org.apache.ibatis.annotations.Param;
/**
 * 查询客户信息
 * @author lz130
 *
 */
public interface AccumuChangeCutyService {
	/**
	 * 查询所有客户信息
	 * @param cuno
	 * @param cuac
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	String getSearch(String cuno, String cuac,Integer pageNo,Integer pageSize);
	/**
	 * 修改客户信息
	 * @param cuty
	 * @param cuno
	 * @param cuac
	 * @param trac
	 * @return
	 */
	String doUpdateCuty(@Param("cuty")String cuty,@Param("cuno")String cuno,@Param("cuac")String cuac,@Param("trac")String trac);
}
