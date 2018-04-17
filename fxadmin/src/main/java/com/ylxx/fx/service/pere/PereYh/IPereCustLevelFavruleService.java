package com.ylxx.fx.service.pere.PereYh;

import java.util.List;

import com.ylxx.fx.core.domain.CustLevelFavRuleVo;
import com.ylxx.fx.core.domain.UpdateFavRuleByOgcdsVo;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;

public interface IPereCustLevelFavruleService {
	
public String addCustLevelFavRule(CustLevelFavRuleVo addCustLevelFavRuleVo,CurrUser curUser);

public  String  deleteCustLevelFavRules(CustLevelFavRuleVo  deleteCustLevelFavRulesVo);
public String exist(String exnm,String level,String ogcd);

public  String  updateRuleByCustLevels(CustLevelFavRuleVo custLevelFavRuleVo,CurrUser curUser);

public  List<Trd_FavruleOgcd> getCustFavRuleByOgcd(String ogcd,String level);
}
