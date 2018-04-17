package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ylxx.fx.core.mapper.accumu.businesspara.DivideParaMapper;
import com.ylxx.fx.service.accumu.businesspara.IDivideParaService;
import com.ylxx.fx.service.po.DivideParaBean;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 分红日
 * @author lz130
 *
 */
@Service("divideParaService")
public class DivideParaServiceImpl implements IDivideParaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DivideParaServiceImpl.class);
	
	@Resource
	private DivideParaMapper divideParaMapper;

	/**
	 * 查询分紅
	 * @return
	 * @throws Exception
	 */
	public List<DivideParaBean> searchDivideParaList() throws Exception {
		LOGGER.info("查询分红日：");
		return divideParaMapper.searchDivideParaList();
	}
	
	/**
	 * 删除分红表记录
	 * @param dddt
	 * @return
	 * @throws Exception
	 */
	public Boolean doDeleteDividePara(String dddt) throws Exception {
		Boolean result = false;
		try {
			int count = divideParaMapper.doDeleteDividePara(dddt);
			if(count > 0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 取得cmm_ptpara_p003的更新时间
	 * @return
	 * @throws Exception
	 */
	public String getP003UPDT() throws Exception {
		String time = divideParaMapper.getP003UPDT();
		if(time != null && time != ""){
			return time;
		}
		return "";
	}
	
	/**
	 * 更新CmmPara
	 * @param time
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Boolean updateCmmPara(String time) throws Exception {
		Boolean result = false;
		try {
			int count = divideParaMapper.updateCmmPara(time);
			if(count > 0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 插入分红
	 * @param time
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Boolean insertDividePara(String time) throws Exception {
		Boolean result = false;
		try {
			int count = divideParaMapper.insertDividePara(time, DataTimeClass.getNowDay());
			if(count > 0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 更新CmmPara并插入分红
	 * @param time
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String updateCmmParaAndinsertDividePara(String time) throws Exception{
		Boolean insertResult = false;
		String result = ResultDomain.getRtnMsg(ErrorCodePrice.E_21.getCode(), null);
		String value = "";
		// 1. 取得cmm_ptpara_p003的更新时间
		value = this.getP003UPDT();
		// 2. 更新CmmPara
		if(value != "" && Integer.parseInt(value) > Integer.parseInt(time)){
			this.updateCmmPara(time);
		}	
		// 3. 插入分红
		insertResult = this.insertDividePara(time);
		if(insertResult){
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(), null);
		}
		return result;
		
	}
}
