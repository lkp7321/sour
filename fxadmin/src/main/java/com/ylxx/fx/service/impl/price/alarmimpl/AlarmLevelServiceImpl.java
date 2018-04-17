package com.ylxx.fx.service.impl.price.alarmimpl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ylxx.fx.core.mapper.price.alarm.AlarmLevelMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.CmmAlarmLevel;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.price.alarm.AlarmLevelService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
@Service("alarmLevelService")
public class AlarmLevelServiceImpl implements AlarmLevelService {

	@Resource
	private AlarmLevelMapper alarmLevelMap;
	private static final Logger log = LoggerFactory.getLogger(AlarmLevelServiceImpl.class);
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	
	@Override
	public List<CmmAlarmLevel> getAllAlarmLevel() {
		List<CmmAlarmLevel> list = null;
		try {
			list = alarmLevelMap.selectAlarmLeveAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public boolean upAlarmLevel(CurrUser curUser, CmmAlarmLevel cmmAlarmLevel, String ip) {
		int a = 0;
		boolean flag = false;
		try {
			a = alarmLevelMap.upAlarmLeve(cmmAlarmLevel);
			if(a>0){
				flag =true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "修改告警级别:告警ID:"
					+ cmmAlarmLevel.getAlid() + ",告警级别:" + cmmAlarmLevel.getAlds()
					+ ",闪屏:" + cmmAlarmLevel.getLffg() + ",声音:"
					+ cmmAlarmLevel.getVofg() + ",短信:" + cmmAlarmLevel.getNtfg()
					+ ",电话:" + cmmAlarmLevel.getTlfg() + ",上时间闪屏:"
					+ cmmAlarmLevel.getLffg() + ",间隔时间(秒):" + cmmAlarmLevel.getIntv()
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("告警级别");
			loginfo.setRemk("修改");
			loginfo
					.setVold("登录产品:" + curUser.getProd() + "修改告警级别:告警ID:"
							+ cmmAlarmLevel.getAlid() + ",告警级别:"
							+ cmmAlarmLevel.getAlds() + ",闪屏:"
							+ cmmAlarmLevel.getLffg() + ",声音:"
							+ cmmAlarmLevel.getVofg() + ",短信:"
							+ cmmAlarmLevel.getNtfg() + ",电话:"
							+ cmmAlarmLevel.getTlfg() + ",上时间闪屏:"
							+ cmmAlarmLevel.getLffg() + ",间隔时间(秒):"
							+ cmmAlarmLevel.getIntv());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "修改告警级别:告警ID:"
					+ cmmAlarmLevel.getAlid() + ",告警级别:" + cmmAlarmLevel.getAlds()
					+ ",闪屏:" + cmmAlarmLevel.getLffg() + ",声音:"
					+ cmmAlarmLevel.getVofg() + ",短信:" + cmmAlarmLevel.getNtfg()
					+ ",电话:" + cmmAlarmLevel.getTlfg() + ",上时间闪屏:"
					+ cmmAlarmLevel.getLffg() + ",间隔时间(秒):" + cmmAlarmLevel.getIntv()
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
		}
		return flag;
	}

	@Override
	public boolean insAlarmLevel(CurrUser curUser, CmmAlarmLevel cmmAlarmLevel, String ip) {
		int a = 0;
		boolean flag = false;
		try {
			a = alarmLevelMap.insertAlarmLeve(cmmAlarmLevel);
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag =false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "添加告警级别:告警ID:"
					+ cmmAlarmLevel.getAlid() + ",告警级别:" + cmmAlarmLevel.getAlds()
					+ ",闪屏:" + cmmAlarmLevel.getLffg() + ",声音:"
					+ cmmAlarmLevel.getVofg() + ",短信:" + cmmAlarmLevel.getNtfg()
					+ ",电话:" + cmmAlarmLevel.getTlfg() + ",上时间闪屏:"
					+ cmmAlarmLevel.getLffg() + ",间隔时间(秒):" + cmmAlarmLevel.getIntv()
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("告警级别");
			loginfo.setRemk("添加");
			loginfo
					.setVold(" 登录产品:" + curUser.getProd() + "添加告警级别:告警ID:"
							+ cmmAlarmLevel.getAlid() + ",告警级别:"
							+ cmmAlarmLevel.getAlds() + ",闪屏:"
							+ cmmAlarmLevel.getLffg() + ",声音:"
							+ cmmAlarmLevel.getVofg() + ",短信:"
							+ cmmAlarmLevel.getNtfg() + ",电话:"
							+ cmmAlarmLevel.getTlfg() + ",上时间闪屏:"
							+ cmmAlarmLevel.getLffg() + ",间隔时间(秒):"
							+ cmmAlarmLevel.getIntv());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "添加告警级别:告警ID:"
					+ cmmAlarmLevel.getAlid() + ",告警级别:" + cmmAlarmLevel.getAlds()
					+ ",闪屏:" + cmmAlarmLevel.getLffg() + ",声音:"
					+ cmmAlarmLevel.getVofg() + ",短信:" + cmmAlarmLevel.getNtfg()
					+ ",电话:" + cmmAlarmLevel.getTlfg() + ",上时间闪屏:"
					+ cmmAlarmLevel.getLffg() + ",间隔时间(秒):" + cmmAlarmLevel.getIntv()
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
		}
		return flag;
	}

	@Override
	public boolean delAlarmLevel(CurrUser curUser, String alid, String ip) {
		int a = 0;
		boolean flag = false;
		try {
			a = alarmLevelMap.deleteAlarmLeve(alid);
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "删除告警级别:告警ID:" + alid
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("告警级别");
			loginfo.setRemk("删除");
			loginfo
					.setVold("登录产品:" + curUser.getProd() + "删除告警级别:告警ID:"
							+ alid);
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "删除告警级别:告警ID:" + alid
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
		}
		return flag;
	}

}
