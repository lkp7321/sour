package com.ylxx.fx.service.impl.person.systemimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.person.system.CytpMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.system.CytpService;
import com.ylxx.fx.service.po.Cytp;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodeSystem;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
@Service("cytpService")
public class CytpServiceImpl implements CytpService{
	@Resource
	private CytpMapper cytpmap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	private static final Logger log = LoggerFactory.getLogger(CytpServiceImpl.class);
	//查询所有
	public String getAllCytp(String userKey) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		List<Cytp> list = null;
		try{
			if(curUser.getProd().equals("P999")){
				list = cytpmap.selTabPrice();
			}else{
				list = cytpmap.selTab(curUser.getProd());
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setUsfg("0".equals(list.get(i).getUsfg()) ? "启用" : "停用");
			}
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_0.getCode(), null);
		}
	}
	//添加币种
	public boolean insCytp(String userKey, Cytp cytp) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			int b = cytpmap.selins(curUser.getProd(), cytp.getCytp());
			if(b>0){
				return false;
			}else{
				a = cytpmap.insertCytp(curUser.getProd(), cytp);
				if(a>0){
					Logfile logfile = new Logfile();
					logfile.setProd(curUser.getProd());
					logfile.setUsem(curUser.getUsnm());
					logfile.setVnew("成功");
					logfile.setVold("登录ip:"+curUser.getCurIP()+"编号号:"+cytp.getCytp()+"币别:"+cytp.getCyen()+"中文名:"+cytp.getCycn());
					logfile.setTymo("品种管理");
					logfile.setRemk("添加币种");
					logfileCmdService.insertLog(logfile);
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	//修改币种
	public boolean upsCytp(String userKey, Cytp cytp) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			cytpmap.updateCytp(curUser.getProd(), cytp);
			Logfile logfile = new Logfile();
			logfile.setProd(curUser.getProd());
			logfile.setUsem(curUser.getUsnm());
			logfile.setVnew("成功");
			logfile.setVold("登录ip:"+curUser.getCurIP()+"编号号:"+cytp.getCytp()+"币别:"+cytp.getCyen()+"中文名:"+cytp.getCycn()+"状态:"+cytp.getUsfg());
			logfile.setTymo("品种管理");
			logfile.setRemk("修改币种");
			logfileCmdService.insertLog(logfile);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	//删除币种
	public boolean delCytp(String userKey, String cytp) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			cytpmap.deleteCytp(curUser.getProd(), cytp);
			Logfile logfile = new Logfile();
			logfile.setProd(curUser.getProd());
			logfile.setUsem(curUser.getUsnm());
			logfile.setVnew("成功");
			logfile.setVold("登录ip:"+curUser.getCurIP()+"编号号:"+cytp);
			logfile.setTymo("品种管理");
			logfile.setRemk("删除币种");
			logfileCmdService.insertLog(logfile);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	
	
}
