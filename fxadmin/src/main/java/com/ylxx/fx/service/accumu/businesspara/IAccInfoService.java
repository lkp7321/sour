package com.ylxx.fx.service.accumu.businesspara;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import com.ylxx.fx.service.po.AccInfoManageBean;
import com.ylxx.fx.service.po.TrdOgcdBean;
import com.ylxx.fx.service.po.TrdOrganBean;
/**
 * 活动专户管理
 * @author lz130
 *
 */
public interface IAccInfoService {

	/**
	 * 查询活动专户
	 * @return
	 * @throws Exception
	 */
	List<AccInfoManageBean> searchAccInfoList() throws Exception;

	/**
	 * 筛选查询当前用户机构下的对公账户数据
	 * @param orgn
	 * @return
	 * @throws Exception
	 */
	List<AccInfoManageBean> searchAccInfoListByOrgn(String orgn) throws Exception;

	/**
	 * 插入活动专户
	 * @param og
	 * @param ptid
	 * @param ptname
	 * @return
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws UnsupportedEncodingException
	 */
	boolean doInsertAccInfo(String og, String ptname) throws Exception;

	/**
	 * 为第一个机构下拉框赋值
	 * 
	 * @return
	 * @throws Exception
	 */
	List<TrdOrganBean> queryOneOrgan1() throws Exception;
	
	/**
	 * 查询当前用户机构名称
	 * 
	 * @param userorg
	 * @return
	 * @throws Exception
	 */
	List<TrdOgcdBean> queryUserOrganForAcc(String userorg) throws Exception;
}

