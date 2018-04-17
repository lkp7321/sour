package com.ylxx.fx.service.impl.userimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.Trd_ogcdDoms;
import com.ylxx.fx.core.domain.UserDoms;
import com.ylxx.fx.core.mapper.user.UserParaMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.service.user.UserService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	@Resource
	private UserParaMapper usermap;
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	/**
	 * 分页查询用户信息
	 */
	public PageInfo<UserDoms> getPageUserList(Integer pageNo, Integer pageSize,
			String userKey) {
		log.info("分页查询用户信息:");
		List<UserDoms> list = null;
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			if(curUser.getUsnm().equals("Administrator")||curUser.getUspt().equals("A004")){
				list = usermap.selectAllUser(curUser.getProd());
			}else{
				list = usermap.selectUser(curUser.getProd(),curUser.getUspt());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		PageInfo<UserDoms> page = new PageInfo<UserDoms>(list);
		if(page.getList()!=null && page.getList().size()>0){
			log.info("获取用户信息列表："+" 登录用户："+curUser.getUsnm()+" 产品:"+curUser.getProd()+" 成功!");
			for(int i = 0;i<list.size();i++){
				if(page.getList().get(i).getUsfg().equals("0")){
					page.getList().get(i).setUsfg("未复核");
				}else{
					page.getList().get(i).setUsfg("复核");
				}
			}
		}
		return page;
	}
	/**
	 * 查询所有用户信息
	 */
	public List<UserDoms> getAllUserList(String userKey) {
		List<UserDoms> list = null;
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			if(curUser.getUsnm().equals("Administrator")||curUser.getUspt().equals("A004")){
				list = usermap.selectAllUser(curUser.getProd());
			}else{
				list = usermap.selectUser(curUser.getProd(),curUser.getUspt());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(list!=null && list.size()>0){
			log.info("获取用户信息列表："+" 登录用户："+curUser.getUsnm()+" 产品:"+curUser.getProd()+" 成功!");
			for(int i = 0;i<list.size();i++){
				if(list.get(i).getUsfg().equals("0")){
					list.get(i).setUsfg("未复核");
				}else{
					list.get(i).setUsfg("复核");
				}
			}
		}
		return list;
	}

	/**
	 * 添加用户信息
	 */
	public boolean insertUsers(String userKey, User user) {
		log.info("添加新用户");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			usermap.insertUser(user);
		} catch (Exception e) {
			return false;
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("用户信息");
		logfile.setRemk("添加用户");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"添加用户:"+user.getUsnm()+",id:"+user.getUsid()+",角色:"+user.getUspt());
		logfile.setVnew("添加成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}

	/**
	 * 修改用户信息
	 */
	public boolean updateUsers(String userKey, User user, String usnm, String prod) {
		log.info("修改用户");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			usermap.updateUser(user, usnm, prod);
		} catch (Exception e) {
			log.error("修改用户出错");
			log.error(e.getMessage(), e);
			return false;
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("用户信息");
		logfile.setRemk("修改用户");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"修改用户:"+usnm+",角色:"+user.getUspt()+",真实姓名:"+user.getCmpt()+
				",机构:"+user.getCstn()+",ip:"+user.getMacip()+
				",邮箱:"+user.getMail()+",移动电话:"+user.getMbtl()+
				",备注:"+user.getRmrk()+",传真:"+user.getUfax());
		logfile.setVnew("修改成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}
	/**
	 * 删除用户信息
	 */
	public boolean deleteUsers(String userKey, User user) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			usermap.deleteUser(user);
		} catch (Exception e) {
			log.error("删除用户出错");
			log.error(e.getMessage(), e);
			return false;
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("用户信息");
		logfile.setRemk("删除用户");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"删除用户:"+user.getUsnm()+",id:"+user.getUsid()+",角色:"+user.getUspt());
		logfile.setVnew("删除成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}

	/**
	 * 添加用户时判断用户是否存在
	 */
	public int isUsers(String usnm, String prod) {
		// TODO Auto-generated method stub
		return usermap.isExitUser(usnm,prod);
	}
	/**
	 * 初始化角色下拉框
	 */
	public List<RoleBean> initSelRole(String prod) {
		// TODO Auto-generated method stub
		return usermap.initRole(prod);
	}
	/**
	 * 初始化机构下拉框
	 */
	public List<Trd_ogcdDoms> initSelOgnm(String prod) {
		// TODO Auto-generated method stub
		if(prod.equals("JH01")) {
			return usermap.initOgnm2();
		}else {
			return usermap.initOgnm();
		}
	}
	/**
	 * 获取用来初始化的密码
	 */
	public String selInPswd(String prod) {
		if(prod.equals("JH01")) {
			return usermap.selInitPassword();
		}else {
			return usermap.selInitPswd();
		}
	}
	/**
	 * 进行密码重置
	 */
	public boolean mitPswds(String userKey, String newPswd, String usnm, String prod) {
		log.info("重置用户密码");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			usermap.mitPswd(newPswd, usnm, prod);
		} catch (Exception e) {
			log.error("重置密码出错");
			log.error(e.getMessage(), e);
			return false;
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("用户信息密码");
		logfile.setRemk("重置密码");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"用户:"+usnm);
		logfile.setVnew("重置成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}

	/**
	 * 用户复核
	 */
	public boolean userFsg(String userKey, String usfg, String usnm, String prod) {
		log.info("用户复核");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			usermap.userFg(usfg, usnm, prod);
		} catch (Exception e) {
			log.error("用户复核出错");
			log.error(e.getMessage(), e);
			return false;
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("用户复核");
		logfile.setRemk("用户复核");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"复核用户:"+usnm+",状态+"+("0".equals(usfg)?"未复核":"复核"));
		logfile.setVnew("复核成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}
	
	/**
	 * 修改密码
	 */
	public boolean updatePswd(String userKey, String newPswd,String utime, String usnm, String prod) {
		log.info("修改用户密码");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("修改密码");
		logfile.setRemk("修改密码");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"用户:"+usnm+",更新时间："+utime+",新密码："+newPswd);
		try {
			usermap.updatePswds(newPswd, utime, usnm, prod);
		} catch (Exception e) {
			log.error("修改密码错误");
			log.error(e.getMessage(), e);
			logfile.setVnew("修改失败");
			logfileCmdService.insertLog(logfile);
			return false;
		}
		logfile.setVnew("修改成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}
	
	/**
	 * 查询登陆异常用户
	 */
	public List<UserDoms> listError(String prod, String ognm) {
		log.info("查询登录异常用户");
		return usermap.listErrorUser(prod, ognm);
	}
	
	/**
	 * 清空异常用户
	 */
	public boolean deleteError(String userKey, String prod) {
		log.info("清空异常登录用户");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			usermap.deleteErrorUser(prod);
		} catch (Exception e) {
			log.error("清空异常登陆用户失败");
			log.error(e.getMessage(), e);
			return false;
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("异常登录用户");
		logfile.setRemk("清空异常登录用户");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"清空产品："+prod+"所有登录异常用户");
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}

}
