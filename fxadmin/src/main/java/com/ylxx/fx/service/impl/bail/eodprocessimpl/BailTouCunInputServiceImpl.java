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
import com.ylxx.fx.core.mapper.bail.eodprocess.BailTouCunInputMapper;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.service.bail.eodprocess.IBailTouCunInputService;
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


@Service("bailTouCunInputService")
public class BailTouCunInputServiceImpl implements IBailTouCunInputService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BailTouCunInputServiceImpl.class);
	@Resource
	private BailTouCunInputMapper bailTouCunInputMapper;
	@Resource
	private BailBalanceInputMapper bailBalanceInputMapper;
	@Resource
	private PdtRParaMapper pdtRParaMapper;

	//查询保证金头寸录入
	public String queryBailTouCunList(String hddate){
		String result = "";
		try {
			List<BailTouCunBean> beans = new ArrayList<BailTouCunBean>();
			List<BailTouCunBean> outAccHDList = bailTouCunInputMapper.selectTouCunList(hddate);
			for (int i = 0; i < outAccHDList.size(); i++) {
				BailTouCunBean bean = new BailTouCunBean();
				BailTouCunBean bailTouCunBean = outAccHDList.get(i);
				bailTouCunBean.setFlag("0");
				beans.add(bailTouCunBean);
				if (bailTouCunBean.getBzdt().equals(hddate)) {
					bean.setExnm(bailTouCunBean.getExnm());
					bean.setLeft(bailTouCunBean.getLeft());
					bean.setRigh(bailTouCunBean.getRigh());
					bean.setLbzam(bailTouCunBean.getLbzam());
					bean.setRbzam(bailTouCunBean.getRbzam());
					bean.setFlag("1");
					beans.add(bean);
				}
			}
			LOGGER.info(hddate+"日期下条数为:"+beans.size());
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(beans));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//保证金头寸录入发送报文
	public String toucunluru(String userKey,String bzDate, 
			List<BailTouCunBean> bailTouCunList){
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
		String headerString = "<Transaction>" +
				                "<Transaction_Header>" +
									"<TRSN>"+trid+"</TRSN>" +
									"<TRID>M5712</TRID>" +
									"<BHID>X520</BHID>" +
									"<CHNL>1400</CHNL>" +
									"<RQDT>"+DataTimeClass.getNowDay()+"</RQDT>" +
									"<RQTM>"+DataTimeClass.getCurTime()+"</RQTM>" +
									"<TRTL>"+curUser.getUsnm()+"</TRTL>" +
									//"<TRTL>1001000002</TRTL>" +
									"<TTYN>00005</TTYN>" +
									"<AUTL>00000008</AUTL>" +
								"</Transaction_Header>";
		StringBuffer bodyString = new StringBuffer("<Transaction_Body><request>");
		bodyString.append("<COUNT>"+bailTouCunList.size()+"</COUNT>").append("<BZDT>"+bzDate+"</BZDT><CYTP>0</CYTP>");
		for(int i=0;i<bailTouCunList.size();i++){
			bodyString.append("<record><EXCD></EXCD><EXNM>").append(bailTouCunList.get(i).getExnm())
			          .append("</EXNM><LEFT>")
			          .append(bailTouCunList.get(i).getLeft())
			          .append("</LEFT><RIGH>").append(bailTouCunList.get(i).getRigh())
			          .append("</RIGH></record>");
		}
		bodyString.append("</request></Transaction_Body></Transaction>");
		String inputString =headerString+bodyString.toString()+"\n";
		
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
		LOGGER.info("inputString=="+inputString+"||||||temString==="+temString);
		String nowtime = DataTimeClass.getCurDateTime();
		if(temString==null || temString.equals("")){
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "后台返回数据为空！");
		}else if ("false".equals(temString)) {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "保证金头寸并账录入报文:" + inputString
						+ ",保证金头寸并账录入IP:" + strip + ",保证金头寸并账录入port:" + strport
						+ ",保证金头寸并账录入失败(socket异常)!时间:" + DataTimeClass.getCurDateTime());
				loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("保证金头寸并账录入");
				loginfo.setRemk("保证金头寸并账录入");
				loginfo.setVold("登录产品:" + curUser.getProd() + ",保证金头寸并账录入IP:" + strip
						+ ",保证金头寸并账录入port:" + strport);
				loginfo.setVnew("保证金头寸并账录入失败(socket异常)");
				loginfo.setProd(curUser.getProd());
				boolean boo;
				try {
					boo = pdtRParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
					}else {
						LOGGER.error("写审计日志失败!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "保证金头寸并账录入失败！");
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
