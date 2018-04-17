package com.ylxx.fx.service.impl.pere.PereYh;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.PereCardFavRuleVo;
import com.ylxx.fx.core.mapper.pere.PereYh.PereCardFavRuleMapper;
import com.ylxx.fx.service.pere.PereYh.IPereCardFavRuleService;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("pereCardFavRuleService")
public class PereCardFavRuleSeriviceImpl implements IPereCardFavRuleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PereCardFavRuleSeriviceImpl.class);
	@Resource
	private PereCardFavRuleMapper pereCardFavRuleMapper;

	//卡bin优惠更新保存
	public String addCardRuleByCard(PereCardFavRuleVo pereCardFavRuleVo) {
		String result="";
		String content=pereCardFavRuleMapper.exist(pereCardFavRuleVo.getOgcd(), pereCardFavRuleVo.getCbin());
		
		if(null!=content){
		result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "当前币种已存在");
			return result;	
		}
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(pereCardFavRuleVo.getUserkey());
		try {
			pereCardFavRuleMapper.addCardRuleByCard(pereCardFavRuleVo);
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "添加成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "添加失败");
		}
		return result;
	}

	@Override
	public String updateRuleByCards(PereCardFavRuleVo pereCardFavRuleVo,
			CurrUser curUser) {
		String flag = "";
		Trd_FavruleOgcd favruleOgcd = null;
	
		for (int i = 0; i < pereCardFavRuleVo.getChlist().size(); i++) {
			favruleOgcd = (Trd_FavruleOgcd) pereCardFavRuleVo.getChlist()
					.get(i);
			if (pereCardFavRuleVo.getByds().trim().equals("")) {
				pereCardFavRuleVo.setByds(favruleOgcd.getByds());
			} else {
				pereCardFavRuleVo.setByds(pereCardFavRuleVo
						.getByds());
			}
			if (pereCardFavRuleVo.getSlds().trim().equals("")) {
				pereCardFavRuleVo.setSlds(favruleOgcd.getSlds());
			} else {
				pereCardFavRuleVo.setSlds(pereCardFavRuleVo
						.getSlds());
			}
			if (pereCardFavRuleVo.getStdt().trim().equals("")) {
				pereCardFavRuleVo.setStdt(favruleOgcd.getStdt());
			} else {
				pereCardFavRuleVo.setStdt(pereCardFavRuleVo
						.getStdt());
			}
			if (pereCardFavRuleVo.getEddt().trim().equals("")) {
				pereCardFavRuleVo.setEddt(favruleOgcd.getEddt());
			} else {
				pereCardFavRuleVo.setEddt(pereCardFavRuleVo
						.getEddt());
			}
			try {
				pereCardFavRuleMapper
						.updateRuleByCards(pereCardFavRuleVo,favruleOgcd.getCbin());
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
	public String deleteCardFavRules(PereCardFavRuleVo pereCardFavRuleVo) {
		Trd_FavruleOgcd favruleOgcd = null;
		String result = "";
		boolean flag = false;
		for (int i = 0; i < pereCardFavRuleVo.getChlist().size(); i++) {
			favruleOgcd = (Trd_FavruleOgcd) pereCardFavRuleVo.getChlist()
					.get(i);
			try {
				pereCardFavRuleMapper.deleteCardFavRules(pereCardFavRuleVo.getOgcd(), favruleOgcd.getCbin());
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		}
		if(flag){
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "删除成功");
		}else{
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "删除失败");
		}
		return result;
}

	@Override
	public List<Trd_FavruleOgcd> getCardFavRuleByOgcd(String ogcd) {
	return 	pereCardFavRuleMapper.getCardFavRuleByOgcd(ogcd);
	}

}
