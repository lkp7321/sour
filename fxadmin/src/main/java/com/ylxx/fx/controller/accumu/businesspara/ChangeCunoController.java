package com.ylxx.fx.controller.accumu.businesspara;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.core.domain.ChangeCunoVo;
import com.ylxx.fx.service.accumu.businesspara.IChangeCunoService;


@Controller
//@RequestMapping("fx")
public class ChangeCunoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeCunoController.class);
	
	@Resource(name="iChangeCunoService")
	private IChangeCunoService iChangeCunoService;
	@RequestMapping(value="/changeSend.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String changeSend(@RequestBody ChangeCunoVo CcVo) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(CcVo.getUserKey());
		return iChangeCunoService.doChange(CcVo.getOldcuno(),CcVo.getNewcuno(),CcVo.getCaty());	
		
	}

}
