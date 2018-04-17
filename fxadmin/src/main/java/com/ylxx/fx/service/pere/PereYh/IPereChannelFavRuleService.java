package com.ylxx.fx.service.pere.PereYh;

import java.util.List;

import com.ylxx.fx.core.domain.ChannelFavRuleVo;
import com.ylxx.fx.core.domain.CustLevelFavRuleVo;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;

public interface IPereChannelFavRuleService {
	
	public String addChannelFavRule(ChannelFavRuleVo channelFavRuleVo,CurrUser curUser);
	public String exist(String exnm,String ogcd,String chnl);
	
	
	public  String  updateRuleByChannels(ChannelFavRuleVo channelFavRuleVo,CurrUser curUser);
	public  List<Trd_FavruleOgcd> getChannelFavRuleByOgcd(String ogcd,String chnl);
	public  String  deleteChannelFavrRule(ChannelFavRuleVo channelFavRuleVo);
}
