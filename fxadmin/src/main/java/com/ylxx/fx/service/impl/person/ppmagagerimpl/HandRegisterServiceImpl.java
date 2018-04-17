package com.ylxx.fx.service.impl.person.ppmagagerimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.ppmagager.CheckppDetailMapper;
import com.ylxx.fx.core.mapper.person.ppmagager.HandRegisterMapper;
import com.ylxx.fx.service.person.ppmagager.IHandRegisterService;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.Register;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.SocketLocal;

@Service("handRegisterService")
public class HandRegisterServiceImpl implements IHandRegisterService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HandRegisterServiceImpl.class);
	@Resource
	private HandRegisterMapper handRegisterMapper;
	@Resource
	private CheckppDetailMapper checkppDetailMapper;
	//在总敞口中查找币别对
	public String selectCurrencyPair(String prod){
		String result = "";
		try {
			if (prod.equals("P007")) {
				prod = "P074";
			}
			List<Map<String, String>> CmmCymsgs = handRegisterMapper.selectCurrencyPair(prod);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(CmmCymsgs));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//获取敞口列表
	public String selectCkno(String prod){
		String result = "";
		try {
			if (prod.equals("P007")) {
				prod = "P074";
			}
			List<Map<String, String>> cks = handRegisterMapper.selectCkno(prod);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(cks));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//获取平盘对手列表
	public String selectPPDS(String prod){
		String result = "";
		try {
			List<Map<String, String>> ppds = new ArrayList<Map<String,String>>();
			if (prod.equals("P007")) {
				ppds = handRegisterMapper.selPPDS();
			}else {
				ppds = handRegisterMapper.selectPPDS();
			}
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(ppds));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//登记
	public String registerCK(String userKey,Register ckno) throws Exception {
		
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P001");
		curUser.setCurIP("127.0.0.1");*/
		
		boolean bo = false;
		ReadStringCodeConfig readString = new ReadStringCodeConfig();
		readString.readStringCodeProperties("strcode1");
		String stringCode=readString.getStringCode();
		SocketLocal mysocket = new SocketLocal();
		LogfileBean loginfo = null;
		ckno.setPrcd(curUser.getProd());
		String bamt="";
		String samt="";
		if(Double.valueOf(ckno.getByam()).doubleValue()>=0){
			ckno.setBynm(ckno.getCurrpair().substring(0, 3));
			bamt=String.valueOf(Math.abs(Double.valueOf(ckno.getByam())));
			ckno.setSlnm(ckno.getCurrpair().substring(3, 6));
			samt=String.valueOf(Math.abs(Double.valueOf(ckno.getSlam())));
		}else{
			ckno.setBynm(ckno.getCurrpair().substring(3, 6));
			bamt=String.valueOf(Math.abs(Double.valueOf(ckno.getSlam())));
			ckno.setSlnm(ckno.getCurrpair().substring(0, 3));
			samt=String.valueOf(Math.abs(Double.valueOf(ckno.getByam())));
		}
		String strxml = "<Transaction><Transaction_Header><TRCD>D5001</TRCD><PRCD>";
		if (curUser.getProd().equals("P007")) {
			strxml += "P074";
		}else {
			strxml += curUser.getProd();
		}
		strxml += "</PRCD></Transaction_Header><Body>"
				+ "<request><DJTY>0</DJTY><SQNO></SQNO><TRDT>"+ckno.getTrdt()+"</TRDT>";
		strxml += "<CKNO>" + ckno.getCkno() + "</CKNO><SLNM>" + ckno.getSlnm()
				+ "</SLNM><BYNM>" + ckno.getBynm() + "</BYNM><SLAM>"
				+ samt + "</SLAM> " + "<BYAM>" + bamt
				+ "</BYAM><CBHL>" + ckno.getCbhl() + "</CBHL><EXPC>"
				+ ckno.getExpc() + "</EXPC><CXFG>" + ckno.getCxfg()
				+ "</CXFG><JSDT>" + ckno.getJsdt() + "</JSDT>" + "<PPDS>"
				+ ckno.getDshou() + "</PPDS><DSNO></DSNO><NAME>"
				+ ckno.getName() + "</NAME>";
		strxml += "</request></Body></Transaction>\r\n";
		ReadSocketConfig readsockt = new ReadSocketConfig();
		readsockt.readWainingProperties("fxipIP","fxipport");
		String strip = null;
		int strport = 0;
		strip = readsockt.getSocketip();
		strport = readsockt.getSocketport();
		LOGGER.info("*******  fxipip:"+strip+"fxipport:"+strport);
		String temString = mysocket.sendPutPrice(strip, strport, strxml);
		String nowtime = DataTimeClass.getCurDateTime();
		if (temString.equals("false")) {
			LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "手工登记报文:" + strxml
					+ ",登记IP:" + strip + ",登记port:" + strport
					+ "登记失败(socket异常)!时间:" + nowtime);
			loginfo = new LogfileBean();
			loginfo.setRzdt(nowtime.substring(0,8));
			loginfo.setRzsj(nowtime.substring(9));
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("手工登记");
			loginfo.setRemk("手工登记");
			loginfo.setVold("登录产品:" + curUser.getProd() + ",登记IP:" + strip
					+ ",登记port:" + strport);
			loginfo.setVnew("登记失败(socket异常)");
			loginfo.setProd(curUser.getProd());
			bo = checkppDetailMapper.insertMarkLogger(loginfo);
			if (bo) {
				//登记失败
				LOGGER.info("写审计日志1成功!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), null);
			}else {
				LOGGER.error("写审计日志1失败!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
			}
		}
		try {
			temString = new String(temString.getBytes(stringCode), "UTF-8");
			LOGGER.info("*****手工登记后台返回值:" + temString);
		} catch (Exception e1) {
			LOGGER.error(e1.getMessage(),e1);
		}
		String temArray[] = temString.split("\\|");
		if (temArray.length < 3) {
			LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "手工登记报文:" + strxml
					+ ",登记IP:" + strip + ",登记port:" + strport + "登记失败!时间:"
					+ DataTimeClass.getCurDateTime());
			loginfo = new LogfileBean();
			loginfo.setRzdt(nowtime.substring(0,8));
			loginfo.setRzsj(nowtime.substring(9));
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("手工登记");
			loginfo.setRemk("手工登记");
			loginfo.setVold("登录产品:" + curUser.getProd() + ",登记IP:" + strip
					+ ",登记port:" + strport);
			loginfo.setVnew("登记失败");
			loginfo.setProd(curUser.getProd());
			bo = checkppDetailMapper.insertMarkLogger(loginfo);
			if (bo) {
				//登记失败
				LOGGER.info("写审计日志2成功!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), null);
			}else {
				LOGGER.error("写审计日志2失败!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
			}
			
		}
		if ("00000".equals(temArray[2])) {
			LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "手工登记报文:" + strxml
					+ ",登记IP:" + strip + ",登记port:" + strport + "登记成功!时间:"
					+ DataTimeClass.getCurDateTime());
			loginfo = new LogfileBean();
			loginfo.setRzdt(nowtime.substring(0,8));
			loginfo.setRzsj(nowtime.substring(9));
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("手工登记");
			loginfo.setRemk("手工登记");
			loginfo.setVold("登录产品:" + curUser.getProd() + ",登记IP:" + strip
					+ ",登记port:" + strport);
			loginfo.setVnew("登记成功");
			loginfo.setProd(curUser.getProd());
			bo = checkppDetailMapper.insertMarkLogger(loginfo);
			if (bo) {
				LOGGER.info("写审计日志3成功!");
			}else {
				LOGGER.error("写审计日志3失败!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
			}
		}
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_40.getCode(), JSON.toJSONString(temArray[3]));
	}
}
