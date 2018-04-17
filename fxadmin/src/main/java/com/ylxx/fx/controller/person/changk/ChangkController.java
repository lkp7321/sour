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
package com.ylxx.fx.controller.person.changk;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ylxx.fx.core.domain.CkVo;
import com.ylxx.fx.core.domain.PpsyBeanDomain;
import com.ylxx.fx.service.impl.person.changkimpl.ChangkServiceImpl;
import com.ylxx.fx.service.po.PpsyBean;
import com.ylxx.fx.service.po.TbCk_ppcontrol;
import com.ylxx.fx.service.po.TbCk_rulet;
import com.ylxx.fx.service.po.TrdSpcut;
import com.ylxx.fx.service.po.TrdTynm;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodeCk;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * 敞口管理
 * @author lz130
 *
 */
@Controller
public class ChangkController {
	@Resource(name="selckService")
	private ChangkServiceImpl selckService;
	
	private static final Logger log = LoggerFactory.getLogger(ChangkController.class);
	/**
	 * 币别下拉框
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/selckUsd.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selckUsd(@RequestBody String userKey){
		return selckService.getUSDExnm(userKey);
	}
	/**
	 * 敞口流水数据
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/selckAll.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selckAll(@RequestBody CkVo ckvo){
		log.info("获取敞口流水数据");
		//起始日，结束日，直盘货币对，敞口流水号
		String userKey = ckvo.getUserKey();
		String sartDate = ckvo.getSartDate();
		String endDate = ckvo.getEndDate();
		String strExnm = ckvo.getStrExnm();
		String lkno = ckvo.getLkno();
		List<PpsyBean> list = selckService.getCondition(userKey, sartDate, endDate, strExnm, lkno);
		String msg = "";
		if(list.size()>0&&list!=null){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_00.getCode(), list);
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null); 
		}
		return msg;
	}
	//============敞口规则修改==================
	/**
	 * 敞口规则数据
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="selck_rulet.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selck_rulet(@RequestBody String userKey){
		return selckService.getSelck_rulet(userKey);
	}
	/**
	 * 添加时（账户，币别，损益,折美元）
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="selck_Dictionary.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selck_Dictionary(@RequestBody String userKey){
		return selckService.getCkdictionary(userKey);
	}
	/**
	 * 账户类型
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="selTrdCustType.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selTrdCustType(@RequestBody String userKey){
		return selckService.getTrdCusType(userKey);
	}
	/**
	 * 上窗口的初始化
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="initProd.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String initProd(@RequestBody String userKey){
		return selckService.getProdCkno(userKey);
	}
	/**
	 * 添加敞口规则
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="insCkRulet.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String insCkRulet(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = currUser.getCurIP();
		TbCk_rulet ckRulet = ckvo.getCkRulet();
		return selckService.insertCkRuleComm(currUser, ckRulet, ip);
	}
	/**
	 * 修改敞口规则
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="upCkRulet.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upCkRulet(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = currUser.getCurIP();
		TbCk_rulet ckRulet = ckvo.getCkRulet();
		return selckService.upCkRuleComm(currUser, ckRulet, ip);
	}
	/**
	 * 等级设置查询
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="selLeveCknm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selLeveCknm(@RequestBody String userKey){
		return selckService.getRuletLevel(userKey);
	}
	/**
	 * 等级设置
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="upLeveCknm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upLeveCknm(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = currUser.getCurIP();
		List<TbCk_rulet> ruletList = ckvo.getRuletList();
		return selckService.setRuletLevel(currUser, ruletList, ip);
	}
	
	//================================
	/**
	 * 平盘规则设置查询
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/listPpcon.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listPpcon(@RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ckno = ckvo.getCkno();
		return selckService.getppCon(currUser, ckno, ckvo.getPageNo(), ckvo.getPageSize());
	}
	/**
	 * 页面下拉框
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/cklis.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String cklis(@RequestBody String userKey){
		return selckService.getSelect(userKey);
	}
	/**
	 * 修改平盘方式
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/upPplist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upPplist(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = currUser.getCurIP();
		List<TbCk_ppcontrol> listppcon = ckvo.getListppcon();
		boolean flag = selckService.upPPconlist(currUser, listppcon, ip);
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_21.getCode(), "修改平盘方式成功");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_21.getCode(), null);
		}
	}
	/**
	 * 修改平盘规则
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/upPpcon.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upPpcon(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = currUser.getCurIP();
		TbCk_ppcontrol ppcontro = ckvo.getPpcontro();
		return selckService.upPPcon(currUser, ppcontro, ip);
	}
	//----------------------------
	/**
	 * 平盘损益查询
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/listPpsy.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listPpsy(@RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		String sartDate = ckvo.getSartDate();
		String endDate = ckvo.getEndDate();
		List<PpsyBeanDomain> list = selckService.getPpsy(userKey,sartDate,endDate);
		if(list.size()>0&&list!=null){
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_10.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null);
		}
	}
	/**
	 * 平盘损益统计
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/listPpTosy.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listPpTosy(@RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String sartDate = ckvo.getSartDate();
		String endDate = ckvo.getEndDate();
		return selckService.getPpTosy(currUser,sartDate,endDate);
	}
	//-----------------------------------
	/**
	 * 特殊账户分类查询
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/listTYNM.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listTYNM(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String apfg = ckvo.getApfg();//编号
		return selckService.getTRD_TYNM(currUser, apfg);
	}
	/**
	 *  特殊账户分类删除
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/delTYNM.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delTYNM(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//apfg:编号，tynm:名称
		TrdTynm trdTynm = ckvo.getTrdTynm();
		return selckService.deleteT_TYNM(currUser, trdTynm);
	}
	/**
	 *  特殊账户分类添加
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/insertTYNM.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String insertTYNM(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//apfg:编号，tynm:名称，stat:0 启用，1 停用
		TrdTynm trdTynm = ckvo.getTrdTynm();
		return selckService.insertT_TYNM(currUser, trdTynm);
	}
	/**
	 *  特殊账户分类修改
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/updateTYNM.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateTYNM(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//trdtynm.setApfg("");//编号
		//trdtynm.setTynm("");//名称
		//trdtynm.setStat("");//状态
		TrdTynm trdTynm = ckvo.getTrdTynm();
		return selckService.updateT_TYNM(currUser, trdTynm);
	}
	//-----------------------------------
	/**
	 * 特殊账户收集查询
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/listSpcut.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listSpcut(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//String cuno = "";//卡号
		String cuno = ckvo.getCuno();
		return selckService.getTRD_SPCUT(currUser, cuno);
	}
	/**
	 * 特殊账户收集删除
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/delSpcut.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delSpcut(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//String cuno = "";//获取
		String cuno = ckvo.getCuno();
		return selckService.deleteTRD_SPCUT(currUser, cuno);
	}
	/**
	 * 特殊账户收集添加/修改的下拉框
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/listTynm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listTynm(HttpServletRequest req,@RequestBody String userKey){
		return selckService.getTrdTynm(userKey);//数据 tynm/apfg:名称/值
	}
	/**
	 * 特殊账户收集添加
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/insertSpcut.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String insertSpcut(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//apfg:下拉框的值，cuno:卡号，stat:0,1:启用/停用
		TrdSpcut trdSpcut = ckvo.getTrdSpcut();
		return selckService.insertTrdSpcut(currUser, trdSpcut);
	}
	/**
	 * 特殊账户收集修改,也需要初始化下拉框
	 * @param req
	 * @param ckvo
	 * @return
	 */
	@RequestMapping(value="/upSpcut.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upSpcut(HttpServletRequest req, @RequestBody CkVo ckvo){
		String userKey = ckvo.getUserKey();
		CurrUser currUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//apfg:账户类型，cuno:卡号，使用:stat
		TrdSpcut trdSpcut = ckvo.getTrdSpcut();
		return selckService.updateTrdSpcut(currUser, trdSpcut);
	}
	/*---------openmanage-----------*/
	/**
	 * 敞口流水查询
	 * @return
	 */
	@RequestMapping("toopenwater.do")
	public String toopenwater(){
		return "personaloffer/openmanage/openwater";
	}
    /**
     * 敞口规则设置
     * @return
     */
	@RequestMapping("toopenrule.do")
	public String toopenrule(){
		return "personaloffer/openmanage/openrule";
	}
	/**
	 * 平盘规则设置
	 * @return
	 */
	@RequestMapping("toplatrule.do")
	public String toplatrule(){
		return "personaloffer/openmanage/platrule";
	}
	/**
	 * 平盘损益查询
	 * @return
	 */
	@RequestMapping("toplatprofit.do")
	public String toplatprofit(){
		return "personaloffer/openmanage/platprofitloss";
	}
	/**
	 * 特殊账户分类
	 * @return
	 */
	@RequestMapping("toaccountclass.do")
	public String toaccountclass(){
		return "personaloffer/openmanage/accountclasstion";
	}
	/**
	 * 特殊账户收集
	 * @return
	 */
	@RequestMapping("toaccountcollect.do")
	public String toaccountcollect(){
		return "personaloffer/openmanage/accountcollect";
	}
	/**
	 * 导出敞口流水数据
	 * @param ckvo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/outputexcelCKTran.do")
    public void showImgCodeCk(CkVo ckvo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = ckvo.getTableName();//前台传的  表名
        List<Table> tableList = ckvo.getTableList();//前台传的  表头，及Key
		String userKey = ckvo.getUserKey();//查询所需要的条件
		String sartDate = ckvo.getSartDate();
		String endDate = ckvo.getEndDate();
		String strExnm = ckvo.getStrExnm();
		String lkno = ckvo.getLkno();
		//这里是我自己查询数据的过程
		List<PpsyBean> list = selckService.getCondition(userKey, sartDate, endDate, strExnm, lkno);
        //数据封装
        PoiExcelExport<PpsyBean> pee = new PoiExcelExport<PpsyBean>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
	
	/**
	 * 导出平盘损益查询的数据
	 * @param ckvo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/outputexcelCKPp.do")
    public void showImgCodeCkpp(CkVo ckvo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		//
		String fileName = ckvo.getTableName();//前台传的  表名
        List<Table> tableList = ckvo.getTableList();//前台传的  表头，及Key
        //
        String userKey = ckvo.getUserKey();
		String sartDate = ckvo.getSartDate();
		String endDate = ckvo.getEndDate();
		//这里是我自己查询数据的过程
		List<PpsyBeanDomain> list = selckService.getPpsy(userKey,sartDate,endDate);
        //数据封装
        PoiExcelExport<PpsyBeanDomain> pee = new PoiExcelExport<PpsyBeanDomain>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
