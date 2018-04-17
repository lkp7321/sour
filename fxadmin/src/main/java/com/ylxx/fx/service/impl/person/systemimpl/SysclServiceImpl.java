package com.ylxx.fx.service.impl.person.systemimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.system.SysclMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.system.SysclService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.Sysctl;
import com.ylxx.fx.service.po.Testtrac;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodeSystem;
import com.ylxx.fx.utils.ResultDomain;
@Service("sysclService")
public class SysclServiceImpl implements SysclService{
	
	private static final Logger log = LoggerFactory.getLogger(SysclServiceImpl.class);
	@Resource
	private SysclMapper sysclmap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	//系统状态  查询所有
	public String getAllTesttrac(CurrUser curUser) {
		List<Testtrac> list = null;
		try{
			list = sysclmap.selectTesttrac(curUser.getProd());
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getUsfg().equals("0")){
					list.get(i).setUsfg("启用");
				}else{
					list.get(i).setUsfg("停用");
				}
			}
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_0.getCode(), null);
		}
	}
	/**
	 * 删除 系统状参数
	 */
	public String deleteTesttrac(CurrUser curUser, String cuac){
		int f = 0;
		try{
			f = sysclmap.deleteSyscl(curUser.getProd(), cuac);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(f>0){
			Logfile logfile = new Logfile();
			logfile.setProd(curUser.getProd());
			logfile.setUsem(curUser.getUsnm());
			logfile.setVnew("成功");
			logfile.setVold("登录ip:"+curUser.getCurIP()+"卡号:"+cuac);
			logfile.setTymo("系统状态");
			logfile.setRemk("删除系统参数");
			logfileCmdService.insertLog(logfile);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "删除成功");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_03.getCode(), null);
		}
	}
	/**
	 * 修改 系统状态参数
	 */
	public String updateTesttrac(CurrUser curUser,Testtrac testtrac){
		int f = 0;
		try{
			f = sysclmap.upTesttrac(curUser.getProd(), testtrac);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(f>0){
			Logfile logfile = new Logfile();
			logfile.setProd(curUser.getProd());
			logfile.setUsem(curUser.getUsnm());
			logfile.setVnew("成功");
			logfile.setVold("登录ip:"+curUser.getCurIP()+"交易账号:"+testtrac.getTrac()+"卡号:"+testtrac.getCuac()+"状态:"+testtrac.getUsfg());
			logfile.setTymo("系统状态");
			logfile.setRemk("修改系统参数");
			logfileCmdService.insertLog(logfile);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_02.getCode(), null);
		}
	}
	
	/**
	 * 添加  系统状态参数
	 */
	public String addTesttrac(CurrUser curUser,Testtrac testtrac){
		String trac ="";
		try {
			trac = sysclmap.selectTesttracD(curUser.getProd(), testtrac.getCuac());
			if(!trac.equals("")) {
				sysclmap.insertTesttracD(curUser.getProd(), testtrac, trac);
				Logfile logfile = new Logfile();
				logfile.setProd(curUser.getProd());
				logfile.setUsem(curUser.getUsnm());
				logfile.setVnew("成功");
				logfile.setVold("登录ip:"+curUser.getCurIP()+"交易账号:"+trac+"卡号:"+testtrac.getCuac());
				logfile.setTymo("系统状态");
				logfile.setRemk("添加系统参数");
				logfileCmdService.insertLog(logfile);
				return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "添加成功");
			}else {
				return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "卡号不存在");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "添加失败或卡号不存在");
		}
	}
	
	//查询系统总控状态
	public String selecSyst(String prod){
		String s1 = "";
		try {
			s1 = sysclmap.selectSysctl(prod);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			s1 = "";
		}
		if(s1.equals("0")){
			s1="开启";
		}else if(s1.equals("1")){
			s1="关闭";
		}else{
			s1="测试";
		}
		return s1;
	}
	
	//查询平盘总控状态
	public String selecPpSyst(String prod){
		String s2 = "";
		try {
			s2 = sysclmap.selectpp(prod);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			s2="";
		}
		if(s2.equals("0")){
			s2="开启";
		}else if(s2.equals("1")){
			s2="关闭";
		}else{
			s2="测试";
		}
		return s2;
	}
	public String initCon(CurrUser curUser){
		String s1 = selecSyst(curUser.getProd());
		String s2 = selecPpSyst(curUser.getProd());
		String []ss = {s1,s2};
		return JSON.toJSONString(ss);
	}
	public String upPpcon(CurrUser curUser, Sysctl sysctl){
		int f =0;
		sysctl.setProd(curUser.getProd());
		try{
			f = sysclmap.upPpsys(sysctl);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(f>0){
			String s = selecPpSyst(curUser.getProd());
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), s);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_04.getCode(), null);
		}
	}
	
	public String upCon(CurrUser curUser, Sysctl sysctl){
		int f = 0;
		sysctl.setProd(curUser.getProd());
		try{
			f = sysclmap.upSysclCon(sysctl);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(f>0){
			String s = selecSyst(curUser.getProd());
			Logfile logfile = new Logfile();
			logfile.setProd(curUser.getProd());
			logfile.setUsem(curUser.getUsnm());
			logfile.setVnew("成功");
			logfile.setVold("登录ip:"+curUser.getCurIP()+"修改系统状态:"+sysctl.getUsfg());
			logfile.setTymo("系统状态");
			logfile.setRemk("修改系统状态");
			logfileCmdService.insertLog(logfile);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), s);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_05.getCode(), "修改系统总控状态失败");
		}
	} 
	
	public boolean upcon(CurrUser curUser,Sysctl sysctl){
		int f = 0;
		sysctl.setProd(curUser.getProd());
		try{
			f = sysclmap.upSysclCon(sysctl);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(f>0){
			Logfile logfile = new Logfile();
			logfile.setProd(curUser.getProd());
			logfile.setUsem(curUser.getUsnm());
			logfile.setVnew("成功");
			logfile.setVold("登录ip:"+curUser.getCurIP()+"修改系统状态:"+sysctl.getUsfg());
			logfile.setTymo("系统状态");
			logfile.setRemk("修改系统状态");
			logfileCmdService.insertLog(logfile);
			return true;
		}else{
			return false;
		}
	}
}
