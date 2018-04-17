package com.ylxx.fx.controller.person.fxipmonitor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.CkmonitorVo;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.person.fxipmonitor.AccumBrnchService;
import com.ylxx.fx.service.po.AccinfoMonitorBean;
import com.ylxx.fx.service.po.ChangeInfoList;
import com.ylxx.fx.service.po.CkTotalBean;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.service.po.QYInfoList;
import com.ylxx.fx.service.po.Trd_ogcd;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 积存金（P003）   
 * -监控管理-
 * @author lz130
 *
 */
@Controller
//@RequestMapping("fx")
public class AccumBrnchMonitorController {
	@Resource(name="accumAllBrnchService")
	private AccumBrnchService accumAllBrnchService;
	private static final Logger log = LoggerFactory.getLogger(AccumBrnchMonitorController.class);
	/**
	 * 总分行价格
	 * @return
	 */
	@RequestMapping(value="/accum/allbrnch.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAccumAllPdtBrnch(){
		List<FxipMonitorVo> list = accumAllBrnchService.getAccumAllPdtBrnch();
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无记录");
		}
	}
	/**
	 * 客户价格
	 */
	@RequestMapping(value="/accum/allcust.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAccumAllPdtCust(){
		List<FxipMonitorVo> list = accumAllBrnchService.getAccumAllPdtCust();
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无记录");
		}
	}
	/**
	 * 定时刷新
	 * 总敞口（市场报价）--数据1
	 * @return
	 */
	@RequestMapping(value="/accum/allckTotalData.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String accumckTotalData(){
		List<CkTotalBean> list = accumAllBrnchService.ckTotalData();
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无记录");
		}
	}
	/**
	 * 总敞口（总敞口and折算敞口）--数据2
	 * @return
	 */
	@RequestMapping(value="/accum/allckTotal.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String accumckTotal(){
		List<CkTotalBean> list = accumAllBrnchService.ckTotal();
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无记录");
		}
	}
	/**
	 * 提取损益
	 * @param ckmonVo
	 * @return
	 */
	@RequestMapping(value="/accum/upckTotal.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String accumupdateCKZCYK(@RequestBody CkmonitorVo ckmonVo){
		String userKey = ckmonVo.getUserKey();
		CkTotalBean ckTotalBean = ckmonVo.getCkTotalBean();
		boolean flag = accumAllBrnchService.updateCKZCYK(userKey, ckTotalBean);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "提取损益成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "提取损益失败");
		}
	}
	/**
	 * 获取盈亏
	 * @return
	 */
	@RequestMapping(value="/accum/getckTotal.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String accumckToalSYYL(){
		CkTotalBean ckTotalBean = accumAllBrnchService.ckToalSYYL();
		if(ckTotalBean!=null){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), ckTotalBean);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取总损益失败");
		}
	}
	/**
	 * 获取敞口
	 * @return
	 */
	@RequestMapping(value="/accum/getckno.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String accumckno(){
		List<Map<String,Object>> list = accumAllBrnchService.getaccumCknoTree();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * 分类敞口
	 * @param ckmonVo
	 * @return
	 */
	@RequestMapping(value="/accum/getclassckAccum.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getclassckAccum(@RequestBody CkmonitorVo ckmonVo){
		String userKey = ckmonVo.getUserKey();
		String ckno = ckmonVo.getCkno();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		List<FormuleBean> list = accumAllBrnchService.getselectclassCk(curUser.getProd(), ckno);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	
	/**
	 * 账户信息统计
	 * @param req
	 * @param ckmonVo
	 * @return
	 */
	@RequestMapping(value="/accum/accuminfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getZhangHuAccum(HttpServletRequest req, @RequestBody CkmonitorVo ckmonVo){
		String userKey = ckmonVo.getUserKey();
		String date = ckmonVo.getDate();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		// orgn:用户机构，date:日期
		List<AccinfoMonitorBean> list = accumAllBrnchService.getAccinfoList(curUser.getOrgn(), date);
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "暂无符合条件记录");
		}
	}
	
	/**
	 * 交易信息统计
	 * @param req
	 * @param ckmonVo
	 * @return
	 */
	@RequestMapping(value="/accum/accumtrans.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getTranAccum(HttpServletRequest req, @RequestBody CkmonitorVo ckmonVo){
		String dateBegin = ckmonVo.getDateBegin();
		String dateEnd = ckmonVo.getDateEnd();
		Trd_ogcd trd_ogcd = ckmonVo.getTrd_ogcd();
		List<ChangeInfoList> list = accumAllBrnchService.getChangeInfo(dateBegin, dateEnd, trd_ogcd.getOgcd(), trd_ogcd.getLeve(), trd_ogcd.getOgnm());
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "暂无符合条件记录");
		}
	}
	/**
	 * 签约信息统计
	 * @param req
	 * @param ckmonVo
	 * @return
	 */
	@RequestMapping(value="/accum/accumQY.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getQianYueAccum(HttpServletRequest req, @RequestBody CkmonitorVo ckmonVo){
		String userKey = ckmonVo.getUserKey();
		String trdtbegin = ckmonVo.getDataBegin();
		log.info("日期："+trdtbegin);
		log.info("id："+userKey);
		PageInfo<QYInfoList> page = accumAllBrnchService.getQYAccumuRegTrade(ckmonVo.getPageNo(), ckmonVo.getPageSize(), trdtbegin, userKey);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
}
