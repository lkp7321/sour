package com.ylxx.fx.core.mapper.person.fxipmonitor;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.ylxx.fx.core.domain.FormuleVo;

public interface CKclassMonitorMapper {
	/*
	 * ckno的下拉列表
	 */
	public List<Map<String, String>> selectCkno(@Param("prcd")String prcd);
	/*
	 * 轧差敞口，分类
	 */
	public List<FormuleVo> selectPrice(
			@Param("prcd")String prcd,@Param("ckno")String ckno);
	/*
	 * 累加敞口,分类
	 */
	public List<FormuleVo> selectLJCKPrice(
			@Param("prcd")String prcd,@Param("ckno")String ckno);
}
