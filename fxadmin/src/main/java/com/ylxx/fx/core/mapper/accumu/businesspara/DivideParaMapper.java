package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.DivideParaBean;
/**
 * 分红日
 * @author lz130
 *
 */
public interface DivideParaMapper{
	
	/**
	 * 查询分紅
	 * @return
	 * @throws Exception
	 */
	List<DivideParaBean> searchDivideParaList() throws Exception;
	
	/**
	 * 删除分紅表记录
	 * @param dddt
	 * @return
	 * @throws Exception
	 */
	int doDeleteDividePara(@Param("dddt")String dddt) throws Exception;
	
	/**
	 * 取得cmm_ptpara_p003的更新时间
	 * @return
	 * @throws Exception
	 */
	String getP003UPDT() throws Exception;
	
	/**
	 * 更新CmmPara
	 * @param time
	 * @return
	 * @throws Exception
	 */
	int updateCmmPara(@Param("time")String time) throws Exception;
	
	/**
	 * 插入分紅表记录
	 * @param time
	 * @param currentTime
	 * @return
	 * @throws Exception
	 */
	int insertDividePara(@Param("time")String time, @Param("currentTime")String currentTime) throws Exception;
}