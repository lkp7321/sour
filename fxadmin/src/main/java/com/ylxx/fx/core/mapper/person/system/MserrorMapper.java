package com.ylxx.fx.core.mapper.person.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Mserror;
/**
 * 错误码管理
 * @author lz130
 *
 */
public interface MserrorMapper {
	/**
	 * 查询
	 * @param StrTxt
	 * @param StrTxt1
	 * @return
	 */
	List<Mserror> selMsgError(@Param("StrTxt")String StrTxt, 
			@Param("StrTxt1")String StrTxt1);
	/**
	 * 添加
	 * @param ercd
	 * @param ertx
	 * @param erin
	 * @return
	 */
	int addErrorCode(@Param("ercd")String ercd, @Param("ertx")String ertx, @Param("erin")String erin);
	/**
	 * 修改
	 * @param ercd
	 * @param ertx
	 * @param erin
	 * @return
	 */
	int upsErrorCode(@Param("ercd")String ercd, @Param("ertx")String ertx, @Param("erin")String erin);
	/**
	 * 删除
	 * @param ercd
	 * @return
	 */
	int delErrorCode(@Param("ercd")String ercd);
}
