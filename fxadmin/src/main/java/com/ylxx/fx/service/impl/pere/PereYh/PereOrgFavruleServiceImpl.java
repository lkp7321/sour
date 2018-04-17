package com.ylxx.fx.service.impl.pere.PereYh;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.AddOrgFavRuleVo;
import com.ylxx.fx.core.domain.DeleteOrgFavrRuleVo;
import com.ylxx.fx.core.domain.ExnmVo;
import com.ylxx.fx.core.domain.SelectFavRuleOgcdVo;
import com.ylxx.fx.core.domain.UpdateFavRuleByOgcdsVo;
import com.ylxx.fx.core.mapper.pere.PereYh.PereOrgFavruleMapper;
import com.ylxx.fx.service.pere.PereYh.IPereOrgFavruleService;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;

@Service("pereOrgFavruleService")
public class PereOrgFavruleServiceImpl implements IPereOrgFavruleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PereOrgFavruleServiceImpl.class);
	@Resource
	private PereOrgFavruleMapper pereOrgFavruleMapper;

	@Override
	public String getOrgn(String userKey) {
		return pereOrgFavruleMapper.getOrgn(userKey);
	}

	@Override
	public String addOrgFavRule(AddOrgFavRuleVo addOrgFavRuleVo) {
		String flag = "";
		try {
			pereOrgFavruleMapper.addOrgFavRule(addOrgFavRuleVo);
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	@Override
	public String deleteOrgFavrRule(DeleteOrgFavrRuleVo deleteOrgFavrRuleVo) {
		Trd_FavruleOgcd favruleOgcd = null;
		for (int i = 0; i < deleteOrgFavrRuleVo.getChlist().size(); i++) {
			favruleOgcd = (Trd_FavruleOgcd) deleteOrgFavrRuleVo.getChlist()
					.get(i);
			try {
				pereOrgFavruleMapper.deleteOrgFavrRule(
						deleteOrgFavrRuleVo.getOgcd(), favruleOgcd.getExnm());
			} catch (Exception e) {
				return "false";
			}
		}
		return "true";
	}

	@Override
	public String updateFavRuleByOgcds(
			UpdateFavRuleByOgcdsVo uspdateFavRuleByOgcdsVo,CurrUser curUser) {
		String flag = "";
		Trd_FavruleOgcd favruleOgcd = null;
	
		for (int i = 0; i < uspdateFavRuleByOgcdsVo.getChlist().size(); i++) {
			favruleOgcd = (Trd_FavruleOgcd) uspdateFavRuleByOgcdsVo.getChlist()
					.get(i);
			if (uspdateFavRuleByOgcdsVo.getByds().trim().equals("")) {
				uspdateFavRuleByOgcdsVo.setByds(favruleOgcd.getByds());
			} else {
				uspdateFavRuleByOgcdsVo.setByds(uspdateFavRuleByOgcdsVo
						.getByds());
			}
			if (uspdateFavRuleByOgcdsVo.getSlds().trim().equals("")) {
				uspdateFavRuleByOgcdsVo.setSlds(favruleOgcd.getSlds());
			} else {
				uspdateFavRuleByOgcdsVo.setSlds(uspdateFavRuleByOgcdsVo
						.getSlds());
			}
			if (uspdateFavRuleByOgcdsVo.getBeginDate().trim().equals("")) {
				uspdateFavRuleByOgcdsVo.setBeginDate(favruleOgcd.getStdt());
			} else {
				uspdateFavRuleByOgcdsVo.setBeginDate(uspdateFavRuleByOgcdsVo
						.getBeginDate());
			}
			if (uspdateFavRuleByOgcdsVo.getEndDate().trim().equals("")) {
				uspdateFavRuleByOgcdsVo.setEndDate(favruleOgcd.getEddt());
			} else {
				uspdateFavRuleByOgcdsVo.setEndDate(uspdateFavRuleByOgcdsVo
						.getEndDate());
			}
			try {
				pereOrgFavruleMapper
						.updateFavRuleByOgcds(uspdateFavRuleByOgcdsVo,favruleOgcd.getExnm());
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
	public List<Trd_FavruleOgcd> getFavruleByOgcd(String ogcd) {
		
		return pereOrgFavruleMapper.getFavruleByOgcd(ogcd);
		 
	}

	@Override
	public List<Tranlist> selectFavRuleOgcd(SelectFavRuleOgcdVo selectFavRuleOgcdVo) {
		
		List<Tranlist> bhidList =null;
		
	
		try {
			bhidList=pereOrgFavruleMapper.selectFavRuleOgcd(selectFavRuleOgcdVo.getOgcdtxt(),selectFavRuleOgcdVo.getOgnmtxt());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bhidList;
	}

	@Override
	public List <ExnmVo>getAllEXNM() {
		return pereOrgFavruleMapper.getAllEXNM();
		
	}

	@Override
	public String exist(String exnm,String ogcd) {
		return	pereOrgFavruleMapper.exist(exnm,ogcd);
		
	}

}
