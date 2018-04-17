package com.ylxx.fx.controller.pere.PereYh;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.AddOrgFavRuleVo;
import com.ylxx.fx.core.domain.DeleteOrgFavrRuleVo;
import com.ylxx.fx.core.domain.ExnmVo;
import com.ylxx.fx.core.domain.GetFavruleByOgcdVo;
import com.ylxx.fx.core.domain.SelectFavRuleOgcdVo;
import com.ylxx.fx.core.domain.UpdateFavRuleByOgcdsVo;
import com.ylxx.fx.service.pere.PereYh.IPereOrgFavruleService;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Controller
//@RequestMapping("fx")
public class PereOrgFavruleController {
private static final Logger LOGGER = LoggerFactory.getLogger(PereOrgFavruleController.class);
	
	@Resource(name="pereOrgFavruleService")
	private  IPereOrgFavruleService  pereOrgFavruleService;
	
	/**@author 韦任
	 * 获取机构
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getOrgn.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public  String getOrgn(@RequestBody String userKey){
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		Trd_FavruleOgcd trd_FavruleOgcd = new Trd_FavruleOgcd();
	    trd_FavruleOgcd.setOgcd(curUser.getOrgn());
		try {
			String exnm=pereOrgFavruleService.getOrgn(curUser.getOrgn());
			trd_FavruleOgcd.setExnm(exnm);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		String result = "";
		try {
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(trd_FavruleOgcd));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"无记录");
		} 
	return result;
	
	}	
	
	
	/**@author 韦任
	 * 添加结构优惠
	 * @param  AddOrgFavRuleVo addOrgFavRuleVo
	 * @return
	 */
	@RequestMapping(value="/addOrgFavRule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addOrgFavRule(@RequestBody AddOrgFavRuleVo addOrgFavRuleVo){
		String result = "";
		String content=pereOrgFavruleService.exist(addOrgFavRuleVo.getCurrency(),addOrgFavRuleVo.getOgcd());
	
		if(null!=content){
		result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "当前币种已存在");
			return result;	
		}
	   CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(addOrgFavRuleVo.getUserKey());
		String flag="";
		//0001	SGDRMB	100	100	20160706	20160806
		try {
			 flag =pereOrgFavruleService.addOrgFavRule(addOrgFavRuleVo);
		} catch (Exception db) {
			LOGGER.error(db.getMessage(), db);
			flag = "false";
		}
		if(flag.equals("true")){
			LOGGER.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加机构优惠：\n机构号" +addOrgFavRuleVo.getOgcd()
					+ "\n买入优惠:" + addOrgFavRuleVo.getByds() + "\n卖出优惠:" +addOrgFavRuleVo.getSlds());
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "添加成功");
			return result;
		}else{
			LOGGER.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加机构优惠：\n机构号" + addOrgFavRuleVo.getOgcd()
					+ "\n买入优惠:" + addOrgFavRuleVo.getByds() + "\n卖出优惠:" + addOrgFavRuleVo.getSlds());
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "添加失败 ");
			return result;
		}
	}
	
	/**@AUTHOR 韦任
	 * 删除机构优惠
	 * @PARAM DeleteOrgFavrRuleVo deleteOrgFavrRuleVo
	 * @RETURN
	 */
	@RequestMapping(value="/deleteOrgFavrRule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteOrgFavrRule(@RequestBody DeleteOrgFavrRuleVo deleteOrgFavrRuleVo){
		 String result = "";	
		String flag=pereOrgFavruleService.deleteOrgFavrRule(deleteOrgFavrRuleVo);	
		if(flag.equals("true")){
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "删除成功");
					
				}
				else{
					
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "删除失败");
				}
			
			
			return result;
	}
	/**@AUTHOR 韦任
	 * 修改机构优惠
	 * @PARAM UpdateFavRuleByOgcdsVo uspdateFavRuleByOgcdsVo
	 * @RETURN
	 */
	@RequestMapping(value="/updateFavRuleByOgcds.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateFavRuleByOgcds(@RequestBody UpdateFavRuleByOgcdsVo uspdateFavRuleByOgcdsVo) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(uspdateFavRuleByOgcdsVo.getUserKey());
		String result="";		
		String flag=pereOrgFavruleService.updateFavRuleByOgcds(uspdateFavRuleByOgcdsVo,curUser);
		if(flag.equals("true")){
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "修改成功");
			
		}else{
			
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"修改失败");
		}
			
		
		return result;	
	}
	
	/**@AUTHOR 韦任
	 * 查询机构优惠
	 * @PARAM String ogcd
	 * @RETURN
	 */
	@RequestMapping(value="/getFavruleByOgcd.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String  getFavruleByOgcd(@RequestBody GetFavruleByOgcdVo getFavruleByOgcdVo ) {
		String result="";	
		String ogcd=getFavruleByOgcdVo.getOgcd();
		Integer pageNo=getFavruleByOgcdVo.getPageNo();
		Integer pageSize=getFavruleByOgcdVo.getPageSize();
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		try {
			List<Trd_FavruleOgcd> linkedList = pereOrgFavruleService.getFavruleByOgcd(ogcd);
			PageInfo<Trd_FavruleOgcd> page=new PageInfo<Trd_FavruleOgcd>(linkedList);
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		}catch(Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"无记录");
		}
		return result;	
	}
	/**@AUTHOR 韦任
	 * 点击选择，弹出页面的查询
	 * @PARAM selectFavRuleOgcdVo
	 * @RETURN
	 */
	@RequestMapping(value="/selectFavRuleOgcd.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String  selectFavRuleOgcd(@RequestBody SelectFavRuleOgcdVo  selectFavRuleOgcdVo){
		Integer pageNo=selectFavRuleOgcdVo.getPageNo();
		Integer pageSize=selectFavRuleOgcdVo.getPageSize();
		pageNo = pageNo == null?2:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		List<Tranlist> ETBhidList = pereOrgFavruleService.selectFavRuleOgcd(selectFavRuleOgcdVo);
		String result="";		
		try {
			PageInfo<Tranlist> page=new PageInfo<Tranlist>(ETBhidList);
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"无记录");
		} 
		return result;	
	}	
	
	/**@AUTHOR 韦任
	 * 结售汇优惠获取币种
	 * @PARAM 
	 * @RETURN
	 */
	@RequestMapping(value="/getAllEXNM.do",produces="application/json;charset=UTF-8")
	@ResponseBody
		public String getAllEXNM() {
	     String result="";	
		try {
			List <ExnmVo>list=pereOrgFavruleService.getAllEXNM();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"无记录");
		} 
		return result;	
	}
	
}
