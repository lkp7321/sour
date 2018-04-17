package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.accumu.businesspara.AccInfoMapper;
import com.ylxx.fx.service.accumu.businesspara.IAccInfoService;
import com.ylxx.fx.service.po.AccInfoManageBean;
import com.ylxx.fx.service.po.TrdOgcdBean;
import com.ylxx.fx.service.po.TrdOrganBean;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
/**
 * 活动专户管理
 * @author lz130
 *
 */
@Service("accInfoService")
public class AccInfoServiceImpl implements IAccInfoService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccInfoServiceImpl.class);

	@Resource
	private AccInfoMapper accInfoMapper;

	
	private String readMessage = null;
	private StringBuffer message = new StringBuffer();

//	public StringBuffer value = new StringBuffer(
//			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<combox>");
//	public String value0 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
//			+ "<combox><item label=\"所有\" data=\"all\"/>";
//	public String val1 = "<item label=\"";
//	public String val2 = "\" data=\"";
//	public String val3 = "\" ogup=\"";
//	public String val8 = "\" leve=\"";
//	public String val4 = "\"/>";
//	public String valueend = "</combox>";

	/**
	 * 查询活动专户管理
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<AccInfoManageBean> searchAccInfoList() throws Exception {
		return accInfoMapper.searchAccInfoList(null);
	}

	/**
	 * 筛选查询当前用户机构下的对公账户数据
	 * 
	 * @param orgn
	 * @return
	 * @throws Exception
	 */
	public List<AccInfoManageBean> searchAccInfoListByOrgn(String orgn)
			throws Exception {
		return accInfoMapper.searchAccInfoList(orgn);
	}

	/**
	 * 插入活动专户
	 * 
	 * @param og
	 * @param ptid
	 * @param ptname
	 * @return
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws UnsupportedEncodingException
	 */
	public boolean doInsertAccInfo(String og, String ptname)
			throws MalformedURLException, UnsupportedEncodingException {
		ReadStringCodeConfig readString = new ReadStringCodeConfig();	
		Date currentDate = new Date();
		SimpleDateFormat rq = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat time = new SimpleDateFormat("HHmmss");
		String curDate = rq.format(currentDate);
		String curTime = time.format(currentDate);

		readString.readStringCodeProperties("strcode1");// utf-8
		String stringCode = readString.getStringCode();
		ReadSocketConfig readsockt = new ReadSocketConfig();
		readsockt.readWainingProperties("dgglIp", "dgglPort");

		String input = "<Transaction><Transaction_Header><TRSN>56801"
				+ curDate
				+ curTime
				+ "0000568"
				+ curTime
				+ "</TRSN><TRID>A1111</TRID><TRTL></TRTL><BHID>9001</BHID><CHNL>1101</CHNL>"
				+ "<RQDT>"
				+ curDate
				+ "</RQDT><RQTM>"
				+ curTime
				+ "</RQTM><TTYN></TTYN><AUTL></AUTL></Transaction_Header><Transaction_Body><request><OGCD>"
				+ og + "</OGCD>" + "<EXDA></EXDA><CUNM>" + ptname
				+ "</CUNM></request></Transaction_Body></Transaction>";

		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		LOGGER.info("ip："+strip+" 端口："+strport);
		String result = this.sendMessage(strip, strport, input);
		LOGGER.info("message："+input);
		LOGGER.info(result
				+ "-------------------------------------------------------");
		// modify by zhaohan at 2015/04/22
		// if(result.equals("W")){
		if (result.equals("S")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param ip
	 * @param port
	 * @param input
	 * @return
	 */
	private String sendMessage(String ip, Integer port, String input) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		int i = 0;
		try {
			socket = new Socket(ip, port);

			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(), "utf-8")), true);
			out.println(input);
			while ((readMessage = in.readLine()) != null) {
				message.append(readMessage);
			}
			LOGGER.info("Socket返回的数据为：" + message);
			System.out.println("Socket返回的数据为：" + message);

			i = message.indexOf("<type>");

		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return String.valueOf(message.charAt(i + 6));
	}

	/**
	 * 为第一个机构下拉框赋值
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<TrdOrganBean> queryOneOrgan1() throws Exception {
//		List<TrdOrganBean> organList = accInfoMapper.queryOneOrgan1();
//		if (organList != null && organList.size() > 0) {
//			for (TrdOrganBean trdOrganBeanTemp : organList) {
//				value = value.append(val1).append(trdOrganBeanTemp.getOgnm())
//						.append(val2).append(trdOrganBeanTemp.getOgcd())
//						.append(val3).append(trdOrganBeanTemp.getOgup())
//						.append(val8).append(trdOrganBeanTemp.getLeve())
//						.append(val4);
//			}
//		}
//		value = value.append(valueend);
//		return value.toString();
		return accInfoMapper.queryOneOrgan1();
	}
	
	/**
	 * 查询当前用户机构名称
	 * 
	 * @param userorg
	 * @return
	 * @throws Exception
	 */
	public List<TrdOgcdBean> queryUserOrganForAcc(String userorg) throws Exception {
//		List<TrdOgcdBean> organList = accInfoMapper.queryUserOrganForAcc(userorg);
//		if (organList != null && organList.size() > 0) {
//			for (TrdOgcdBean trdOrganBeanTemp : organList) {
//				value = value.append(val1).append(trdOrganBeanTemp.getOgnm())
//						.append(val2).append(trdOrganBeanTemp.getOgcd())
//						.append(val8).append(trdOrganBeanTemp.getLeve())
//						.append(val4);
//			}
//		}
//		value = value.append(valueend);
//		return value.toString();
//		String result = accInfoMapper.queryUserOrganForAcc(userorg, value, val1, val2, val8, val4, valueend);
//		return result ;
		return accInfoMapper.queryUserOrganForAcc(userorg);
	}
}
