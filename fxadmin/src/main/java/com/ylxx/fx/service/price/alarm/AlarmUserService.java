package com.ylxx.fx.service.price.alarm;
import java.util.*;
import com.ylxx.fx.service.po.Cmmalarmuser;
import com.ylxx.fx.utils.CurrUser;
public interface AlarmUserService {
	/**
	 * 
	 * @return List<Cmmalarmuser> 用户列表
	 */
	public List<Cmmalarmuser> getAlarmUser();
	/**
	 * 
	 * @param user 用户
	 * @param cmmalarmuser 修改告警用户信息
	 * @param ip ip地址
	 * @return boolean 
	 */
	public boolean upAlarmUser(CurrUser curUser, Cmmalarmuser cmmalarmuser, String ip);
	/**
	 * 
	 * @param user 用户
	 * @param cmmalarmuser 添加告警用户信息
	 * @param ip ip地址
	 * @return boolean
	 */
	public boolean insAlarmUser(CurrUser curUser, Cmmalarmuser cmmalarmuser, String ip);
	/**
	 * 
	 * @param user 用户
	 * @param usid 删除id
	 * @param usnm 删除用户名
	 * @param ip ip地址
	 * @return boolean
	 */
	public boolean delAlarmUser(CurrUser curUser, String usid, String usnm, String ip);
	/**
	 * 
	 * @return String usid
	 */
	public String getAlarmUsid();
}
