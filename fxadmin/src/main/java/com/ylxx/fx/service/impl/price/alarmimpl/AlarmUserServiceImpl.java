package com.ylxx.fx.service.impl.price.alarmimpl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ylxx.fx.core.mapper.price.alarm.AlarmUserMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.Cmmalarmuser;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.price.alarm.AlarmUserService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
@Service("alarmUserService")
public class AlarmUserServiceImpl implements AlarmUserService{
	@Resource
	private AlarmUserMapper alarmUserMap;
	private static final Logger log = LoggerFactory.getLogger(AlarmUserServiceImpl.class);
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	
	/**
	 * 告警用户-查询数据
	 */
	public List<Cmmalarmuser> getAlarmUser() {
		List<Cmmalarmuser> list = null;
		try {
			list = alarmUserMap.selectAlarmn();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setMusc(list.get(i).getMusc().equals("0")?"启用":list.get(i).getMusc().equals("1")?"停用":"未知");
				list.get(i).setStcd(list.get(i).getStcd().equals("0")?"启用":list.get(i).getStcd().equals("1")?"停用":"未知");
			}
		}
		return list;
	}

	/**
	 * 修改
	 */
	public boolean upAlarmUser(CurrUser curUser, Cmmalarmuser cmmalarmuser, String ip) {
		int a =0;
		boolean flag =false;
		try {
			a = alarmUserMap.upAlarm(cmmalarmuser);
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "修改告警用户:用户ID:"
					+ cmmalarmuser.getUsid() + ",用户名称:" + cmmalarmuser.getUsnm()
					+ ",用户IP:" + cmmalarmuser.getUsip() + ",声音:"
					+ cmmalarmuser.getMusc() + ",电话:" + cmmalarmuser.getTlno() + ",手机:"
					+ cmmalarmuser.getMbno() + ",状态:" + cmmalarmuser.getStcd()
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("告警用户");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "修改告警用户:用户ID:"
					+ cmmalarmuser.getUsid() + ",用户名称:" + cmmalarmuser.getUsnm()
					+ ",用户IP:" + cmmalarmuser.getUsip() + ",声音:"
					+ cmmalarmuser.getMusc() + ",电话:" + cmmalarmuser.getTlno() + ",手机:"
					+ cmmalarmuser.getMbno() + ",状态:" + cmmalarmuser.getStcd());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "修改告警用户:用户ID:"
					+ cmmalarmuser.getUsid() + ",用户名称:" + cmmalarmuser.getUsnm()
					+ ",用户IP:" + cmmalarmuser.getUsip() + ",声音:"
					+ cmmalarmuser.getMusc() + ",电话:" + cmmalarmuser.getTlno() + ",手机:"
					+ cmmalarmuser.getMbno() + ",状态:" + cmmalarmuser.getStcd()
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
		}
		return flag;
	}

	/**
	 * 添加
	 */
	public boolean insAlarmUser(CurrUser curUser, Cmmalarmuser cmmalarmuser, String ip) {
		int a =0;
		boolean flag =false;
		try {
			a = alarmUserMap.insertAlarm(cmmalarmuser);
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "告警用户添加:用户ID:"
					+ cmmalarmuser.getUsid() + ",用户名称:" + cmmalarmuser.getUsnm()
					+ ",用户IP:" + cmmalarmuser.getUsip() + ",声音:"
					+ cmmalarmuser.getMusc() + ",电话:" + cmmalarmuser.getTlno() + ",手机:"
					+ cmmalarmuser.getMbno() + ",状态:" + cmmalarmuser.getStcd()
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("告警用户");
			loginfo.setRemk("添加");
			loginfo.setVold("登录产品:" + curUser.getProd() + "告警用户添加:用户ID:"
					+ cmmalarmuser.getUsid() + ",用户名称:" + cmmalarmuser.getUsnm()
					+ ",用户IP:" + cmmalarmuser.getUsip() + ",声音:"
					+ cmmalarmuser.getMusc() + ",电话:" + cmmalarmuser.getTlno() + ",手机:"
					+ cmmalarmuser.getMbno() + ",状态:" + cmmalarmuser.getStcd());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		}else{
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "告警用户添加:用户ID:"
					+ cmmalarmuser.getUsid() + ",用户名称:" + cmmalarmuser.getUsnm()
					+ ",用户IP:" + cmmalarmuser.getUsip() + ",声音:"
					+ cmmalarmuser.getMusc() + ",电话:" + cmmalarmuser.getTlno() + ",手机:"
					+ cmmalarmuser.getMbno() + ",状态:" + cmmalarmuser.getStcd()
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
		}
		return flag;
	}

	/**
	 * 删除
	 */
	public boolean delAlarmUser(CurrUser curUser, String usid, String usnm, String ip) {
		int a =0;
		boolean flag =false;
		try {
			a = alarmUserMap.deleteAlarm(usid, usnm);
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "删除告警用户:用户ID:" + usid
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("告警用户");
			loginfo.setRemk("删除");
			loginfo
					.setVold("登录产品:" + curUser.getProd() + "删除告警用户:用户ID:"
							+ usid);
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "删除告警用户:用户ID:" + usid
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
		}
		return flag;
	}

	/**
	 * 初始化 添加的id(通过后台生成)
	 */
	public String getAlarmUsid() {
		return alarmUserMap.selAlarmUsid();
	}
	
}
