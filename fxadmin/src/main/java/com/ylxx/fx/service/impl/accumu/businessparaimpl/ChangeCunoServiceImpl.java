package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.service.accumu.businesspara.IChangeCunoService;
import com.ylxx.fx.service.impl.pere.presysmanagerimpl.QueryNationCodeServiceImpl;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
import com.ylxx.fx.utils.ResultDomain;
@Service("iChangeCunoService")
public class ChangeCunoServiceImpl implements IChangeCunoService{
	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeCunoServiceImpl.class);
	private  ReadStringCodeConfig  readString=new ReadStringCodeConfig();
	private  String readMessage=null;
	private  StringBuffer message=new StringBuffer();
	
	public String doChange(String oldcuno, String newcuno, String caty) {
		String resulta = "";
		Date currentDate = new Date();
		SimpleDateFormat rq = new SimpleDateFormat("yyyyMMdd");		
		SimpleDateFormat time = new SimpleDateFormat("HHmmss");
		String curDate = rq.format(currentDate);
		String curTime = time.format(currentDate);
		
		readString.readStringCodeProperties("strcode1");//utf-8
		String  stringCode=readString.getStringCode();
		ReadSocketConfig readsockt = new ReadSocketConfig();
		readsockt.readWainingProperties("ghkhIp", "ghkhPort");

		String input="<Transaction><Transaction_Header><TRSN>56801"+curDate+curTime+"0000568"+curTime+"</TRSN><TRID>A1112</TRID><TRTL></TRTL><BHID>9001</BHID><CHNL>1101</CHNL>" +
		"<RQDT>"+curDate+"</RQDT><RQTM>"+curTime+"</RQTM><TTYN></TTYN><AUTL></AUTL></Transaction_Header><Transaction_Body><request><CATY>"+caty+"</CATY><OLCU>"+oldcuno+"</OLCU>" +
				"<NECU>"+newcuno+"</NECU><EXDA></EXDA></request></Transaction_Body></Transaction>";
						
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String result=sendMessage(strip,strport,input);
		System.out.print(result);
		if(result.equals("S")){
			resulta=ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), "更换成功");
			
		}else{
			resulta=ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), "更换失败");
		}	
		return resulta;
	}
	
	private String sendMessage(String ip, int port, String input) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		int i=0;
		try {
			socket = new Socket(ip, port);
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(), "utf-8")), true);			
			out.println(input);
			while((readMessage=in.readLine())!=null){
				message.append(readMessage);
			}
			LOGGER.info("Socket返回的数据为："+message);
			System.out.println("Socket返回的数据为："+message);
			
			i=message.indexOf("<type>"); 
						
			
		} catch (Exception e) {
			LOGGER.info(e.getMessage(),e);
		} finally {
			try {
				if(socket!=null){
					socket.close();
				}
				if(in!=null){
					in.close();
				}
				if(out!=null){
					out.close();
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return  String.valueOf( message.charAt(i+6));
	}

}
