package com.ylxx.qt.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ylxx.qt.core.mapper.YQUserMapper;
import com.ylxx.qt.service.YQUserService;
import com.ylxx.qt.service.po.MenuBean;
import com.ylxx.qt.service.po.RoleBean;
import com.ylxx.qt.service.po.UserInfoBean;
import com.ylxx.qt.utils.AESUtil;

/**
 * 
 * @author mengpeitong
 * 
 */
@Service
public class YQUserServiceImpl implements YQUserService {

	@Resource
	private YQUserMapper user;

	@Override
	public List<UserInfoBean> getAccount(String userid) {
		List<UserInfoBean> list = new ArrayList<UserInfoBean>();
		try {
			list = user.getUserAccount(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public UserInfoBean findUserByName(String username) {
		// 根据用户名查询
		UserInfoBean userValues = user.findUserByName(username);
		return userValues;
	}

	@Override
	public UserInfoBean findUserByPhone(String phonenumber) {
		// 根据手机号查询
		UserInfoBean userValues = user.findUserByPhone(phonenumber);
		return userValues;
	}

	@Override
	public UserInfoBean findUserByEmail(String email) {
		// 根据邮箱查询
		UserInfoBean userValues = user.findUserByEmail(email);
		return userValues;
	}

	@Override
	public int insertUser(UserInfoBean userInfo) {
		// 新增用户
		return user.insertUser(userInfo);
	}

	@Override
	public int updateUser(UserInfoBean userInfo) {
		// 修改用户
		return user.updateUser(userInfo);
	}

	@Override
	public void addRoles(List<RoleBean> role) throws Exception {
		// 添加角色
		try {
			user.addRoles(role);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int addDefaultRoles(RoleBean roleBean) {
		// 用户注册成功后添加默认角色
		return user.addDefaultRoles(roleBean);
	}

	@Override
	public UserInfoBean login(String userName, String password) throws Exception {
		List<UserInfoBean> userList = user.login();
		UserInfoBean userInfo = null;
		// 查询所有的用户
		if (userName != null && password != null) {
			for (int i = 0; i < userList.size(); i++) {

				String pwd = userList.get(i).getPassword();
				String key = userList.get(i).getUserid();
				// 对输入的密码进行加密，并查询与之匹配的用户
				if ((userName.equals(userList.get(i).getUsername())) && (password.equals(AESUtil.decrypt(pwd, key)))) {
					List<RoleBean> roleList = user.findRoleIDByUserId(userList.get(i).getUserid());
					List<Integer> list = new ArrayList<Integer>();
					for (int j = 0; j < roleList.size(); j++) {
						list.add(Integer.parseInt(roleList.get(j).getRoleid()));
					}
					userInfo = new UserInfoBean();
					Collections.sort(list);
					userInfo = userList.get(i);
					userInfo.setRoleid(list.get(0).toString());
					break;
				}
			}
		}
		return userInfo;
	}

	@Override
	public List<MenuBean> findMenu(String role, String parent) {

		return user.findMenu(role, parent);
	}

	@Override
	public List<MenuBean> showAllMenu() {
		return user.showAllMenu();
	}

	@Override
	public List<UserInfoBean> findAllUser() {

		return user.findAllUser();
	}

	@Override
	public List<RoleBean> getAllRoles() {
		return user.getAllRoles();
	}

	@Override
	public List<MenuBean> showMenuByRole(String role) {

		return user.showMenuByRole(role);
	}

	@Override
	public int updateMenu(String roleId, String menuId, String isShow) {
		return user.updateMenu(roleId, menuId, isShow);
	}

	@Override
	public int updateUserRole(List<UserInfoBean> userList) {
		int result = 0;
		int count = 0;
		for (int i = 0; i < userList.size(); i++) {
			result = user.updateUserRole(userList.get(i).getUserid(), userList.get(i).getUsername(),
					userList.get(i).getRoleid());
			count = count + result;
		}

		return count;
	}

	@Override
	public List<UserInfoBean> selectAllUser(int page, int limit) {
		int index = (page - 1) * limit;
		return user.selectAllUser(index, limit);
	}

	@Override
	public List<UserInfoBean> findUserByAccount(String account) {
		List<UserInfoBean> userList = null;
		try {
			userList = user.findUserByAccount(account);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	@Override
	public Map<String, String> getAccess_token(String AppID, String AppSecret, String CODE) {
		Map<String, String> hashMap = null;

		String api_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AppID + "&secret=" + AppSecret
				+ "&code=" + CODE + "&grant_type=authorization_code";

		String charset = "utf-8";

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		try {
			URL localURL = new URL(api_url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) localURL.openConnection();
			httpURLConnection.setRequestProperty("Accept-Charset", charset);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-java-serialized-object;charset=utf-8");
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
			JSONObject jsonObject = JSONObject.parseObject(resultBuffer.toString());
			String s = jsonObject.get("access_token").toString();
			hashMap=new HashMap<String, String>();
			hashMap.put("access_token", s);
			hashMap.put("openid", jsonObject.get("openid").toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return hashMap;

	}

	@Override
	public UserInfoBean getUserInfo(String access_token, String openid) {
		String api_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;

		String charset = "utf-8";
		UserInfoBean user = new UserInfoBean();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		try {
			URL localURL = new URL(api_url);

			HttpURLConnection httpURLConnection = (HttpURLConnection) localURL.openConnection();
			httpURLConnection.setRequestProperty("Accept-Charset", charset);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-java-serialized-object;charset=utf-8");
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

			JSONObject jsonObject = JSONObject.parseObject(resultBuffer.toString());
			if ("1".equals(jsonObject.get("sex").toString())) {
				user.setSex("male");
			} else if ("0".equals(jsonObject.get("sex").toString())) {
				user.setSex("female");
			} else {
				user.setSex("secrecy");
			}

			// unionid 在同一个应用中unionid是唯一的
			user.setWeixinID(jsonObject.get("unionid").toString());
			user.setUsername(jsonObject.get("nickname").toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return user;

	}

	@Override
	public UserInfoBean IsHavingWeiXin(String WeiXinID) {
		UserInfoBean userBean = null;
		List<UserInfoBean> userList = user.findUserByWeiXinID(WeiXinID);
		if (userList != null && userList.size() > 0) {
			return userBean = userList.get(0);
		}
		return userBean;
	}

	@Override
	public UserInfoBean loginByPhone(String userName, String password)throws Exception {
		List<UserInfoBean> userList = user.login();
		UserInfoBean userInfo = null;
		// 查询所有的用户
		if (userName != null && password != null) {
			for (int i = 0; i < userList.size(); i++) {

				String pwd = userList.get(i).getPassword();
				String key = userList.get(i).getUserid();
				// 对输入的密码进行加密，并查询与之匹配的用户
				if ((userName.equals(userList.get(i).getPhonenumber())) && (password.equals(AESUtil.decrypt(pwd, key)))) {
					List<RoleBean> roleList = user.findRoleIDByUserId(userList.get(i).getUserid());
					List<Integer> list = new ArrayList<Integer>();
					for (int j = 0; j < roleList.size(); j++) {
						list.add(Integer.parseInt(roleList.get(j).getRoleid()));
					}
					userInfo = new UserInfoBean();
					Collections.sort(list);
					userInfo = userList.get(i);
					userInfo.setRoleid(list.get(0).toString());
					break;
				}
			}
		}
		return userInfo;
	}

	@Override
	public UserInfoBean loginByEmail(String userName, String password) throws Exception {
		List<UserInfoBean> userList = user.login();
		UserInfoBean userInfo = null;
		// 查询所有的用户
		if (userName != null && password != null) {
			for (int i = 0; i < userList.size(); i++) {

				String pwd = userList.get(i).getPassword();
				String key = userList.get(i).getUserid();
				// 对输入的密码进行加密，并查询与之匹配的用户
				if ((userName.equals(userList.get(i).getEamil())) && (password.equals(AESUtil.decrypt(pwd, key)))) {
					List<RoleBean> roleList = user.findRoleIDByUserId(userList.get(i).getUserid());
					List<Integer> list = new ArrayList<Integer>();
					for (int j = 0; j < roleList.size(); j++) {
						list.add(Integer.parseInt(roleList.get(j).getRoleid()));
					}
					userInfo = new UserInfoBean();
					Collections.sort(list);
					userInfo = userList.get(i);
					userInfo.setRoleid(list.get(0).toString());
					break;
				}
			}
		}
		return userInfo;
	}
}
