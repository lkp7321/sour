package com.ylxx.fx.core.mapper.jsh;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 定投管理
 * @author lz130
 *
 */
public interface DT_TranRemsgMapper {

	/**
	 * 签约查询
	 * @param tbTrd_RegMsgList
	 * @return
	 */
	List<Map<String, Object>> selDT_TranRemsgListQ(
			@Param("rgdt")String rgdt, @Param("crdt")String crdt, @Param("exnm")String exnm, @Param("cuac")String cuac);
	/**
	 * 解约查询
	 * @param tbTrd_RegMsgList
	 * @return
	 */
	List<Map<String, Object>> selDT_TranRemsgListJ(
			@Param("rgdt")String rgdt, @Param("crdt")String crdt, @Param("exnm")String exnm, @Param("cuac")String cuac);
	/**
	 * 定投流水查询
	 * @param trdt
	 * @param trtm
	 * @param fonm
	 * @param acum
	 * @return
	 */
	List<Map<String, Object>> selDT_TranList(
			@Param("trdt")String trdt, @Param("trtm")String trtm, @Param("fonm")String fonm, @Param("cuac")String cuac);
	/**
	 * 定投价格查询
	 * @param trdt
	 * @param exnm
	 * @return
	 */
	List<Map<String, Object>> selDT_Price(@Param("trdt")String trdt, @Param("trtm")String trtm, @Param("exnm")String exnm);
}
