package com.ylxx.fx.service.impl.person.ppmagagerimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.ppmagager.CheckppDetailMapper;
import com.ylxx.fx.core.mapper.person.ppmagager.MoZhangMapper;
import com.ylxx.fx.service.person.ppmagager.IMoZhangService;
import com.ylxx.fx.service.po.CkHandMxDetail;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.SocketLocal;

@Service("moZhangService")
public class MoZhangServiceImpl implements IMoZhangService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MoZhangServiceImpl.class);
	@Resource
	private MoZhangMapper moZhangMapper;
	@Resource
	private CheckppDetailMapper checkppDetailMapper;
	
	//获取所有数据
	public String selectAllMxDetail(String prod){
		String result = "";
		try {
			List<CkHandMxDetail> details = moZhangMapper.selectAllMxDetail(prod);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(details));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//条件查询
	public String selectMxDetail(String prod,String dateApdt,String dataEndInput){
		String result = "";
		try {
			if (prod.equals("P007")) {
				prod = "P074";
			}
			List<CkHandMxDetail> details = moZhangMapper.selectMxDetail(prod,dateApdt, dataEndInput);
			for (int i = 0; i < details.size(); i++) {
				details.get(i).setDjty(details.get(i).getDjty().equals("0")?"手工登记":"手工抹账");
			}
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(details));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//抹账
	public String mozhang(String userKey,CkHandMxDetail ckno) throws Exception {
		boolean bo = false;
		
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P001");
		curUser.setCurIP("127.0.0.1");*/
		
		ReadStringCodeConfig readString = new ReadStringCodeConfig();
		readString.readStringCodeProperties("strcode1");
		String stringCode=readString.getStringCode();
		SocketLocal mysocket = new SocketLocal();
		LogfileBean loginfo = null;
		String strxml = "<Transaction><Transaction_Header><TRCD>D5001</TRCD><PRCD>";
		if (curUser.getProd().equals("P007")) {
			strxml += "P074";
		}else {
			strxml += curUser.getProd();
		}		
		strxml += "</PRCD></Transaction_Header><Body>" + "<request>";
		strxml += "<DJTY>1</DJTY><SQNO>" + ckno.getSgno() + "</SQNO><TRDT>"
				+ ckno.getApdt() + "</TRDT>";
		strxml += "	</request></Body></Transaction>\r\n";
		ReadSocketConfig readsockt = new ReadSocketConfig();
		readsockt.readWainingProperties("fxipIP", "fxipport");
		String strip = null;
		int strport = 0;
		strip = readsockt.getSocketip();
		strport = readsockt.getSocketport();
		LOGGER.info("fxipip:" + strip + " fxipport:" + strport);
		String temString = mysocket.sendPutPrice(strip, strport, strxml);
		String nowtime = DataTimeClass.getCurDateTime();
		if (temString.equals("false")) {
			LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "手工抹账报文:" + strxml
					+ ",抹账IP:" + strip + ",抹账port:" + strport
					+ "抹账失败(socket异常)!时间:" + nowtime);
			loginfo = new LogfileBean();
			loginfo.setRzdt(nowtime.substring(0,8));
			loginfo.setRzsj(nowtime.substring(9));
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("手工抹账");
			loginfo.setRemk("手工抹账");
			loginfo.setVold("登录产品:" + curUser.getProd() + ",抹账IP:" + strip
					+ ",抹账port:" + strport);
			loginfo.setVnew("抹账失败(socket异常)");
			loginfo.setProd(curUser.getProd());
			bo = checkppDetailMapper.insertMarkLogger(loginfo);
			if (bo) {
				//抹账失败
				LOGGER.info("写审计日志1成功!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_51.getCode(), null);
			}else {
				LOGGER.error("写审计日志1失败!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_51.getCode(), "抹账异常");
			}
		}
		try {
			temString = new String(temString.getBytes(stringCode), "UTF-8");
			LOGGER.info("*****手工登记抹账后台返回值:" + temString);
		} catch (Exception e1) {
			LOGGER.error(e1.getMessage(),e1);
		}
			
		String temArray[] = temString.split("\\|");
		if (temArray.length < 3) {
			LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "手工抹账报文:" + strxml
					+ ",抹账IP:" + strip + ",抹账port:" + strport + "抹账失败!时间:"
					+ nowtime);
			loginfo = new LogfileBean();
			loginfo.setRzdt(nowtime.substring(0,8));
			loginfo.setRzsj(nowtime.substring(9));
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("手工抹账");
			loginfo.setRemk("手工抹账");
			loginfo.setVold("登录产品:" + curUser.getProd() + ",抹账IP:" + strip
					+ ",抹账port:" + strport);
			loginfo.setVnew("抹账失败");
			loginfo.setProd(curUser.getProd());
			bo = checkppDetailMapper.insertMarkLogger(loginfo);
			if (bo) {
				//抹账失败
				LOGGER.info("写审计日志2成功!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_51.getCode(), null);
			}else {
				LOGGER.error("写审计日志2失败!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_51.getCode(), "抹账异常");
			}
		}
		if ("00000".equals(temArray[2])) {
			LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "手工抹账报文:" + strxml
					+ ",抹账IP:" + strip + ",抹账port:" + strport + "抹账成功!时间:"
					+ DataTimeClass.getCurDateTime());
			loginfo = new LogfileBean();
			loginfo.setRzdt(nowtime.substring(0,8));
			loginfo.setRzsj(nowtime.substring(9));
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("手工抹账");
			loginfo.setRemk("手工抹账");
			loginfo.setVold("登录产品:" + curUser.getProd() + ",抹账IP:" + strip
					+ ",抹账port:" + strport);
			loginfo.setVnew("抹账成功");
			loginfo.setProd(curUser.getProd());
			bo = checkppDetailMapper.insertMarkLogger(loginfo);
			if (bo) {
				LOGGER.info("写审计日志3成功!");
			}else {
				LOGGER.error("写审计日志3失败!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_51.getCode(), "抹账异常");
			}
		}
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_50.getCode(), JSON.toJSONString(temArray[3]));
	}
}
