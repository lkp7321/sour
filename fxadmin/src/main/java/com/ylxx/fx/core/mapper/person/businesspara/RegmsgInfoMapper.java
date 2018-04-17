package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.RegmsgBean;
/**
 * 客户签约信息查询
 * @author lz130
 *
 */
public interface RegmsgInfoMapper {
	/**
	 * 查询
	 * @param userProd
	 * @param strcuno
	 * @param strcuac
	 * @param comaogcd
	 * @param combogcd
	 * @return
	 */
	List<RegmsgBean> selectRegmsgInfoList(
			@Param("userProd")String userProd,@Param("strcuno")String strcuno,
			@Param("strcuac")String strcuac, @Param("comaogcd")String comaogcd, 
			@Param("combogcd")String combogcd);
}
