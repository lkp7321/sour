package com.ylxx.fx.service.impl.person.ppmagagerimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.ppmagager.CheckppDetailMapper;
import com.ylxx.fx.core.mapper.person.ppmagager.PpAccountMapper;
import com.ylxx.fx.service.person.ppmagager.IPpAccountService;
import com.ylxx.fx.service.po.Ck_ppresultdetail;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.SocketLocal;

@Service("ppAccountService")
public class PpAccountServiceImpl implements IPpAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PpAccountServiceImpl.class);
	@Resource
	private PpAccountMapper ppAccountMapper;
	@Resource
	private CheckppDetailMapper checkppDetailMapper;
	
	//查询所有不明交易
	public String selPPAccount(String lcno){
		String result = "";
		try {
			List<Ck_ppresultdetail> details = ppAccountMapper.selectallaccount(lcno.trim());
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(details));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//查询UBS联系方式
	public String queryPtpara(String paid){
		String result = "";
		try {
			String valu = ppAccountMapper.queryPtpara(paid);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(valu));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//成功处理
	public String onSucessManage(String userKey,Ck_ppresultdetail ppresult)throws Exception{
		boolean bo = false;
		ReadStringCodeConfig readString = new ReadStringCodeConfig();
		readString.readStringCodeProperties("strcode1");
		String stringCode=readString.getStringCode();
		SocketLocal mysocket = new SocketLocal();
		
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P001");
		curUser.setCurIP("127.0.0.1");*/
		
		String currency1 = ppresult.getExnm().substring(0, 3); // 左币种
		String currency2 = ppresult.getExnm().substring(3, 6); // 右币种
		String amountCurrency = ""; // 平盘币种
		String amount = ""; // 交易金额
		String counterAmtccy = "";// 成交金额(美元)
		String faceAmt = "";// 交易金额(非美元)
		if (currency1.equals("USD")) {
			// 如果美元在前，交易币种为右币种，成交后币种为左币种
			amountCurrency = currency2;
			counterAmtccy = currency1;
		} else {
			// 如果美元在后，交易币种为左币种，成交后币种为右币种
			amountCurrency = currency1;
			counterAmtccy = currency2;
		}
		String counterAmt = "";// 美元币种金额
		// 如果交易币种是卖出币种交易金额取卖出金额，反之取买入金额
		if ("USD".equals(ppresult.getSlcy())) {
			amount = ppresult.getBamt() + "";
			counterAmt = ppresult.getSamt() + "";
			faceAmt = ppresult.getBamt() + "";
		} else {
			amount = ppresult.getSamt() + "";
			counterAmt = ppresult.getBamt() + "";
			faceAmt = ppresult.getSamt() + "";
		}

		String side = "";// 币别对方向
		if (ppresult.getBsfx().equals("买入")) {
			side = "BUY";
		} else {
			side = "SELL";
		}
		String strxmlb = "<?xml version=\"" + "1.0" + "\" encoding=\""
				+ "gb2312" + "\"?>" + "<Transaction>" + "<Transaction_Header>"
				+ "<PPNO>"
				+ ppresult.getPpno()
				+ "</PPNO>"
				+ "<TRCD>PP004</TRCD><PRCD>P001</PRCD><BIMP>0006</BIMP>"
				+ "<CODE>0</CODE><MESG>succesful</MESG></Transaction_Header>"
				+ "<Transaction_Body><response>"
				+ "<currency1>"
				+ currency1
				+ "</currency1>"
				+ "<currency2>"
				+ currency2
				+ "</currency2>"
				+ "<amountCurrency>"
				+ amountCurrency
				+ "</amountCurrency>"
				+ "<amount>"
				+ amount
				+ "</amount>"
				+ "<side>"
				+ side
				+ "</side>"
				+ "<clientreference>"
				+ ppresult.getSeqn()
				+ "</clientreference>"
				+ "<executionstrategy>2003</executionstrategy>"
				+ "<dealReference>"
				+ ppresult.getDsno()
				+ "</dealReference>"
				+ "<faceAmtccy>"
				+ amountCurrency
				+ "</faceAmtccy>"
				+ "<faceAmt>"
				+ faceAmt
				+ "</faceAmt>"
				+ "<counterAmtccy>"
				+ counterAmtccy
				+ "</counterAmtccy>"
				+ "<counterAmt>"
				+ counterAmt
				+ "</counterAmt>"
				+ "<spotPrice>"
				+ ppresult.getExpc()
				+ "</spotPrice>"
				+ "<valueDate>"
				+ ppresult.getJgdt()
				+ "</valueDate>"
				+ "<spotdate>"
				+ ppresult.getQxdt()
				+ "</spotdate>"
				+ "<bankreference></bankreference>"
				+ "</response></Transaction_Body></Transaction>\r\n";
		ReadSocketConfig readsockt = new ReadSocketConfig();
		// 参数 1：用户IP
		// 参数 2：用户端口
		readsockt.readWainingProperties("fxipIP", "fxipport");

		String strip = null;
		int strport = 0;
		strip = readsockt.getSocketip();
		strport = readsockt.getSocketport();

		String temString = mysocket.sendPutPrice(strip, strport, strxmlb);
		String nowtime = DataTimeClass.getCurDateTime();
		if (temString.equals("false")) {
			LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "不明交易成功处理:发送报文:" + strxmlb
					+ ",处理失败(socket异常)!时间:" + nowtime);
			LogfileBean loginfo = new LogfileBean();
			loginfo.setRzdt(nowtime.substring(0,8));
			loginfo.setRzsj(nowtime.substring(9));
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("平盘管理");
			loginfo.setRemk("不明交易成功处理");
			loginfo.setVold("登录产品:" + curUser.getProd() + "不明交易成功处理");
			loginfo.setVnew("处理失败(socket异常)");
			loginfo.setProd(curUser.getProd());
			bo = checkppDetailMapper.insertMarkLogger(loginfo);
			if (bo) {
				//不明交易成功处理失败
				LOGGER.info("写审计日志1成功!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "不明交易成功处理失败！");
			}else {
				LOGGER.error("写审计日志1失败!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "不明交易成功处理异常！");
			}
		}
		String temArray[] = temString.split("\\|");
		String str = null;

		try {
			LOGGER.info("*****不明交易成功处理后台返回值="
					+ new String(temString.getBytes(stringCode), "UTF-8"));
			if (temArray[2].equals("00000")) {
				str = ResultDomain.getRtnMsg(ErrorCodePrice.E_60.getCode(), "平盘交易处理成功");
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "不明交易成功处理:发送报文:" + strxmlb + ",处理成功!时间:"
						+ nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("平盘管理");
				loginfo.setRemk("不明交易成功处理");
				loginfo.setVold("登录产品:" + curUser.getProd());
				loginfo.setVnew("处理成功");
				loginfo.setProd(curUser.getProd());
				bo = checkppDetailMapper.insertMarkLogger(loginfo);
				if (bo) {
					LOGGER.info("写审计日志2成功!");
				}else {
					LOGGER.error("写审计日志2失败!");
				}

				if (ppresult.getTrfl().equals("直通式平盘")) {
					boolean boo = ppAccountMapper.update(ppresult.getPpno(), curUser.getProd());
					if (!boo) {
						str = ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "更改异常处理标志失败");
						LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP() 
								+ " 登录产品:" + curUser.getProd() + "直通式平盘,更改异常处理标志失败!时间:"
								+ nowtime);
					}
				}
			} else {
				str = ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "[" + temArray[2] + "] 平盘交易处理失败!");
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP() 
						+ " 登录产品:" + curUser.getProd()
						+ "不明交易成功处理:发送报文:" + strxmlb + ",处理失败!时间:"
						+ nowtime);
				LogfileBean  loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("平盘管理");
				loginfo.setRemk("不明交易成功处理");
				loginfo.setVold("登录产品:" + curUser.getProd());
				loginfo.setVnew("处理失败");
				loginfo.setProd(curUser.getProd());
				bo = checkppDetailMapper.insertMarkLogger(loginfo);
				if (bo) {
					LOGGER.info("写审计日志3成功!");
				}else {
					LOGGER.error("写审计日志3失败!");
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
			str = ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(),"处理异常,请查证！");
			return str;
		}
		return str;
	}
	//失败处理
	public String onFaultManage(String userKey,String ppno, 
			String seqn,String trfl) throws Exception{
		boolean bo = false;
		ReadStringCodeConfig readString = new ReadStringCodeConfig();
		readString.readStringCodeProperties("strcode1");
		String stringCode=readString.getStringCode();
		SocketLocal mysocket = new SocketLocal();
		
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P001");
		curUser.setCurIP("127.0.0.1");*/
		
		String strxmlb = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
				+ "<Transaction><Transaction_Header><TRCD>PP004</TRCD><PRCD>P001</PRCD>"
				+ "<PPNO>"
				+ seqn
				+ "</PPNO><BIMP>0007</BIMP><CODE>6008</CODE><MESG>Deal failed</MESG>"
				+ "</Transaction_Header></Transaction>\r\n";
		ReadSocketConfig readsockt = new ReadSocketConfig();
		readsockt.readWainingProperties("fxipIP", "fxipport");
		String strip = null;
		int strport = 0;
		strip = readsockt.getSocketip();
		strport = readsockt.getSocketport();
		String temString = mysocket.sendPutPrice(strip, strport, strxmlb);
		String nowtime = com.ylxx.fx.utils.DataTimeClass.getCurDateTime();
		if (temString.equals("false")) {
			LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "不明交易失败处理:发送报文:" + strxmlb
					+ ",处理失败(socket异常)!时间:" + nowtime);
			LogfileBean loginfo = new LogfileBean();
			loginfo.setRzdt(nowtime.substring(0,8));
			loginfo.setRzsj(nowtime.substring(9));
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("平盘管理");
			loginfo.setRemk("不明交易失败处理");
			loginfo.setVold("登录产品:" + curUser.getProd() + "不明交易失败处理");
			loginfo.setVnew("处理失败(socket异常)");
			loginfo.setProd(curUser.getProd());
			bo = checkppDetailMapper.insertMarkLogger(loginfo);
			if (bo) {
				//处理失败
				LOGGER.info("写审计日志1成功!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "处理失败！");
			}else {
				LOGGER.error("写审计日志1失败!");
			}
		}
		String temArray[] = temString.split("\\|");
		String str = null;
		try {
			LOGGER.info("*****不明交易失败处理后台返回值="
					+ new String(temString.getBytes(stringCode), "UTF-8"));
			if (temArray[2].equals("00000")) {
				str = ResultDomain.getRtnMsg(ErrorCodePrice.E_60.getCode(), "平盘交易处理成功");
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "不明交易失败处理:发送报文:" + strxmlb + ",处理成功!时间:"
						+ nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("平盘管理");
				loginfo.setRemk("不明交易处理");
				loginfo.setVold("登录产品:" + curUser.getProd() + "不明交易失败处理");
				loginfo.setVnew("处理成功");
				loginfo.setProd(curUser.getProd());
				bo = checkppDetailMapper.insertMarkLogger(loginfo);
				if (bo) {
					//处理成功
					LOGGER.info("写审计日志2成功!");
				}else {
					LOGGER.error("写审计日志2失败!");
				}
				if (trfl.equals("直通式平盘")) {
					boolean boo = ppAccountMapper.update(ppno, curUser.getProd());
					if (!boo) {
						str = ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "更改异常处理标志失败");
						LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP() 
								+ " 登录产品:" + curUser.getProd() + "直通式平盘,更改异常处理标志失败!时间:" 
								+ nowtime);
					}
				}
			} else {
				str = ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "[" + temArray[2] + "] 平盘交易处理失败!");
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "不明交易失败处理:发送报文:" + strxmlb + ",平盘交易处理失败!时间:"
						+ nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("平盘管理");
				loginfo.setRemk("不明交易处理");
				loginfo.setVold("登录产品:" + curUser.getProd() + "不明交易失败处理");
				loginfo.setVnew("处理失败");
				loginfo.setProd(curUser.getProd());
				bo = checkppDetailMapper.insertMarkLogger(loginfo);
				if (bo) {
					LOGGER.info("写审计日志成功!");
				}else {
					LOGGER.error("写审计日志失败!");
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
			str = ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "处理异常,请查证！");
			return str;
		}
		return str;
	}
}
