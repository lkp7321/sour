package com.ylxx.fx.service.price.putliquid;
import java.util.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.NrlrgtUp;
import com.ylxx.fx.service.po.PdtChkpara;
import com.ylxx.fx.service.po.Pdtinfo;
import com.ylxx.fx.service.po.Put_Config;
import com.ylxx.fx.service.po.Put_Liquid;
public interface PutliQuidService {
	/**
	 * 获取分页价格使用信息
	 * @param stat 申请状态
	 * @param pageNo
	 * @param pageSize
	 * @return PageInfo
	 */
	public PageInfo<Put_Liquid> getAllPutliQuid(String stat, 
			Integer pageNo, Integer pageSize);
	/**
	 * 价格使用申请
	 * @param userKey 登录用户
	 * @param putLiquid 申请信息
	 * @return boolean
	 */
	public boolean addPutliquid(String userKey, Put_Liquid putLiquid);
	/**
	 * 价格使用审批
	 * @param userKey 登录用户
	 * @param putLiquid 审批信息
	 * @return boolean
	 */
	public boolean updatePutliquid(String userKey, Put_Liquid putLiquid);
	/**
	 * 价格接口配置
	 * @param userKey 登陆用户
	 * @param put_config
	 * @param pdtin
	 * @param put_Liquid
	 * @return String 价格使用配置
	 */
	public String upbuildput(String userKey, String inid, List<Put_Config> put_configList, 
			List<Pdtinfo> pdtinList, Put_Liquid put_Liquid);
	/**
	 * 获取价格接口配置的所有产品
	 * @return List
	 */
	public List<Map<String,String>> getAllptno();
	/**
	 * 根据产品编号获取 货币接口
	 * @param prod 产品
	 * @return
	 */
	public List<PdtChkpara> getPtidJk(String prod);
	/**
	 * 根据inid获取 所有接口
	 * @param inid
	 * @return List
	 */
	public List<PdtChkpara> getAllPtidJK(String inid);
	/**
	 * 根据价格使用编号获取当前使用信息
	 * @param sqid 价格使用编号
	 * @return Put_Liquid
	 */
	public Put_Liquid getonlyquid(String sqid,String name,String unit);
}
