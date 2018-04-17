package com.ylxx.fx.service.impl.person.priceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.price.PdtChkParaMapper;
import com.ylxx.fx.service.person.price.IPdtChkParaService;
import com.ylxx.fx.service.po.PdtChkParaBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("pdtChkParaService")
public class PdtChkParaServiceImpl implements IPdtChkParaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PdtChkParaServiceImpl.class);
	
	@Resource
	private PdtChkParaMapper pdtChkParaMapper;
	
	
	/**
	 * 得到产品校验参数
	 */
	public String getChkList(String prod, Integer pageNo, Integer pageSize) {
		List<PdtChkParaBean> pdtChks = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			pdtChks = pdtChkParaMapper.selectAllPdtChkpara(prod.trim());
		} catch (Exception e) {
			e.printStackTrace();
		}		 
		PageInfo<PdtChkParaBean> page = new PageInfo<PdtChkParaBean>(pdtChks);
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), page);
	}
	//更新启用状态 启用0 停用1
	public String updateChk(String userkey,String prod,String usfg){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userkey);
		PdtChkParaBean pdt = null;
		try {
			//测试用
			List<PdtChkParaBean> pdtChks = pdtChkParaMapper.selectAllPdtChkpara(prod);
			LOGGER.info("测试用产品校验参数列表查询成功!");
			
			for (int i = 0; i < pdtChks.size(); i++) {
				pdt = new PdtChkParaBean();
				pdt.setTerm(pdtChks.get(i).getTerm().trim());
				pdt.setExnm(pdtChks.get(i).getExnm().trim());
				/*if (pdtChks.get(i).getTpfg().trim().equals("即期")) {
					pdt.setTpfg("SPT");
				}*/
				if (usfg.equals("启用")||usfg.equals("全部启用")) {
					pdt.setUsfg("0");
				}else if (usfg.equals("停用")||usfg.equals("全部停用")) {
					pdt.setUsfg("1");
				}
				pdt.setCxfg("2");
				boolean boo = pdtChkParaMapper.updatePriceusfgAll(prod.trim(), pdt);
				String nowtime = DataTimeClass.getCurDateTime();
				if (boo) {
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP() 
							+ " 登录产品:" + curUser.getProd()
							+ "更新"+prod+"产品校验参数使用标志:币别对:" + pdtChks.get(i).getExnm().trim()
							+ "标志为:" + pdt.getUsfg() + "成功!时间:" + nowtime); 
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd()
							+ "更新"+prod+"产品校验参数使用标志:币别对:" + pdtChks.get(i).getExnm().trim()
							+ "标志为:" + pdt.getUsfg() + "失败!时间:" + nowtime);
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
				if (i==pdtChks.size()-1 && boo) {
					//TODO Psocket.SendSocketB();
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
					//TODO Psocket.SendSocketB();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	
}
