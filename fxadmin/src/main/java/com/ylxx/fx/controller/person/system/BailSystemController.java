package com.ylxx.fx.controller.person.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.BailSystemVo;
import com.ylxx.fx.service.person.system.BailSystemService;
import com.ylxx.fx.service.po.BailExceBean;
import com.ylxx.fx.service.po.BailMt4Bean;
import com.ylxx.fx.service.po.BailTranlist;
import com.ylxx.fx.service.po.Cmmptparac;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 民生金
 * 系统管理
 * @author lz130
 *
 */
@Controller
public class BailSystemController {
	@Resource(name="bailSystemService")
	private BailSystemService bailSystemService;
	/**
	 * 系统参数   - 查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/AllBailSysPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String AllBailSysPrice(HttpServletRequest req){
		List<Cmmptparac> list = bailSystemService.getAllBailSysPrice();
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无数据");
		}
	}
	/**
	 * 修改系统参数
	 * @param bailSysVo
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/upBailSysPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upBailSysPrice(HttpServletRequest req, @RequestBody BailSystemVo bailSysVo){
		String userKey = bailSysVo.getUserKey();
		Cmmptparac bean = bailSysVo.getBean();
		boolean flag = bailSystemService.updates(userKey, bean);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改系统参数成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "修改系统参数失败");
		}
	}
	
	/**
	 * 获取交易码
	 * @return
	 */
	@RequestMapping(value="/AllBailtranCodePrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String AllBailtranCodePrice(HttpServletRequest req){
		List<Map<String, Object>> list = bailSystemService.querytrancodes();
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取交易类型失败");
		}
	}
	/**
	 * 交易数据
	 * @return
	 */
	@RequestMapping(value="/AllBailtransPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String AllBailtransPrice(HttpServletRequest req, @RequestBody BailSystemVo bailSysVo){
		String acno = bailSysVo.getAcno();
		String strcuac = bailSysVo.getStrcuac(); 
		String trdtbegin = bailSysVo.getTrdtbegin(); 
		String trdtend = bailSysVo.getTrdtend(); 
		String trcd = bailSysVo.getTrcd();
		List<BailTranlist> list = bailSystemService.getsFxipTranlist(acno, strcuac, trdtbegin, trdtend, trcd);
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无数据");
		}
	}
	/**
	 * 异常交易监控
	 * @param bailSysVo
	 * @return
	 */
	@RequestMapping(value="/bailExceptionData.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String bailExceptionData(HttpServletRequest req, @RequestBody BailSystemVo bailSysVo){
		String curDate = bailSysVo.getCurDate();
		String toDate = bailSysVo.getToDate();
		List<BailExceBean> list = bailSystemService.bailExceptionData(curDate, toDate);
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无数据");
		}
	}
	/**
	 * MT4用户组 数据
	 * @return
	 */
	@RequestMapping(value="/queryBailMt4Para.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryBailMt4Para(HttpServletRequest req){
		List<BailMt4Bean> list = bailSystemService.queryBailMt4Para();
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无数据");
		}
	}
	
	/**
	 * MT4 添加
	 * @param userKey
	 * @param cmmbean
	 * @return
	 */
	@RequestMapping(value="/insinsertPtpara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String insinsertPtpara(HttpServletRequest req, @RequestBody BailSystemVo bailSysVo){
		String userKey = bailSysVo.getUserKey();
		BailMt4Bean cmmbean = bailSysVo.getCmmbean();
		boolean flag = bailSystemService.insertPtpara(userKey, cmmbean);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "添加成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "添加失败");
		}
	}
	/**
	 * 修改 MT4
	 * @param bailSysVo
	 * @return
	 */
	@RequestMapping(value="/upUpdatePtpara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upUpdatePtpara(HttpServletRequest req, @RequestBody BailSystemVo bailSysVo){
		String userKey = bailSysVo.getUserKey();
		BailMt4Bean cmmbean = bailSysVo.getCmmbean();
		boolean flag = bailSystemService.updatePtpara(userKey, cmmbean);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "修改失败");
		}
	}
	/**
	 * 异常处理，交易码
	 * @return
	 */
	@RequestMapping(value="/selstrancodes.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selstrancodes(HttpServletRequest req){
		List<Map<String, Object>> list = bailSystemService.seltrancodes();
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "获取交易码失败");
		}
	}
	/**
	 * 异常交易数据
	 * @param curDate
	 * @param tranCode
	 * @param toDate
	 * @return
	 */
	@RequestMapping(value="/bailExceptionDatas.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String bailExceptionDatas(HttpServletRequest req, @RequestBody BailSystemVo bailSysVo){
		String curDate=bailSysVo.getCurDate();
		String toDate = bailSysVo.getToDate();
		String tranCode = bailSysVo.getTranCode();
		List<BailExceBean> list = bailSystemService.bailExceptionData(curDate, tranCode, toDate);
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "获取交易码失败");
		}
	}
	/**
	 * 初始化
	 * @param userKey
	 * @param tranCode
	 * @param bailExceList
	 * @return
	 */
	@RequestMapping(value="/updateInitialize.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateInitialize(HttpServletRequest req, @RequestBody BailSystemVo bailSysVo){
		String userKey = bailSysVo.getUserKey();
		String tranCode = bailSysVo.getTranCode();
		List<BailExceBean> bailExceList = bailSysVo.getBailExceList();
		return bailSystemService.updateInitialize(userKey, tranCode, bailExceList);
	}
}
