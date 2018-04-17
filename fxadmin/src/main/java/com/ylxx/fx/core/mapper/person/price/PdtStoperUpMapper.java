package com.ylxx.fx.core.mapper.person.price;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.CmmCymsgBean;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.PdtStoperBean;

public interface PdtStoperUpMapper{
	
	//查询实盘币种
	public List<CmmCymsgBean> selectfxipEXNM() throws Exception;
	//查询纸黄金币种
	public List<CmmCymsgBean> selectgoldEXNM() throws Exception;
	//获得停牌器的所有数据
	public List<PdtStoperBean> selectAllCmmStopers(@Param("ptid")String ptid) throws Exception;
	//条件获得停牌器的数据
	public List<PdtStoperBean> selectAllCmmStoper(@Param("ptid")String ptid,@Param("excd")String excd)throws Exception;
	//批量修改未停牌
	public boolean updateYesCtrlusfg(@Param("prod")String prod, @Param("pdtStoper")PdtStoperBean pdtStoper) throws Exception;
	//写审计日志
	public boolean insertLog(@Param("logfileBean")LogfileBean logfileBean) throws Exception;
	//根据货币对名称查询货币对编号
	public String getExcd(@Param("ptid")String ptid,@Param("exnm")String exnm) throws Exception;
}