package com.crm.dao.impl;

import java.util.List;

import com.crm.dao.CustomDao;
import com.crm.entity.Custom;
import com.crm.util.DBUtil;

public class CustomDaoImpl implements CustomDao{
	/**
	 * 获取记录总量
	 * @param cusid
	 * @param userId
	 * @return
	 */
	public int getAllCount(String cusid,int userId) {
		String sql="SELECT * FROM custom1 WHERE managerid="+userId;
		if(cusid!=null&&!cusid.equals("")){
			sql+=" AND cusid= '"+cusid+"'";
			
		}
		List<Custom> list=DBUtil.getObjectList(sql,null,Custom.class);
		return list.size();
	}

	/**
	 * 分页
	 * @param currPage
	 * @param pageSize
	 * @param cusid
	 * @param userId
	 * @return
	 */
	public List<Custom> getPageList(int currPage, int pageSize, String cusid,int userId) {
		String sql="SELECT * FROM custom1 WHERE managerid="+userId;
		if(cusid!=null&&!cusid.equals("")){
			sql+=" AND cusid='"+cusid+"'";
	 	}
		sql+=" order by id asc limit ?,?";
		Object[] params={(currPage-1)*pageSize,pageSize};
		List<Custom> list=DBUtil.getObjectList(sql, params, Custom.class);
		return list;
	}
	public Custom findById(int id) {
		 String sql="SELECT * FROM custom1 WHERE id="+id;
		 List<Custom> list=DBUtil.getObjectList(sql, null, Custom.class);
		 Custom custom=list.get(0);
		return custom;
	}
	public int updateCustom(String cusId, String cusName, String level,
			String area, String dept, String industy, String type, int id) {
		String sql="UPDATE custom1 SET cusid=?,name=?,level=?,area=?," +
				"cusdept=?,cusindursty=?,custype=? WHERE id=?";
		Object[] params={cusId,cusName,level,area,dept,industy,type,id};
		int rows=DBUtil.execute(sql, params);
		return rows;
	}
	public int delete(String[] id) {
		String sqlstr="";
		for(int i=0;i<id.length;i++){
			sqlstr+=id[i]+",";// in(12,2,4)
		}
		
		 String sql="DELETE FROM custom1 " +
		 		"WHERE id in("+sqlstr.substring(0,sqlstr.length()-1)+")";
		 int rows=DBUtil.execute(sql,null);
		return rows;
	}
	public int addCustom(String cusid, String name, String level, String area,
			int managerid,String dept,String inderstry, String type) {
		 String sql="INSERT INTO custom1(cusid,name,level,area,managerid,cusdept,cusindursty,custype) VALUES(?,?,?,?,?,?,?,?);";
		 Object[] params={cusid,name,level,area,managerid,dept,inderstry,type};
		 int row=DBUtil.execute(sql, params);
		return row;
	}
	public int findByCusId(String cusid) {
		String sql="SELECT * FROM custom1 WHERE cusid="+cusid;
		List<Custom> list=DBUtil.getObjectList(sql,null,Custom.class);
		return list.size();
	}
	
}
