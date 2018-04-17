package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.AccInfoManageBean;
import com.ylxx.fx.service.po.TrdOgcdBean;
import com.ylxx.fx.service.po.TrdOrganBean;
/**
 * 活动专户
 * @author lz130
 *
 */
public interface AccInfoMapper{
	
	/**
	 * 查询活动专户(筛选查询当前用户机构下的对公账户数据)
	 * @return
	 * @throws Exception
	 */
	List<AccInfoManageBean> searchAccInfoList(@Param("orgn")String orgn) throws Exception;
	
	/**
	 * 为第一个机构下拉框赋值
	 * @return
	 * @throws Exception
	 */
	List<TrdOrganBean> queryOneOrgan1() throws Exception;
	
	/**
	 * 查询当前用户机构名称
	 * @Param userorg
	 * @return
	 * @throws Exception
	 */
//	String queryUserOrganForAcc(@Param("userorg")String userorg,@Param("value")StringBuffer value,@Param("val1")String val1,@Param("val2")String val2,@Param("val8")String val8,@Param("val4")String val4, @Param("valueend")String valueend) throws Exception;
	List<TrdOgcdBean> queryUserOrganForAcc(@Param("userorg")String userorg) throws Exception;
}