package com.ylxx.fx.core.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Nrlrgt;
import com.ylxx.fx.service.po.NrlrgtUp;

public interface NrlrgtMapper {
	/**
	 * 查询添加的权限
	 * @param sqno:产品名称
	 * @param ptid：角色id
	 * @return
	 */
	List<NrlrgtUp> selectAddNr(@Param("sqno")String sqno,@Param("ptid")String ptid);
	int isLevelNrgt(@Param("rltn")String rltn);
	List<NrlrgtUp> showNrlrgt(@Param("sqno")String sqno, @Param("ptid")String ptid);
	/**
	 * 添加权限
	 * @param lsit
	 * @throws Exception
	 */
	void addNrlrgt(@Param("list")List<NrlrgtUp> lsit)throws Exception;
	/**
	 * 查询已添加的权限
	 * @param ptid
	 * @return
	 */
	List<Nrlrgt> selectDelNrlrgt1(@Param("ptid")String ptid);
	/**
	 * 查询所有的权限
	 * @param sqno
	 * @return
	 */
	List<NrlrgtUp> selectDelNrlrgt2(@Param("sqno")String sqno);
	/**
	 * 删除权限的分页
	 * @param sqno
	 * @param ptid
	 * @return
	 */
	List<NrlrgtUp> delnrlist(@Param("sqno")String sqno,@Param("ptid")String ptid);
	/**
	 * 复核权限的分页
	 * @param sqno
	 * @param ptid
	 * @return
	 */
	List<NrlrgtUp> selnrlist(@Param("sqno")String sqno,@Param("ptid")String ptid);
	/**
	 * 删除一级菜单
	 * @param ptid
	 * @param rltn
	 * @throws Exception
	 */
	void delOneNr(@Param("ptid")String ptid,@Param("rltn")String rltn)throws Exception;
	/**
	 * 删除二级菜单
	 * @param ptid
	 * @param rltn
	 * @param idno
	 * @throws Exception
	 */
	void delTwoNr(@Param("ptid")String ptid,@Param("rltn")String rltn,@Param("idno")String idno)throws Exception;
	/**
	 * 删除三级菜单
	 * @param ptid
	 * @param mnid
	 * @throws Exception
	 */
	void delOthNr(@Param("ptid")String ptid,@Param("mnid")String mnid)throws Exception;
	/**
	 * 复核，展示权限
	 * @param ptid
	 * @return
	 */
	List<Nrlrgt> selRoNl(@Param("ptid")String ptid);
	/**
	 * 复核未通过
	 * @param list
	 * @return
	 * @throws Exception
	 */
	boolean roleNrlrgtNO(@Param("list")List<Nrlrgt> list)throws Exception;
	/**
	 * 修改本级菜单
	 * @param nrlrgt
	 * @return
	 * @throws Exception
	 */
	void isPrice(@Param("nrlrgt")Nrlrgt nrlrgt)throws Exception;
	/**
	 * 插入父级菜单
	 * @param ptid
	 * @param sqno
	 * @param mnid
	 * @return
	 * @throws Exception
	 */
	void iPrice(@Param("ptid")String ptid,@Param("sqno")String sqno,@Param("mnid")String mnid)throws Exception;
	/**
	 * 插入上上级菜单
	 * @param ptid
	 * @param sqno
	 * @param mnid
	 * @return
	 * @throws Exception
	 */
	void isPri(@Param("ptid")String ptid,@Param("sqno")String sqno,@Param("mnid")String mnid)throws Exception;
	/**
	 * 查看上级
	 * @param sqno
	 * @param mnid
	 * @param ptid
	 * @return
	 */
	Nrlrgt selPrice(@Param("sqno")String sqno,@Param("mnid")String mnid,@Param("ptid")String ptid);
	/**
	 * 查看上上级
	 * @param sqno
	 * @param mnid
	 * @param ptid
	 * @return
	 */
	Nrlrgt selPric(@Param("sqno")String sqno,@Param("mnid")String mnid,@Param("ptid")String ptid);
}
