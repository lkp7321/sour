package com.ylxx.fx.core.mapper.price.putliquid;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.PdtChkpara;
import com.ylxx.fx.service.po.Pdtinfo;
import com.ylxx.fx.service.po.Put_Config;
import com.ylxx.fx.service.po.Put_Liquid;
public interface PutliQuidMapper {
	public List<Put_Liquid> selectPutList(@Param("stat")String stat);
	public int insertPrice(@Param("put_liquid")Put_Liquid put_liquid);
	public int updatePrice(@Param("stat")String stat, @Param("sqid")String sqid);
	
	//============================
	public int delePtnm(@Param("inid")String inid);
	public int insertPutConfig(@Param("put_config")Put_Config put_config, 
			@Param("pdtin")Pdtinfo pdtin);
	public Pdtinfo returnPdtinfo(@Param("ptnm")String ptnm);
	public List<Put_Config> returnConfig(@Param("inid")String inid);
	public int updateIDPass(@Param("put_Liquid")Put_Liquid put_Liquid);
	//获取所有接口信息
	public List<PdtChkpara> reConfigCmm(@Param("inid")String inid);
	//获取相应的产品下的接口信息
	public List<PdtChkpara> selectMkid(@Param("mkid")String mkid);
	//获取所有产品
	public List<Map<String,String>> selectPdtinfo();
	//查询当前选中数据信息
	public Put_Liquid selectOnLiquid(@Param("sqid")String sqid,@Param("name")String name,@Param("unit")String unit);
}
