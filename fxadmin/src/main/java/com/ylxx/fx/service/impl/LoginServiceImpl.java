package com.ylxx.fx.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.ProductDoms;
import com.ylxx.fx.core.mapper.LoginMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.LoginService;
import com.ylxx.fx.service.MenuService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ErrorCode;
import com.ylxx.fx.utils.GetIp;
import com.ylxx.fx.utils.ResultDomain;

@Service("loginServicer")
public class LoginServiceImpl implements LoginService {
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource
	private LoginMapper logimap;
	
	
	/**
	 * 点击登陆时的操作
	 */
	public String login(User user, HttpServletRequest req){
		String ips = GetIp.getIpAddr(req);
		String message = "";
		User user1=null;
		user1 = getUser(user);
		String nowtime = DataTimeClass.getNowDay();//获取当前时间
		Logfile logfile = new Logfile();
		logfile.setProd(user1.getProd());
		logfile.setRzdt(nowtime);
		logfile.setRzsj(DataTimeClass.getCurTime());
		logfile.setTymo("用户登录");
		logfile.setUsem(user1.getUsnm());
		logfile.setRemk("用户登录");
		if(user1.getLtime().equals(nowtime)){//最后一次登录是在今天
			LOGGER.info("【进行登录验证】："+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【最后一次登陆是在今天】");
			//复用代码
			message = loger(user1, user, logfile, ips);
		}else {//最后一次登录不在今天
			LOGGER.info("【进行登录验证】："+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【最后一次登陆不在今天】");
			int a = upTime(nowtime, user1);//将最后一次登录时间更新为今天
			if(a>0){
				LOGGER.info("【进行登录验证】："+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【更新LTIME时间为今天，成功】");
				//复用代码
				message = loger(user1,user,logfile,ips);
			}else{
				LOGGER.error("【进行登录验证】："+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【更新最后一次登录时间为今天，失败】");
				logfile.setVnew("登录失败");
				logfile.setVold("登录ip:"+ips+"更新最后登陆时失败");
				message = ErrorCode.E_0013.getCode();
			}
		}
		logfileCmdService.insertLog(logfile);
		return message;
	}
	
	/**
	 * 判断登陆时间为当天，主要是复用此代码
	 * @param user1：user1为查询过的
	 * @param user：user为前台传过来的
	 * @param logfile
	 * @param ips
	 * @return
	 */
	public String loger(User user1,User user, Logfile logfile, String ips){
		String message="";
		if(user1.getUsnm().equals("Administrator") || (user1.getEcount()<6 && !user1.getUsnm().equals("Administrator"))){
			int i = findRole(user);//判断用户角色是否存在，可用
			if(i>0){
				LOGGER.info("【进行登录验证】："+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【该用户角色存在】");
				User user2 = onLoger(user);
				if(user2==null){
					int a = upErr(user1);
					if(a>0){
						LOGGER.error("【进行登录验证】"+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【输入密码错误，更新错误次数成功】");
						message=ErrorCode.E_0015.getCode();
					}else{
						LOGGER.error("【进行登录验证】"+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【输入密码错误，更新错误次数出错】");
						message=ErrorCode.E_0014.getCode();
					}
					logfile.setVnew("登录失败");
					logfile.setVold("登录ip:"+ips+"密码错误");
				}else{
					LOGGER.info("进行登录验证"+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【密码验证成功】");
					String utime = user2.getUtime();
					Date xx1 = null;
					Date xx2 = null;
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						String ntime = DataTimeClass.getNowDay();
						xx1 = sdf.parse(utime);
						xx2 = sdf.parse(ntime);
						int day = (int)((xx2.getTime()-xx1.getTime())/(1000*3600*24));
						LOGGER.info("【进行登录验证】"+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【登录间隔为】："+day+"豪秒");
						if(day>=30 && !user2.getUsnm().equals("Administrator")){
							LOGGER.info("【进行登录验证】"+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【超过30天未修改密码】");
							logfile.setVnew("登录校验");
							logfile.setVold("登录ip:"+ips+"超过30天未修改密码");
							message = ErrorCode.E_0016.getCode();//确认 OR 取消
						}else if(user2.getUtime().equals("99999999") && !user2.getUsnm().equals("Administrator")){
							LOGGER.info("【进行登录验证】"+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【新用户首次登陆】");
							logfile.setVnew("登录校验");
							logfile.setVold("登录ip:"+ips+"新用户首次登录");
							message = ErrorCode.E_0017.getCode();//确认 OR 取消
						}else if(user2.getUsnm().equals("Administrator") || (day<30 && !user2.getUsnm().equals("Administrator"))){
							//现在改为，更新用户的登陆更新时间
							if(!user2.getUsnm().equals("Administrator")){
								upOveTime(ntime, user2);
							}
							LOGGER.info("【进行登录验证】"+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【登陆验证成功】");
							logfile.setVnew("登录成功");
							logfile.setVold("登录ip:"+ips+"成功");
							LOGGER.info("【为什么我存了两次】");
							message = "success";//存入登陆成功的信息
						}else {
							message = null;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}else{
				LOGGER.error("【进行登录验证】"+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【该用户角色不存在】");
				logfile.setVnew("登录失败");
				logfile.setVold("登录ip:"+ips+"该用户角色不存在，或未复核");
				message = ErrorCode.E_0012.getCode();
			}
		}
		else{
			LOGGER.error("【进行登录验证】"+" 用户："+user.getUsnm()+" 产品："+user.getProd()+",【该用户已被锁】");
			logfile.setVnew("登录失败");
			logfile.setVold("登录ip:"+ips+"登录错误次数大于6,用户已被锁");
			message = ErrorCode.E_0011.getCode();
		}
		return message;
	}
	
	/**
	 * 获取用户产品的list
	 */
	public List<ProductDoms> findProduct(String usnm) {
		
		return logimap.findProList(usnm);
	}
	public List<ProductDoms> findProduct1(String usnm) {
		
		return logimap.findProList1(usnm);
	}
	
	/**
	 * 判断用户名和密码
	 */
	public User onLoger(User user) {
		
		return logimap.onLogin(user);
	}

	/**
	 * 判断角色是否存在
	 */
	public int findRole(User user) {
		
		return logimap.findUserRole(user);
	}

	/**
	 * 获取用户信息
	 */
	public User getUser(User user) {
		LOGGER.info("获取用户上次登录时间：");
		return logimap.findUser(user);
	}

	/**
	 * 更新错误系数
	 */
	public int upErr(User user) {
		
		return logimap.upErrCount(user);
	}
	
	/**
	 * 更新初始登录时间
	 */
	public int upTime(String nowtime, User user){
		
		return logimap.upLoginTime(nowtime, user);
		}
	
	/**
	 * 更新登陆完成的时间
	 */
	public int upOveTime(String nowtime,User user){
		
		return logimap.upOverTime(nowtime, user);	
		}
	
	/**
	 * 
	 */
	@Override
	public String findPswd(String usnm, String prod, String oldPswd, String newPswd, HttpServletRequest req) {
		String ips = GetIp.getIpAddr(req);
		String nowTime = DataTimeClass.getNowDay();
		try {
			if(logimap.selectPassword(usnm, oldPswd, prod) > 0) {
				LOGGER.info("原密码输入正确");
				logimap.updatePasswordBegin(usnm, newPswd, prod);
			}else {
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "原密码输入错误，请重新输入!");
			}
		} catch (Exception e) {
			LOGGER.error("新用户修改密码出现错误");
			LOGGER.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "密码修改失败，请重新输入!");
		}
		User user = new User();
		user.setProd(prod);
		user.setPswd(newPswd);
		user.setUsnm(usnm);
		upOveTime(nowTime, user);
		Logfile logfile = new Logfile();
		logfile.setProd(prod);
		logfile.setTymo("用户登录");
		logfile.setUsem(usnm);
		logfile.setRemk("修改登录密码");
		logfile.setVnew("修改成功");
		logfile.setVold("登录ip:"+ips+"登录时间大于30天，或初次登录,修改登录密码");
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "密码修改成功，请登陆!");
	}
}
