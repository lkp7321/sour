package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accumu.businesspara.AccumuAddCutyMapper;
import com.ylxx.fx.service.accumu.businesspara.AccumuAddCutyService;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;
@Service("accumuAddCutyService")
public class AccumuAddCutyServiceImpl implements AccumuAddCutyService {
	@Resource
	public AccumuAddCutyMapper accumuAddCutyMapper;
	/**
	 * 查询客户等级
	 */
	@Override
	public String doSearchList(String ptid, String gstp, Integer pageNo, Integer pageSize) {
		String result = "";
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		try {
		List<HashMap<String, String>> list = accumuAddCutyMapper.doSearchList(ptid, gstp);
		if(list.size() > 0){
			PageInfo<HashMap<String,String>> page=new PageInfo<HashMap<String,String>>(list);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		}else{
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "查询无记录");
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	@Override
	public String insertCuty(String gstp, String bp, String name,String ptid) {
		String result = "";
		boolean bool = false;
		String level="";
		String maxCutyString = accumuAddCutyMapper.getCutyMax(name, ptid);
		if(("核心等级").equals(name)){
			int maxcutyint=Integer.parseInt(maxCutyString);//转化为整数
			
			if("".equals(maxCutyString)){
				level="1";
			}else{	
				if(maxcutyint>=1&&maxcutyint<=8){
				level=Integer.toString(maxcutyint+1);
				}else{
					bool=false;
				}
					if(Integer.parseInt(level)>9){
						bool=false;
						}
					}
			   }
		if("自定义等级".equals(name)){
			if("".equals(maxCutyString)){
				level="a";
			}
			else{
				byte[] gc=maxCutyString.getBytes();//a
				int maxcuty=(int)gc[0];//97
				if(maxcuty>=97&&maxcuty<=121){
					int inlevel=maxcuty+1;
					char slevel=(char)inlevel;
					level=String.valueOf(slevel);
				}else{
					bool=false;
				}												
			}
		}
		try {
			accumuAddCutyMapper.insertCuty(ptid, level, name, gstp, bp);
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		if(bool){
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(), "插入成功");
		}else{
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_21.getCode(), "插入失败");
		}
		
		return result;
	}
	@Override
	public String selMaxCuty(String tynm) {
		String result = "";
		String cuty = "";
		try {
			cuty = accumuAddCutyMapper.selMaxCuty(tynm);
			if(!cuty.equals(null)&&!"".equals(cuty)){
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(),cuty );
			}else{
				if(tynm.equals("核心等级")){
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(),"0" );
				}else if(tynm.equals("自定义等级")){
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(),"A" );
				}
				
			}
			
		} catch (Exception e) {
			
		}
		return result;
	}

	

}
