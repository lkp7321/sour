package com.ylxx.fx.core.mapper.pere.PereYh;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.ylxx.fx.core.domain.AddOrgFavRuleVo;
import com.ylxx.fx.core.domain.ExnmVo;
import com.ylxx.fx.core.domain.SelectFavRuleOgcdVo;
import com.ylxx.fx.core.domain.UpdateFavRuleByOgcdsVo;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;



public interface PereOrgFavruleMapper {
	public String  getOrgn(@Param("userkey") String userKey);
	public void  addOrgFavRule(@Param("bean") AddOrgFavRuleVo addOrgFavRuleVo);
	public void deleteOrgFavrRule(@Param("ogcd")String ogcd,@Param("exnm")String exnm);
	public void updateFavRuleByOgcds(@Param("bean") UpdateFavRuleByOgcdsVo updateFavRuleByOgcdsVo ,@Param("exnm") String exnm );
	public  List<Trd_FavruleOgcd> getFavruleByOgcd(@Param("ogcd") String ogcd);
	public List<Tranlist> selectFavRuleOgcd(@Param("ogcd") String ogcd,@Param("ognm") String ognm);
	public List<ExnmVo> getAllEXNM();
	public String exist(@Param("exnm") String exnm,@Param("ogcd") String ogcd);
}
