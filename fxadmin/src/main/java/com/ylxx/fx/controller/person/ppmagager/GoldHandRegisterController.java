package com.ylxx.fx.controller.person.ppmagager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.MoZhangVo;
import com.ylxx.fx.service.person.ppmagager.IGoldHandRegisterService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
/**
 * 贵金属敞口登记
 * @author lz130
 *
 */
@Controller
public class GoldHandRegisterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoldHandRegisterController.class);
	
	@Resource(name="goldHandRegisterService")
	private IGoldHandRegisterService goldHandRegisterService;
	
	/**
	 * 敞口名称改变时触发函数
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/onchange.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String onchange(@RequestBody MoZhangVo mzVo) throws Exception {
	   /*MoZhangVo mzVo = new MoZhangVo();
	   mzVo.setProd("P002");
	   mzVo.setCombox(2);*/
	   CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(mzVo.getUserKey());
	   return goldHandRegisterService.onchange(curUser.getProd(), mzVo.getCombox());	
	}
	/**
	 * 判断买入币种在标准币别对的左边还是右边
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/SelectSamt.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String SelectSamt(@RequestBody MoZhangVo mzVo) throws Exception {
	   /*MoZhangVo mzVo = new MoZhangVo();
	   mzVo.setProd("P002");
	   mzVo.setBexnm("USD");
	   mzVo.setSexnm("RMB");*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(mzVo.getUserKey());
	   return goldHandRegisterService.selectSamt(curUser.getProd(), mzVo.getBexnm(), mzVo.getSexnm());	
	}
	/**
	 * 判断卖出币种在标准币别对的左边还是右边
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectBamt.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectBamt(@RequestBody MoZhangVo mzVo) throws Exception {
	   /*MoZhangVo mzVo = new MoZhangVo();
	   mzVo.setProd("P002");
	   mzVo.setBexnm("USD");
	   mzVo.setSexnm("RMB");*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(mzVo.getUserKey());
	   return goldHandRegisterService.selectBamt(curUser.getProd(), mzVo.getBexnm(), mzVo.getSexnm());	
	}
	/**
	 * 查询货币对
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selExnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selExnm(@RequestBody MoZhangVo mzVo) throws Exception {
	   /*MoZhangVo mzVo = new MoZhangVo();
	   mzVo.setProd("P002");
	   mzVo.setBexnm("USD");
	   mzVo.setSexnm("RMB");
	   String sexnm = mzVo.getSexnm();
	   String bexnm = mzVo.getBexnm();
	   return goldHandRegisterService.selExnm(mzVo.getProd(), sexnm+bexnm, bexnm+sexnm);*/
		String sexnm = mzVo.getSexnm();
		String bexnm = mzVo.getBexnm();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(mzVo.getUserKey());
		return goldHandRegisterService.selExnm(curUser.getProd(), sexnm+bexnm, bexnm+sexnm);
	}
	/**
	 * 执行登记
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goldregisterCK.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String goldregisterCK(@RequestBody MoZhangVo mzVo) throws Exception {
		/*MoZhangVo mzVo = new MoZhangVo();
		mzVo.setUserKey("123");
		Register ckno = new Register();
		ckno.setCkno("2");//敞口名称data
		ckno.setSlnm("RMB");//卖出币种
		ckno.setBynm("AUB");//买入币种
		ckno.setSlam("-2");//卖出金额
		ckno.setByam("2");//买入金额
		ckno.setExpc("-1");//成交汇率
		ckno.setCxfg("2");//钞汇标志data
		ckno.setJsdt("20171101");//交易日期
		ckno.setDshou("xlj");//平盘对手
		ckno.setName("Administrator");//交易员
		mzVo.setRegister(ckno);	*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(mzVo.getUserKey());
		/*CurrUser curUser = new CurrUser();
		curUser.setProd("P003");*/
 		if (curUser.getProd().equals("P002")) {
			//纸黄金
 			return goldHandRegisterService.registerCK(mzVo.getUserKey(),mzVo.getRegister());
		}else {
			//民生金
			return goldHandRegisterService.spgoldregisterCK(mzVo.getUserKey(),mzVo.getRegister());
		}
	}
}
