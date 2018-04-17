package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Favrule;
/**
 * 积存金
 * 分行优惠设置
 * @author lz130
 *
 */
public interface AccumFavruleMapper {
	/**
	 * 获取机构,P001,P002,P003,P004
	 * @param userorgleve
	 * @param userorgn
	 * @return
	 */
	List<Map<String, Object>> queryOrganS(
			@Param("userorgleve")String userorgleve, 
			@Param("userorgn")String userorgn);
	/**
	 * 获取分行优惠数据,P002,P003
	 * @param prod
	 * @param ogcd
	 * @return
	 */
	List<Favrule> SelectFavrule(@Param("ogcd")String ogcd);
	
	List<Map<String,Object>> selectPrice();
	/**
	 * 获取渠道
	 * @return
	 */
	List<Map<String, Object>> SelctChannel();
	List<Map<String, Object>> select();
	String SelChann(@Param("qdno")String qdno);
	String SelClient(@Param("cuty")String cuty);
	/**
	 * 修改保存操作
	 * @param ogcd
	 * @param rule
	 * @param fvid
	 * @return
	 */
	int onsave(
			@Param("ogcd")String ogcd,
			@Param("rule")String rule,
			@Param("fvid")String fvid);
	/**
	 * 启用
	 * @param ogcd
	 * @param fvid
	 * @return
	 */
	int Begin(@Param("ogcd")String ogcd,@Param("fvid")String fvid);
	/**
	 * 停用
	 * @param ogcd
	 * @param fvid
	 * @return
	 */
	int End(@Param("ogcd")String ogcd,@Param("fvid")String fvid);
	/**
	 * 添加初始化规则
	 * @param ogcd
	 * @return
	 */
	int insRule(@Param("ogcd")String ogcd);
}
