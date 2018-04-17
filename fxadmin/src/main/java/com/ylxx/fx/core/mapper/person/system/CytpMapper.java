package com.ylxx.fx.core.mapper.person.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Cytp;

public interface CytpMapper {
	/*
	 * 查询
	 */
	public List<Cytp> selTab(@Param("prod")String prod);
	public List<Cytp> selTabPrice();
	/**
	 * 
	 * @param prod
	 * @param cytp
	 * @return
	 */
	public int selins(@Param("prod")String prod,@Param("cytp")String cytp);
	/*
	 * 新增
	 */
	public int insertCytp(@Param("prod")String prod, @Param("cytpObj")Cytp cytpObj);
	/*
	 * 修改
	 */
	public int updateCytp(@Param("prod")String prod, @Param("cytpObj")Cytp cytpObj);
	/*
	 * 删除
	 */
	public int deleteCytp(@Param("prod")String prod, @Param("cytp")String cytp);
}
