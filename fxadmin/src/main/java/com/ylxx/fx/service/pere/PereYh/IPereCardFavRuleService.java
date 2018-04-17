package com.ylxx.fx.service.pere.PereYh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.PereCardFavRuleVo;
import com.ylxx.fx.core.domain.UpdateFavRuleByOgcdsVo;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;

public interface IPereCardFavRuleService {
	//卡bin优惠更新保存
	public String addCardRuleByCard(PereCardFavRuleVo pereCardFavRuleVo);
	public String deleteCardFavRules(PereCardFavRuleVo pereCardFavRuleVo);
	public  String  updateRuleByCards(PereCardFavRuleVo pereCardFavRuleVo,CurrUser curUser);
	
	public  List<Trd_FavruleOgcd> getCardFavRuleByOgcd(String ogcd);
}

