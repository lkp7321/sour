package com.ylxx.fx.core.mapper.jsh;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 外管利率，登录柜员信息
 * @author lz130
 *
 */
public interface JshWgjSafeMapper {
	/**
	 * 查询利率
	 * @param cyen
	 * @return
	 */
	List<Map<String, Object>> selJshWgjSafePriceList(@Param("cyen")String cyen);
	/**
	 * 查询利率记录数
	 * @param cyen
	 * @return
	 */
	int selJshWgjSafePriceCount(@Param("cyen")String cyen);
	/**
	 * 添加 外管利率
	 * @param cyen
	 * @param cout
	 * @param trdt
	 */
	void insJshWghSafePrice(@Param("cyen")String cyen,@Param("cout")String cout,@Param("trdt")String trdt);
	/**
	 * 查询外管登录信息
	 * @param tellerId
	 * @return
	 */
	List<Map<String, Object>> selJshWgjSafeInfoList(@Param("tellerId")String tellerId);
	/**
	 * 添加外管登陆信息
	 * @param bhid
	 * @param chnl
	 * @param ogcd
	 * @param tltp
	 * @param trtl
	 * @param pass
	 * @return
	 */
	int insJshWgjSafeInfo(
			@Param("bhid")String bhid, @Param("chnl")String chnl, @Param("ogcd")String ogcd, 
			@Param("tltp")String tltp, @Param("trtl")String trtl, @Param("pass")String pass);
	/**
	 * 修改外管登陆信息
	 * @param bhid
	 * @param chnl
	 * @param ogcd
	 * @param tltp
	 * @param trtl
	 * @param pass
	 * @param tellerId
	 * @return
	 */
	int upsJshWgjSafeInfo(
			@Param("bhid")String bhid, @Param("chnl")String chnl, @Param("ogcd")String ogcd, 
			@Param("tltp")String tltp, @Param("trtl")String trtl, @Param("pass")String pass,
			@Param("tellerId")String tellerId);
	/**
	 * 将外管登陆信息迁移数据到历史表
	 * @param tellerId
	 */
	void moveJshWgjSafeInfo(@Param("tellerId")String tellerId);
	/**
	 * 删除外管登录信息
	 * @param tellerId
	 */
	void delJsnWgjSafeInfo(@Param("tellerId")String tellerId);
}
