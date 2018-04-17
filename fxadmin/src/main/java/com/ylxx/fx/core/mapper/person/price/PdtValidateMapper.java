package com.ylxx.fx.core.mapper.person.price;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.TpfgtBean;
import com.ylxx.fx.service.po.CurrmsgBean;
import com.ylxx.fx.service.po.PdtValidateBean;

public interface PdtValidateMapper{
	//查询产品均价校验器列表
	public List<PdtValidateBean> selectProductVaList(@Param("ptid")String ptid) throws Exception;
	//获取价格类型列表
	public List<TpfgtBean> getTpfg() throws Exception;
	//根据价格类型名称获取价格类型
	public String getTpfgByTpnm(@Param("tpnm")String tpnm) throws Exception;
	//查询货币对列表
	public List<CurrmsgBean> selectExnm(@Param("ptid")String ptid,@Param("tpfg")String tpfg,
			@Param("term")String term,@Param("cxfg")String cxfg) throws Exception;
	//添加校验器
	public boolean addProductVa(@Param("ptid")String ptid,@Param("pdtVa")PdtValidateBean pdtVa) throws Exception;
	//修改校验器
	public boolean updateProductVa(@Param("ptid")String ptid,@Param("pdtVa")PdtValidateBean pdtVa) throws Exception;
	//删除校验器
	public boolean deleteProductVa(@Param("ptid")String ptid,@Param("pdtVa")PdtValidateBean pdtVa) throws Exception;
	//启用/停用
	public boolean updateUsfg(@Param("ptid")String ptid,@Param("pdtVa")PdtValidateBean pdtVa) throws Exception;
}