package com.ylxx.fx.service.person.businesspara;

import java.util.*;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.service.po.Trd_Favrule;

public interface PereFavruleService {
	
	public boolean openChannel (String userKey, List<Trd_Favrule> chlist);
	public boolean closeChannel (String userKey, List<Trd_Favrule> chlist);
	//查询分页数据分行优惠
	public PageInfo<Favrule> getPereFenfavrule(String ogcd,
			Integer pageNo, Integer pageSize);
	//导出分行数据
	public List<Favrule> getAllPereFenfavrule(String ogcd);
	//初始化当前修改的页面规则
	public List<FavourRule> onSelFavrule(String rule, String fvid);
	//客户渠道
	public List<Map<String, Object>> custboxData();
	//修改保存
	public boolean saveFavruleRule(String userKey, List<FavourRule> list, String fvid,
			String ogcd);
	//撤销与修改流水导出
	public List<Tranlist> selectTranlist(String userkey ,String start ,String end);
	//撤销与修改流水查询
	public PageInfo<Tranlist> pageTranlist(String userkey ,String start ,String end,
			Integer pageNo, Integer pageSize);
}
