package com.ylxx.fx.service.pere.PereYh;

import java.util.List;

import com.ylxx.fx.core.domain.AddOrgFavRuleVo;
import com.ylxx.fx.core.domain.DeleteOrgFavrRuleVo;
import com.ylxx.fx.core.domain.ExnmVo;
import com.ylxx.fx.core.domain.SelectFavRuleOgcdVo;
import com.ylxx.fx.core.domain.UpdateFavRuleByOgcdsVo;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.service.po.Trd_ogcd;
import com.ylxx.fx.utils.CurrUser;

public interface IPereOrgFavruleService {
	public String  getOrgn(String userKey);
	public  String addOrgFavRule(AddOrgFavRuleVo addOrgFavRuleVo);
	public  String  deleteOrgFavrRule(DeleteOrgFavrRuleVo deleteOrgFavrRuleVo);
	public  String  updateFavRuleByOgcds(UpdateFavRuleByOgcdsVo uspdateFavRuleByOgcdsVo,CurrUser curUser);
	public  List<Trd_FavruleOgcd> getFavruleByOgcd(String ogcd);
	public  List<Tranlist> selectFavRuleOgcd(SelectFavRuleOgcdVo selectFavRuleOgcdVo);
	public List<ExnmVo> getAllEXNM();
	public String exist(String exnm,String ogcd);
}
