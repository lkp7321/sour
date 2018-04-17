package com.ylxx.fx.service.impl.jsh;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.jsh.JshChkParaMapper;
import com.ylxx.fx.service.jsh.IJshChkParaService;
import com.ylxx.fx.service.po.jsh.JshChkParaVo;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;
@Service("jshChkParaService")
public class JshChkParaServiceImpl implements IJshChkParaService{
	private static final Logger LOGGER = LoggerFactory.getLogger(JshChkParaServiceImpl.class);
	@Resource(name="jshChkParaMapper")
	private JshChkParaMapper jshChkParaMapper;
	@Override
	public String selJshChkPara(String exnm, Integer pageNo, Integer pageSize) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		String result="";
		try {
			List<HashMap<String,String>> list=jshChkParaMapper.selJshChkPara(exnm);
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
	public String insJshChkPara(JshChkParaVo chkPara) {
		String result="";
		//CurrUser curUser=new CurrUser();
		 /*curUser.setProd("P999");
		 curUser.setUsnm("chenrui");*/
		//CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(ChkPara.getUserKey());
			try{
				chkPara.setMxmd(chkPara.getMdmd().add(chkPara.getPoint().divide(new BigDecimal(Math.pow(10,chkPara.getOffset().doubleValue())))));
				chkPara.setMimd(chkPara.getMdmd().subtract(chkPara.getPoint().divide(new BigDecimal(Math.pow(10,chkPara.getOffset().doubleValue())))));
			jshChkParaMapper.insJshChkPara(chkPara);
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(), "添加成功");
			 }catch(Exception e){
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_21.getCode(), "添加失败");
			}
		return result;	
	}
	@Override
	public String updJshChkPara(JshChkParaVo chkPara) {
		// TODO Auto-generated method stub
	/*	CurrUser curUser=new CurrUser();
		 curUser.setProd("P999");
		 curUser.setUsnm("chenrui");*/
		String result="";
		 try{
			 	chkPara.setMxmd(chkPara.getMdmd().add(chkPara.getPoint().divide(new BigDecimal(Math.pow(10,chkPara.getOffset().doubleValue())))));
				chkPara.setMimd(chkPara.getMdmd().subtract(chkPara.getPoint().divide(new BigDecimal(Math.pow(10,chkPara.getOffset().doubleValue())))));
			 jshChkParaMapper.updJshChkPara(chkPara);
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), "修改成功");
			 }catch(Exception e){
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), "修改失败");
			}
		return result;	
	}
	@Override
	public String delJshChkPara(JshChkParaVo chkPara) {
		String result="";
		 //CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		 try{
			  jshChkParaMapper.delJshChkPara(chkPara);
					result= ResultDomain.getRtnMsg(ErrorCodePrice.E_30.getCode(), "删除成功");
			 }catch(Exception e){
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_31.getCode(), "删除失败");
			}
		return result;
	}
	@Override
	public String selChkParaExnmExist(JshChkParaVo chkPara) {
		String result="";
		//CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			int count = jshChkParaMapper.selChkParaExnmExist(chkPara);
			if (count>0) {
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "该用户已存在");
			}else {
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "该用户可以使用");
			}
		} catch (Exception e) {
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查重异常");
		}
		return result;
	}
	@Override
	public String selChkParaExnm() {
		String result="";
		try {
			List<HashMap<String,String>> list=jshChkParaMapper.selChkParaExnm();
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
	

