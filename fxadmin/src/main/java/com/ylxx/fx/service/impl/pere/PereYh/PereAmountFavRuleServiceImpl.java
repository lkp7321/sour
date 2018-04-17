package com.ylxx.fx.service.impl.pere.PereYh;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.PereAmountFavRuleVo;
import com.ylxx.fx.core.mapper.pere.PereYh.PereAmountFavRuleMapper;
import com.ylxx.fx.service.pere.PereYh.IPereAmountFavRuleService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;


@Service("pereAmountFavRuleService")
public class PereAmountFavRuleServiceImpl implements IPereAmountFavRuleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PereAmountFavRuleServiceImpl.class);
	@Resource
	private PereAmountFavRuleMapper pereAmountFavRuleMapper;
	@Override
	public String addAmountRuleByAmount(PereAmountFavRuleVo pereAmountFavRuleVo) {
		
		String result = "";
		
		String content=pereAmountFavRuleMapper.exist(pereAmountFavRuleVo.getOgcd(),pereAmountFavRuleVo.getMin(), pereAmountFavRuleVo.getMax());
		
		if(null!=content){
		result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "当前币种已存在");
			return result;	
		}
		
		
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(pereAmountFavRuleVo.getUserKey());
		boolean flag = false;
		try {
			pereAmountFavRuleMapper.addAmountRuleByAmount(pereAmountFavRuleVo);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "添加成功");
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "添加失败");
			flag = false;
		}
		if(flag){
			LOGGER.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加机构优惠：\n机构号" + pereAmountFavRuleVo.getOgcd()
					+ "\n买入优惠:" + pereAmountFavRuleVo.getByds() + "\n卖出优惠:" + pereAmountFavRuleVo.getSlds() );
		}else{
			LOGGER.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加机构优惠：\n机构号" + pereAmountFavRuleVo.getOgcd()
					+ "\n买入优惠:" + pereAmountFavRuleVo.getByds() + "\n卖出优惠:" + pereAmountFavRuleVo.getSlds() );
		}
		return result;
	}
	@Override
	public String deleteFavrRuleByAmount(PereAmountFavRuleVo pereAmountFavRuleVo) {
		String result = "";
		try {
			pereAmountFavRuleMapper.deleteFavrRuleByAmount(pereAmountFavRuleVo);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "删除失败");
		}
		return result;
	}
	@Override
	public String updateAmountFavRuleByAmount(PereAmountFavRuleVo pereAmountFavRuleVo) {
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(pereAmountFavRuleVo.getUserKey());
		String result = "";
		boolean flag = false;
		try {
			pereAmountFavRuleMapper.updateAmountFavRuleByAmount(pereAmountFavRuleVo);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "修改成功");
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "修改失败");
			flag = false;
		}
		if(flag){
			LOGGER.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加机构优惠：\n机构号" + pereAmountFavRuleVo.getOgcd()
					+ "\n买入优惠:" + pereAmountFavRuleVo.getByds() + "\n卖出优惠:" + pereAmountFavRuleVo.getSlds() );
		}else{
			LOGGER.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加机构优惠：\n机构号" + pereAmountFavRuleVo.getOgcd()
					+ "\n买入优惠:" + pereAmountFavRuleVo.getByds() + "\n卖出优惠:" + pereAmountFavRuleVo.getSlds() );
		}
		return result;
	}
	@Override
	public String getAmountFavRuleByOgcd(String ogcd,Integer pageNo,Integer pageSize) {
		String result = "";
		pageNo =pageNo ==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo, pageSize);
		try {
		List<HashMap<String, String>> list = pereAmountFavRuleMapper.getAmountFavRuleByOgcd(ogcd);
		if(list.size() > 0){
			PageInfo<HashMap<String,String>> page=new PageInfo<HashMap<String,String>>(list);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		}else{
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询无记录");
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询无记录");
		}
		return result;
	}
	@Override
	public String getAmountFavRuleLastData(String ogcd) {
		String result = "";
		try {
			List<HashMap<String, String>> list = pereAmountFavRuleMapper.getAmountFavRuleByOgcd(ogcd);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(),JSON.toJSONString(list.get(list.size()-1)));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询无记录");
		}
		
		return result;
	}

	

}
