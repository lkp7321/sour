/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * 价格管理---报价参数设置
 */
package com.ylxx.fx.controller.person.price;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.PriceVo;
import com.ylxx.fx.service.person.price.IPdtRParaService;
import com.ylxx.fx.service.po.PdtChkParaBean;
import com.ylxx.fx.service.po.PdtPointBean;
import com.ylxx.fx.service.po.PdtRParaTBean;
import com.ylxx.fx.service.po.PdtStoperBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
/**
 * 报价参数设置
 * @author lz130
 *
 */
@Controller
public class PdtRParaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdtRParaController.class);
	
	@Resource(name="pdtRParaService")
	private IPdtRParaService pdtRParaService;
	
	/*String user ="xlj";
 	String prod = "P001";//从用户信息读取，其实是可能ptid??
 	//extp=="1"，校验、停牌、点差 测试
 	String exnm = "AUDCAD";//从选中的数据读取
	String stid = "SC99";//停牌器ID,从前台获取
	LoginUser logUser = new LoginUser(user,prod);*/
		
	/**
	 * 查询报价参数
	 * @param pVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectPdtRList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectPdtRList(@RequestBody PriceVo pVo) throws Exception {
		LOGGER.info("查询报价参数数据：");
		/*PriceVo pVo = new PriceVo();
		pVo.setProd("P007");
		pVo.setPageNo(2);
		pVo.setPageSize(6);
		CurrUser curUser = new CurrUser();
		curUser.setProd("P007");*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.addpage(pVo.getProd(),pVo.getPageNo(),pVo.getPageSize());
		}else {
			return pdtRParaService.addpage(curUser.getProd(),pVo.getPageNo(),pVo.getPageSize());
		}
		
	}
	//P999查询产品列表
	@RequestMapping(value="/pdtCom.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String pdtCom() throws Exception {
		return pdtRParaService.pdtCom();
	}
	//报价参数设置中读取币种交叉盘标识从产品币种表中读取:查询货币对类型
	@RequestMapping(value="/selectExtp.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectExtp(@RequestBody PriceVo pVo) throws Exception {
		/*PriceVo pVo = new PriceVo();
		pVo.setProd(prod);
		pVo.setExnm(exnm);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		//根据币别对名称（主键）查询记录,取币别对类型,以判断跳转页面
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.selectObjPdtr(pVo.getProd(), pVo.getExnm());
		}else {
			return pdtRParaService.selectObjPdtr(curUser.getProd(), pVo.getExnm());
		}
		
		//取产品名称，显示在页面上
		//ptnm = pdtRParaService.getPtnm(ptid);//管理台中传入参数为prod，sql语句执行时为prid
	}
	//查询货币对类型@RequestBody String exnm
	@RequestMapping(value="/selectExtp2.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectExtp2(@RequestBody String exnm) throws Exception {
		return pdtRParaService.selectObjPdtr2(exnm);
	}
	//【校验】页面初始化
	@RequestMapping(value="/ChkParaInit.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ChkParaInit(@RequestBody PriceVo pVo) throws Exception{
		/*PriceVo pVo = new PriceVo();
		pVo.setProd(prod);
		pVo.setExnm(exnm);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.getCurChk(pVo.getExnm(),pVo.getProd());
		}else {
			return pdtRParaService.getCurChk(pVo.getExnm(),curUser.getProd());
		}
		
	}
	//保存【校验】数据
	@RequestMapping(value="/SaveChkPara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String SaveChkPara(@RequestBody PriceVo pVo) throws Exception{
		//【校验】部分测试数据
		/*float mdmd = (float) 1.0356;//买入价:小数位数不能超过10位;不能为负数;整数与小数分隔符只能出现一次;不能为空;必须是数字
		String mxbp = "1001";//中间价两次浮动点差:[两次波动点差]有效位数不能大于4;...不能为负数;...必须为整数;...必须是数字;...不能为空
		String mxud = "9999";//价格多少次未变为无效:[未波动次数]有效位数不能大于4;...不能为负数;...必须为整数;...必须是数字;...不能为空.
		String mxdp = "2001";//最大波动点差:[最大波动点差]有效位数不能大于4;...不能为负数;...必须为整数;...必须是数字;...不能为空.
		String mxct = "10";//合法波动次数:[合法波动次数]有效位数不能大于4;...不能为负数;...必须为整数;...必须是数字;...不能为空. 
		String usfg = "1";//使用标志
		//修改【校验】数据后,点击保存
		PdtChkParaBean pdtChkPara = new PdtChkParaBean();
		pdtChkPara.setMdmd(mdmd);
		pdtChkPara.setMxbp(mxbp);
		pdtChkPara.setMxud(mxud);
		pdtChkPara.setMxdp(mxdp);
		pdtChkPara.setMxct(mxct);
		pdtChkPara.setUsfg(usfg);	
		
		pdtChkPara.setTerm("0");
		pdtChkPara.setExnm("AUDCAD");
		pdtChkPara.setCxfg("2");
		
		pdtChkPara.setTpfg("即期");
		String tpfg = pdtRParaService.getTpfgByTpnm(pdtChkPara.getTpfg());
		pdtChkPara.setTpfg(tpfg);
		
		PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		pVo.setPdtChk(pdtChkPara);
		pVo.setExnm(exnm);
		
		return pdtRParaService.saveChkPara(pVo.getLogUser().getUsnm(), pVo.getLogUser().getProd(), 
				pVo.getPdtChk(), pVo.getExnm());*/
		PdtChkParaBean pdtChkPara = pVo.getPdtChk();
		String tpfg = pdtRParaService.getTpfgByTpnm(pdtChkPara.getTpfg());
		pdtChkPara.setTpfg(tpfg);
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.saveChkPara(pVo.getUserKey(), pVo.getProd(), 
					pdtChkPara, pVo.getExnm());
		}else {
			return pdtRParaService.saveChkPara(pVo.getUserKey(), curUser.getProd(), 
					pdtChkPara, pVo.getExnm());
		}
		
		//TODO ut.SendSocketB1();
	}
	//【停牌】页面初始化
	@RequestMapping(value="/CurStopInit.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String CurStopInit(@RequestBody PriceVo pVo) throws Exception{
		/*PriceVo pVo = new PriceVo();
		pVo.setProd(prod);
		pVo.setExnm(exnm);
		pVo.setStid(stid);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.getCurStop(pVo.getProd(),pVo.getExnm(),pVo.getStid());
		}else {
			return pdtRParaService.getCurStop(curUser.getProd(),pVo.getExnm(),pVo.getStid());
		}
		//TODO ut.SendSocketB1();
	}
	//保存【停牌】数据
	@RequestMapping(value="/SavePdtStop.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String SavePdtStop(@RequestBody PriceVo pVo) throws Exception{
		/*//【停牌】部分测试数据
		String tpnm = "即期";
		String term = "0";//从前台选中取:期限
		String stfg = "1";//0正常,1停牌. 应该从前台选中取。修改之前为停牌状态1.
		//修改【停牌】数据后,点击保存
		
		pdtStoper.setStfg(stfg);
		pdtStoper.setUsfg("0");
		
		pdtStoper.setStid(stid);
		pdtStoper.setTerm(term);
		pdtStoper.setExnm(exnm);
		String tpfg = pdtRParaService.getTpfgByTpnm(tpnm);
		pdtStoper.setTpfg(tpfg);

		PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		pVo.setPdtStoper(pdtStoper);
		return pdtRParaService.saveStop(pVo.getLogUser().getUsnm(), pVo.getLogUser().getProd(), 
				pVo.getPdtStoper());*/
		PdtStoperBean pdtStoper = pVo.getPdtStoper();
		String tpfg = pdtRParaService.getTpfgByTpnm(pVo.getPdtStoper().getTpfg());
		pdtStoper.setTpfg(tpfg);
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.saveStop(pVo.getUserKey(), pVo.getProd(), pdtStoper);
		}else {
			return pdtRParaService.saveStop(pVo.getUserKey(), curUser.getProd(), pdtStoper);
		}
		//TODO ut.SendSocketB1();
		
	}
	//【点差】页面初始化
	@RequestMapping(value="/CurPointInit.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String CurPointInit(@RequestBody PriceVo pVo) throws Exception{
		/*PriceVo pVo = new PriceVo();
		pVo.setProd(prod);
		pVo.setExnm(exnm);	*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.getCurPoint(pVo.getProd(),pVo.getExnm());
		}else {
			return pdtRParaService.getCurPoint(curUser.getProd(),pVo.getExnm());
		}
		//TODO ut.SendSocketB1();		
	}
	/**
	 * 保存【点差】数据
	 * @param pVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/SavePoint.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String SavePoint(@RequestBody PriceVo pVo) throws Exception {
		/*PdtPointBean pdtPoint = new PdtPointBean();
		pdtPoint.setQtcy("120");//价格生命周期(秒)
		pdtPoint.setBhbd("60");//总行对分行买入点差
		pdtPoint.setCubd("120");//总行对客户买入点差
		pdtPoint.setPrtp("0");//报价模式(0-双边价、1-中间价)
		pdtPoint.setBhsd("63");//总行对分行卖出点差
		pdtPoint.setCusd("121");//总行对客户卖出点差
		pdtPoint.setTpfg("SPT");//价格类型:SPT即期FWD远期SWP掉期OPT期权.
		pdtPoint.setTerm("0");//期限
		pdtPoint.setExnm("KAURMB");//币别对名称
		pdtPoint.setCxfg("2");
		PriceVo pVo =  new PriceVo();
		pVo.setPdtPoint(pdtPoint);
		return pdtRParaService.savePoint("123", "P003",pVo.getPdtPoint());*/
		PdtPointBean pdtPoint = pVo.getPdtPoint();
		String tpfg = pdtRParaService.getTpfgByTpnm(pVo.getPdtPoint().getTpfg());
		pdtPoint.setTpfg(tpfg);
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.savePoint(pVo.getUserKey(), pVo.getProd(), pdtPoint);
		}else {
			return pdtRParaService.savePoint(pVo.getUserKey(), curUser.getProd(), pdtPoint);
		}
	}
	//【产品市场源头选择】初始化: 获取“产品市场”、“市场权重设置”
	@RequestMapping(value="/MarkInit1.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String MarkInit1(@RequestBody PriceVo pVo) throws Exception {
		
		/*PriceVo pVo = new PriceVo();
		pVo.setProd(prod);
		pVo.setExnm(exnm);*/
		
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		//产品市场MKNM、市场权重设置MKSL(、市场id MKID)
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.curMkList(pVo.getProd(), pVo.getExnm());
		}else {
			return pdtRParaService.curMkList(curUser.getProd(), pVo.getExnm());
		}
		//TODO ut.SendSocketB1();
	}
	//【产品市场源头选择】初始化: 获取“产品市场源头”
	@RequestMapping(value="/MarkInit2.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String MarkInit2(@RequestBody PriceVo pVo) throws Exception {
		
		/*PriceVo pVo = new PriceVo();
		pVo.setProd(prod);
		pVo.setExnm(exnm);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		//产品市场源头MKNM(、市场名称MKID)
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.allMkList(pVo.getProd(), pVo.getExnm());
		}else {
			return pdtRParaService.allMkList(curUser.getProd(), pVo.getExnm());
		}
	}
	//保存【产品市场源头选择】数据
	@RequestMapping(value="/SaveMark.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String SaveMark(@RequestBody PriceVo pVo) throws Exception {
		/*PdtRParaTBean pdtr = new PdtRParaTBean();
		pdtr.setMkst("M0201|M0199|M0101");
		pdtr.setMksl(".3|.2|.5");
		pdtr.setMklv("1|2|3");
		
		pdtr.setTpfg("即期");
		String tpfg = pdtRParaService.getTpfgByTpnm(pdtr.getTpfg());
		pdtr.setTpfg(tpfg);
		pdtr.setTerm("0");
		pdtr.setExnm(exnm);
		pdtr.setCxfg("2");

		PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		pVo.setPdtrPara(pdtr);
		
		return pdtRParaService.saveMarket(pVo.getLogUser().getUsnm(), pVo.getLogUser().getProd(), 
				pVo.getPdtrPara());*/
		
		PdtRParaTBean pdtr = pVo.getPdtrPara();
		String tpfg = pdtRParaService.getTpfgByTpnm(pdtr.getTpfg());
		pdtr.setTpfg(tpfg);
		
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.saveMarket(pVo.getUserKey(), pVo.getProd(), pdtr);
		}else {
			return pdtRParaService.saveMarket(pVo.getUserKey(), curUser.getProd(), pdtr);
		}
		//TODO ut.SendSocketB1();
	}
	//【策略】页面初始化:获取当前策略
	@RequestMapping(value="/GetGmnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String GetGmnm(@RequestBody PriceVo pVo) throws Exception{
		/*PriceVo pVo = new PriceVo();
		pVo.setProd(prod);
		pVo.setExnm(exnm);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.selectGMNM(pVo.getProd(),pVo.getExnm());//市场优先
		}else {
			return pdtRParaService.selectGMNM(curUser.getProd(),pVo.getExnm());
		}
	}
	//【策略】页面初始化:获取所有策略
	@RequestMapping(value="/GetAllGmnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String GetAllGmnm() throws Exception{
		return pdtRParaService.selectPriceUs();
	}
	/**
	 * 保存【策略】数据
	 * @param pVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/SaveGmnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String SaveGmnm(@RequestBody PriceVo pVo) throws Exception {
		/*PdtRParaTBean pdt = new PdtRParaTBean();
		pdt.setGmnm("权重优先");
		String gmid = pdtRParaService.getGmidByGmnm(pdt.getGmnm());
		pdt.setPmid(gmid);
		pdt.setTpfg("即期");
		String tpfg = pdtRParaService.getTpfgByTpnm(pdt.getTpfg());
		pdt.setTpfg(tpfg);
		pdt.setTerm("0");
		pdt.setExnm(exnm);
		pdt.setCxfg("2");//2:钞,1:汇
		PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		pVo.setPdtrPara(pdt);
		pVo.setExnm(exnm);
		return pdtRParaService.saveGmnm(pVo.getLogUser().getUsnm(), pVo.getLogUser().getProd(), 
				pVo.getPdtrPara(), pVo.getExnm());*/
		
		PdtRParaTBean pdt = pVo.getPdtrPara();
		String tpfg = pdtRParaService.getTpfgByTpnm(pdt.getTpfg());
		pdt.setTpfg(tpfg);
		String gmid = pdtRParaService.getGmidByGmnm(pdt.getGmnm());
		pdt.setPmid(gmid);
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.saveGmnm(pVo.getUserKey(), pVo.getProd(), pdt, pVo.getExnm());
		}else {
			return pdtRParaService.saveGmnm(pVo.getUserKey(), curUser.getProd(), pdt, pVo.getExnm());
		}
		//TODO ut.SendSocketB1();
	}
	//【干预】页面初始化
	@RequestMapping(value="/GetPdtCtrl.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String GetPdtCtrl(@RequestBody PriceVo pVo) throws Exception{
		/*PriceVo pVo = new PriceVo();
		pVo.setProd(prod);
		pVo.setCtid("CP99");
		pVo.setExnm(exnm);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.curPdtCtrl(pVo.getProd(), pVo.getCtid(), pVo.getExnm());
		}else {
			return pdtRParaService.curPdtCtrl(curUser.getProd(), pVo.getCtid(), pVo.getExnm());
		}
	}
	//保存【干预】数据
	@RequestMapping(value="/SaveCtrl.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String SaveCtrl(@RequestBody PriceVo pVo) throws Exception{
		/*PdtCtrlPriTBean pdtCtrl = new PdtCtrlPriTBean();
		pdtCtrl.setNebp("2");//买入价干预点数
		pdtCtrl.setNesp("1");//卖出价干预点数
		pdtCtrl.setBgtm("20131012 00:00:00");//生效起始时间
		pdtCtrl.setEdtm("20181012 19:19:19");//生效截止时间
		pdtCtrl.setStfg("0");//干预标志（0 干预 1不干预）
		pdtCtrl.setCtid("CP99");
		pdtCtrl.setTerm("0");
		pdtCtrl.setExnm(exnm);
		pdtCtrl.setCxfg("2");
		//pdtCtrl.setExcd("2914");
		//pdtCtrl.setCtnm("手工干预");
		//pdtCtrl.setFabp("0");
		//pdtCtrl.setFasp("0");
		
		PriceVo pVo = new PriceVo();
		pVo.setLogUser(logUser);
		pVo.setPdtCtrl(pdtCtrl);*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(pVo.getUserKey());
		if (curUser.getProd().equals("P999")) {
			return pdtRParaService.saveCtrl(pVo.getUserKey(), pVo.getProd(), pVo.getPdtCtrl());
		}else {
			return pdtRParaService.saveCtrl(pVo.getUserKey(), curUser.getProd(), pVo.getPdtCtrl());
		}
		//TODO 刷新页面后，ut.SendSocketB1();
	}
	//账户交易->报价管理->报价参数设置:生效
	@RequestMapping(value="/SendAccExPdtRparaSocket.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String SendAccExPdtRparaSocket() throws Exception {
		return pdtRParaService.SendAccExPdtRparaSocket();
	}
	
}
