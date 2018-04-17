package com.ylxx.fx.core.mapper.person.fxipmonitor;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.FormuleVo;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.po.GckwebBean;

public interface GoldBrnchMonitorMapper {
	/*
	 * 纸黄金，总分行价格监控
	 */
	public List<FxipMonitorVo> selectAllPdtBrnch();
	/*
	 * 客户价格监控
	 */
	public List<FxipMonitorVo> selectAllPdtCust();
	/*
	 * 敞口监控
	 */
	public List<GckwebBean> selectwebPrice();
	/*
	 * 敞口列表
	 */
	public List<Map<String, String>> selectGoldCkno();
	/*
	 * 分类敞口监控
	 */
	public List<FormuleVo> selectGoldclassPrice(@Param("ckno")String ckno);
	
}
