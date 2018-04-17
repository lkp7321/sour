package com.ylxx.fx.service.impl.userimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.user.RoleMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.service.user.RoleService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;

@Service("roleService")
public class RoleServiceImpl implements RoleService{
	private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	@Resource
	private RoleMapper roleMap;
	/**
	 * 获取角色信息表
	 */
	public List<RoleBean> listRole(String prod) {
		return roleMap.getRolelist(prod);
	}
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public boolean addRoles(String userKey, RoleBean role)throws Exception {
		log.info("添加角色信息");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			roleMap.addRole(role);
		} catch (Exception e) {
			log.error("添加角色出错");
			log.error(e.getMessage(), e);
			return false;
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("角色管理");
		logfile.setRemk("添加角色");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"添加角色:,名称"+role.getPtnm()+",id:"+role.getPtid()+",备注:"+role.getRemk()+",状态:"+("0".equals(role.getUsfg())?"启用":"停用"));
		logfile.setVnew("添加成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}
	
	/**
	 * 删除角色
	 * @param ptid
	 * @param prod
	 * @return
	 * @throws Exception
	 */
	public boolean deleteRole(String userKey, String ptid, String prod)throws Exception {
		log.info("删除角色信息");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			roleMap.deleteRole(ptid, prod);
			roleMap.deleteRoleNrgt(ptid);
		} catch (Exception e) {
			log.error("删除角色出错");
			log.error(e.getMessage(), e);
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("角色管理");
		logfile.setRemk("删除角色");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"删除角色:,id:"+ptid);
		logfile.setVnew("删除成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public boolean upRole(String userKey, RoleBean role)throws Exception{
		log.info("修改角色信息");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			roleMap.upRole(role);
		} catch (Exception e) {
			log.error("修改角色出错");
			log.error(e.getMessage(), e);
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("角色管理");
		logfile.setRemk("修改角色");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"修改角色:,名称："+role.getPtnm()+",id:"+role.getPtid()+",说明:"+role.getRemk());
		logfile.setVnew("修改成功");
		logfileCmdService.insertLog(logfile);
		return true;
		
	}

	@Override
	public int hasRole(String ptid,String ptnm, String prod) {
		
		return roleMap.hasrole(ptid,ptnm,prod);
	}

	@Override
	public int getroles(RoleBean role) {
		// TODO Auto-generated method stub
		return roleMap.hasroles(role);
	}
	
}
