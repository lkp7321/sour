package com.ylxx.qt.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ylxx.qt.service.ShortMessageService;
import com.ylxx.qt.utils.Env;

/**
 * 短信服务
 * 
 * @author SMM
 * 
 */
@Service("shortmessageservice")
public class ShortMessageServiceImpl implements ShortMessageService{
	@Resource
	private ShortMessageService sms;

	
	/**
	 * @desc ：1.发送短信 用户注册验证接口
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码
	 * @throws ClientException
	 * @throws Exception
	 */
	public SendSmsResponse sendSms(String phone, String code) throws ClientException{
		// 1.准备好短信模板变量——验证码code
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		String TemplateParam = jsonObject.toJSONString();

		// 2.设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 3.初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
				Env.ACCESSKEY_ID, Env.ACCESSKEY_SECRET);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", Env.PRODUCT,
				Env.DOMAIN);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		// 使用post提交
		request.setMethod(MethodType.POST);
		// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(phone);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(Env.SIGN_NAME);
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(Env.TEMPLATE_CODE);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		// "您的验证码是:" + code + "该验证码5分钟内有效，请勿泄漏于他人！"
		request.setTemplateParam(TemplateParam);
		// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");
		// 请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		return sendSmsResponse;
	}

	/**
	 * @desc ：2.短信发送记录查询接口 用于查询短信发送的状态，是否成功到达终端用户手机
	 * @param phone
	 * @param bizId
	 * @return
	 * @throws ClientException 
	 */
	public QuerySendDetailsResponse querySendDetails(String bizId, String phone) throws ClientException {
		// 可自助调整超时时间:设置从服务器读取响应的超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
				Env.ACCESSKEY_ID, Env.ACCESSKEY_SECRET);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", Env.PRODUCT,
				Env.DOMAIN);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象
		QuerySendDetailsRequest request = new QuerySendDetailsRequest();
		// 必填-号码
		request.setPhoneNumber(phone);
		// 可选-流水号
		request.setBizId(bizId);
		// 必填-发送日期 支持30天内记录查询，格式yyyyMMdd
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		request.setSendDate(ft.format(new Date()));
		// 必填-页大小
		request.setPageSize(10L);
		// 必填-当前页码从1开始计数
		request.setCurrentPage(1L);

		// hint 此处可能会抛出异常，注意catch
		QuerySendDetailsResponse querySendDetailsResponse = acsClient
				.getAcsResponse(request);
		return querySendDetailsResponse;
	}
}
