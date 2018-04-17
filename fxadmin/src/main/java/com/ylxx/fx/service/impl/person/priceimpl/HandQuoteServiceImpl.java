package com.ylxx.fx.service.impl.person.priceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.HandPriceListBean;
import com.ylxx.fx.core.mapper.person.price.HandQuoteMapper;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.service.person.price.IHandQuoteService;
import com.ylxx.fx.service.po.HandQuoteVoBean;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("handQuoteService")
public class HandQuoteServiceImpl implements IHandQuoteService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HandQuoteServiceImpl.class);
	
	@Resource
	private HandQuoteMapper handQuoteMapper;
	@Resource
	private PdtRParaMapper pdtRParaMapper;
	
	//手工报价配置表
	public String selectHandQuoteList(String ptid,String stfg){
		String result = "";
		try {
			List<HandQuoteVoBean> hqvBeans = handQuoteMapper.selectHandQuoteList(ptid.trim(), stfg.trim());
			if (hqvBeans!=null&&hqvBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(hqvBeans));				
			}else if (hqvBeans!=null&&hqvBeans.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//修改手工报价录入表
	public String updateProduct(String userKey,String prod,HandQuoteVoBean hqVoBean) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P004");
		curUser.setCurIP("127.0.0.1");*/
		
		String result = "";
		boolean bo = false;
		List<HandQuoteVoBean> hqvBeans = new ArrayList<HandQuoteVoBean>();
		try {
			//更新报价和审核状态
			bo = handQuoteMapper.updateProductVa(prod, hqVoBean);
			String cxfg=hqVoBean.getCxfg().equals("1")?"汇":"钞";
			/*String ocfg = hqVoBean.getOcfg();
			if (ocfg!=null&&"".equals(ocfg)) {
				ocfg = hqVoBean.getOcfg().equals("0")?"启用":"停用";
			}*/
			String nowtime = DataTimeClass.getCurDateTime();
			//hqVoBean.setStfg("0");
			if (bo) {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:"+ curUser.getCurIP() 
						+ " 登录产品:" + curUser.getProd()
						+ "修改" + prod + "手工报价:价格类型" + hqVoBean.getTpfg()
						+ "期限:" + hqVoBean.getTerm() + "钞汇标志:"
						+ cxfg + "币别对:" + hqVoBean.getExnm() + "买入价:" +
						hqVoBean.getNeby() + "卖出价:" + hqVoBean.getNesl() + 
						"中间价:" + hqVoBean.getNemd() 
						+ "成功!时间:" + nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("手工报价");
				loginfo.setRemk("修改");
				loginfo.setVold("登录产品:" + curUser.getProd() + "修改"
						+ prod + "手工报价:价格类型" + hqVoBean.getTpfg() + "期限:"
						+ hqVoBean.getTerm() + " 钞汇标志:" + cxfg
						+ "币别对:" + hqVoBean.getExnm() + "买入价:" + hqVoBean.getNeby() 
						+ "卖出价:" + hqVoBean.getNesl() + "中间价:" + hqVoBean.getNemd());
				loginfo.setVnew("成功");
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
				hqVoBean.setPrcd(prod);
				hqvBeans.add(hqVoBean);
				//插入手工报价流水表insertHandQuoteList
				boolean bl = insertHandQuoteList(curUser.getUsnm(),hqvBeans,"修改");
				if (bl) {
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null); 
				}else {
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:"+ curUser.getCurIP() 
						+ " 登录产品:" + curUser.getProd()
						+ "修改" + prod + "手工报价:价格类型" + hqVoBean.getTpfg()
						+ "期限:" + hqVoBean.getTerm() + "钞汇标志:"
						+ cxfg + "币别对:" + hqVoBean.getExnm() + "买入价:" +
						hqVoBean.getNeby() + "卖出价:" + hqVoBean.getNesl() + 
						"中间价:" + hqVoBean.getNemd() 
						+ "失败!时间:" + nowtime);
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	//修改手工报价状态
	public String updateHandQuoteState(String userKey,String prod,
			List<HandQuoteVoBean> hqVoList,String labnm){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P004");
		curUser.setCurIP("127.0.0.1");*/
		
		String result = "";
		boolean bo = false;
		try {
			for (int i = 0; i < hqVoList.size(); i++) {
				hqVoList.get(i).setPrcd(prod);
				if(labnm.equals("提交")||labnm.equals("全部提交")){
					hqVoList.get(i).setStfg("0");
				}else if (labnm.equals("复核")||labnm.equals("全部复核")){
					hqVoList.get(i).setStfg("1");
				}else if(labnm.equals("未通过")||labnm.equals("全部未通过")){
					hqVoList.get(i).setStfg("2");
				}				
			}
			try {
				//用foreach遍历数组 更新表
				handQuoteMapper.updateHandQuoteState(hqVoList);
				bo = true;
				LOGGER.info("更新状态返回的标识为："+bo);
				if (bo) {
					if(labnm.equals("复核")||labnm.equals("全部复核")){
						int num = 0;
						HandQuoteVoBean hqVo;
						for (int i = 0; i < hqVoList.size(); i++) {
							hqVo = hqVoList.get(i);
							boolean b = handQuoteMapper.updateHandprice(prod, hqVo);
							LOGGER.info("更新报价平台返回的标识为:"+b);
							if (b) {
								num++;
							}else {
								LOGGER.error("更新报价平台返回的标识失败!");
								return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
							}
						}
						LOGGER.info("待更新总条数为:"+hqVoList.size()+";报价平台影响条数为："+num);
					}
					//插入手工报价流水表insertHandQuoteList
					boolean bl = insertHandQuoteList(curUser.getUsnm(),hqVoList,labnm);
					LOGGER.info("插入手工报价流水表:"+bl);
					if (bl) {
						result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null); 
					}else {
						return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
					}
				}else {
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			} catch (Exception e) {
				LOGGER.error("更新手工报价状态失败!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	//插入手工报价流水表
	public boolean insertHandQuoteList(String user,List<HandQuoteVoBean> hqVoList,String labnm){
		boolean bo = false;
		Random random=new Random();
		String bcfg = "";
		HandPriceListBean hpList = null;
		for (int i = 0; i < hqVoList.size(); i++) {
			hpList = new HandPriceListBean();
			String currDateTime = DataTimeClass.getCurDateTime();
			String uuid=DataTimeClass.getFormatCurDateTime()+random.nextInt(10);
			String pro=hqVoList.get(i).getPrcd().trim();
			hpList.setTbpk(uuid);
			hpList.setMkid(hqVoList.get(i).getMkid().trim());
			hpList.setTpfg(hqVoList.get(i).getTpfg().trim());
			hpList.setTerm(hqVoList.get(i).getTerm().trim());
			hpList.setExnm(hqVoList.get(i).getExnm().trim());
			hpList.setCxfg(hqVoList.get(i).getCxfg().trim());
			hpList.setExcd(hqVoList.get(i).getExcd().trim());
			BigDecimal neby = hqVoList.get(i).getNeby();
			hpList.setNeby(neby);
			BigDecimal nesl = hqVoList.get(i).getNesl();
			hpList.setNesl(nesl);
			BigDecimal nemd = hqVoList.get(i).getNemd();
			hpList.setNemd(nemd);
			hpList.setPrcd(pro);
			if (pro.equals("P001")||pro.equals("P002")) {
				bcfg = "2";
			}else if (pro.equals("P004")) {
				if(hqVoList.get(i).getBcfg().equals("分行价")){
					bcfg="0";
				}else if(hqVoList.get(i).getBcfg().equals("客户价")){
					bcfg="1";
				}
			}
			hpList.setBcfg(bcfg);
			hpList.setUsnm(user);
			hpList.setMdtm(currDateTime);
			hpList.setMdfg(labnm);
			//插入手工报价流水表
			boolean boole = false;
			try {
				boole = handQuoteMapper.insertHandQuoteList(hpList);
				if (boole) {
					LOGGER.info("插入手工报价流水表成功!");
				}else {
					LOGGER.error("插入手工报价流水表失败!");
				}
				if (boole&&i==hqVoList.size()-1) {
					bo = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bo;		
	}
	//手工报价的价格源公告板
	public String selectHandPriceAfficheList(String ptid){
		String result = "";
		try {
			List<HandQuoteVoBean> hqvBeans = handQuoteMapper.selectHandPriceAfficheList(ptid);
			if (hqvBeans!=null&&hqvBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(hqvBeans));				
			}else if (hqvBeans!=null&&hqvBeans.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//启用
	public String openHandQuote(String prod,List<HandQuoteVoBean> hqVos){
		String result = "";
		String ocfg = "0";		
		boolean bo = false;
		HandQuoteVoBean hqVo = null;
		if (prod.equals("P004")) {
			if (hqVos.size()>0&&hqVos.get(0).getOcfg().equals("启用")) {
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
			}
			bo = process(prod,"0");
			if (bo) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		}else if (prod.equals("P001")||prod.equals("P002")) {
			for (int i = 0; i < hqVos.size(); i++) {
				hqVo = new HandQuoteVoBean();
				hqVo = hqVos.get(i);
				try {
					bo = handQuoteMapper.updateHandpriceState(prod, hqVo, ocfg);
					if (bo) {
						LOGGER.info("开启手工报价成功!");
						if (i==hqVos.size()-1) {
							LOGGER.info("所选记录全部启用成功!");
							result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
						}
					}else {
						LOGGER.error("开启手工报价失败!");
						return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}
		}
		return result;
	}
	public boolean process(String prod,String flag){
		Boolean bo = false;
		try {
			List<String> ptids = handQuoteMapper.getUpdatePtid();//M9002,M9003
			if (ptids.size()!=2) {
				LOGGER.info("手工报价获取互斥产品编号失败!");
				return false;
			}
			if (!handQuoteMapper.updateCmmpdtparaForDelete()) {
				LOGGER.info("手工报价重置市场信息表失败!");
				return false;
			}
			if (!handQuoteMapper.updateCmmpdtparaForSet(ptids.get(0), ptids.get(1))) {
				LOGGER.info("手工报价修改市场信息表失败!");
				return false;
			}
			String mkid = handQuoteMapper.getUpdateMkid();//M9003
			if (mkid==null) {
				LOGGER.info("手工报价获取互斥市场编号失败!");
				return false;
			}
			if (!handQuoteMapper.updateMktinfoForDelete()) {
				LOGGER.info("手工报价重置(非)价格加工类配置表失败!");
				return false;
			}
			if (!handQuoteMapper.updateMktinfoForSet(mkid)) {
				LOGGER.info("手工报价修改(非)价格加工类配置表失败!");
				return false;
			}
			if (!handQuoteMapper.updateHandpriceState2(prod, flag)) {
				LOGGER.info("修改对应的手工报价公告板模板表失败!");
				return false;
			}
			bo = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bo;
	}
	//停用
	public String closeHandQuote(String prod,List<HandQuoteVoBean> hqVos){
		String result = "";
		String ocfg = "1";		
		boolean bo = false;
		HandQuoteVoBean hqVo = null;
		if (prod.equals("P004")) {
			if (hqVos.size()>0&&hqVos.get(0).getOcfg().equals("停用")) {
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
			}
			bo = process(prod,"1");
			if (bo) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		}else if (prod.equals("P001")||prod.equals("P002")) {
			for (int i = 0; i < hqVos.size(); i++) {
				hqVo = new HandQuoteVoBean();
				hqVo = hqVos.get(i);
				try {
					bo = handQuoteMapper.updateHandpriceState(prod, hqVo, ocfg);
					if (bo) {
						LOGGER.info("停用手工报价成功!");
						if (i==hqVos.size()-1) {
							LOGGER.info("所选记录全部停用成功!");
							result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
						}
					}else {
						LOGGER.error("停用手工报价失败!");
						return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}
		}
		return result;
	}
	//手工报价操作表
	public String queryHandPriceOperate(String prod,String usnm,String optm, Integer pageNo, Integer pageSize){
		List<HandQuoteVoBean> hqVos =null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			String optmfrom = "";
			String optmto = "";
			if (optm!=null&&!optm.equals("")) {
				optmfrom=optm+" 00:00:00";
				optmto=optm+" 23:59:59";
			}			
			hqVos = handQuoteMapper.selectHandQuoteOperateList(prod, usnm, 
					optmfrom,optmto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), new PageInfo<HandQuoteVoBean>(hqVos));			
	}
	
	/*//测试用
	public List<HandQuoteVoBean> getHandQuoteVoBeanlist(String ptid) throws Exception{
		List<HandQuoteVoBean> list = handQuoteMapper.selectHandPriceAfficheList(ptid);
		return list;
	}*/
}
