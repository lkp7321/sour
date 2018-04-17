package com.ylxx.fx.service.impl.accex.tradeMananger;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accex.tradeMananger.AccexAdjustmentDateMapper;
import com.ylxx.fx.service.accex.tradeMananger.IAccexAdjustmentDateService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("iaccexAdjustmentDateService")
public class AccexAdjustmentDateServiceImpl implements IAccexAdjustmentDateService {
	
	private static final Logger log = LoggerFactory.getLogger(AccexAdjustmentDateServiceImpl.class);
	@Resource
	private AccexAdjustmentDateMapper accexAdjustmentDateMapper;
	//页面加载的交易品种
	public String selCurrencyPair(String userKey) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		List<Map<String, Object>> list = null;
		try {
			 list = accexAdjustmentDateMapper.selCurrencyPair(curUser.getProd());
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "查询交易品种出错");
		}
	}
	
	//添加
	public String addAdjust(String ostp,String osbs,String nstp,String nsbs,String orbs,String nrbs,String osas,String nsas,String oras,String nras,String exnm,String femd,String stat) {
		int a =0;
		int b =0;
		try {
			a = accexAdjustmentDateMapper.selectAdjustByKey(exnm, femd);
			if(a>0){
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "调整日配置于已有信息重叠！");
			}else{
				b =  accexAdjustmentDateMapper.addAdjust(ostp,osbs,nstp,nsbs,orbs,nrbs,osas,nsas,oras,nras,exnm,femd,stat);
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		} 
		if(b>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "添加成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "添加失败");
		}
	}
	
	//分页查询数据
	public String selOilAdjustList(String beginDate,String endDate,String exnm,Integer pageNo, Integer pageSize) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		List<Map<String, Object>> list = null;
		try {
			list = accexAdjustmentDateMapper.selOilAdjustList(beginDate,endDate,exnm);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		PageInfo<Map<String,Object>> page=new PageInfo<Map<String,Object>>(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	public List<Map<String, Object>> selAllOilAdjustList(String beginDate,String endDate,String exnm){
		List<Map<String, Object>> list = null;
		try {
			list = accexAdjustmentDateMapper.selOilAdjustList(beginDate,endDate,exnm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	
	//修改
	public String updateAdjust(
			String ostp,String osbs,String nstp,
			String nsbs,String orbs,String nrbs,
			String osas,String nsas,String oras,
			String nras,String exnm,String femd) {
		int a = 0;
		try {
			a = accexAdjustmentDateMapper.updateAdjust(ostp, osbs, nstp, nsbs, orbs, nrbs, osas, nsas, oras, nras, exnm, femd);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		if(a>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "修改失败");
		}
	}
				
				
	//删除
	@Override
	public String deleteAdjustByKey(String exnm,String femd) {
		int a = 0;
		try {
			a = accexAdjustmentDateMapper.deleteAdjustByKey(exnm, femd);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		if(a>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "删除成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "删除失败");
		}
	}


}
