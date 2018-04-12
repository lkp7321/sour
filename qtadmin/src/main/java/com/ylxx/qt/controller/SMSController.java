package com.ylxx.qt.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylxx.qt.service.ShortMessageService;
import com.ylxx.qt.utils.CheckSumBuilder;

/**
 * 阿里云短信sdk接口
 * 
 * @author: suimanman
 * @date : 2018年02月11日 下午14:10:06
 * 
 */
@Controller
@RequestMapping("/sms")
public class SMSController {
	@Resource
	private ShortMessageService sms;

	/**
	 * @desc ：调用发送短信
	 * @param phone
	 * @param bizId
	 * @return
	 * @throws ServerException
	 * @throws ClientException
	 * @throws InterruptedException
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/sendSMS.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody
	String sendRegisterMessage(HttpServletRequest request,
			HttpServletResponse response, String phone) throws ClientException,
			InterruptedException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		// 获取6位数字验证码
		CheckSumBuilder checkSum = new CheckSumBuilder();
		String code = checkSum.getRandomStr(6, 0);

		// 1.调用接口，发短信
		SendSmsResponse smsResponse = sms.sendSms(phone, code);
		if (smsResponse.getCode() != null && smsResponse.getCode().equals("OK")) {
			map.put("code", code);// 验证码
			map.put("phone", phone);// 手机号
			map.put("bizid", smsResponse.getBizId());// 验证码流水号
			map.put("sendDate", String.valueOf(System.currentTimeMillis()));// 当前时间
		}

		return mapper.writeValueAsString(map);
	}

	/**
	 * @desc ：查询输入的短信验证码
	 * @param phoneNum
	 * @param phoneCode
	 * @param bizId
	 * @param sendDate
	 * @param phone
	 * @param code
	 * @return
	 * @throws ServerException
	 * @throws ClientException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/checkSMS.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody
	String checkRegisterMessage(HttpServletRequest request,
			HttpServletResponse response, String phoneNum, String phoneCode,
			String bizid, String sendDate, String phone, String code)
			throws ClientException, InterruptedException {
		JSONObject result = new JSONObject();
		// 查明细
		if (null != code && code != "") {
			if (phoneCode.equals(code) && phoneNum.equals(phone)) {
				QuerySendDetailsResponse querySendDetailsResponse = sms
						.querySendDetails(bizid, phoneNum);
				if (null != querySendDetailsResponse.getCode()
						&& querySendDetailsResponse.getCode().equals("OK")) {
					for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse
							.getSmsSendDetailDTOs()) {
						String sendNewDate = smsSendDetailDTO.getSendDate();
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date sendDateTime;
						// 得到long类型当前时间
						long curdate = System.currentTimeMillis();
						// 转换提日期输出格式
						try {
							sendDateTime = df.parse(sendNewDate);
							long send = sendDateTime.getTime();
							long diff = curdate - send;
							// 大于5分钟=300000毫秒，验证码失效，5分钟后从session中删除
							if (diff > 300000) {
								result.put("success", false);
								result.put("msg", "验证码已失效！");
							} else {
								result.put("success", true);
								result.put("msg", "发送成功！");
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				result.put("success", false);
				result.put("msg", "手机号或验证码不一致！");
			}
		} else {
			result.put("success", false);
			result.put("msg", "验证码不能为空！");
		}
		return result.toString();
	}

}
