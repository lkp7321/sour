package com.ylxx.fx.core.mapper.pere.PereYh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.PereCardFavRuleVo;
import com.ylxx.fx.core.domain.UpdateFavRuleByOgcdsVo;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;

public interface PereCardFavRuleMapper{
	//卡bin优惠更新保存
	public void addCardRuleByCard(@Param("bean")PereCardFavRuleVo pereCardFavRuleVo);
	public String exist(@Param("ogcd") String ogcd,@Param("cbin") String cbin);
	public  List<Trd_FavruleOgcd> getCardFavRuleByOgcd(@Param("ogcd") String ogcd);
	public void updateRuleByCards(@Param("bean") PereCardFavRuleVo pereCardFavRuleVo ,@Param("cbin") String cbin );
	public void deleteCardFavRules(@Param("ogcd")String ogcd,@Param("cbin") String cbin);
	
}