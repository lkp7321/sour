package com.ylxx.fx.core.domain;

import java.util.List;

import com.ylxx.fx.service.po.Nrlrgt;
import com.ylxx.fx.service.po.NrlrgtUp;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.utils.Table;

public class UserVo {
	private String tableName;
	private List<Table> tableList;
	private String prod;
	private Integer pageNo;
	private String userKey;
	private User user;//用户
	private RoleBean role;//角色
	private NrlrgtUp nrup;//权限
	private Nrlrgt nr;//权限
	private List<NrlrgtUp> list;//权限数组
	private LoginUser loginuser;//登录用户
	private String newpswd;//新密码
	private Integer pageNum;
	private Integer pageSize;
	
	
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Table> getTableList() {
		return tableList;
	}
	public void setTableList(List<Table> tableList) {
		this.tableList = tableList;
	}
	public RoleBean getRole() {
		return role;
	}
	public void setRole(RoleBean role) {
		this.role = role;
	}
	public NrlrgtUp getNrup() {
		return nrup;
	}
	public void setNrup(NrlrgtUp nrup) {
		this.nrup = nrup;
	}
	public Nrlrgt getNr() {
		return nr;
	}
	public void setNr(Nrlrgt nr) {
		this.nr = nr;
	}
	public List<NrlrgtUp> getList() {
		return list;
	}
	public void setList(List<NrlrgtUp> list) {
		this.list = list;
	}
	public LoginUser getLoginuser() {
		return loginuser;
	}
	public void setLoginuser(LoginUser loginuser) {
		this.loginuser = loginuser;
	}
	public String getNewpswd() {
		return newpswd;
	}
	public void setNewpswd(String newpswd) {
		this.newpswd = newpswd;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
