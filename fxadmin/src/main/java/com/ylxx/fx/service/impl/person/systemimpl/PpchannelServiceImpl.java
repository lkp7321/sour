package com.ylxx.fx.service.impl.person.systemimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ylxx.fx.core.mapper.person.system.PpchangelMapper;
import com.ylxx.fx.service.person.system.PpchannelService;
import com.ylxx.fx.service.po.Ppchannel;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodeSystem;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
@Service("ppchanService")
public class PpchannelServiceImpl implements PpchannelService {
	@Resource
	private PpchangelMapper ppchanmap;
	private static final Logger log = LoggerFactory.getLogger(PpchannelServiceImpl.class);
	private List<Ppchannel> list = null;
	
	public String getAllPpchan(String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			list = ppchanmap.selectexnm(curUser.getProd());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_0.getCode(), null);
		}
	}
	public String changleChan(String userKey, List<Ppchannel> list,String s){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPrcd(curUser.getProd());
		}
		try{
			if(s.equals("1")){
				ppchanmap.closeChan(list);
				return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "平盘通道关闭成功");
			}else{
				ppchanmap.openChan(list);
				return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "平盘通道开启成功");
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_06.getCode(), null);
		}
	}
}
