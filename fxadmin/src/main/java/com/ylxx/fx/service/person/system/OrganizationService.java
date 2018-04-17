package com.ylxx.fx.service.person.system;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.Trd_ogcd;

public interface OrganizationService {
	public Trd_ogcd getUserog(String userKey);
	public String getAllOgcd(String userKey);
	public PageInfo<Trd_ogcd> getPageAllOgcd(String userKey, Integer pageNo, Integer pageSize);
	/**
	 * 添加机构
	 * @param trdOgcd
	 * @param userKey
	 * @return
	 */
	String insert(Trd_ogcd trdOgcd, String userKey);
	public String updateOg(Trd_ogcd trdOgcd, String userKey);
	public String curLeveList(String userKey, String leve);
	public String organUpList(String userKey, String leve);
	//===
	public String delog(String userKey,String ogcd);
	public String getHeBing(String prod, String ogup, String ogcd);
	public String heBing(String userKey, String newogcd, String oldogcd);
	
}
