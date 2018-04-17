package com.ylxx.fx.core.mapper.person.price;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.PdtCtrlSwhBean;
import com.ylxx.fx.service.po.PdtRParaTBean;
import com.ylxx.fx.service.po.PdtinfoBean;

public interface PdtCtrlSwhMapper{
	//得到当前所有干预器列表及状态
	public List<PdtCtrlSwhBean> selectPrice(@Param("ptid")String ptid) throws Exception;
	//得到所有币种信息
	public List<PdtRParaTBean> selectPriceUSD(@Param("ptid")String ptid) throws Exception;
	//查询产品信息
	public PdtinfoBean selectObjPrice(@Param("ptid")String ptid) throws Exception;
	//查询实盘产品价格干预器总控
	public List<PdtCtrlSwhBean> selectPri(@Param("ptid")String ptid,@Param("pdtCtrl")PdtCtrlSwhBean pdtCtrl);
	//更新实盘产品价格干预器总控
	public boolean updatePri(@Param("ptid")String ptid,@Param("pdtCtrl")PdtCtrlSwhBean pdtCtrl) throws Exception;
	//添加实盘产品价格干预器总控
	public boolean insertPri(@Param("ptid")String ptid,@Param("pdtCtrl")PdtCtrlSwhBean pdtCtrl) throws Exception;
	//写审计日志
	public boolean insertLog(@Param("logfile")LogfileBean logfile) throws Exception;
	//删除点差干预上限
	public boolean deletePrice(@Param("ptid")String ptid,@Param("tpfg")String tpfg,@Param("term")String term,
			@Param("exnm")String exnm,@Param("cxfg")String cxfg) throws Exception;
	//启用/停用
	public boolean updateUsfg(@Param("ptid")String ptid,@Param("pdtCtrl")PdtCtrlSwhBean pdtCtrl) throws Exception;
}