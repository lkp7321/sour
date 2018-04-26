package com.crm.service.impl;

import com.crm.dao.CustomDao;
import com.crm.dao.impl.CustomDaoImpl;
import com.crm.entity.Custom;
import com.crm.service.CustomService;
import com.crm.util.Pager;

public class CustomServiceImpl implements CustomService{
private CustomDao dao=new CustomDaoImpl();
	public Pager<Custom> getPager(int currPage, int pageSize, String cusId,int userId) {
		Pager<Custom> pager=new Pager<Custom>();
		pager.setAllPage(dao.getAllCount(cusId,userId));
		pager.setCurPage(currPage);
		pager.setPageSize(pageSize);
		pager.setList(dao.getPageList(currPage, pageSize, cusId,userId));
		return pager;
	}
	public Custom getDetailMasage(int id) {
		 Custom custom=dao.findById(id);
		return custom;
	}
	public int updateCustom(String cusId, String cusName, String level,
			String area, String dept, String industy, String type, int id) {
		int rows=dao.updateCustom(cusId, cusName, level, area, dept, industy, type, id);
		return rows;
	}
	public int deleteCustom(String[] id) {
	 int rows=dao.delete(id);
		return rows;
	}
	public int addCustom(String cusid, String name, String level, String area,
			int managerid, String dept, String inderstry, String type) {
		 int rows=dao.addCustom(cusid, name, level, area, managerid, dept, inderstry, type);
		return rows;
	}
	public int findByCusId(String cusid) {
		int size=dao.findByCusId(cusid);
		return size;
	}

}
