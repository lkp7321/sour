package com.ylxx.fx.service.impl.person.ppmagagerimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.ppmagager.CheckppDetailMapper;
import com.ylxx.fx.core.mapper.person.ppmagager.CkPPExprtanMapper;
import com.ylxx.fx.service.person.ppmagager.ICkPPExprtanService;
import com.ylxx.fx.service.po.CkPPExprtan;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;



@Service("ckPPExprtanService")
public class CkPPExprtanServiceImpl implements ICkPPExprtanService {

	@Resource
	private CkPPExprtanMapper ckPPExprtanMapper;
	@Resource
	private CheckppDetailMapper checkppDetailMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(CkPPExprtanServiceImpl.class);
	
	//获得全部异常交易的数据
	public String queryExprtan(String trac){
		String result = "";
		try {
			List<CkPPExprtan> exprtans = ckPPExprtanMapper.selectExprtan(trac.trim());
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(exprtans));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//处理
	public String updateExprtan(String userKey,String ppno){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		try {
			boolean bo = ckPPExprtanMapper.updateExp(ppno);
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "异常平盘处理:处理请求流水号" + ppno
						+ "成功!时间:"+nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("异常平盘处理");
				loginfo.setRemk("处理");
				loginfo.setVold("登录产品:" + curUser.getProd() + "异常平盘处理:处理请求流水号" + ppno);
				loginfo.setVnew("成功");
				loginfo.setProd(curUser.getProd());
				boolean boo = checkppDetailMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "异常平盘处理:处理请求流水号" + ppno
						+ "失败!时间:"+nowtime);
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
}
