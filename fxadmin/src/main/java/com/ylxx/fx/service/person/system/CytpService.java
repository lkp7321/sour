package com.ylxx.fx.service.person.system;

import com.ylxx.fx.service.po.Cytp;

public interface CytpService {
	/**
	 * 
	 * @param prod 产品
	 * @return String 币种列表
	 */
	public String getAllCytp(String userKey);
	/**
	 * 
	 * @param user 登陆用户
	 * @param cytp 添加的币种信息
	 * @return boolean
	 */
	public boolean insCytp(String userKey, Cytp cytp);
	/**
	 * 
	 * @param user 登陆用户
	 * @param cytp 修改的币种信息
	 * @return boolean
	 */
	public boolean upsCytp(String userKey, Cytp cytp);
	/**
	 * 
	 * @param user 登录用户
	 * @param cytp 删除的币种编号
	 * @return boolean
	 */
	public boolean delCytp(String userKey, String cytp);
	
}
