package com.ylxx.fx.service.user;

import java.util.List;

import com.ylxx.fx.service.po.RoleBean;

public interface RoleService {
	
	List<RoleBean> listRole(String prod);
	
	boolean addRoles(String userKey, RoleBean role)throws Exception;
	
	boolean deleteRole(String userKey, String ptid,String prod)throws Exception;
	
	boolean upRole(String userKey, RoleBean role)throws Exception;

	int hasRole(String ptid, String ptnm, String prod);
	int getroles(RoleBean role);
}
