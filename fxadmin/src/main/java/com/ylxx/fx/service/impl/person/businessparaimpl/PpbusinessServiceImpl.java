package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.person.businesspara.PpbusinessMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.businesspara.PpbusinessService;
import com.ylxx.fx.service.po.CK_handMxdetail;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.QutCmmPrice;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
import com.ylxx.fx.utils.SocketLocal;

@Service("ppbusinessService")
public class PpbusinessServiceImpl implements PpbusinessService {
	private ReadStringCodeConfig readString=new ReadStringCodeConfig();
	private String  stringCode="";
	@Resource
	private PpbusinessMapper ppbusinessMap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	private static final Logger log = LoggerFactory.getLogger(PpbusinessServiceImpl.class);
	/*****
	 * 原油手工登记
	 * 
	 * @param price
	 *            
	 * @return 通知结果
	 */
	public String sendToCkServer(QutCmmPrice price,String userKey) {
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		SocketLocal mysocket = new SocketLocal();
		Logfile loginfo = null;
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		
		String strxml = "<Transaction><Transaction_Header><TRCD>PP007</TRCD><PRCD>"+curUser.getProd()
						+"</PRCD></Transaction_Header><Body><request>" ;
		String requestPrice = "" ;
		if(price.getSide()!=null&&price.getSide().equals("1")){
			requestPrice = "<requestPrice>"+price.getNeby()+"</requestPrice>" ;
		}else if(price.getSide()!=null&&price.getSide().equals("2")){
			requestPrice = "<requestPrice>"+price.getNesl()+"</requestPrice>" ;
		}else{
			requestPrice = "<requestPrice></requestPrice>" ;
		}
		
		String currencyLeft = "" ;
		if(price.getExcd().contains("WTI")){
			currencyLeft = "<currencyLeft>WTI</currencyLeft><faceCurrency>WTI</faceCurrency>";
		}else if(price.getExcd().contains("BRT")){
			currencyLeft = "<currencyLeft>BRT</currencyLeft><faceCurrency>BRT</faceCurrency>";
		}else{
			currencyLeft = "<currencyLeft></currencyLeft><faceCurrency></faceCurrency>";
		}
		strxml += requestPrice+"<currency>"+price.getYearcode()+"</currency><side>"+
					price.getSide()+"</side><OrdType>H</OrdType>"+
					currencyLeft+"<currencyRight>USD</currencyRight><faceAmt>"+
					price.getAmat()+"</faceAmt><name>"+
					curUser.getUsnm()+"</name><ppds>高盛</ppds><point>"+
					price.getPoint()+"</point><SettlementDate>"+
					price.getSettlementtd()+"</SettlementDate><fixingDate>"+
					price.getFixingtd()+"</fixingDate><ogcd>"
					+curUser.getCstn()+"</ogcd></request></Body></Transaction>\r\n";
		ReadSocketConfig readsockt = new ReadSocketConfig();
		// 参数 1：用户IP
		// 参数 2：用户端口
		readsockt.readWainingProperties("OILPPIP", "OILPPPORT");
		String strip = null;
		int strport = 0;
		strip = readsockt.getSocketip();
		strport = readsockt.getSocketport();
		log.info("fxipip:" + strip + " fxipport:" + strport);
		String temString = mysocket.sendPutPrice(strip, strport, strxml);
		if (temString.equals("false")) {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "手工登记报文:" + strxml
					+ ",平盘IP:" + strip + ",平盘port:" + strport
					+ "平盘失败(socket异常)!时间:" + DataTimeClass.getCurDateTime());
			loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("原油手工平盘");
			loginfo.setRemk("原油手工平盘");
			loginfo.setVold("登录产品:" + curUser.getProd() + ",平盘IP:" + strip
					+ ",平盘port:" + strport);
			loginfo.setVnew("原油手工平盘失败(socket异常)");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return "原油手工平盘请求失败！";

		}

		try {
			temString = new String(temString.getBytes(stringCode), "UTF-8");
			log.info("*****手工登记后台返回值:" + temString);
		} catch (UnsupportedEncodingException e1) {
			 log.error(e1.getMessage(),e1);
		}
		String temArray[] = temString.split("\\|");

		if (temArray.length < 3) {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "手工登记报文:" + strxml
					+ ",平盘IP:" + strip + ",平盘port:" + strport + "平盘失败!时间:"
					+ DataTimeClass.getCurDateTime());
			loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("原油手工平盘");
			loginfo.setRemk("原油手工平盘");
			loginfo.setVold("登录产品:" + curUser.getProd() + ",平盘IP:" + strip
					+ ",平盘port:" + strport);
			loginfo.setVnew("原油手工平盘失败");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return "原油手工平盘请求失败！";
		}
		if ("00000".equals(temArray[2])) {
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "手工登记报文:" + strxml
					+ ",平盘IP:" + strip + ",平盘port:" + strport + "平盘成功!时间:"
					+ DataTimeClass.getCurDateTime());
			loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("原油手工平盘");
			loginfo.setRemk("原油手工平盘");
			loginfo.setVold("登录产品:" + curUser.getProd() + ",平盘IP:" + strip
					+ ",平盘port:" + strport);
			loginfo.setVnew("原油手工平盘请求成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		}
		return temArray[3];
	}
	
	/**
	 * 平盘交易查询
	 */
	public List<CK_handMxdetail> getdate(String apdt, String jsdt) {
		List<CK_handMxdetail> list=null;
		try {
			list= ppbusinessMap.seldate(apdt, jsdt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	//原油手工平盘
	public List<QutCmmPrice> selPrice() {
		List<QutCmmPrice> list = null;
		try {
			list = ppbusinessMap.selectPrice();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setExnm(exchangeExnm(list.get(i).getExnm()));
			}
		}
		return list;
	}
	private String exchangeExnm(String exnm) {
		if(exnm==null){
			return "";
		}
		if(exnm.endsWith("F")){
			if(exnm.startsWith("CL")){
				return "北美一月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特一月" ;
			}
		}
		if(exnm.endsWith("G")){
			if(exnm.startsWith("CL")){
				return "北美二月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特二月" ;
			}
		}
		if(exnm.endsWith("H")){
			if(exnm.startsWith("CL")){
				return "北美三月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特三月" ;
			}
		}
		if(exnm.endsWith("J")){
			if(exnm.startsWith("CL")){
				return "北美四月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特四月" ;
			}
		}
		
		if(exnm.endsWith("K")){
			if(exnm.startsWith("CL")){
				return "北美五月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特五月" ;
			}
		}
		if(exnm.endsWith("M")){
			if(exnm.startsWith("CL")){
				return "北美六月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特六月" ;
			}
		}
		if(exnm.endsWith("N")){
			if(exnm.startsWith("CL")){
				return "北美七月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特七月" ;
			}
		}
		if(exnm.endsWith("Q")){
			if(exnm.startsWith("CL")){
				return "北美八月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特八月" ;
			}
		}
		if(exnm.endsWith("U")){
			if(exnm.startsWith("CL")){
				return "北美九月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特九月" ;
			}
		}
		if(exnm.endsWith("V")){
			if(exnm.startsWith("CL")){
				return "北美十月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特十月" ;
			}
		}
		if(exnm.endsWith("X")){
			if(exnm.startsWith("CL")){
				return "北美十一月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特十一月" ;
			}
		}
		if(exnm.endsWith("Z")){
			if(exnm.startsWith("CL")){
				return "北美十二月";
			}else if(exnm.startsWith("LCO")){
				return "布伦特十二月" ;
			}
		}
		return "其他";
	}
	
}
