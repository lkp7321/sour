package com.ylxx.fx.service.person.businesspara;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.Maxpavpoint;
import com.ylxx.fx.utils.CurrUser;
/**
 * 最大优惠设置
 * @author lz130
 *
 */
public interface MaxpavpointService {
	/**
	 * 查询最大优惠机构下拉列表P001,P002,P003,P004
	 * @param prod
	 * @param userorgorgn
	 * @param userorgleve
	 * @return
	 */
	List<Map<String, String>> queryMaxpoint(
			String prod, String userorgorgn, String userorgleve);
	/**
	 * 查询最大优惠所有数据P001,P002,P003,P004
	 * @param curUser
	 * @param combogcd
	 * @return
	 */
	List<Maxpavpoint> selMaxpavpoint(CurrUser curUser,String combogcd);
	PageInfo<Maxpavpoint> selPageMaxpavpoint(CurrUser curUser,String combogcd,
			Integer pageNo, Integer pageSize);
	/**
	 * 查询添加页面的货币对下拉框P002,P003
	 * @param prod
	 * @param prod
	 * @return
	 */
	List<Map<String, String>> getMaxExnm(String userKey,String ogcd);
	/**
	 * 添加修改删除  P002,P003
	 * @param userKey
	 * @param maxpoint
	 * @return
	 */
	boolean adMaxpoint(String userKey, Maxpavpoint maxpoint);
	/**
	 * 修改
	 * @param userKey
	 * @param maxpoint
	 * @return
	 */
	boolean upMaxpoint(String userKey, Maxpavpoint maxpoint);
	/**
	 * 删除
	 * @param userKey
	 * @param maxpoint
	 * @return
	 */
	boolean deMaxpoint(String userKey, Maxpavpoint maxpoint);
}
