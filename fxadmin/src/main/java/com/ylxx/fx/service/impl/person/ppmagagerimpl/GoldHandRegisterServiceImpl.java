package com.ylxx.fx.service.impl.person.ppmagagerimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.CmmCymsgBean;
import com.ylxx.fx.core.mapper.person.ppmagager.GoldHandRegisterMapper;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.service.person.ppmagager.IGoldHandRegisterService;
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

@Service("goldHandRegisterService")
public class GoldHandRegisterServiceImpl implements IGoldHandRegisterService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoldHandRegisterServiceImpl.class);
	String ip = "127.0.0.1";
	@Resource
	private GoldHandRegisterMapper goldHandRegisterMapper;
	@Resource
	private PdtRParaMapper pdtRParaMapper;

	//敞口名称改变时触发函数
	public String onchange(String prod,int ckdata) {
		String result = "";
		List<CmmCymsgBean> beans = new ArrayList<CmmCymsgBean>();
		try {
			if (ckdata==0) {
				beans = goldHandRegisterMapper.selJSHTabUs(prod);
			}else if (ckdata==1) {
				beans = goldHandRegisterMapper.selJWTabUs(prod);
			}else if (ckdata==2) {
				beans = goldHandRegisterMapper.selJJSTabUs(prod);
			}
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(beans));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//判断买入币种在标准币别对的左边还是右边
	public String selectSamt(String prod,String bexnm,String sexnm){
		String result = null;
		try {
			List<String> currencyPairList=selectCurrencyPair(prod);
			if (currencyPairList.contains(createLeftAndRight(bexnm,sexnm))) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "left");
			}else if (currencyPairList.contains(createRightAndLeft(bexnm,sexnm))) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "right");
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("判断买入币种在标准币别对的左边还是右边异常!");
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//判断卖出币种在标准币别对的左边还是右边
	public String selectBamt(String prod,String bexnm,String sexnm){
		String result = null;
		try {
			List<String> currencyPairList=selectCurrencyPair(prod);
			if (currencyPairList.contains(createLeftAndRight(bexnm,sexnm))) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "right");
			}else if (currencyPairList.contains(createRightAndLeft(bexnm,sexnm))) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "left");
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("判断买入币种在标准币别对的左边还是右边异常!");
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//筛选标准币别对列表
	public List<String> selectCurrencyPair(String prod) throws Exception{
		List<String> currencyPairList = goldHandRegisterMapper.selCurrencyPair(prod);
		currencyPairList.add("AGBRMB");
		currencyPairList.add("AUBRMB");
		return currencyPairList;
	}
	private String createLeftAndRight(String currency1,String currency2) {
		return currency1 + currency2;
	}
	private String createRightAndLeft(String currency1,String currency2) {
		return currency2 + currency1;
	}
	//查询货币对
	public String selExnm(String prod,String exnm1, String exnm2){
		String result = "";
		try {
			String exnm = goldHandRegisterMapper.selExnm(exnm1, exnm2, prod);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(exnm));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//手工登记---纸黄金
	public String registerCK(String userKey, Register ckno)
			throws Exception {
		ReadStringCodeConfig readString = new ReadStringCodeConfig();
		readString.readStringCodeProperties("strcode1");
		String stringCode=readString.getStringCode();
		
		SocketLocal mysocket = new SocketLocal();
		LogfileBean loginfo = null;
		
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P001");
		curUser.setCurIP("127.0.0.1");*/
		
		ckno.setPrcd(curUser.getProd());
		String strxml = "<Transaction><Transaction_Header><TRCD>D5001</TRCD><PRCD>"
				+ curUser.getProd()
				+ "</PRCD></Transaction_Header><Body>"
				+ "<request><DJTY>0</DJTY><SQNO></SQNO><TRDT></TRDT>";
		strxml += "<CKNO>" + ckno.getCkno() + "</CKNO><SLNM>" + ckno.getSlnm()
				+ "</SLNM><BYNM>" + ckno.getBynm() + "</BYNM><SLAM>"
				+ ckno.getSlam() + "</SLAM>" + "<BYAM>" + ckno.getByam()
				//+ "</BYAM><CBHL>" + ckno.getCBHL() + "</CBHL><EXPC>"
				+ "</BYAM><CBHL>" + ckno.getExpc() + "</CBHL><EXPC>"
				+ ckno.getExpc() + "</EXPC><CXFG>" + ckno.getCxfg()
				+ "</CXFG><JSDT>" + ckno.getJsdt() + "</JSDT>" + "<PPDS>"
				+ ckno.getDshou() + "</PPDS><DSNO></DSNO><NAME>"
				+ ckno.getName() + "</NAME>";
		strxml += "</request></Body></Transaction>\r\n";
		
		ReadSocketConfig readsockt = new ReadSocketConfig();
		// 参数 1：用户IP
		// 参数 2：用户端口
		readsockt.readWainingProperties("goldIP", "goldport");
		String strip = null;
		int strport = 0;
		strip = readsockt.getSocketip();
		strport = readsockt.getSocketport();
		LOGGER.info("goldip:" + strip + " goldport:" + strport);
		String temString = mysocket.sendPutPrice(strip, strport, strxml);
		String nowtime = DataTimeClass.getCurDateTime();
		if (temString.equals("false")) {
			LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "纸黄金手工登记报文:" + strxml
					+ ",登记IP:" + strip + ",登记port:" + strport
					+ ",登记失败(socket异常)!时间:" + nowtime);
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
			try {
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
			}
		}
		try {
			temString = new String(temString.getBytes(stringCode), "UTF-8");
			LOGGER.info("*****纸黄金手工登记后台返回值:" + temString);
		} catch (Exception e1) {
			 LOGGER.error(e1.getMessage(),e1);
		}
		String temArray[] = temString.split("\\|");
		if (temArray.length < 3) {
			LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "纸黄金手工登记报文:" + strxml
					+ ",登记IP:" + strip + ",登记port:" + strport + ",登记失败!时间:"
					+ nowtime);
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
			try {
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
			}
		}
		if ("00000".equals(temArray[2])) {
			LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "纸黄金手工登记报文:" + strxml
					+ ",登记IP:" + strip + ",登记port:" + strport + ",登记成功!时间:"
					+ nowtime);
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
			try {
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
			}
		}
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_40.getCode(), JSON.toJSONString(temArray[3]));
	}
	//执行登记---积存金
	public String spgoldregisterCK(String userKey, Register ckno)
			throws Exception {
		ReadStringCodeConfig readString = new ReadStringCodeConfig();
		readString.readStringCodeProperties("strcode1");
		String stringCode=readString.getStringCode();
		
		SocketLocal mysocket = new SocketLocal();
		LogfileBean loginfo = null;
		
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P001");
		curUser.setCurIP("127.0.0.1");*/
		
		ckno.setPrcd(curUser.getProd());
		String strxml = "<Transaction><Transaction_Header><TRCD>D5001</TRCD><PRCD>"
				+ curUser.getProd()
				+ "</PRCD></Transaction_Header><Body>"
				+ "<request><DJTY>0</DJTY><SQNO></SQNO><TRDT></TRDT>";
		strxml += "<CKNO>" + ckno.getCkno() + "</CKNO><SLNM>" + ckno.getSlnm()
				+ "</SLNM><BYNM>" + ckno.getBynm() + "</BYNM><SLAM>"
				+ ckno.getSlam() + "</SLAM>" + "<BYAM>" + ckno.getByam()
				//+ "</BYAM><CBHL>" + ckno.getCBHL() + "</CBHL><EXPC>"
				+ "</BYAM><CBHL>" + ckno.getExpc() + "</CBHL><EXPC>"
				+ ckno.getExpc() + "</EXPC><CXFG>" + ckno.getCxfg()
				+ "</CXFG><JSDT>" + ckno.getJsdt() + "</JSDT>" + "<PPDS>"
				+ ckno.getDshou() + "</PPDS><DSNO></DSNO><NAME>"
				+ ckno.getName() + "</NAME>";
		strxml += "</request></Body></Transaction>\r\n";
		LOGGER.info("-----XML:"+strxml);
		ReadSocketConfig readsockt = new ReadSocketConfig();
		// 参数 1：用户IP
		// 参数 2：用户端口
		readsockt.readWainingProperties("spgoldIP", "spgoldport");
		String strip = null;
		int strport = 0;
		strip = readsockt.getSocketip();
		strport = readsockt.getSocketport();
		LOGGER.info("spgoldip:" + strip + " spgoldport:" + strport);
		String temString = mysocket.sendPutPrice(strip, strport, strxml);
		String nowtime = DataTimeClass.getCurDateTime();
		if (temString.equals("false")) {
			LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "积存金手工登记报文:" + strxml
					+ ",登记IP:" + strip + ",登记port:" + strport
					+ ",登记失败(socket异常)!时间:" + nowtime);
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
			try {
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
			}
		}
		try {
			temString = new String(temString.getBytes(stringCode), "UTF-8");
			LOGGER.info("*****积存金手工登记后台返回值:" + temString);
		} catch (Exception e1) {
			 LOGGER.error(e1.getMessage(),e1);
		}
		String temArray[] = temString.split("\\|");
		if (temArray.length < 3) {
			LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "积存金手工登记报文:" + strxml
					+ ",登记IP:" + strip + ",登记port:" + strport + ",登记失败!时间:"
					+ nowtime);
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
			try {
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
			}
		}
		if ("00000".equals(temArray[2])) {
			LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "积存金手工登记报文:" + strxml
					+ ",登记IP:" + strip + ",登记port:" + strport + ",登记成功!时间:"
					+ nowtime);
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
			try {
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_41.getCode(), "登记异常");
			}
		}
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_40.getCode(), JSON.toJSONString(temArray[3]));
	}

}
