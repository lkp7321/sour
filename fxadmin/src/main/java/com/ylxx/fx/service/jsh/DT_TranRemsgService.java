package com.ylxx.fx.service.jsh;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.jsh.DT_Price;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.service.po.jsh.TbTrd_RegMsgList;
import com.ylxx.fx.service.po.jsh.TbTrd_TranList;
/**
 * 定投管理
 * @author lz130
 *
 */
public interface DT_TranRemsgService {
	/**
	 * 定投签约信息查询
	 * @param tbTrd_RegMsgList
	 * @return
	 */
	PageInfo<Map<String, Object>> selectDtTranRemsgList(JshPages<TbTrd_RegMsgList> tbTrd_RegMsgList);
	List<Map<String, Object>> selectAllDtTranRemsgList(String rgtp, String rgdt, String crdt, String exnm, String cuac);
	/**
	 * 定投流水信息查询
	 * @param tbTrd_TranList
	 * @return
	 */
	PageInfo<Map<String, Object>> selectDtTransList(JshPages<TbTrd_TranList> tbTrd_TranList);
	List<Map<String, Object>> selectAllDtTransList(String trdt, String trtm, String cuac, String fonm);
	/**
	 * 定投价格查询
	 * @param dT_Price
	 * @return
	 */
	PageInfo<Map<String, Object>> selectDtPriceList(JshPages<DT_Price> dT_Price);
	List<Map<String, Object>> selectAllDtPriceList(String trdt, String trtm, String exnm);
	
}
