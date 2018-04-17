package com.ylxx.fx.utils;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.*;
public class LoginUsers {
	static final Logger log = LoggerFactory.getLogger(LoginUsers.class);
	private static LoginUsers loginUser = null;

	private static Map<String, CurrUser> logins = new HashMap<String, CurrUser>();
	private static Map<String, Timer> times = new HashMap<String, Timer>();
	@SuppressWarnings("unchecked")
	public synchronized void saveTimes(String userkey, Timer timer) {
		times.put(userkey, timer);
	}
	@SuppressWarnings("unchecked")
	public synchronized Timer getTimes(String userKey) {
		return times.get(userKey);
	}
	@SuppressWarnings("unchecked")
	public synchronized String saveCurUser(CurrUser currUser) {
		log.info(">>>登陆session长度:"+logins.size());
		String key = null;
		if (logins.size() > 0) {
			for (Iterator iter = logins.entrySet().iterator(); iter.hasNext();) {

				Map.Entry entry = (Map.Entry) iter.next();

				CurrUser user = (CurrUser) entry.getValue();
				if (currUser.getProd().equals(user.getProd())
						&& currUser.getUsnm().equals(user.getUsnm())
						&& currUser.getCurIP().equals(user.getCurIP())) {
					key = (String) entry.getKey();

					return key;
				} else {
					key = buildRandom(currUser.getUsnm());
					logins.put(key, currUser);

					return key;
				}
			}
		} else {
			key = buildRandom(currUser.getUsnm());
			logins.put(key, currUser);

			return key;
		}
		return key;
	}

	/***
	 * 当用户退出时把记录的信息从map中删除
	 * 
	 * @param key
	 * **/
	public synchronized void deleteCurUser(String key) {
		log.info("==删除登陆session：length:"+logins.size());
		if (logins.containsKey(key))
			logins.remove(key);
	}
	public synchronized void deleteTimes(String key) {
		log.info("==删除定时session：length:"+logins.size());
		if (times.containsKey(key))
			times.remove(key);
	}

	/****
	 * 查询用户信息
	 * 
	 * @param uuid
	 *            用户操作时发过来的id
	 * @return currUser 当前用户
	 */
	public synchronized CurrUser getCurrUser(String uuid) {
		CurrUser user = logins.get(uuid);

		return user;
	}

	/***
	 * 根据用户生成一个随机数
	 * 
	 * @param userid
	 *            当前用户的id
	 * @return String
	 */
	public String buildRandom(String userid) {
		// 写入所希望的所有的字母A-Z,a-z,0-9
		String randStr = "ABCDEFGHIJKLMNO0123PQRSTUVWXYZ45abcdefghijklm6789nopqrstuvwxyz";
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int randStrLength = 8; // 生成的随机数的长度
		for (int i = 0; i < randStrLength; i++) {
			int randNum = rand.nextInt(62);
			generateRandStr.append(randStr.substring(randNum, randNum + 1));
		}
		generateRandStr.append(easyDateFormat("yyyyMMddHHmmss")).append(userid);
		return new String(generateRandStr);
	}

	/***
	 * 返回格式化的日期
	 * 
	 * @param format
	 *            日期格式
	 * @return String
	 */
	public String easyDateFormat(String format) {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String datenewformat = formatter.format(today);
		return datenewformat;
	}

	/*** 返回当前对象 */
	public synchronized static LoginUsers getLoginUser() {
		if (loginUser == null)
			loginUser = new LoginUsers();
		return loginUser;
	}

	public synchronized static void setLoginUser(LoginUsers loginUser) {
		LoginUsers.loginUser = loginUser;
	}

	public synchronized static Map<String, CurrUser> getLogins() {
		return logins;
	}

	public synchronized static void setLogins(Map<String, CurrUser> logins) {
		LoginUsers.logins = logins;
	}
	public synchronized static void setTimes(Map<String, Timer> times) {
		LoginUsers.times = times;
	}
	public synchronized static Map<String, Timer> getTimes() {
		return times;
	}

}
