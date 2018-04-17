package com.ylxx.fx.core.mapper.pere.PereYh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.CustLevelFavRuleVo;
import com.ylxx.fx.core.domain.UpdateFavRuleByOgcdsVo;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;

public interface PereCustLevelFavruleMapper {
	public void addCustLevelFavRule(@Param("bean") CustLevelFavRuleVo addCustLevelFavRuleVo);
	public void deleteCustLevelFavRules(@Param("ogcd")String ogcd,@Param("exnm")String exnm,@Param("level") String level);
	public String exist(@Param("exnm") String exnm,@Param("level") String level,@Param("ogcd") String ogcd);
	public void updateRuleByCustLevels(@Param("bean") CustLevelFavRuleVo custLevelFavRuleVo ,@Param("exnm") String exnm );
	
	public  List<Trd_FavruleOgcd> getCustFavRuleByOgcd(@Param("ogcd") String ogcd,@Param("level")String level);
}
