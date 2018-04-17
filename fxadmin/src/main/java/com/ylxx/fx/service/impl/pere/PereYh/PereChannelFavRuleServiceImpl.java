package com.ylxx.fx.service.impl.pere.PereYh;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.AddOrgFavRuleVo;
import com.ylxx.fx.core.domain.ChannelFavRuleVo;
import com.ylxx.fx.core.mapper.pere.PereYh.PereChannelFavRuleMapper;
import com.ylxx.fx.core.mapper.pere.PereYh.PereCustLevelFavruleMapper;
import com.ylxx.fx.service.pere.PereYh.IPereChannelFavRuleService;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;

@Service("pereChannelFavRuleService")
public class PereChannelFavRuleServiceImpl implements
		IPereChannelFavRuleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PereChannelFavRuleServiceImpl.class);
	@Resource
	private PereChannelFavRuleMapper pereChannelFavRuleMapper;
	@Override
	public String addChannelFavRule(ChannelFavRuleVo channelFavRuleVo,
			CurrUser curUser) {
		String flag = "";
		try {
			pereChannelFavRuleMapper.addChannelFavRule(channelFavRuleVo);
			return "true";
		} catch (Exception e) {
			return "false";
		}

	}
	@Override
	public String exist(String exnm,String ogcd, String chnl) {
		return	pereChannelFavRuleMapper.exist(exnm,ogcd,chnl);
	}
	@Override
	public String deleteChannelFavrRule(ChannelFavRuleVo channelFavRuleVo) {
		Trd_FavruleOgcd favruleOgcd = null;
		for (int i = 0; i < channelFavRuleVo.getChlist().size(); i++) {
			favruleOgcd = (Trd_FavruleOgcd) channelFavRuleVo.getChlist()
					.get(i);
			try {
				pereChannelFavRuleMapper.deleteChannelFavrRule(channelFavRuleVo.getOgcd(), favruleOgcd.getExnm(),channelFavRuleVo.getChnl());
			} catch (Exception e) {
				return "false";
			}
		}
		return "true";
	}
	@Override
	public String updateRuleByChannels(ChannelFavRuleVo channelFavRuleVo,
			CurrUser curUser) {
		String flag = "";
		Trd_FavruleOgcd favruleOgcd = null;
	
		for (int i = 0; i < channelFavRuleVo.getChlist().size(); i++) {
			favruleOgcd = (Trd_FavruleOgcd) channelFavRuleVo.getChlist()
					.get(i);
			if (channelFavRuleVo.getByds().trim().equals("")) {
				channelFavRuleVo.setByds(favruleOgcd.getByds());
			} else {
				channelFavRuleVo.setByds(channelFavRuleVo
						.getByds());
			}
			if (channelFavRuleVo.getSlds().trim().equals("")) {
				channelFavRuleVo.setSlds(favruleOgcd.getSlds());
			} else {
				channelFavRuleVo.setSlds(channelFavRuleVo
						.getSlds());
			}
			if (channelFavRuleVo.getBeginDate().trim().equals("")) {
				channelFavRuleVo.setBeginDate(favruleOgcd.getStdt());
			} else {
				channelFavRuleVo.setBeginDate(channelFavRuleVo
						.getBeginDate());
			}
			if (channelFavRuleVo.getEndDate().trim().equals("")) {
				channelFavRuleVo.setEndDate(favruleOgcd.getEddt());
			} else {
				channelFavRuleVo.setEndDate(channelFavRuleVo
						.getEndDate());
			}
			try {
				pereChannelFavRuleMapper.updateRuleByChannels(channelFavRuleVo,favruleOgcd.getExnm());
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
	public List<Trd_FavruleOgcd> getChannelFavRuleByOgcd(String ogcd,String chnl) {
		return pereChannelFavRuleMapper.getChannelFavRuleByOgcd(ogcd, chnl);
	}

}
