package com.ylxx.fx.service.person.system;

import com.ylxx.fx.service.po.Sysctl;
import com.ylxx.fx.service.po.Testtrac;
import com.ylxx.fx.utils.CurrUser;
/**
 * 系统状态
 * @author lz130
 *
 */
public interface SysclService {
	String getAllTesttrac(CurrUser curUser);
	String deleteTesttrac(CurrUser curUser, String cuac);
	String updateTesttrac(CurrUser curUser,Testtrac testtrac);
	/**
	 * 添加
	 * @param curUser
	 * @param testtrac
	 * @return
	 */
	String addTesttrac(CurrUser curUser,Testtrac testtrac);
	
	String initCon(CurrUser curUser);
	String upPpcon(CurrUser curUser, Sysctl sysctl);
	String upCon(CurrUser curUser, Sysctl sysctl);
	boolean upcon(CurrUser curUser,Sysctl sysctl);
	//查询系统总控状态
	String selecSyst(String prod);
}
