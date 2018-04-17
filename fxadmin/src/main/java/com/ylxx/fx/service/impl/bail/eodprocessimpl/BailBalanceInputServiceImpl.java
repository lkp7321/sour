package com.ylxx.fx.service.impl.bail.eodprocessimpl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.ylxx.fx.core.mapper.bail.eodprocess.BailBalanceInputMapper;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.service.bail.eodprocess.IBailBalanceInputService;
import com.ylxx.fx.service.po.BailBalanceBean;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.SocketLocal;


@Service("bailBalanceInputService")
public class BailBalanceInputServiceImpl implements IBailBalanceInputService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BailBalanceInputServiceImpl.class);
	@Resource
	private BailBalanceInputMapper bailBalanceInputMapper;
	@Resource
	private PdtRParaMapper pdtRParaMapper;
	
	//查询保证金余额录入/保证金利息录入
	public String queryBailBalanceList(String hddate,String tradeCode){
		String result = "";
		try {
			List<BailBalanceBean> outAccountList = bailBalanceInputMapper
					.selectBalanceInputList(hddate, tradeCode);
			List<BailBalanceBean> beans = new ArrayList<BailBalanceBean>();
			BailBalanceBean bean = null;
			for (int i = 0; i < outAccountList.size(); i++) {
				bean = new BailBalanceBean();
				bean = outAccountList.get(i);
				String stcd = bean.getStcd();
				if(stcd.equals("0")){
					stcd="录入";
				}else if(stcd.equals("1")){
					stcd="处理中";
				}else if(stcd.equals("2")){
					stcd="复核";
				}else if(stcd.equals("3")){
					stcd="过期无效";
				}
				bean.setStcd(stcd);
				bean.setFlag("0");
				beans.add(bean);
				if (bean.getBzdt().equals(hddate)) {
					BailBalanceBean balanceBean = new BailBalanceBean();
					balanceBean.setPacm(bean.getPacm());
					balanceBean.setKcam(bean.getKcam());
					balanceBean.setTllx(bean.getTllx());
					balanceBean.setComm(bean.getComm());
					balanceBean.setFlag("1");
					beans.add(balanceBean);
				}
			}
			LOGGER.info(hddate+"日期下条数为："+beans.size());
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(beans));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//保证金余额录入/保证金利息录入发送报文
	public String sunyiluru(String userKey,BailBalanceBean bailBalanceBean) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setCurIP("127.0.0.1");
		curUser.setProd("P009");*/
		
		ReadStringCodeConfig readString = new ReadStringCodeConfig();
		readString.readStringCodeProperties("strcode1");
		String stringCode=readString.getStringCode();
		
		SocketLocal mysocket = new SocketLocal();
		LogfileBean loginfo = null;
		//生成渠道流水号
		String trid=generatePK();
		String inputString ="";
		if("M5715".equals(bailBalanceBean.getTrid())){
			inputString ="<Transaction><Transaction_Header>" +
						"<TRSN>"+trid+"</TRSN>" +
						"<TRID>"+bailBalanceBean.getTrid()+"</TRID>" +
						"<BHID>X520</BHID>" +
						"<CHNL>1400</CHNL>" +
						"<RQDT>"+DataTimeClass.getNowDay()+"</RQDT>" +
						"<RQTM>"+DataTimeClass.getCurTime()+"</RQTM>" +
						"<TRTL>"+curUser.getUsnm()+"</TRTL>" +
						//"<TRTL>1001000002</TRTL>" +
						"<TTYN></TTYN>" +
						"<AUTL></AUTL>" +
						"</Transaction_Header>" +
						"<Transaction_Body>" +
						"<request>" +
						"<BZDT>"+bailBalanceBean.getBzdt()+"</BZDT>" +
						"<PACM>"+bailBalanceBean.getPacm()+"</PACM>" +
						"<KCAM>"+bailBalanceBean.getKcam()+"</KCAM>" +
						"<TLLX>"+bailBalanceBean.getTllx()+"</TLLX>" +
						"</request>" +
						"</Transaction_Body>" +
						"</Transaction>\n";
		}else if("M5716".equals(bailBalanceBean.getTrid())){
			inputString ="<Transaction><Transaction_Header>" +
						"<TRSN>"+trid+"</TRSN>" +
						"<TRID>"+bailBalanceBean.getTrid()+"</TRID>" +
						"<BHID>X520</BHID>" +
						"<CHNL>1400</CHNL>" +
						"<RQDT>"+DataTimeClass.getNowDay()+"</RQDT>" +
						"<RQTM>"+DataTimeClass.getCurTime()+"</RQTM>" +
						"<TRTL>"+curUser.getUsnm()+"</TRTL>" +
						//"<TRTL>1001000002</TRTL>" +
						"<TTYN></TTYN>" +
						"<AUTL></AUTL>" +
						"</Transaction_Header>" +
						"<Transaction_Body>" +
						"<request>" +
						"<BZDT>"+bailBalanceBean.getBzdt()+"</BZDT>" +
						"<COMM>"+bailBalanceBean.getComm()+"</COMM>" +
						"</request>" +
						"</Transaction_Body>" +
						"</Transaction>\n";
		}
		ReadSocketConfig readsockt = new ReadSocketConfig();
		// 参数 1：用户IP   参数 2：用户端口
		readsockt.readWainingProperties("bailIp", "bailPort");
		String strip = null;
		int strport = 0;
		strip = readsockt.getSocketip();
		strport = readsockt.getSocketport();
		LOGGER.info("bailIp:" + strip + " bailPort:" + strport);
		String temString = mysocket.sendPutPrice(strip, strport, inputString);
		LOGGER.info("inputString=="+inputString+"||||||temString==="+temString);
		if(temString==null || temString.equals("")){
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "后台返回数据为空!");
		}else if ("false".equals(temString)) {
			String nowtime = DataTimeClass.getCurDateTime();
			if("M5715".equals(bailBalanceBean.getTrid())){
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "保证金余额并账录入报文:" + inputString
						+ ",保证金余额并账录入IP:" + strip + ",保证金余额并账录入port:" + strport
						+ ",保证金余额并账录入失败(socket异常)!时间:" + DataTimeClass.getCurDateTime());
				loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("保证金余额并账录入");
				loginfo.setRemk("保证金余额并账录入");
				loginfo.setVold("登录产品:" + curUser.getProd() + ",保证金余额并账录入IP:" + strip
						+ ",保证金余额并账录入port:" + strport);
				loginfo.setVnew("保证金余额并账录入失败(socket异常)");
				loginfo.setProd(curUser.getProd());
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "保证金余额并账录入失败!");
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "保证金余额并账录入异常!");
				}
			}else  if("M5716".equals(bailBalanceBean.getTrid())){
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "保证金利息并账录入报文:" + inputString
						+ ",保证金利息并账录入IP:" + strip + ",保证金利息并账录入port:" + strport
						+ ",保证金利息并账录入失败(socket异常)!时间:" + DataTimeClass.getCurDateTime());
				loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("保证金利息并账录入");
				loginfo.setRemk("保证金利息并账录入");
				loginfo.setVold("登录产品:" + curUser.getProd() + ",保证金利息并账录入IP:" + strip
						+ ",保证金利息并账录入port:" + strport);
				loginfo.setVnew("保证金利息并账录入失败(socket异常)");
				loginfo.setProd(curUser.getProd());
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "保证金利息并账录入失败!");
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "保证金利息并账录入异常!");
				}
			}
		}
		try {
			temString = new String(temString.getBytes(stringCode), "UTF-8");
			LOGGER.info("*****"+bailBalanceBean.getTrid()+"后台返回值:" + temString);
		} catch (UnsupportedEncodingException e1) {
			 LOGGER.error(e1.getMessage(),e1);
			 return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "录入异常!");
		}
		String inTransCode = parseMessage(temString);
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_60.getCode(), JSON.toJSONString(inTransCode));
	}
	//生成渠道流水号
	public String generatePK(){
		String trid = null;
		try {
			String sql1 = bailBalanceInputMapper.generatorseq1();
			String sql2 = bailBalanceInputMapper.generatorseq2();
			trid="52000"+DataTimeClass.getNowDay()+sql1+"520"+sql2;
			LOGGER.info("前台流水号："+trid);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("生成渠道流水号失败!");
		}
		return trid;
	}
	//解析报文
	public String parseMessage(String temString){
		Document document = null;
		String outputString = "";
		String[] tempOutput = temString.split("end");
		outputString = tempOutput[0];
		try {
			document = DocumentHelper.parseText(outputString);
		} catch (DocumentException e1) {
			 LOGGER.error(e1.getMessage(),e1);
		}
		Element root = document.getRootElement();
		Element desc = (Element) root.selectSingleNode("/Transaction/Transaction_Header/tran_response/desc");
		String inTransCode = desc.getText();
		return inTransCode;
	}
	
}
