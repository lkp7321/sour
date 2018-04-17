package com.ylxx.fx.core.domain;

import java.util.List;

import com.ylxx.fx.service.po.Nrlrgt;
import com.ylxx.fx.service.po.NrlrgtUp;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.service.po.User;

public class Vo {
	private User user;//用户
	private RoleBean role;//角色
	private NrlrgtUp nrup;//权限
	private Nrlrgt nr;//权限
	private List<NrlrgtUp> list;//权限数组
	private LoginUser loginuser;//登录用户
	private String newpswd;//新密码
	private Integer pageNum;
	private Integer pageSize;
	
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getNewpswd() {
		return newpswd;
	}
	public void setNewpswd(String newpswd) {
		this.newpswd = newpswd;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public Vo(User user, RoleBean role, NrlrgtUp nrup, Nrlrgt nr,
			LoginUser loginuser,List<NrlrgtUp> list,String newpswd,Integer pageNum,Integer pageSize) {
		super();
		this.user = user;
		this.role = role;
		this.nrup = nrup;
		this.nr = nr;
		this.list = list;
		this.loginuser=loginuser;
		this.newpswd=newpswd;
		this.pageNum=pageNum;
		this.pageSize=pageSize;
	}
	public Vo() {
		super();
	}
	
}
