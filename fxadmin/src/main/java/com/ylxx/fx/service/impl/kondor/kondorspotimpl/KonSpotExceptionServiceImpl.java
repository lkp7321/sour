package com.ylxx.fx.service.impl.kondor.kondorspotimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.kondor.kondorspot.KonSpotExceptionMapper;
import com.ylxx.fx.service.kondor.kondorspot.IKonSpotExceptionService;
import com.ylxx.fx.service.po.Kon_SpotExceptionBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
import com.ylxx.fx.utils.ResultDomain;


@Service("konSpotExceptionService")
public class KonSpotExceptionServiceImpl implements IKonSpotExceptionService {

	@Resource
	private KonSpotExceptionMapper konSpotExceptionMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(KonSpotExceptionServiceImpl.class);
	//查询
	public String selExceptionList(String startDate, String endDate,
			Integer pageNo,Integer pageSize){
		String result = "";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
	 	try {
			List<Kon_SpotExceptionBean> beans = konSpotExceptionMapper.selExceptionList(startDate.trim(),
					endDate.trim());
			for (int i = 0; i < beans.size(); i++) {
				String useflag = beans.get(i).getProductcode();
				String value = "";
				if(useflag=="SPOTP001")
		          	value = "个人实盘";
		        else if(useflag=="SPOTP004")
		          	value = "结售汇";
		        else if(useflag=="SPOTP006")
		          	value ="外币兑换";
		        else if(useflag=="SPOTP071")
	          	    value ="账户结售汇";
	          	else if(useflag=="SPOTP072")
	          	    value ="账户外汇";
	          	else if(useflag=="SPOTP073")
	          	    value ="账户贵金属";
	          	else if(useflag=="SPOTP074")
	          	    value ="贵金属平盘";
	            else
		          	value ="未知";
				beans.get(i).setProductcode(value);
			}
			PageInfo<Kon_SpotExceptionBean> page = new PageInfo<Kon_SpotExceptionBean>(beans);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
	 	return result;
	}
	//重新发送
	public String sendException(String prcd,String rpcNo){
		ReadSocketConfig rs = new ReadSocketConfig();
		rs.readWainingProperties("kondorIp", "kondorPort");
		String ip = rs.getSocketip();
		int port = rs.getSocketport();
		String sendString="send";
		sendString+="|"+prcd+"|"+rpcNo;
		LOGGER.info(ip+"----"+port+"----"+sendString);
		String str = this.sendExcMsg(ip,port,sendString);
		return str;
	}
	//
	public String sendExcMsg(String tempstrIP, int tempintPort,
			String sendString){
		Socket client = null;
		PrintWriter out=null;
		BufferedReader in = null;
		ReadStringCodeConfig readString = new ReadStringCodeConfig();
		readString.readStringCodeProperties("strcode1");
		String stringCode=readString.getStringCode();
		try {
			// logger.info("strIP="+tempstrIP+"	intPort=:"+tempintPort);

			SocketAddress sa=new InetSocketAddress(tempstrIP, tempintPort);
			client = new Socket();
			client.connect(sa,2000);
			client.setSoTimeout(5000);
			in = new BufferedReader(new InputStreamReader(client
					.getInputStream(),stringCode));
			out  = new PrintWriter(new OutputStreamWriter(client.getOutputStream(),stringCode),true);
			out.println(sendString);

			out.flush();
			client.shutdownOutput();
			String temp = in.readLine();
			LOGGER.info("temp="+temp);
			
			
			//if(temp!=null&&!"".equals("")){
			//RV前置中外汇前置交易--》交易异常信息中点击重新发送后始终报未成功
			if(temp!=null&&!"".equals(temp)){
				if (temp.endsWith("true")) {
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_60.getCode(), "发送成功!");
				}
			}
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "发送失败，请稍候重试...");

		} catch (UnknownHostException e1) {
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "未知的主机异常");
		} catch (IOException e2) {
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "服务器连接异常");
		} finally {
			try {
				if(client!=null){
					client.close();
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
			}
			sendString = null;
			out = null;
			client = null;
		}
	}
}
