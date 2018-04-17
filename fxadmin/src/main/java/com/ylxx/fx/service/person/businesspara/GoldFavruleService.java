package com.ylxx.fx.service.person.businesspara;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;

public interface GoldFavruleService {
	public List<Map<String,Object>> selectOrganlist(String userkey, String userorgleve);
	public List<Map<String, Object>> comboxData();
	public List<Map<String,Object>> custboxData();
	//分页数据
	public PageInfo<Favrule> selectAllFavrule(
			String Ogcd, Integer pageNo, Integer pageSize);
	
	public boolean openStat(String userKey, List<Favrule> list);
	public boolean stopStat(String userKey, List<Favrule> list);
	public List<FavourRule> onSelFavrule(String rule, String fvid);
	public boolean saveFavruleRule(String userKey, List<FavourRule> list, String fvid,
			String ogcd);
	public boolean insertFavrule(String userKey, String ogcd);
}
