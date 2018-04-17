package com.ylxx.fx.service.impl.userimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.user.NrlrgtMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.Nrlrgt;
import com.ylxx.fx.service.po.NrlrgtUp;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.service.user.NrlrgtService;
import com.ylxx.fx.utils.CurrUser;

@Service("nrlrService")
public class NrlrgtServiceImpl implements NrlrgtService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NrlrgtServiceImpl.class);
	@Resource
	private NrlrgtMapper nrmap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	
	/**
	 * 添加权限,批量操作
	 */
	public boolean addNrlrgts( CurrUser curUser, List<NrlrgtUp> list, String nrgtNames ){
		try {
			nrmap.addNrlrgt(list);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("登录产品："+curUser.getProd()+"用户："+curUser.getUsnm()+"添加权限："+nrgtNames+"失败");
			return false;
		}
		LOGGER.info("登录产品："+curUser.getProd()+"用户："+curUser.getUsnm()+"添加权限："+nrgtNames+"成功");
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setTymo("角色管理");
		logfile.setRemk("权限添加");
		logfile.setUsem(curUser.getUsnm());
		logfile.setVold("登录ip:"+curUser.getCurIP()+",添加权限:"+nrgtNames);
		logfile.setVnew("添加成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}
	
	/**
	 * 删除权限
	 */
	public boolean deleteThisNrgt(CurrUser curUser, List<NrlrgtUp> nrlrgtupList, RoleBean role) {
		List<Nrlrgt> list2=new ArrayList<Nrlrgt>();//要执行操作的数据
		Nrlrgt nrgt=null;
		for(int i=0;i<nrlrgtupList.size();i++){
			nrgt = new Nrlrgt();
			nrgt.setMnid(nrlrgtupList.get(i).getMnid());
			nrgt.setPtid(role.getPtid());
			nrgt.setSqno(curUser.getProd());
			nrgt.setClas(nrlrgtupList.get(i).getMamn());
			list2.add(nrgt);
		}
		String nrgtNames = "";
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setTymo("角色管理");
		logfile.setRemk("权限删除");
		logfile.setUsem(curUser.getUsnm());
		for(int j=0;j<list2.size();j++){
			nrgtNames += list2.get(j).getMnid()+"||";
			try {
				if("1".equals(list2.get(j).getClas())){//一级的权限（删除一级二级三级权限）
					nrmap.delOneNr(list2.get(j).getPtid(), list2.get(j).getMnid());
				}else if("2".equals(list2.get(j).getClas())){//二级的权限（删除二级三级权限）
					nrmap.delTwoNr(list2.get(j).getPtid(), list2.get(j).getMnid(),list2.get(j).getMnid());
				}else{//删除本级权限
					nrmap.delOthNr(list2.get(j).getPtid(), list2.get(j).getMnid());
				}
			} catch (Exception e) {
				LOGGER.error("删除权限出错："+list2.get(j).getMnid());
				LOGGER.error(e.getMessage(), e);
				logfile.setVnew("删除失败");
				logfile.setVold("登录ip:"+curUser.getCurIP()+",删除权限:"+list2.get(j).getMnid()+",角色："+list2.get(j).getPtid());
				logfileCmdService.insertLog(logfile);
				return false;
			}
		}
		logfile.setVold("登录ip:"+curUser.getCurIP()+",删除权限:"+nrgtNames+",角色："+list2.get(0).getPtid());
		logfile.setVnew("删除成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}
	


	/**
	 * 权限复核
	 * 复核“未通过”
	 * 对初始添加的权限删除
	 */
	public boolean roleNrlrNo(CurrUser curUser, List<Nrlrgt> list) throws Exception {
		LOGGER.info("开始权限复核未通过");
		String nrgtNames = "";
		try {
			nrmap.roleNrlrgtNO(list);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("复核未通过权限失败");
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			nrgtNames += list.get(i).getMnid()+"||";
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setTymo("角色管理");
		logfile.setRemk("权限复核（未通过）");
		logfile.setUsem(curUser.getUsnm());
		logfile.setVold("登录ip:"+curUser.getCurIP()+"权限复核（未通过）:"+nrgtNames);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}
	/**
	 * 权限复核
	 * 复核“通过”
	 */
	public boolean insertPrice(CurrUser curUser, List<Nrlrgt> list) throws Exception {
		LOGGER.info("开始权限复核通过");
		String nrgtNames = "";
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setTymo("角色管理");
		logfile.setRemk("权限复核（通过）");
		logfile.setUsem(curUser.getUsnm());
		logfile.setVold("登录产品:"+curUser.getProd()+",权限复核（未通过）:"+nrgtNames);
		for(int i=0;i<list.size();i++){
			nrgtNames += list.get(i).getMnid()+"||";
			try {
				Nrlrgt nrlrgt = list.get(i);
				if("2".equals(nrlrgt.getClas())){
					LOGGER.info("复核二级菜单");
					nrmap.isPrice(nrlrgt);//修改本级，二级菜单状态
					Nrlrgt nrgt = nrmap.selPrice(nrlrgt.getSqno(), nrlrgt.getMnid(), nrlrgt.getPtid());//查询上级菜单
					if(nrgt!=null) {
						LOGGER.info("该二级菜单的上级菜单存在："+nrgt.getMnid()+",复核上级菜单");
						nrmap.isPrice(nrgt);//修改上级菜单状态
					}else {
						LOGGER.info("插入二级菜单的上级菜单");
						nrmap.iPrice(nrlrgt.getPtid(), nrlrgt.getSqno(), nrlrgt.getMnid());//插入上级菜单并复核
					}
				}else if("3".equals(nrlrgt.getClas())){
					LOGGER.info("复核三级菜单");
					nrmap.isPrice(nrlrgt);//修改本级菜单，三级菜单
					Nrlrgt nrgtOne = nrmap.selPric(nrlrgt.getSqno(), nrlrgt.getMnid(), nrlrgt.getPtid());//查询三级菜单上上级菜单
					if(nrgtOne!=null) {//上上级菜单
						nrmap.isPrice(nrgtOne);//修改一级菜单
					}else {
						nrmap.isPri(nrlrgt.getPtid(), nrlrgt.getSqno(), nrlrgt.getMnid());//插入上上级菜单并复核
					}
					Nrlrgt nrgtTwo = nrmap.selPrice(nrlrgt.getSqno(), nrlrgt.getMnid(), nrlrgt.getPtid());//查询三级菜单上级菜单
					if(nrgtTwo!=null) {//上级菜单
						nrmap.isPrice(nrgtTwo);//修改二级菜单
					}else {
						nrmap.iPrice(nrlrgt.getPtid(), nrlrgt.getSqno(), nrlrgt.getMnid());//插入上级菜单并复核
					}
				}else if("1".equals(nrlrgt.getClas())){
					LOGGER.info("复核一级菜单");
					nrmap.isPrice(nrlrgt);//复核本级菜单
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				LOGGER.error("复核通过权限失败:"+list.get(i).getMnid()+",角色:"+list.get(i).getPtid());
				logfile.setVnew("失败");
				logfile.setVold("登录ip:"+curUser.getCurIP()+",权限复核（通过）:"+nrgtNames+",角色:"+list.get(0).getPtid());
				logfileCmdService.insertLog(logfile);
				return false;
			}
		}
		logfile.setVold("登录ip:"+curUser.getCurIP()+",权限复核（通过）:"+nrgtNames+",角色:"+list.get(0).getPtid());
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
		return true;
	}
	/**
	 * 添加权限的数据展示
	 */
	public PageInfo<NrlrgtUp> addpage(String sqno, String ptid, Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		List<NrlrgtUp> list = null;
		try {
			list = nrmap.selectAddNr(sqno, ptid);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		PageInfo<NrlrgtUp> page = new PageInfo<NrlrgtUp> (list);
		return page;
	}

	/**
	 * 查看权限的分页
	 */
	public PageInfo<NrlrgtUp> selpage(String sqno, String ptid, Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		List<NrlrgtUp> list = null;
	    try {
			//先查询所有的权限
	    	list = nrmap.showNrlrgt(sqno, ptid);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	    PageInfo<NrlrgtUp> page = new PageInfo<NrlrgtUp> (list);
		return page;
	}
	
	/**
	 * 删除权限的查看
	 */
	public PageInfo<NrlrgtUp> delpage(String sqno,String ptid, Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<NrlrgtUp> list = null;
	    try {
	    	list = nrmap.delnrlist(sqno, ptid);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	    PageInfo<NrlrgtUp> page = new PageInfo<NrlrgtUp> (list);
		return page;
	}


	/**
	 * 权限复核的查看
	 */
	public PageInfo<NrlrgtUp> fgpage(String sqno, String ptid, Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<NrlrgtUp> list = null;
	    try {
			list = nrmap.selnrlist(sqno, ptid);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	    PageInfo<NrlrgtUp> page = new PageInfo<NrlrgtUp> (list);
		return page;
	}
	
}
