package com.ylxx.fx.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.Nrlrgt;
import com.ylxx.fx.service.po.NrlrgtUp;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.utils.CurrUser;

public interface NrlrgtService {
	/**
	 * 添加权限
	 * @param list
	 * @param curUser
	 * @return
	 */
	boolean addNrlrgts(CurrUser curUser, List<NrlrgtUp> list, String nrgtNames);
	/**
	 * 删除权限
	 * @param nrlrgtupList
	 * @param curUser
	 * @param role
	 * @return
	 */
	boolean deleteThisNrgt(CurrUser curUser, List<NrlrgtUp> nrlrgtupList, RoleBean role);
	/**
	 * 权限复核未通过
	 * @param list
	 * @return
	 * @throws Exception
	 */
	boolean roleNrlrNo(CurrUser curUser, List<Nrlrgt> list)throws Exception;
	/**
	 * 权限复核通过
	 * @param user
	 * @param list1
	 * @return
	 * @throws Exception
	 */
	boolean insertPrice(CurrUser curUser, List<Nrlrgt> list1)throws Exception;
	/**
	 * 删除权限的查看
	 * @param sqno
	 * @param ptid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo<NrlrgtUp> delpage(String sqno, String ptid, Integer pageNo, Integer pageSize);
	/**
	 * 复核权限的分页
	 * @param sqno
	 * @param ptid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo<NrlrgtUp> fgpage(String sqno, String ptid, Integer pageNo, Integer pageSize);
	/**
	 * 查看权限的分页
	 * @param sqno
	 * @param ptid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo<NrlrgtUp> selpage(String sqno, String ptid, Integer pageNo, Integer pageSize);
	/**
	 * 添加权限的查看
	 * @param sqno
	 * @param ptid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo<NrlrgtUp> addpage(String sqno, String ptid, Integer pageNo, Integer pageSize); 

}
