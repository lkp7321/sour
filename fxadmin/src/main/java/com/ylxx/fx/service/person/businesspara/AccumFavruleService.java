package com.ylxx.fx.service.person.businesspara;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
/**
 * 积存金
 * 分行优惠设置
 * @author lz130
 *
 */
public interface AccumFavruleService {
	/**
	 * 获取机构
	 * @param userkey
	 * @param userorgleve
	 * @return
	 */
	List<Map<String,Object>> selectOrganlist(String userKey, String userorgleve);
	/**
	 * 查询
	 * @return
	 */
	List<Map<String, Object>> comboxData();
	/**
	 * 查询客户类型
	 * @return
	 */
	List<Map<String,Object>> custboxData();
	/**
	 * 查询分行优惠数据
	 * @param ogcd
	 * @return
	 */
	PageInfo<Favrule> pageSelecFavrule(
			String userKey,String ogcd,
			Integer pageNo,Integer pageSize);
	/**
	 * 启用
	 * @param userKey
	 * @param list
	 * @return
	 */
	boolean openStat(String userKey, List<Favrule> list);
	/**
	 * 停用
	 * @param userKey
	 * @param list
	 * @return
	 */
	boolean stopStat(String userKey, List<Favrule> list);
	/**
	 * 初始化修改弹窗
	 * @param rule
	 * @param fvid
	 * @return
	 */
	List<FavourRule> onSelFavrule(String rule, String fvid);
	/**
	 * 修改保存操作
	 * @param userKey
	 * @param list
	 * @param fvid
	 * @param ogcd
	 * @return
	 */
	boolean saveFavruleRule(String userKey, List<FavourRule> list, String fvid,
			String ogcd);
	/**
	 * 添加保存操作
	 * @param userKey
	 * @param ogcd
	 * @return
	 */
	boolean insertFavrule(String userKey, String ogcd);
}
