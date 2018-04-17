package com.ylxx.fx.service.person.price;

import java.util.List;

import com.ylxx.fx.service.po.PdtValidateBean;

public interface IPdtValidateService {

	//查询产品均价校验器列表
	public String selectProductVaList(String prod) throws Exception;
	//获取价格类型列表
	public String getTpfg() throws Exception;
	//根据价格类型名称获取价格类型
	public String getTpfgByTpnm(String tpnm) throws Exception;
	//查询货币对列表
	public String selectExnm(String ptid,String tpfg,String term,String cxfg) throws Exception;
	//添加校验器
	public String addProductVa(String userKey,String prod,PdtValidateBean pdtVa) throws Exception;
	//修改校验器
	public String updateProductVa(String userKey,String prod,PdtValidateBean pdtVa) throws Exception;
	//删除校验器
	public String deleteProductVa(String userKey,String prod,PdtValidateBean pdtVa) throws Exception;
	//启用
	public String openUsfg(String userKey,String prod,List<PdtValidateBean> pdtVas) throws Exception;
	//停用
	public String closeUSFG(String userKey,String prod,List<PdtValidateBean> pdtVas) throws Exception;
}

