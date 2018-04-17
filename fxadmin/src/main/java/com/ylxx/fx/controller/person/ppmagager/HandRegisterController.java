package com.ylxx.fx.controller.person.ppmagager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.MoZhangVo;
import com.ylxx.fx.service.person.ppmagager.IHandRegisterService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
/**
 * 手工敞口登记
 * @author lz130
 *
 */
@Controller
public class HandRegisterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandRegisterController.class);
	
	@Resource(name="handRegisterService")
	private IHandRegisterService handRegisterService;
		
	/**
	 * 查询币别对列表
	 * @param userKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectCurrencyPair.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectCurrencyPair(@RequestBody String userKey) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return handRegisterService.selectCurrencyPair(curUser.getProd());	
	}
	/**
	 * 获取敞口列表
	 * @param userKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectCkno.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectCkno(@RequestBody String userKey) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return handRegisterService.selectCkno(curUser.getProd());	
	}
	/**
	 * 获取平盘对手列表
	 * @param userKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectPPDS.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectPPDS(@RequestBody String userKey) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return handRegisterService.selectPPDS(curUser.getProd());	
	}
	/**
	 * 登记操作
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/registerCK.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String registerCK(@RequestBody MoZhangVo mzVo) throws Exception {
		LOGGER.info("手工敞口登记");
		/*MoZhangVo mzVo = new MoZhangVo();
		mzVo.setUserKey("123");
		Register ckno = new Register();
		ckno.setByam("2");
		ckno.setCurrpair("AUDUSD");
		ckno.setSlam("-2");
		ckno.setTrdt("20171123");
		ckno.setCkno("0001");
		ckno.setCbhl("1");
		ckno.setExpc("1");
		ckno.setCxfg("1");
		ckno.setJsdt("20171128");
		ckno.setDshou("CITI_LON");
		ckno.setName("Administrator");
		mzVo.setRegister(ckno);	*/	
		return handRegisterService.registerCK(mzVo.getUserKey(),mzVo.getRegister());	
	}
}
