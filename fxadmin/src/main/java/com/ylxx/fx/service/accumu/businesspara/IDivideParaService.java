package com.ylxx.fx.service.accumu.businesspara;

import java.util.List;

import com.ylxx.fx.service.po.DivideParaBean;
/**
 * 分红日
 * @author lz130
 *
 */
public interface IDivideParaService {

	/**
	 * 查询分紅
	 * @return
	 * @throws Exception
	 */
	List<DivideParaBean> searchDivideParaList() throws Exception;

	/**
	 * 删除分红表记录
	 * @param dddt
	 * @return
	 * @throws Exception
	 */
	Boolean doDeleteDividePara(String dddt) throws Exception;
	
	/**
	 * 取得cmm_ptpara_p003的更新时间
	 * @return
	 * @throws Exception
	 */
	String getP003UPDT() throws Exception;
	
	/**
	 * 更新CmmPara
	 * @param time
	 * @return
	 * @throws Exception
	 */
	Boolean updateCmmPara(String time) throws Exception;
	
	/**
	 * 插入分红
	 * @param time
	 * @return
	 * @throws Exception
	 */
	Boolean insertDividePara(String time) throws Exception;
	
	/**
	 * 更新CmmPara并插入分红
	 * @param time
	 * @return
	 * @throws Exception
	 */
	String updateCmmParaAndinsertDividePara(String time) throws Exception;
}

