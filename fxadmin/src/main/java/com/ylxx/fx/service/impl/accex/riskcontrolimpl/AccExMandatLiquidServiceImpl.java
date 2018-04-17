package com.ylxx.fx.service.impl.accex.riskcontrolimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.AdditionalMarginVo;
import com.ylxx.fx.core.mapper.LogfileCmdMapper;
import com.ylxx.fx.core.mapper.accex.riskcontrol.AccExMandatLiquidMapper;
import com.ylxx.fx.core.mapper.accex.riskcontrol.AdditionalMarginMapper;
import com.ylxx.fx.service.accex.riskcontrol.IAccExMandatLiquidService;
import com.ylxx.fx.service.po.AccExMandatLiquid;
import com.ylxx.fx.service.po.AdditionalMargin;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
import com.ylxx.fx.utils.ResultDomain;
@Service("accExMandatLiquidService")
public class AccExMandatLiquidServiceImpl implements IAccExMandatLiquidService{
private static final Logger LOGGER = LoggerFactory.getLogger(AccExMandatLiquidServiceImpl.class);
	@Resource
	private AccExMandatLiquidMapper accExMandatLiquidMapper;
	@Resource(name="additionalMarginMapper")
	private AdditionalMarginMapper additionalMarginMapper;
	@Resource(name="logfileCmdMapper")
	private LogfileCmdMapper logfileCmdMapper;
	
	@Override
	public String mandatLiquid(String userKey, String prod, Integer pageSize, Integer pageNo) {
		List<AccExMandatLiquid> ambList = new ArrayList<AccExMandatLiquid>();
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
	String result="";
		try {
			BigDecimal[] risks = getRiskProperties(prod);
			List<AccExMandatLiquid> list=accExMandatLiquidMapper.mandatLiquid(prod);
			if(list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					BigDecimal risk=new BigDecimal(list.get(i).getRisk());
					if (risk.compareTo(risks[1])<=0){
					AccExMandatLiquid amb=new AccExMandatLiquid();
					amb.setTrac(list.get(i).getTrac());
					amb.setCyen(list.get(i).getCyen());
					amb.setAmut(list.get(i).getAmut());
					amb.setMarg(list.get(i).getMarg());
					amb.setProfit(list.get(i).getProfit());
					amb.setRisk(list.get(i).getRisk());
					amb.setCuno(list.get(i).getCuno());
					amb.setCuac(list.get(i).getCuac());
					amb.setTlno(list.get(i).getTlno());
					ambList.add(amb);
					}
				}
			PageInfo<AccExMandatLiquid> page=new PageInfo<AccExMandatLiquid>(ambList);
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}else {
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(),"查询无结果");
			}
		}catch(Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询异常");
		}
		
		return result;
	}
	@Override
	public String mandatLiquidService(String userKey, ArrayList<AdditionalMarginVo> admlist) {
		String result="";
		try {
			int success = 0;
			int fail = 0;
			int error = 0;
			getSocketConfig();
			if (ip==null || port==0){
				return "强平ip端口获取失败";
			}
			LOGGER.info("获取的强平IP:"+ip+",端口:"+port);
			for(int i=0;i<admlist.size();i++) {
				 
				String trac = admlist.get(i).getTrac();
				String cyen = admlist.get(i).getCyen();
				String lcno = generateLCNO("P007");
				if (lcno == null || lcno.length()!=8){
					error ++;
					continue;
				}
				String trsn = "Z01"+"00"+DataTimeClass.getCurDateTime()+lcno+"00"+"Z01"+"000000";
				String msg = msg1stpart+trsn+msg2ndpart+trac+msg3thpart+cyen+msglastpart;
				LOGGER.info("账户外汇强平     交易帐号:"+trac+",保证金币种:"+cyen);
				String returnMsg = Action(msg);
				if(returnMsg != null) {
					returnMsg = returnMsg.split("end")[0];
					String stcd = splitMsg(returnMsg);
					if (stcd.equals("S")){
						success++;
					} else if (stcd.equals("E")){
						fail++;
					} else {
						error ++;
					}
				} else {
					error ++;
				}
			}
			error = admlist.size() - success - fail;
			StringBuffer sb = new StringBuffer();
			sb.append("执行结果:成功").append(success).append("笔,失败").append(fail).append("笔,异常").append(error).append("笔.");
			LOGGER.info("返回页面信息:" + sb.toString());
			 result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(),sb.toString());
		} catch (Exception e) {
			// TODO: handle exception
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(),"强平异常");
		}
		return result;
	}

	
	public BigDecimal[] getRiskProperties(String prod){
		BigDecimal[] prop = new BigDecimal[2];
		try {
			List<AdditionalMargin> list = additionalMarginMapper.selectPtpara(prod.trim());
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getPaid().equals("BZJ60")){
					prop[0] = new BigDecimal(list.get(i).getValu());
				}
				if(list.get(i).getPaid().equals("BZJ20")){
					prop[1] = new BigDecimal(list.get(i).getValu());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return prop;
	}
	// 内部通讯
	protected ClientSocket cs;
	protected String ip;
	protected int port = 0;
	protected int timeout;
	private String msg1stpart = "<Transaction><Transaction_Header><TRSN>";
	private String msg2ndpart = "</TRSN><TRID>B2109</TRID><BHID>XZ01</BHID><CHNL>1400</CHNL><RQDT>20160114</RQDT><RQTM>09:00:12</RQTM><TRTL>990010001</TRTL><TTYN></TTYN><AUTL></AUTL></Transaction_Header><Transaction_Body><request><TRAC>";
	private String msg3thpart = "</TRAC><CYEN>";
	private String msglastpart = "</CYEN></request></Transaction_Body></Transaction>";
	public void getSocketConfig(){
		ReadStringCodeConfig readString = new ReadStringCodeConfig();
		readString.readStringCodeProperties("strcode1");
		ReadSocketConfig readsockt = new ReadSocketConfig();
		readsockt.readWainingProperties("mandatliquidIp", "mandatliquidPort");
		ip = readsockt.getSocketip();
		port = readsockt.getSocketport();
		timeout = 60000;
	}
	public String generateLCNO(String ptid) {
		return accExMandatLiquidMapper.generateLCNO(ptid);
	}
	public String Action(String msgin) {
		String msgrt = null;
		LOGGER.info("账户外汇强平发送报文: " + msgin);
		try {
			if (createConnection()) {
				
				sendMessage(msgin);
				msgrt = getMessage();
				LOGGER.info("账户外汇强平接收报文: " + msgrt);
			}

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return "ActionError";
		}finally{
			if(cs != null){
				cs.shutDownConnection();
			}
		}
		return msgrt;
	}
	private boolean createConnection() {
		cs = new ClientSocket(ip, port, timeout);
		try {
			cs.CreateConnection();
			LOGGER.debug("连接服务器成功!" + "\n");

			return true;
		} catch (Exception e) {
			LOGGER.debug("连接服务器失败!" + "\n");
			return false;
		}

	}
	private void sendMessage(String msg) {
		if (cs == null)
			return;
		try {
			cs.sendMessage(msg);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("发送消息失败!" + "\n");
		}
	}
	
	private String getMessage() {
		String rt = "";
		if (cs == null)
			return null;
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(cs.getMessageStream());

		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("接收消息缓存错误\n");
			return null;
		}

		try {

			while (true) {
				String read = null;

				if (inputStream != null) {
					read = inputStream.readLine();
				}

				if (read == null) {
					break;
				}
				rt += read;
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("接收消息错误" + "\n");
			return null;
		}
		try {
			return new String(rt.getBytes(), "utf-8");

			// return new String((rt.getBytes(ISO8859_1),"gb2312"));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
public String splitMsg(String msg){
	Document document = null;
	Element root = null;
	try {
		document = DocumentHelper.parseText(msg);
		root = document.getRootElement();

		LOGGER.info("拆解交易返回的报文头");

		String stcd = null;
		Element transactionHeader = (Element) root
				.selectSingleNode("/Transaction/Transaction_Header");
		List<?> list = transactionHeader.elements();
		LOGGER.info("拆解交易返回交易状态");
		transactionHeader = (Element) root
				.selectSingleNode("/Transaction/Transaction_Header/tran_response");
		list = transactionHeader.elements();
		for (int j = 0; j < list.size(); j++) {
			Element element = (Element) list.get(j);
			if (element.getName().equals("type")) {
				stcd = element.getText();
				return stcd;
			}
		}
		return stcd;
	} catch (DocumentException e) {
		return "F";
	}
}
@Override
public String upAutoForceStat(String prod, String stat) {
	String result="";
	try {
		boolean flag=accExMandatLiquidMapper.upAutoForceStat(prod,stat);
		if (flag==true) {
			stat = getAutoForceStat("", prod);
			if ("0".equals(stat)) {
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "强平开关开启");	
			}
			if("1".equals(stat)) {
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "强平开关关闭");	
			}
		}
	} catch (Exception accExMandatLiquidMappere) {
		result= ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "强平开关配置失败");
	}
	
	return result;
}
private String getAutoForceStat(String userKey, String prod) {
	HashMap<String,String> autoMap=accExMandatLiquidMapper.getAutoForceStat(prod);
	return autoMap.get("VALU");
}
@Override
public String getAutoForceSta(String userKey, String prod) {
	String result="";
	try {
		HashMap<String,String> autoMap=accExMandatLiquidMapper.getAutoForceStat(prod);
		result= ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), autoMap.get("VALU"));	
	} catch (Exception e) {
		result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "获取开关配置失败");
	}
	return result;
}
}
	class ClientSocket {
		private String ip;

		private int port;

		private int outtime;

		private Socket socket = null;

		PrintWriter out = null;

		InputStreamReader getMessageStream = null;
	 Logger LOGGER = LoggerFactory.getLogger(ClientSocket.class);
		public ClientSocket(String ip, int port, int outtime) {
			this.ip = ip;
			this.port = port;
			this.outtime = outtime;
		}
		public void CreateConnection() throws IOException {
			try {
				// setSoTimeout
				// SocketAddress endpoint = new InetSocketAddress(ip, port);
				socket = new Socket(ip, port);
				socket.setSoTimeout(outtime);
				// socket.connect(endpoint, outtime);

			} catch (Exception e) {
				// e.printStackTrace();
				if (socket != null)
					socket.close();
				throw e;
			} finally {
			}		
		}
		public void sendMessage(String sendMessage) throws Exception {
			try {

				out = new PrintWriter(socket.getOutputStream());
				out.println(sendMessage);
				out.flush();

			} catch (Exception e) {
				// e.printStackTrace();
				if (out != null)
					out.close();
				throw e;
			} finally {
			}
		}
		public InputStreamReader getMessageStream() throws Exception {
			try {

				getMessageStream = new InputStreamReader(socket.getInputStream());
				return getMessageStream;
			} catch (Exception e) {
				// e.printStackTrace();
				if (getMessageStream != null)
					getMessageStream.close();
				throw e;
			} finally {
			}
		}
		public void shutDownConnection() {
			try {
				if (out != null)
					try{
						out.close();
					}catch(Exception e){
						LOGGER.error(e.getMessage(), e);
					}
				if (getMessageStream != null){
					try{
						getMessageStream.close();
					}catch(Exception e){
						LOGGER.error(e.getMessage(), e);
					}
				}
				if (socket != null){
					try{
						socket.close();
					}catch(Exception e){
						LOGGER.error(e.getMessage(), e);
					}
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

