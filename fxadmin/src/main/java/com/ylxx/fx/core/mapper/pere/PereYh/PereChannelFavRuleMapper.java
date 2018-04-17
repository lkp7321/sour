package com.ylxx.fx.core.mapper.pere.PereYh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.ChannelFavRuleVo;
import com.ylxx.fx.core.domain.CustLevelFavRuleVo;
import com.ylxx.fx.core.domain.UpdateFavRuleByOgcdsVo;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;

public interface PereChannelFavRuleMapper {

	public void addChannelFavRule(@Param("bean") ChannelFavRuleVo channelFavRuleVo);
	public String exist(@Param("exnm") String exnm,@Param("ogcd") String ogcd,@Param("chnl") String chnl);
	public void updateRuleByChannels(@Param("bean") ChannelFavRuleVo channelFavRuleVo ,@Param("exnm") String exnm );
	
	public  List<Trd_FavruleOgcd> getChannelFavRuleByOgcd(@Param("ogcd") String ogcd,@Param("chnl") String chnl);
	public void deleteChannelFavrRule(@Param("ogcd")String ogcd,@Param("exnm")String exnm,@Param("chnl") String level);
}
