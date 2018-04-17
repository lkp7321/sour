package com.ylxx.fx.service.impl.pere.presysmanagerimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.CustFavVo;
import com.ylxx.fx.core.mapper.LogfileCmdMapper;
import com.ylxx.fx.core.mapper.pere.presysmanager.DfzfWaterMapper;
import com.ylxx.fx.service.pere.presysmanager.IDfzfWaterService;
import com.ylxx.fx.service.po.DfzfWater;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;
@Service("dfzfWaterService")
public class DfzfWaterServiceImpl implements IDfzfWaterService{
private static final Logger LOGGER = LoggerFactory.getLogger(DfzfWaterServiceImpl.class);
private static Map<String,String> extpMap = new HashMap<String,String> ();
private static Map<String,String> jhtpMap = new HashMap<String,String> ();
private static Map<String,String> shtpMap = new HashMap<String,String> ();
private static Map<String,Map<String,String>> extrMap = new HashMap<String,Map<String,String>> ();
private static Map<String,String> bytpMap  = new HashMap<String,String> ();
	@Resource
	private DfzfWaterMapper dfzfWaterMapper;
	@Resource(name="logfileCmdMapper")
	private LogfileCmdMapper logfileCmdMapper;
	@Override
	public String selcDfzfWater(String dgfieldbegin, String dgfieldend, String comStcd, Integer pageNo,
			Integer pageSize) {
		List<CustFavVo> tradeList = new ArrayList<CustFavVo>();
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
	String result="";
		try {
			List<DfzfWater> list=dfzfWaterMapper.selcDfzfWater(dgfieldbegin,dgfieldend,comStcd);
			if(list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				CustFavVo custFavVo=new CustFavVo(); 
				//此处将TRSN的值赋值到LCNO中
				custFavVo.setLcno(list.get(i).getTrsn());
				custFavVo.setTrtp(list.get(i).getTrtp());
				custFavVo.setCont(list.get(i).getCont());
				custFavVo.setIdtp(list.get(i).getIdtp());
				custFavVo.setIdno(list.get(i).getIdno());
				custFavVo.setCunm(list.get(i).getCunm());
				custFavVo.setCuac(list.get(i).getCuac());
				String sourceEXTP=list.get(i).getExtp();
				String reverseEXTP=(String) DfzfWaterServiceImpl.initEXTPMap().get(sourceEXTP);
				if(null!=reverseEXTP&&!reverseEXTP.equals("")){
					sourceEXTP=reverseEXTP;
				}
				custFavVo.setExtp(sourceEXTP);
				String sourceEXTR=list.get(i).getExtr();
				String trtp=list.get(i).getJstp();
				Map<String,String> extrMap=(HashMap<String, String>) DfzfWaterServiceImpl.initEXTRMap().get(trtp);
				String reverseEXTR=(String) extrMap.get(sourceEXTR);
				if(null!=reverseEXTR&&!reverseEXTR.equals("")){
					sourceEXTR=reverseEXTR;
				}
				custFavVo.setExtr(sourceEXTR);
				//此处将BYTP 的值赋值到COPYCOUT中
				String sourceBYTP=list.get(i).getBytp();
				String reverseBYTP=(String) DfzfWaterServiceImpl.initBYTPMap().get(sourceBYTP);
				if(null!=reverseBYTP && !reverseBYTP.equals("")){
					sourceBYTP=reverseBYTP;
				}
				custFavVo.setCopycout(sourceBYTP);
				
				custFavVo.setFonm(list.get(i).getFonm());
				//此处将FOAM值赋值到FRAM中
				//BigDecimal bg=new BigDecimal(list.get(i).get("FOAM"));
				custFavVo.setFram(list.get(i).getFoam());
				//此处将CYAM的值赋值到CASH中
				custFavVo.setCash(list.get(i).getCyam());
				//此处将AKPC的值赋值到EXPC中
				custFavVo.setExpc(list.get(i).getAkpc());
				custFavVo.setCxfg(list.get(i).getCxfg());
				tradeList.add(custFavVo);
			}
			
			PageInfo<CustFavVo> page=new PageInfo<CustFavVo>(tradeList);
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}else {
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(),"查询无结果");
			}
		}catch(Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询异常");
		}
		
		return result;
	}
	
	//初始化结售汇交易种类MAP，转化编码时从内存中取
		public static Map<String,String> initEXTPMap(){
			extpMap.put("100", "结汇经常项目");
			extpMap.put("200", "结汇资本与金融项目");
			extpMap.put("300", "售汇经常项目");
			extpMap.put("400", "售汇资本与金融项目");
			return extpMap;
		}
		//初始化结售汇交易项目代码MAP，转化编码时从内存中取
		public static Map<String,Map<String,String>> initEXTRMap(){
			//结汇项目代码
			jhtpMap.put("100",	"经常项目");
			jhtpMap.put("110",	"货物贸易");
			jhtpMap.put("121",	"运输");
			jhtpMap.put("122",	"旅游");
			jhtpMap.put("123",	"金融和保险服务");
			jhtpMap.put("124",	"专有权利使用费和特许费");
			jhtpMap.put("125",	"咨询服务");
			jhtpMap.put("126",	"其他服务");
			jhtpMap.put("131",	"职工报酬和赡家款");
			jhtpMap.put("132",	"投资收益");
			jhtpMap.put("133",	"其他经常转移");
			jhtpMap.put("134",	"银联结汇");
			jhtpMap.put("200",	"资本与金融项目");
			jhtpMap.put("210",	"资本账户");
			jhtpMap.put("220",	"直接投资");
			jhtpMap.put("230",	"证券投资");
			jhtpMap.put("231",	"对境外证券投资撤回");
			jhtpMap.put("232",	"证券筹资");
			jhtpMap.put("240",	"其他投资");
			jhtpMap.put("250",	"国内外汇贷款");
			jhtpMap.put("270",	"其他");
			extrMap.put("0", jhtpMap);
			//售汇项目代码
			shtpMap.put("300",	"经常项目");
			shtpMap.put("310",	"货物贸易");
			shtpMap.put("321",	"运输");
			shtpMap.put("322",	"旅游");
			shtpMap.put("323",	"金融和保险服务");
			shtpMap.put("324",	"专有权利使用费和特许费");
			shtpMap.put("325",	"咨询服务");
			shtpMap.put("326",	"其他服务");
			shtpMap.put("331",	"职工报酬和赡家款");
			shtpMap.put("332",	"投资收益");
			shtpMap.put("333",	"其他经常转移");
			shtpMap.put("334",	"银联结汇");
			shtpMap.put("400",	"资本与金融项目");
			shtpMap.put("410",	"资本账户");
			shtpMap.put("420",	"直接投资");
			shtpMap.put("430",	"证券投资");
			shtpMap.put("431",	"对境外证券投资撤回");
			shtpMap.put("432",	"证券筹资");
			shtpMap.put("440",	"其他投资");
			shtpMap.put("450",	"国内外汇贷款");
			shtpMap.put("470",	"其他");
			extrMap.put("1", shtpMap);
			return extrMap;
		}
		//初始化购汇用途MAP，转化编码时从内存中取
		public static Map<String,String> initBYTPMap(){
			bytpMap.put("01","境外旅游");
			bytpMap.put("02","自费出境学习");
			bytpMap.put("04","探亲");
			bytpMap.put("05","商务考察");
			bytpMap.put("06","朝觐");
			bytpMap.put("07","出境定居");
			bytpMap.put("08","境外就医");
			bytpMap.put("09","被聘工作");
			bytpMap.put("10","境外邮购");
			bytpMap.put("11","缴纳国际组织会员费");
			bytpMap.put("12","境外直系亲属救助");
			bytpMap.put("13","国际交流");
			bytpMap.put("14","境外培训");
			bytpMap.put("16","外派劳务");
			bytpMap.put("17","外汇理财");
			bytpMap.put("18","货物贸易及相关费用");
			bytpMap.put("19","境外咨询");
			bytpMap.put("20","其他服务贸易费用");
			bytpMap.put("24","补购外汇");
			bytpMap.put("25","境外个人经常项目收入购汇");
			bytpMap.put("26","境外个人原币兑回");
			bytpMap.put("99","其他");
			return bytpMap;
		}
		
	public List<CustFavVo> selcAllDfzfWater(String dgfieldbegin, String dgfieldend, String comStcd){
		List<DfzfWater> list=null;
		List<CustFavVo> list1 = new ArrayList<CustFavVo>();
		try {
			list = dfzfWaterMapper.selcDfzfWater(dgfieldbegin,dgfieldend,comStcd);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		if(list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				CustFavVo custFavVo=new CustFavVo(); 
				//此处将TRSN的值赋值到LCNO中
				custFavVo.setLcno(list.get(i).getTrsn());
				custFavVo.setTrtp(list.get(i).getTrtp());
				custFavVo.setCont(list.get(i).getCont());
				custFavVo.setIdtp(list.get(i).getIdtp());
				custFavVo.setIdno(list.get(i).getIdno());
				custFavVo.setCunm(list.get(i).getCunm());
				custFavVo.setCuac(list.get(i).getCuac());
				String sourceEXTP=list.get(i).getExtp();
				String reverseEXTP=(String) DfzfWaterServiceImpl.initEXTPMap().get(sourceEXTP);
				if(null!=reverseEXTP&&!reverseEXTP.equals("")){
					sourceEXTP=reverseEXTP;
				}
				custFavVo.setExtp(sourceEXTP);
				String sourceEXTR=list.get(i).getExtr();
				String trtp=list.get(i).getJstp();
				Map<String,String> extrMap=(HashMap<String, String>) DfzfWaterServiceImpl.initEXTRMap().get(trtp);
				String reverseEXTR=(String) extrMap.get(sourceEXTR);
				if(null!=reverseEXTR&&!reverseEXTR.equals("")){
					sourceEXTR=reverseEXTR;
				}
				custFavVo.setExtr(sourceEXTR);
				//此处将BYTP 的值赋值到COPYCOUT中
				String sourceBYTP=list.get(i).getBytp();
				String reverseBYTP=(String) DfzfWaterServiceImpl.initBYTPMap().get(sourceBYTP);
				if(null!=reverseBYTP && !reverseBYTP.equals("")){
					sourceBYTP=reverseBYTP;
				}
				custFavVo.setCopycout(sourceBYTP);
				
				custFavVo.setFonm(list.get(i).getFonm());
				//此处将FOAM值赋值到FRAM中
				//BigDecimal bg=new BigDecimal(list.get(i).get("FOAM"));
				custFavVo.setFram(list.get(i).getFoam());
				//此处将CYAM的值赋值到CASH中
				custFavVo.setCash(list.get(i).getCyam());
				//此处将AKPC的值赋值到EXPC中
				custFavVo.setExpc(list.get(i).getAkpc());
				custFavVo.setCxfg(list.get(i).getCxfg());
				list1.add(custFavVo);
			}
		}
		return list1;
	}
}
