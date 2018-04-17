package com.ylxx.fx.service.jsh;

import java.util.*;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.service.po.jsh.Trd_SafeInfo;
import com.ylxx.fx.service.po.jsh.Trd_SafePrice;
/**
 * 外管利率，柜员信息
 * @author lz130
 *
 */
public interface JshWgjSafeInfoService {
	/**
	 * 查询外管利率
	 * @param cyen
	 * @return
	 */
	List<Map<String, Object>> selectJshSafePriceList(String cyen);
	/**
	 * 添加外管利率
	 * @param trd_SafePrice
	 * @return
	 */
	String insertJshSafePrice(JshPages<Trd_SafePrice> trd_SafePrice);
	/**
	 * 分页查询外管柜员信息
	 * @param trd_SafeInfo
	 * @return
	 */
	PageInfo<Map<String, Object>> selectJshSafeInfoList(JshPages<Trd_SafeInfo> trd_SafeInfo);
	/**
	 * 添加柜员信息
	 * @param trd_SafeInfo
	 * @return
	 */
	String insertJshSafeInfo(JshPages<Trd_SafeInfo> trd_SafeInfo);
	/**
	 * 修改柜员信息
	 * @param trd_SafeInfo
	 * @return
	 */
	String updateJshSafeInfo(JshPages<Trd_SafeInfo> trd_SafeInfo);
	/**
	 * 删除柜员信息
	 * @param trd_SafeInfo
	 * @return
	 */
	String deleteJshSafeInfo(JshPages<Trd_SafeInfo> trd_SafeInfo);

}
