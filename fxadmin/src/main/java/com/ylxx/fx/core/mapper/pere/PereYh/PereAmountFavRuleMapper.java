package com.ylxx.fx.core.mapper.pere.PereYh;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.PereAmountFavRuleVo;

public interface PereAmountFavRuleMapper {
	public void addAmountRuleByAmount(@Param("bean")PereAmountFavRuleVo pereAmountFavRuleVo);
	public void deleteFavrRuleByAmount(@Param("bean")PereAmountFavRuleVo pereAmountFavRuleVo);
	public void updateAmountFavRuleByAmount(@Param("bean")PereAmountFavRuleVo pereAmountFavRuleVo);
	public List<HashMap<String, String>> getAmountFavRuleByOgcd(@Param("ogcd")String ogcd);

	public String exist(@Param("ogcd") String ogcd,@Param("min") String min,@Param("max") String max);
}
