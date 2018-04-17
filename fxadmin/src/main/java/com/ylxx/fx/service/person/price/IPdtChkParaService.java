package com.ylxx.fx.service.person.price;

public interface IPdtChkParaService {

	//得到产品校验参数
	String getChkList(String prod, Integer pageNo, Integer pageSize) throws Exception;
	//更新启用状态 启用0 停用1  
	//测试需要删除  参数List<PdtChkParaBean> pdtChks.
	String updateChk(String userkey,String prod,String usfg) throws Exception;
	
}

