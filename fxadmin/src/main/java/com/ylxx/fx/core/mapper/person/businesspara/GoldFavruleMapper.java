package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Favrule;

public interface GoldFavruleMapper {
	/**
	 * 获取机构P002
	 * @param userorgleve
	 * @param userorgn
	 * @return
	 */
	public List<Map<String, Object>> queryOrganS(@Param("userorgleve")String userorgleve, @Param("userorgn")String userorgn);
	public List<Favrule> SelectFavrule(@Param("ogcd")String ogcd);
	
	public List<Map<String,Object>> selectPrice();
	public List<Map<String, Object>> SelctChannel();
	public List<Map<String, Object>> select();
	public String SelChann(@Param("qdno")String qdno);
	public String SelClient(@Param("cuty")String cuty);
	public int onsave(@Param("rule")String ruel,@Param("ogcd")String ogcd,@Param("fvid")String fvid);
	public int insRule(@Param("ogcd")String ogcd);
	/**
	 * P002 启用，停用
	 * @param prcd
	 * @param ppch
	 * @return
	 */
	public int Begin(@Param("ogcd")String ogcd,@Param("fvid")String fvid);
	public int End(@Param("ogcd")String ogcd,@Param("fvid")String fvid);
}
