package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.controller.user.UserController;
import com.ylxx.fx.core.domain.AddFavruleBean;
import com.ylxx.fx.core.mapper.accumu.businesspara.AccumuAddFavruleMapper;
import com.ylxx.fx.service.accumu.businesspara.IAccumuAddFavruleService;
import com.ylxx.fx.service.po.Trd_CapitalTransfer;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("AccumuAddFavruleService")
public class AccumuAddFavruleServiceImpl implements IAccumuAddFavruleService {
	private static final Logger log = LoggerFactory.getLogger(AccumuAddFavruleServiceImpl.class);
	@Resource
	private AccumuAddFavruleMapper accumuAddFavruleMapper;
	//获取下拉列表
	public String getBoxList() {
		String result="";
		try {
			List<HashMap<String,String>> list=accumuAddFavruleMapper.getBoxList();
			if(list.size()>0) {
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			}else {
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "查询无记录");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询异常");
		}
	
		
		
		return result;
	}
	//查询
	public String getValueList(String ornm,Integer pageNo,Integer pageSize) {
		String result="";
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		try {
			List<HashMap<String,String>> list=accumuAddFavruleMapper.getValueList(ornm);
			if(list.size()>0) {
				PageInfo<HashMap<String,String>> page=new PageInfo<HashMap<String,String>>(list);
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}
			else {
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "查询无记录");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	/*//添加区间
	public AddFavruleBean getBeValueList(String lable,String small,String data,String big) {
		AddFavruleBean addbean=new AddFavruleBean();
		addbean.setMYLABLE(lable);
		addbean.setMYVALUE(small+"到"+big);
		addbean.setMYDATA(data);			
		return addbean;
	}*/
	//添加非区间的优惠（保存按钮）
	public String insertFavrule(String idog, String yhbm, String yhmc,
			String defau, String bian, String con,String maxyh) {
		boolean bool = false;
		String result = "";
		String bm="";
		if(yhbm.equals("")){
			try {
				accumuAddFavruleMapper.insetMaxyh(idog, maxyh);
				bool = true;
			} catch (Exception e) {
				e.printStackTrace();
				bool = false;
			}
			
			bm="F01";
		}else{
			if(!maxyh.equals("")){
				try {
					accumuAddFavruleMapper.updateMaxyh(maxyh, idog);;
					bool = true;
				} catch (Exception e) {
					e.printStackTrace();
					bool = false;
				}
			
		}
			String x=yhbm.substring(0,1);
			String id=yhbm.substring(1,yhbm.length());
			int i=Integer.parseInt(id)+1;
			if(i<=9){
				bm=x+"0"+i;
			}else{
				 bm=x+i;
			}
		}
		String set="setx("+bian+");";
		
		
		String rule=set+con+"default("+defau+");";
		
		try {
			accumuAddFavruleMapper.insertFavrule1(idog, bm, yhmc, rule);
			bool=true;
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		if(bool) {
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(), "true");
		}else {
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_21.getCode(), "false");
		}
		
		return result;
	}
	//规则修改保存按钮
	public String doUpdateFav(String idog, String yhbm,String tiao, String yhmc,
			String bian, String defau, String maxyh,String con) {
		boolean bool = false;
		String result="";
		if(!maxyh.equals("")){
			try {
				accumuAddFavruleMapper.updateMaxyh(maxyh,idog);
				bool = true;
			} catch (Exception e) {
				e.printStackTrace();
				bool = false;
			}
        String set="setx("+bian+");";
		
		
		
		String rule=set+con+"default("+defau+");";
		try {
			accumuAddFavruleMapper.updatefavrule(yhmc, rule,idog,yhbm);
			bool=true;
		}catch(Exception e) {
			e.printStackTrace();
			bool=false;
		}
		
		}
		if(bool) {
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), "true");
		}else {
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), "false");
		}
		return result;
	}
	//查询电商商户号 
	public String queryShnoInfo(){
		String result="";
		try {
			List<HashMap<String,String>> list=accumuAddFavruleMapper.queryShnoInfo();
			if(list.size()>0) {
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			}else {
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "查询无记录");
			}
		}catch(Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	public String selectTranfer(String trdtbegin,String trdtend,String shno,String dirc,Integer pageNo,Integer pageSize){
		String result="";
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		
		try {
			List<Trd_CapitalTransfer> list=accumuAddFavruleMapper.selectTranfer(trdtbegin, trdtend, shno, dirc);
			PageInfo<Trd_CapitalTransfer> page=new PageInfo<Trd_CapitalTransfer>(list);
			if (page.getList().size()>0) {
				
			
			for(int i = 0;i<page.getList().size();i++) {
				page.getList().get(i).setDirc(page.getList().get(i).getDirc().equals("0")?"0-电商转账给民生":"1-民生转账给电商");
				page.getList().get(i).setShno(page.getList().get(i).getShno());
				page.getList().get(i).setFldt(page.getList().get(i).getFldt());
				page.getList().get(i).setTotal(page.getList().get(i).getTotal());
				page.getList().get(i).setJrmb(page.getList().get(i).getJrmb().abs());
				page.getList().get(i).setMafl(page.getList().get(i).getMafl().equals("0")?"0-未手工中止":"1-手工中止");
				page.getList().get(i).setTrdt(page.getList().get(i).getTrdt());
				page.getList().get(i).setTrtm(page.getList().get(i).getTrtm());
				if("0".equals(page.getList().get(i).getStcd()))
					page.getList().get(i).setStcd("0-初始状态");
				else if("S".equals(page.getList().get(i).getStcd()))
					page.getList().get(i).setStcd("S-交易成功");
				else if("C".equals(page.getList().get(i).getStcd()))
					page.getList().get(i).setStcd("C-已撤销");
				else
					page.getList().get(i).setStcd("W-交易执行中");
				page.getList().get(i).setErcd(page.getList().get(i).getErcd());
				page.getList().get(i).setErmg(page.getList().get(i).getErmg());
				page.getList().get(i).setShac(page.getList().get(i).getShac());
				page.getList().get(i).setJkau(page.getList().get(i).getJkau());
				}
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}
			else {
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无记录"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), JSON.toJSONString(null));
		}
		return result;
	}
	//电商积存金查询 
	public String getTransferTotal(String trdtbegin , String trdtend,
			String shno ,String dirc ){
		String result="";
		
		try {
			int value=accumuAddFavruleMapper.getTransferTotal(trdtbegin, trdtend, shno, dirc);
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(value));
		} catch (Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), JSON.toJSONString(null));
		}
		
		return result;
	}
	/**
	 * 金生金查询
	 * @param pageNo
	 * @param pageSize
	 * @param pdtp
	 * @return
	 */
	public String selectGold(Integer pageNo,Integer pageSize,String pdtp) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		List<Map<String,Object>> list = null;
		try {
			list = accumuAddFavruleMapper.selectGold(pdtp);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 金生金导出Excel
	 */
	public List<Map<String, Object>> selectAllGold(String pdtp) {
		List<Map<String,Object>> list = null;
		try {
			list = accumuAddFavruleMapper.selectGold(pdtp);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return list;
	}
	/**
	 * 金生金交易总额
	 */
	public String selectSumcblv(String pdtp) {
		String result="";
		try {
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), accumuAddFavruleMapper.selectSumcblv(pdtp));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
				
	}
	
	/**
	 * 电商积存金手动中止	
	 */
	public String stopOrStrat(String shno,String fldt,String mafl) {
		String result="";
		boolean flag = false ;
		if("0-未手工中止".equals(mafl)){
			mafl="1";
		}else if("1-手工中止".equals(mafl)){
			mafl="0";
		}else{
			return result=ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), "手动中止或启动失败");
		}
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		String day = sdf1.format(date);
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		String time = sdf2.format(date);
		try {
			accumuAddFavruleMapper.stopOrStrat(shno, fldt, mafl, day, time);
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), "手动中止或启动成功");
		} catch (Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), "手动中止或启动失败");
		}
		return result;
	}
		//初始化页面后获取该机构的目前的优惠
			public String getSearchList(String ogcd) {
				String result="";
				try {
					List<HashMap<String, String>> list = accumuAddFavruleMapper.getSearchList(ogcd);
					if(list.size()!=0) {
						String rule = accumuAddFavruleMapper.getMax(ogcd);
						String value = "";
						if(!rule.equals(null) && !"".equals(rule)) {
							String[] y=rule.split("x");
							value=y[1].substring(1,y[1].length()-2);
						}
						for (int i = 0; i < list.size(); i++) {
							list.get(i).put("mymax", value);
							if(list.get(i).get("RULE").contains("equals")) {
								list.get(i).put("RULE", "equals");
							}
							if(list.get(i).get("RULE").contains("bigger")) {
								list.get(i).put("RULE", "bigger");
							}
							if(list.get(i).get("RULE").contains("smaller")) {
								list.get(i).put("RULE", "smaller");
							}
							if(list.get(i).get("RULE").contains("between")) {
								list.get(i).put("RULE", "between");
							}
						}
						HashMap<String, String> flist = list.get(0);
						result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(flist));
					}else {
						result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
				}
				return result;
			}
			@Override
			public String getinitList(String ogcd) {
				List<AddFavruleBean> addlist = new ArrayList<AddFavruleBean>();
				AddFavruleBean addbean=new AddFavruleBean();
				String lable="";
				String s="";
				String china="";
				String result = "";
				String rds = "";
				try {
					s = accumuAddFavruleMapper.getinitList(ogcd);
					if(s != null && !"".equals(s)) {
						if(s.contains("equals")){
							lable="equals";
						}else if(s.contains("bigger")){
							lable="bigger";
						}else if(s.contains("smaller")){
							lable="smaller";
						}else if(s.contains("between")){
							lable="between";
						}
					
					if(lable.equals("equals")){
						china="等于";
					}
					if(lable.equals("bigger")){
						china="大于";
					}
					if(lable.equals("smaller")){
						china="小于";
					}
					if(lable.equals("between")){
						china="区间";
					}
					if(!lable.equals("between")){
						String[] s1=s.split(lable);
						for(int i=1;i<s1.length;i++){
							addbean=new AddFavruleBean();
							addbean.setMYLABLE(china);
							String[] s2=s1[i].split(",");
							addbean.setMYVALUE(s2[0].replace("(",""));
							if(i==s1.length-1){
								String[]s3=s2[1].split(";"); 
								addbean.setMYDATA(s3[0].substring(0,s3[0].length()-1));
							}else{
							addbean.setMYDATA(s2[1].substring(0,s2[1].length()-2));
							}
							addlist.add(addbean);
						}
					}else {
						String[] s1=s.split(lable);
						for(int i=1;i<s1.length;i++){
							addbean=new AddFavruleBean();
							addbean.setMYLABLE(china);
							String[] s2=s1[i].split(",");
							String min=s2[0].replace("(","");
							String max=s2[1];
							addbean.setMYVALUE(min+"到"+max);
							if(i==s1.length-1){
								String[]s3=s1[i].split(";"); 
								String[] s4=s3[0].split(",");
								String minl=s4[0].replace("(","");
								String maxl=s4[1];
								addbean.setMYVALUE(minl+"到"+maxl);
								addbean.setMYDATA(s4[2].substring(0,s4[2].length()-1));
							}else{
							addbean.setMYDATA(s2[2].substring(0,s2[2].length()-2));
							}
							addlist.add(addbean);
						}
					}
					result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(addlist));
					}else {
						result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
					}
					
				} catch (Exception e) {
					result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
				}
				return result;
			}
			@Override
			public String getDeleteList(String ogcd, String fvid) {
				String result = "";
				try {
					accumuAddFavruleMapper.getDeleteList(ogcd, fvid);
					result=ResultDomain.getRtnMsg(ErrorCodePrice.E_30.getCode(), "删除成功");
				} catch (Exception e) {
					e.printStackTrace();
					result=ResultDomain.getRtnMsg(ErrorCodePrice.E_31.getCode(), "删除失败");
				}
				return result;
			}
			@Override
			public String FavruleList(String ogcd, String fvid) {
				String result="";
				String lable = "";
				try {
					List<HashMap<String, String>> list = accumuAddFavruleMapper.FavruleList(ogcd, fvid);
					AddFavruleBean favruleBean = new AddFavruleBean();
					if(list.size() > 0) {
						favruleBean.setOGCD(list.get(0).get("OGCD"));
						favruleBean.setORNM(list.get(0).get("ORNM"));
						favruleBean.setFVNM(list.get(0).get("FVNM"));
						favruleBean.setMYMAX(accumuAddFavruleMapper.getMax(ogcd));
						favruleBean.setFVID(list.get(0).get("FVID"));
						favruleBean.setSTAT(list.get(0).get("STAT"));
						String s=list.get(0).get("RULE");
						if(s.contains("equals")){
							lable="equals";
						}else if(s.contains("bigger")){
							lable="bigger";
						}else if(s.contains("smaller")){
							lable="smaller";
						}else if(s.contains("between")){
							lable="between";
						}
						favruleBean.setMYLABLE(lable);
						String[] y=s.split(";");
						String[] s1=y[0].split("x");
						favruleBean.setMYINT(s1[1].substring(1,s1[1].length()-1));
						String[] x=s.split("default");
						favruleBean.setMYDEFAULT(x[1].substring(1,x[1].length()-2));
						result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(favruleBean));
					}else {
						result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "查询无记录");
					}
				} catch (Exception e) {
					e.printStackTrace();
					result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询异常");
				}
				return result;
			}
			@Override
			public String LableList(String ogcd, String fvid) {
				String result = "";
				List<AddFavruleBean> updatelist=new ArrayList<AddFavruleBean>();
				String lable="";
				String s="";
				String china="";
				try {
					s = accumuAddFavruleMapper.LableList(ogcd, fvid);
					if(!s.equals(null)&&!"".equals(s)) {
						
					
					if(s.contains("equals")){
						lable="equals";
					}else if(s.contains("bigger")){
						lable="bigger";
					}else if(s.contains("smaller")){
						lable="smaller";
					}else if(s.contains("between")){
						lable="between";
					}
				

		
				if(!lable.equals("between")){
					String[] s1=s.split(lable);
					for(int i=1;i<s1.length;i++){
						AddFavruleBean upbean=new AddFavruleBean();
						upbean.setMYLABLE(lable);
						String[] s2=s1[i].split(",");
						upbean.setMYVALUE(s2[0].replace("(",""));
						if(i==s1.length-1){
							String[]s3=s2[1].split(";"); 
							upbean.setMYDATA(s3[0].substring(0,s3[0].length()-1));
						}else{
							upbean.setMYDATA(s2[1].substring(0,s2[1].length()-2));
						}
						updatelist.add(upbean);
					}
				}else {
					String[] s1=s.split(lable);
					for(int i=1;i<s1.length;i++){
						AddFavruleBean upbean=new AddFavruleBean();
						upbean.setMYLABLE(lable);
						String[] s2=s1[i].split(",");
						String min=s2[0].replace("(","");
						String max=s2[1];
						upbean.setMYVALUE(min+"到"+max);
						if(i==s1.length-1){
							String[]s3=s1[i].split(";"); 
							String[] s4=s3[0].split(",");
							String minl=s4[0].replace("(","");
							String maxl=s4[1];
							upbean.setMYVALUE(minl+"到"+maxl);
							upbean.setMYDATA(s4[2].substring(0,s4[2].length()-1));
						}else{
							upbean.setMYDATA(s2[2].substring(0,s2[2].length()-2));
						}
						updatelist.add(upbean);
					}
					
				}
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(updatelist));
					}else {
						result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "查询无数据");
					}
				} catch (Exception e) {
					e.printStackTrace();
					result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询异常");
				}
				
				return result;
			}
}
