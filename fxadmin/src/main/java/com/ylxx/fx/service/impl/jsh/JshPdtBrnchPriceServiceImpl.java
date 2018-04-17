package com.ylxx.fx.service.impl.jsh;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.jsh.JshPdtBrnchPriceMapper;
import com.ylxx.fx.service.jsh.IJshPdtBrnchPriceService;
import com.ylxx.fx.service.po.jsh.JshPdtBrnchPriceVo;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;
@Service("jshPdtBrnchPriceService")
public class JshPdtBrnchPriceServiceImpl implements IJshPdtBrnchPriceService{
	private static final Logger LOGGER = LoggerFactory.getLogger(JshPdtBrnchPriceServiceImpl.class);
	@Resource(name="jshPdtBrnchPriceMapper")
	private JshPdtBrnchPriceMapper jshpdtBrnchPriceMapper;
	@Override
	public String selJshPdtBrnchPrice(String exnm, String trfg, Integer pageNo, Integer pageSize) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		String result="";
		try {
			List<HashMap<String,String>> list=jshpdtBrnchPriceMapper.selJshPdtBrnchPrice(exnm,trfg);
			if(list.size()>0) {
			PageInfo<HashMap<String,String>> page=new PageInfo<HashMap<String,String>>(list);
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
	@Override
	public String insJshPdtBrnchPrice(JshPdtBrnchPriceVo pdtBrnchPrice) {
		String result="";
		//CurrUser curUser=new CurrUser();
		 /*curUser.setProd("P999");
		 curUser.setUsnm("chenrui");*/
		//CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(pdtBrnchPrice.getUserKey());
			try{
			jshpdtBrnchPriceMapper.insJshPdtBrnchPrice(pdtBrnchPrice);
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(), "添加成功");
			 }catch(Exception e){
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_21.getCode(), "添加失败");
			}
		return result;	
	}
	@Override
	public String updJshPdtBrnchPrice(JshPdtBrnchPriceVo pdtBrnchPrice) {
		// TODO Auto-generated method stub
	/*	CurrUser curUser=new CurrUser();
		 curUser.setProd("P999");
		 curUser.setUsnm("chenrui");*/
		String result="";
		 try{
			 jshpdtBrnchPriceMapper.updJshPdtBrnchPrice(pdtBrnchPrice);
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), "修改成功");
			 }catch(Exception e){
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), "修改失败");
			}
		return result;	
	}
	@Override
	public String delJshPdtBrnchPrice(JshPdtBrnchPriceVo pdtBrnchPrice) {
		String result="";
		 //CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		 try{
			  jshpdtBrnchPriceMapper.delJshPdtBrnchPrice(pdtBrnchPrice);
					result= ResultDomain.getRtnMsg(ErrorCodePrice.E_30.getCode(), "删除成功");
			 }catch(Exception e){
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_31.getCode(), "删除失败");
			}
		return result;
	}
	@Override
	public String selBrnchExnmExist(JshPdtBrnchPriceVo pdtBrnchPrice) {
		String result="";
		//CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			int count = jshpdtBrnchPriceMapper.selBrnchExnmExist(pdtBrnchPrice);
			if (count>0) {
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "该货币对已存在");
			}else {
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "该货币对可以使用");
			}
		} catch (Exception e) {
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查重异常");
		}
		return result;
	}
	@Override
	public String selJshBrnchExnm() {
		String result="";
		try {
			List<HashMap<String,String>> list=jshpdtBrnchPriceMapper.selJshBrnchExnm();
			if(list.size()>0) {
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			}else {
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(),"查询无结果");
			}
		}catch(Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询异常");
		}
		
		return result;
	}
	}
	

