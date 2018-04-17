package com.ylxx.fx.service.impl.bail.eodprocessimpl;

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
import com.ylxx.fx.core.mapper.bail.eodprocess.BailTouCunBingZhangMapper;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.service.bail.eodprocess.IBailTouCunBingZhangService;
import com.ylxx.fx.service.po.BailTouCunBean;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.SocketLocal;


@Service("bailTouCunBingZhangService")
public class BailTouCunBingZhangServiceImpl implements IBailTouCunBingZhangService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BailTouCunBingZhangServiceImpl.class);
	@Resource
	private BailTouCunBingZhangMapper bailTouCunBingZhangMapper;
	@Resource
	private BailBalanceInputMapper bailBalanceInputMapper;
	@Resource
	private PdtRParaMapper pdtRParaMapper;

	//查询保证金头寸并账
	public String queryBailTouCunBZList(String hddate){
		String result = "";
		try {
			List<BailTouCunBean> beans = new ArrayList<BailTouCunBean>();
			List<BailTouCunBean> outAccHDList = bailTouCunBingZhangMapper.selectTouCunBingZhangList(hddate);
			for (int i = 0; i < outAccHDList.size(); i++) {
				BailTouCunBean bailTouCunBean = outAccHDList.get(i);
				bailTouCunBean.setFlag("0");
				beans.add(bailTouCunBean);
				if (bailTouCunBean.getBzdt().equals(hddate)&&bailTouCunBean.getStcd().equals("录入")) {
					BailTouCunBean bean = new BailTouCunBean();
					bean.setFlag("1");
					beans.add(bean);
				}
			}
			LOGGER.info(hddate+"条数为:"+beans.size());
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(beans));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//检查并账日期前是否有未并账的记录
	public String checkInput(String userKey,BailTouCunBean bailTouCunBean) {
		String result = "";
		String tradeid="'M5712'";
		try {
			int num = bailTouCunBingZhangMapper.checkInput(bailTouCunBean.getBzdt(), tradeid);
			boolean bo = true;
			if (num>0) {
				bo = false;
			}
			if (!bo) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_60.getCode(), "复核日期前有未并账的记录");
			}else {
				//保证金余额并账/保证金利息并账发送报文
				result = balanceBingZhang(userKey,bailTouCunBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//保证金头寸并账发送报文
	public String balanceBingZhang(String userKey,BailTouCunBean bailTouCunBean){
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
		String trid=generatePK();
		String inputString ="<Transaction><Transaction_Header>" +
				"<TRSN>"+trid+"</TRSN>" +
				"<TRID>M5713</TRID>" +
				"<BHID>X520</BHID>" +
				"<CHNL>1400</CHNL>" +
				"<RQDT>"+DataTimeClass.getNowDay()+"</RQDT>" +
				"<RQTM>"+DataTimeClass.getCurTime()+"</RQTM>" +
				"<TRTL>"+curUser.getUsnm()+"</TRTL>" +
				//"<TRTL>1001000002</TRTL>" +
				"<TTYN>00005</TTYN>" +
				"<AUTL>00000008</AUTL>" +
				"</Transaction_Header>" +
				"<Transaction_Body>" +
				"<request>" +
				"<BZDT>"+bailTouCunBean.getBzdt()+"</BZDT>" +
				"</request>" +
				"</Transaction_Body>" +
				"</Transaction>\n";
		ReadSocketConfig readsockt = new ReadSocketConfig();
		// 参数 1：用户IP   参数 2：用户端口
		readsockt.readWainingProperties("bailIp", "bailPort");
		String strip = null;
		int strport = 0;
		strip = readsockt.getSocketip();
		strport = readsockt.getSocketport();
		
		LOGGER.info("bailIp:" + strip + " bailPort:" + strport);
		String temString = mysocket.sendPutPrice(strip, strport, inputString);
		//String temString = mysocket.sendPutPrice("197.1.5.92", 8087, inputString);
		String nowtime = DataTimeClass.getCurDateTime();
		if(temString==null || temString.equals("")){
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "后台返回数据为空！");
		}else if ("false".equals(temString)) {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "保证金头寸并账报文:" + inputString
						+ ",保证金头寸并账IP:" + strip + ",保证金头寸并账port:" + strport
						+ ",保证金头寸并账失败(socket异常)!时间:" + DataTimeClass.getCurDateTime());
				loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("保证金头寸并账");
				loginfo.setRemk("保证金头寸并账");
				loginfo.setVold("登录产品:" + curUser.getProd() + ",保证金头寸并账IP:" + strip
						+ ",保证金头寸并账port:" + strport);
				loginfo.setVnew("保证金头寸并账失败(socket异常)");
				loginfo.setProd(curUser.getProd());
				try {
					boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
					}else {
						LOGGER.error("写审计日志失败!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "保证金头寸并账失败！");
		}
		try {
			temString = new String(temString.getBytes(stringCode), "UTF-8");
			LOGGER.info("*****后台返回值:" + temString);
		} catch (Exception e1) {
			LOGGER.error(e1.getMessage(),e1);
		}
		//解析报文
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
