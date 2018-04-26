package com.crm.dao;

import java.util.List;

import com.crm.entity.Custom;
public interface CustomDao {
public int getAllCount(String cusid,int userId);
public List<Custom> getPageList(int currPage,int pageSize,String cusid,int userId);
public Custom findById(int id);
public int updateCustom(String cusId,String cusName,String level,String area,String dept,String industy,String type,int id);
public int delete(String[] id);
public int findByCusId(String cusid);
public int addCustom(String cusid, String name, String level, String area,
		int managerid,String dept,String inderstry, String type);
}
