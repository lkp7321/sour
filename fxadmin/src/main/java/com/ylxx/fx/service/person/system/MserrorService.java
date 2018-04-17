package com.ylxx.fx.service.person.system;
/**
 * 错误码管理
 * @author lz130
 *
 */
public interface MserrorService {
	/**
	 * 查询
	 * @param pageNo
	 * @param pageSize
	 * @param StrTxt
	 * @param StrTxt1
	 * @return
	 */
	String getMsgErrorList(Integer pageNo, Integer pageSize, String StrTxt, String StrTxt1);
	/**
	 * 添加
	 * @return
	 */
	String addMsgError(String userKey, String ercd, String ertx, String erin);
	/**
	 * 修改
	 * @return
	 */
	String upsMsgError(String userKey, String ercd, String ertx, String erin);
	/**
	 * 删除
	 * @return
	 */
	String delMsgError(String userKey, String ercd);

}
