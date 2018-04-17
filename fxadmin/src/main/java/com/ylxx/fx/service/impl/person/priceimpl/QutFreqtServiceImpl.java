package com.ylxx.fx.service.impl.person.priceimpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.price.QutFreqtMapper;
import com.ylxx.fx.service.person.price.IQutFreqtService;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.PdtinfoBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("qutFreqtService")
public class QutFreqtServiceImpl implements IQutFreqtService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QutFreqtServiceImpl.class);
	@Resource
	private QutFreqtMapper qutFreqtMapper;
	
	//查询报价频率参数*
	public String selectQutFreqt(String ptid) {
		PdtinfoBean pdtinfo = null;
		try {
			pdtinfo = qutFreqtMapper.selectQutFreqt(ptid);
			if (pdtinfo!=null) {
				pdtinfo.setUsfg("0".equals(pdtinfo.getUsfg())?"启用":"停用");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtinfo));
			}else {
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
	}
	//修改报价频率参数
	public String updateQutFreqt(String userKey,PdtinfoBean freqt){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean bo = false;
		String result = "";
		try {
			bo = qutFreqtMapper.upQutFreqt(freqt);
			//获取当前时间
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				LOGGER.info("更新数据执行成功!");
				//TODO UDPsocket.SendSocketPdtInfo();
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "修改报价频率参数:产品编号:"
						+ freqt.getPtid() + "产品名称:" + freqt.getPtnm()
						+ ",价格类型:" + freqt.getQtty() + ",报价频率:"
						+ freqt.getFrqy() + ",使用标志:" + freqt.getUsfg()
						+ "成功!时间:" + nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("报价频率参数");
				loginfo.setRemk("修改");
				loginfo.setVold("登录产品:" + curUser.getProd() + "修改报价频率参数:产品编号:"
						+ freqt.getPtid() + "产品名称:" + freqt.getPtnm()
						+ ",价格类型:" + freqt.getQtty() + ",报价频率:"
						+ freqt.getFrqy() + ",使用标志:" + freqt.getUsfg());
				loginfo.setVnew("成功");
				boolean boo = qutFreqtMapper.insertLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "修改报价频率参数:产品编号:"
						+ freqt.getPtid() + "产品名称:" + freqt.getPtnm()
						+ ",价格类型:" + freqt.getQtty() + ",报价频率:"
						+ freqt.getFrqy() + ",使用标志:" + freqt.getUsfg()
						+ "失败!时间:" + nowtime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
}
