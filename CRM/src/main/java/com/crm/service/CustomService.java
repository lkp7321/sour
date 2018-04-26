package com.crm.service;

import com.crm.entity.Custom;
import com.crm.util.Pager;

public interface CustomService {
public Pager<Custom> getPager(int currPage,int pageSize,String cusId,int userId);
public Custom getDetailMasage(int id);
public int updateCustom(String cusId, String cusName, String level,
		String area, String dept, String industy, String type, int id);
public int deleteCustom(String[] id);
public int findByCusId(String cusid);
public int addCustom(String cusid, String name, String level, String area,
		int managerid,String dept,String inderstry, String type);
}
