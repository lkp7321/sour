package com.ylxx.fx.core.mapper.person.price;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.PdtChkParaBean;

public interface PdtChkParaMapper{
	
	//选择性的返回某个产品的结果集 以后不同的产品都调用它
	public List<PdtChkParaBean> selectAllPdtChkpara(@Param("ptid")String ptid) throws Exception;
	//更新启用状态 启用0 停用1
	public boolean updatePriceusfgAll(@Param("ptid")String ptid,@Param("pdtChk")PdtChkParaBean pdtChk) throws Exception;
	//根据价格类型名称获取价格类型
	public String getTpnmByTpfg(@Param("tpfg")String tpfg) throws Exception;
}