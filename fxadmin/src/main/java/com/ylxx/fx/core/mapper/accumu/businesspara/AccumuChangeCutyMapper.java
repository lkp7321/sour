package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
/**
 * 修改客户等级
 * @author lz130
 *
 */
public interface AccumuChangeCutyMapper {
	/**
	 * 查询
	 * @param cuno
	 * @param cuac
	 * @return
	 */
	List<HashMap<String,String>> getSearch(@Param("cuno")String cuno, @Param("cuac")String cuac);
	/**
	 * 修改
	 * @param cuty
	 * @param cuno
	 * @param cuac
	 * @param trac
	 */
	void doUpdateCuty(@Param("cuty")String cuty,@Param("cuno")String cuno,@Param("cuac")String cuac,@Param("trac")String trac);
}
