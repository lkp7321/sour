package com.ylxx.fx.service.impl.person.ppmagagerimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.ppmagager.SelppDetailMapper;
import com.ylxx.fx.service.person.ppmagager.ISelppDetailService;
import com.ylxx.fx.service.po.Ck_PpDetail;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.TestFormatter;



@Service("selppDetailService")
public class SelppDetailServiceImpl implements ISelppDetailService {

	@Resource
	private SelppDetailMapper selppDetailMapper;

	//查询货币对列表
	public String selUSDEXNM(String userKey) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		try {
			List<String> usdexnm = selppDetailMapper.selUSDExnm(curUser.getProd());
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(usdexnm));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//(条件)查询页面数据列表
	public String selAllList(String prod,String sartDate, String endDate,
			String strExnm, String strStat){
		String result = "";
		try {
			List<Ck_PpDetail> details = selppDetailMapper.selCondition(prod, sartDate.trim(), 
					endDate.trim(), strExnm.trim(), strStat.trim());
			TestFormatter test = new TestFormatter();
			for (int i = 0; i < details.size(); i++) {
				details.get(i).setSamt(test.getDecimalFormat(details.get(i).getSamt()+"",2));
				details.get(i).setBamt(test.getDecimalFormat(details.get(i).getBamt()+"",2));
				details.get(i).setYkam(test.getDecimalFormat(details.get(i).getYkam()+"",2));
			}
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(details));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}		
		return result;		
	}
}
