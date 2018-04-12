package com.ylxx.qt.service;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;

/**
 * 短信服务
 * 
 * @author SMM
 * 
 */
public interface ShortMessageService {
	/**
	 * @desc ：1.发送短信 用户注册验证接口
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码
	 * @throws ClientException
	 * @throws Exception
	 */
	public SendSmsResponse sendSms(String phone, String code) throws ClientException;

	/**
	 * @desc ：2.短信发送记录查询接口 用于查询短信发送的状态，是否成功到达终端用户手机
	 * @param phone
	 * @param bizId
	 * @return
	 * @throws ServerException
	 * @throws ClientException
	 */
	public QuerySendDetailsResponse querySendDetails(String bizId, String phone) throws ClientException;
	
}
