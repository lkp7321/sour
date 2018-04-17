package com.ylxx.fx.service.person.ppmagager;

import com.ylxx.fx.service.po.Register;

public interface IGoldHandRegisterService {

	//敞口名称改变时触发函数
	public String onchange(String prod,int ckdata) throws Exception;
	//判断买入币种在标准币别对的左边还是右边
	public String selectSamt(String prod,String bexnm,String sexnm) throws Exception;
	//判断卖出币种在标准币别对的左边还是右边
	public String selectBamt(String prod,String bexnm,String sexnm) throws Exception;
	//查询货币对
	public String selExnm(String prod,String exnm1, String exnm2) throws Exception;
	//执行登记---纸黄金
	public String registerCK(String userKey,Register ckno) throws Exception;
	//执行登记---积存金
	public String spgoldregisterCK(String userKey,Register ckno) throws Exception;
}

