package com.ylxx.fx.core.mapper.jsh;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 错误码
 * @author lz130
 *
 */
public interface JshErrorCodeMapper {
	/**
	 * 查询错误码
	 * @param ercd
	 * @return
	 */
	List<Map<String, Object>> selErrorCodeMsg(@Param("ercd") String ercd);
	int selErrorCodeCount(@Param("ercd")String ercd);
	void insertErrorCode(@Param("ercd") String ercd, @Param("ertx")String ertx, @Param("erin")String erin);
	void updateErrorCode(@Param("ercd") String ercd, @Param("ertx")String ertx, @Param("erin")String erin);
	void deleteErrorCode(@Param("ercd") String ercd);
}
