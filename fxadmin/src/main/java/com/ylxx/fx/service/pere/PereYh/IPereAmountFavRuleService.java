package com.ylxx.fx.service.pere.PereYh;



import com.ylxx.fx.core.domain.PereAmountFavRuleVo;

public interface IPereAmountFavRuleService {
	public String addAmountRuleByAmount(PereAmountFavRuleVo pereAmountFavRuleVo);
	public String deleteFavrRuleByAmount(PereAmountFavRuleVo pereAmountFavRuleVo);
	public String updateAmountFavRuleByAmount(PereAmountFavRuleVo pereAmountFavRuleVo);
	public String getAmountFavRuleByOgcd(String ogcd,Integer pageNo,Integer pageSize);
	public String getAmountFavRuleLastData(String ogcd);
}
