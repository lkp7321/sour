package com.ylxx.fx.service.impl.pere.PereYh;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.CustLevelFavRuleVo;
import com.ylxx.fx.core.mapper.pere.PereYh.PereCustLevelFavruleMapper;
import com.ylxx.fx.service.pere.PereYh.IPereCustLevelFavruleService;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;

@Service("pereCustLevelFavruleService")
public class PereCustLevelFavruleServiceImpl implements IPereCustLevelFavruleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PereCustLevelFavruleServiceImpl.class);
	@Resource
	private PereCustLevelFavruleMapper  pereCustLevelFavruleMapper;
	@Override
	public String addCustLevelFavRule(CustLevelFavRuleVo addCustLevelFavRuleVo, CurrUser curUser) {
		String flag="";
		try {
			pereCustLevelFavruleMapper.addCustLevelFavRule(addCustLevelFavRuleVo);
			flag="true";
		} catch (Exception e) {
			flag="false";
		}
		if(flag.equals("true")){
			LOGGER.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加机构优惠：\n机构号" + addCustLevelFavRuleVo.getOgcd()
					+ "\n买入优惠:" + addCustLevelFavRuleVo.getByds() + "\n卖出优惠:" + addCustLevelFavRuleVo.getSlds() );
		}else{
			LOGGER.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加机构优惠：\n机构号" +  addCustLevelFavRuleVo.getOgcd()
					+ "\n买入优惠:" + addCustLevelFavRuleVo.getByds() + "\n卖出优惠:" + addCustLevelFavRuleVo.getSlds() );
		}
		return flag;
	}
	@Override
	public String deleteCustLevelFavRules( CustLevelFavRuleVo deleteCustLevelFavRulesVo) {
		Trd_FavruleOgcd favruleOgcd = null;
		for (int i = 0; i < deleteCustLevelFavRulesVo.getChlist().size(); i++) {
			favruleOgcd = (Trd_FavruleOgcd) deleteCustLevelFavRulesVo.getChlist()
					.get(i);
			try {
				pereCustLevelFavruleMapper.deleteCustLevelFavRules(deleteCustLevelFavRulesVo.getOgcd(), favruleOgcd.getExnm(),deleteCustLevelFavRulesVo.getLevel());
			} catch (Exception e) {
				return "false";
			}
		}
		return "true";
	}
	@Override
	public String exist(String exnm,String level,String ogcd) {
		return	pereCustLevelFavruleMapper.exist(exnm,level,ogcd);
	}
	@Override
	public String updateRuleByCustLevels(CustLevelFavRuleVo custLevelFavRuleVo,
		CurrUser curUser) {
		String flag = "";
		Trd_FavruleOgcd favruleOgcd = null;
	
		for (int i = 0; i < custLevelFavRuleVo.getChlist().size(); i++) {
			favruleOgcd = (Trd_FavruleOgcd) custLevelFavRuleVo.getChlist()
					.get(i);
			if (custLevelFavRuleVo.getByds().trim().equals("")) {
				custLevelFavRuleVo.setByds(favruleOgcd.getByds());
			} else {
				custLevelFavRuleVo.setByds(custLevelFavRuleVo
						.getByds());
			}
			if (custLevelFavRuleVo.getSlds().trim().equals("")) {
				custLevelFavRuleVo.setSlds(favruleOgcd.getSlds());
			} else {
				custLevelFavRuleVo.setSlds(custLevelFavRuleVo
						.getSlds());
			}
			if (custLevelFavRuleVo.getBeginDate().trim().equals("")) {
				custLevelFavRuleVo.setBeginDate(favruleOgcd.getStdt());
			} else {
				custLevelFavRuleVo.setBeginDate(custLevelFavRuleVo
						.getBeginDate());
			}
			if (custLevelFavRuleVo.getEndDate().trim().equals("")) {
				custLevelFavRuleVo.setEndDate(favruleOgcd.getEddt());
			} else {
				custLevelFavRuleVo.setEndDate(custLevelFavRuleVo
						.getEndDate());
			}
			try {
				pereCustLevelFavruleMapper.updateRuleByCustLevels(custLevelFavRuleVo,favruleOgcd.getExnm());
				flag = "true";
			} catch (Exception e) {
				flag = "false";
			}
		}
		
		if(flag.equals("true")){
			LOGGER.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加机构优惠：\n机构号" + favruleOgcd.getOgcd()
					+ "\n币种:" + favruleOgcd.getExnm() + "\n买入优惠:"
					+ favruleOgcd.getByds() + "\n卖出优惠:" + favruleOgcd.getSlds());
		}else{
			LOGGER.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加机构优惠：\n机构号" + favruleOgcd.getOgcd()
					+ "\n币种:" + favruleOgcd.getExnm() + "\n买入优惠:"
					+ favruleOgcd.getByds() + "\n卖出优惠:" + favruleOgcd.getSlds());
		}		
		return flag;
		
		
	
	}
	@Override
	public List<Trd_FavruleOgcd> getCustFavRuleByOgcd(String ogcd,String level) {
				return pereCustLevelFavruleMapper.getCustFavRuleByOgcd(ogcd, level);
	}
	
	
	
	
	
}
