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

import com.ylxx.fx.core.domain.ChannelFavRuleVo;
import com.ylxx.fx.service.pere.PereYh.IPereChannelFavRuleService;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;



@Controller
//@RequestMapping("fx")
public class PereChannelFavRuleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PereChannelFavRuleController.class);
	
	@Resource(name = "pereChannelFavRuleService")
	private	IPereChannelFavRuleService pereChannelFavRuleService;
	/**@author 韦任
	 * 添加渠道优惠
	 * @param ChannelFavRuleVo channelFavRuleVo
	 * @param 
	 * @return
	 */
//String userKey,String ogcd,String currency,String CHNL,String byds,String slds,String beginDate,String endDate){
	@RequestMapping(value="/addChannelFavRule.do",produces="application/json;charset=UTF-8")
	@ResponseBody	
	public String  addChannelFavRule(@RequestBody ChannelFavRuleVo channelFavRuleVo){
			
//			 ChannelFavRuleVo channelFavRuleVo=new  ChannelFavRuleVo();
//			 channelFavRuleVo.setOgcd("3300");
//			 channelFavRuleVo.setCurrency("EURRMB");
//			 channelFavRuleVo.setChnl("1001");
//			 channelFavRuleVo.setSlds("1");
//			 channelFavRuleVo.setByds("1");
//			 channelFavRuleVo.setBeginDate("20171115");
//			 channelFavRuleVo.setEndDate("20171115");
//				CurrUser curUser= new CurrUser();
//				curUser.setUsnm("weiren");
//				curUser.setProd("dfddff");
//				curUser.setCurIP("11566111555");
//				curUser.setProd("FSD");	
			 String result = "";
				String content=pereChannelFavRuleService.exist(channelFavRuleVo.getCurrency(),channelFavRuleVo.getOgcd(),channelFavRuleVo.getChnl());
				if(null!=content){
						result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "当前币种已存在");
					return result;
					
				}
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(channelFavRuleVo.getUserKey());		
				
				
			String flag="";
			//0001	SGDRMB	100	100	20160706	20160806
			try {
				 flag =pereChannelFavRuleService.addChannelFavRule(channelFavRuleVo, curUser);
				
			} catch (Exception db) {
				Exception exception=db;
				flag = "false";
			}
			if(flag.equals("true")){
				LOGGER.info("用户：" + curUser.getUsnm() + " \n登录IP:"
						+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
						+ "\n添加机构优惠：\n机构号" +channelFavRuleVo.getOgcd()
						+ "\n买入优惠:" + channelFavRuleVo.getByds() + "\n卖出优惠:" +channelFavRuleVo.getSlds());
				
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "添加成功");
				return result;
			}else{
				LOGGER.error("用户：" + curUser.getUsnm() + " \n登录IP:"
						+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
						+ "\n添加机构优惠：\n机构号" + channelFavRuleVo.getOgcd()
						+ "\n买入优惠:" + channelFavRuleVo.getByds() + "\n卖出优惠:" + channelFavRuleVo.getSlds());
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "添加失败 ");
			
				return result;		
				
			}
		 }
	
	/**@author 韦任
	 * 删除渠道优惠
	 * @param userKey
	 * @param calVo
	 * @return
	 */
	//String userKey, ArrayList chlist,String ogcd,String chnl
	@RequestMapping(value="/deleteChannelFavrRule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
		public String deleteChannelFavrRule(@RequestBody ChannelFavRuleVo channelFavRuleVo){
		 String result = "";	
			String flag=pereChannelFavRuleService.deleteChannelFavrRule(channelFavRuleVo);	
			if(flag.equals("true")){
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "删除成功");
					}
					else{
						
						result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "删除失败");
					}
				
				
				return result;
	
	}
	
	/**@AUTHOR 韦任
	 * 修改渠道优惠
	 * @PARAM UpdateFavRuleByOgcdsVo uspdateFavRuleByOgcdsVo
	 * @RETURN
	 */
	//String userKey, ArrayList chlist,String ogcd,String channel,String byds,String slds,String beginDate,String endDate
	@RequestMapping(value="/updateRuleByChannels.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateRuleByChannels(@RequestBody ChannelFavRuleVo channelFavRuleVo) {
		//@RequestBody ChannelFavRuleVo channelFavRuleVo
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(channelFavRuleVo.getUserKey());
		
		String result="";
		String flag=pereChannelFavRuleService.updateRuleByChannels(channelFavRuleVo, curUser);
		if(flag.equals("true")){
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "修改成功");
			
		}else{
			
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"修改失败");
		}
		return result;	
	}
	
	
	//根据机构号、渠道获取渠道优惠
	@RequestMapping(value="/getChannelFavRuleByOgcd.do",produces="application/json;charset=UTF-8")
	@ResponseBody
		public String getChannelFavRuleByOgcd(@RequestBody ChannelFavRuleVo channelFavRuleVo ) {
		String result="";
		//
//		 ChannelFavRuleVo channelFavRuleVo=new ChannelFavRuleVo();
//		 channelFavRuleVo.setPageNo(1);
//		 channelFavRuleVo.setPageSize(10);
//		 channelFavRuleVo.setOgcd("3300");
//		 channelFavRuleVo.setChnl("1001");
		
			String chnl=channelFavRuleVo.getChnl();
				
			String ogcd=channelFavRuleVo.getOgcd();
		 
			Integer pageNo=channelFavRuleVo.getPageNo();
			Integer pageSize=channelFavRuleVo.getPageSize();
			pageNo = pageNo == null?1:pageNo;
			pageSize = pageSize == null?10:pageSize;
			PageHelper.startPage(pageNo,pageSize);
			try {
				List<Trd_FavruleOgcd> linkedList = pereChannelFavRuleService.getChannelFavRuleByOgcd(ogcd, chnl);
				PageInfo<Trd_FavruleOgcd> page=new PageInfo<Trd_FavruleOgcd>(linkedList);
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}catch(Exception e) {
				e.printStackTrace();
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"无记录");
			}
			return result;	

		}
	
	
	
	
	
	
	
	
	
}
