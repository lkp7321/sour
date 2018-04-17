package com.ylxx.fx.service.impl.person.systemimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.price.CurrmsgForAcc;
import com.ylxx.fx.core.mapper.person.system.CurrmsgMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.system.CurmsgService;
import com.ylxx.fx.service.po.Currmsg;
import com.ylxx.fx.service.po.Cytp;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodeSystem;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("curmsgService")
public class CurmsgServiceimpl implements CurmsgService {
	@Resource
	private CurrmsgMapper curmsgmap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	private static final Logger log = LoggerFactory.getLogger(CurmsgServiceimpl.class);
	//货币对管理查询所有
	public String getAllCurrmsg(String prod){
		List<Currmsg> list = null;
		try{
			if(prod.equals("P999")){
				list = curmsgmap.selectPriceP009();
			}else{
				 list = curmsgmap.selectPrice(prod);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setExtp(list.get(i).getExtp().equals("0") ? "直盘" : "交叉盘");
			}
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_0.getCode(), null);
		}
	}
	//所有
	public String getAllCurrmsgP7(){
		List<CurrmsgForAcc> list = null;
		try{
				list = curmsgmap.selectPriceP7();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setExtp(list.get(i).getExtp().equals("0") ? "直盘" : "交叉盘");
			}
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_0.getCode(), null);
		}
	}
	@Override
	public List<Map<String, Object>> selExnminit() {
		// TODO Auto-generated method stub
		return curmsgmap.selectPriceP7init();
	}
	public boolean upCurrmsgPrice7(CurrmsgForAcc currmsg, String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			a = curmsgmap.updatePriceP7(currmsg.getMult(), currmsg.getDivd(), currmsg.getExnm());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + ",更新"+currmsg.getExnm()+":  放大倍率"
					+ currmsg.getMult() + "计算系数:" + currmsg.getDivd() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			return true;
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + ",更新"+currmsg.getExnm()+":  放大倍率"
					+ currmsg.getMult() + "计算系数:" + currmsg.getDivd() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}
	
	@Override
	public List<Cytp> selCytpExnm(String prod) {
		List<Cytp> list = null;
		try {
			list = curmsgmap.selExnmList(prod);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	@Override
	public String insCurrPrice(String userKey, Currmsg currmsg) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			int a = curmsgmap.selCurrPrice(curUser.getProd(), currmsg.getExnm());
			if(a>0){
				return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "该货币对已存在");
			}else{
				int b = curmsgmap.insertCurrPrice(curUser.getProd(), currmsg);
				if(b>0){
					Logfile logfile = new Logfile();
					logfile.setProd(curUser.getProd());
					logfile.setUsem(curUser.getUsnm());
					logfile.setVnew("成功");
					logfile.setVold("登录ip:"+curUser.getCurIP()+"编号:"+currmsg.getExcd()+"货币对:"+currmsg.getExnm()+"中文名:"+currmsg.getExcn());
					logfile.setTymo("品种对管理");
					logfile.setRemk("添加货币对");
					logfileCmdService.insertLog(logfile);
					return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "添加成功");
				}else{
					return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "添加失败");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "添加货币对失败");
		}
		
	}
	@Override
	public String upsCurrPrice(String userKey, Currmsg currmsg) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		int b = 0;
		try {
			a = curmsgmap.selCurrPrice(curUser.getProd(), currmsg.getExnm());
			if(a>0){
				b = curmsgmap.updateCurrPrice(curUser.getProd(), currmsg);
			}else{
				b = curmsgmap.insertCurrPrice(curUser.getProd(), currmsg);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "货币对修改失败");
		}
		if(b>0){
			Logfile logfile = new Logfile();
			logfile.setProd(curUser.getProd());
			logfile.setUsem(curUser.getUsnm());
			logfile.setVnew("成功");
			logfile.setVold("登录ip:"+curUser.getCurIP()+"编号:"+currmsg.getExcd()+"货币对:"+currmsg.getExnm()+"中文名:"+currmsg.getExcn()+"价格点数:"+currmsg.getPion()+"价格类型:"+currmsg.getExtp());
			logfile.setTymo("品种对管理");
			logfile.setRemk("修改货币对");
			logfileCmdService.insertLog(logfile);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "货币对修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "货币对修改失败");
		}
		
	}
	@Override
	public boolean delCurrPrice(String userKey, String exnm) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			int a = curmsgmap.deleteCurrPrice(curUser.getProd(), exnm);
			if(a>0){
				Logfile logfile = new Logfile();
				logfile.setProd(curUser.getProd());
				logfile.setUsem(curUser.getUsnm());
				logfile.setVnew("成功");
				logfile.setVold("登录ip:"+curUser.getCurIP()+"货币对:"+exnm);
				logfile.setTymo("品种对管理");
				logfile.setRemk("删除货币对");
				logfileCmdService.insertLog(logfile);
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	
	
	
}
