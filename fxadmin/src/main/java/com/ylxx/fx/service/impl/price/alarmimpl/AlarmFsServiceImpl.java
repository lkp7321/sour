package com.ylxx.fx.service.impl.price.alarmimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ylxx.fx.core.mapper.price.alarm.AlarmFsMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.CmmAlarmNotify;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.price.alarm.AlarmFsService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
@Service("alarmFsService")
public class AlarmFsServiceImpl implements AlarmFsService {
	@Resource
	private AlarmFsMapper alarmFsMap;
	private static final Logger log = LoggerFactory.getLogger(AlarmFsServiceImpl.class);
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	@Override
	public List<CmmAlarmNotify> getAllAlarmNotify() {
		List<CmmAlarmNotify> list = null;
		try {
			list = alarmFsMap.selectAlarmNotifyAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<Map<String, String>> getAlarmStcd() {
		List<Map<String,String>> list= null;
		try {
			 list = alarmFsMap.selectAlarmStcd();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<Map<String, String>> selectAlarmLeveAll() {
		List<Map<String,String>> list = null;
		try {
			list = alarmFsMap.selectAlarmLeveAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<Map<String, String>> selectAlarmERR() {
		List<Map<String,String>> list = null;
		try {
			list = alarmFsMap.selectAlarmERR();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public boolean addAlarmfs(CurrUser user, CmmAlarmNotify notify, String ip) {
		int b= 0;
		try {
			int a = alarmFsMap.selectAlarmNotify(notify);
			if(a>0){
				b = alarmFsMap.upAlarmNotify(notify);
			}else{
				b = alarmFsMap.insertAlarmNotify(notify);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(b>0){
			log.info("用户：" + user.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + user.getProd() + "添加(修改)告警事件处理:告警错误码:"
					+ notify.getErcd() + ",错误描述:" + notify.getErtx() + ",用户姓名:"
					+ notify.getUsid() + ",告警级别:" + notify.getAlid() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(user.getUsnm());
			loginfo.setTymo("告警事件处理");
			loginfo.setRemk("添加(修改)");
			loginfo.setVold("登录产品:" + user.getProd() + "添加(修改)告警事件处理:告警错误码:"
					+ notify.getErcd() + ",错误描述:" + notify.getErtx() + ",用户姓名:"
					+ notify.getUsid() + ",告警级别:" + notify.getAlid());
			loginfo.setVnew("成功");
			loginfo.setProd(user.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		}else{
			log.error("用户：" + user.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + user.getProd() + "添加(修改)告警事件处理:告警错误码:"
					+ notify.getErcd() + ",错误描述:" + notify.getErtx() + ",用户姓名:"
					+ notify.getUsid() + ",告警级别:" + notify.getAlid() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	@Override
	public boolean modifyAlarmfs(CurrUser user, CmmAlarmNotify notify, String ip) {
		int b= 0;
		try {
			int a = alarmFsMap.selectAlarmNotify(notify);
			if(a>0){
				b = alarmFsMap.upAlarmNotify(notify);
			}else{
				b = alarmFsMap.insertAlarmNotify(notify);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(b>0){
			log.info("用户：" + user.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + user.getProd() + "修改告警事件处理:告警错误码:"
					+ notify.getErcd() + ",错误描述:" + notify.getErtx() + ",用户姓名:"
					+ notify.getUsid() + ",告警级别:" + notify.getAlid() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(user.getUsnm());
			loginfo.setTymo("告警事件处理");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + user.getProd() + "修改告警事件处理:告警错误码:"
					+ notify.getErcd() + ",错误描述:" + notify.getErtx() + ",用户姓名:"
					+ notify.getUsid() + ",告警级别:" + notify.getAlid());
			loginfo.setVnew("成功");
			loginfo.setProd(user.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		}else{
			log.error("用户：" + user.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + user.getProd() + "添加(修改)告警事件处理:告警错误码:"
					+ notify.getErcd() + ",错误描述:" + notify.getErtx() + ",用户姓名:"
					+ notify.getUsid() + ",告警级别:" + notify.getAlid() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	@Override
	public boolean delAlarmfs(CurrUser user, CmmAlarmNotify notify, String ip) {
		int b= 0;
		try {
			b = alarmFsMap.deleteAlarmNotify(notify.getErcd(), notify.getUsid());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(b>0){
			log.info("用户：" + user.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + user.getProd() + "删除告警事件处理:告警代码:" + notify.getErcd()
					+ ",用户ID:" + notify.getUsid() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(user.getUsnm());
			loginfo.setTymo("告警事件处理");
			loginfo.setRemk("删除");
			loginfo.setVold("登录产品:" + user.getProd() + "删除告警事件处理:告警代码:"
					+ notify.getErcd() + ",用户ID:" + notify.getUsid());
			loginfo.setVnew("成功");
			loginfo.setProd(user.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		}else{
			log.error("用户：" + user.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + user.getProd() + "删除告警事件处理:告警代码:" + notify.getErcd()
					+ ",用户ID:" + notify.getUsid() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

}
