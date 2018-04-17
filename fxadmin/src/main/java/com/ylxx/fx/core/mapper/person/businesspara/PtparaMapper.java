package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Ptpara;
/**
 * 业务参数
 * @author lz130
 *
 */
public interface PtparaMapper {
	/**
	 * 查询所有业务参数
	 * @param prod
	 * @return
	 */
	List<Ptpara> selectPtpara(@Param("prod")String prod);
	/**
	 * 修改业务参数
	 * @param prcd
	 * @param ptpara
	 * @return
	 */
	int upPtpara(@Param("prcd")String prcd, 
			@Param("ptpara")Ptpara ptpara);
}
