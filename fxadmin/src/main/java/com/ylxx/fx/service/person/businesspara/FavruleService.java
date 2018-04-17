package com.ylxx.fx.service.person.businesspara;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.utils.CurrUser;

public interface FavruleService {
	//分行优惠查询
	public List<Favrule> getFavruletlist(String prod,String ogcd);
	//分行优惠查询分页
	public PageInfo<Favrule> getPageFavrulelist(
			Integer pageNo, Integer pageSize, 
			String prod, String ogcd);
	public boolean openStat(CurrUser curUser, List<Favrule> list, String ip);
	public boolean stopStat(CurrUser curUser, List<Favrule> list, String ip);
	
	public List<FavourRule> onSelFavrule(CurrUser curUser, String rule, String fvid);
	
	public boolean saveFavruleRule(CurrUser curUser, List<FavourRule> list, 
			String fvid, String ogcd, String ip);
	/*
	 * 初始化优惠规则
	 */
	public boolean insertFavrule(CurrUser curUser, String ogcd, String ip);
	
	public List<Map<String,String>> getfavbox(String prod, String fvid);
}
