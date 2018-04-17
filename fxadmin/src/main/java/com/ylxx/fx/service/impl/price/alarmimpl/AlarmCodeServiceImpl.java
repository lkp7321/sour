package com.ylxx.fx.service.impl.price.alarmimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ylxx.fx.core.mapper.price.alarm.AlarmCodeMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.CmmAlarmCode;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.price.alarm.AlarmCodeService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
@Service("alarmCodeService")
public class AlarmCodeServiceImpl implements AlarmCodeService{
	@Resource
	private AlarmCodeMapper alarmCodeMap;
	private static final Logger log = LoggerFactory.getLogger(AlarmCodeServiceImpl.class);
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	
	public List<Map<String, String>> getAllalarmCode() {
		List<Map<String,String>> list = null;
		try {
			list = alarmCodeMap.selectAlarmLeveAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public boolean upAlarmCode(CurrUser curUser, CmmAlarmCode cmmAlarmCode, String ip) {
		int a = 0;
		boolean flag = false;
		String erms = cmmAlarmCode.getErms();
		String ercd = cmmAlarmCode.getErcd();
		try {
			a = alarmCodeMap.upAlarmCode(erms, ercd);
			log.info("\n更新告警代码条数:"+a);
			flag = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("\n更新告警代码失败");
			flag =false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "修改告警代码:告警代码:"
					+ cmmAlarmCode.getErcd() + ",告警描述:" + cmmAlarmCode.getErms()
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("告警代码");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "修改告警代码:告警代码:"
					+ cmmAlarmCode.getErcd() + ",告警描述:" + cmmAlarmCode.getErms());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "修改告警代码:告警代码:"
					+ cmmAlarmCode.getErcd() + ",告警描述:" + cmmAlarmCode.getErms()
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
		}
		return flag;
	}

	@Override
	public boolean insAlarmCode(CurrUser curUser, CmmAlarmCode cmmAlarmCode, String ip) {
		int a = 0;
		String erms = cmmAlarmCode.getErms();
		String ercd = cmmAlarmCode.getErcd();
		try {
			a = alarmCodeMap.insertAlarmCod(erms, ercd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "添加告警代码:告警代码:"
					+ cmmAlarmCode.getErcd() + ",告警描述:" + cmmAlarmCode.getErms()
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("告警代码");
			loginfo.setRemk("添加");
			loginfo.setVold("登录产品:" + curUser.getProd() + "添加告警代码:告警代码:"
					+ cmmAlarmCode.getErcd() + ",告警描述:" + cmmAlarmCode.getErms());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "添加告警代码:告警代码:"
					+ cmmAlarmCode.getErcd() + ",告警描述:" + cmmAlarmCode.getErms()
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
			return false;
		}
	}

	@Override
	public boolean delAlarmCode(CurrUser curUser, String ercd, String ip) {
		int a = 0;
		boolean flag = false;
		try {
			a = alarmCodeMap.deleteAlarmLeve(ercd);
			if(a>0){
				flag = true;
			}else{
				flag = false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "删除告警代码:告警代码:" + ercd
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("告警代码");
			loginfo.setRemk("删除");
			loginfo
					.setVold("登录产品:" + curUser.getProd() + "删除告警代码:告警代码:"
							+ ercd);
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "删除告警代码:告警代码:" + ercd
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
		}
		return flag;
	}

}
