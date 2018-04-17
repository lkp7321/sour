package com.ylxx.fx.service.impl.person.systemimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.system.OrganizationMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.system.OrganizationService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.Trd_ogcd;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodeSystem;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
@Service("organService")
public class OrganizationServiceImpl implements OrganizationService{
	@Resource
	private OrganizationMapper organmap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	private List<Trd_ogcd> list = null;
	//获取当前用户的机构信息
	public Trd_ogcd getUserog(String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return organmap.queryOgcdLeve(curUser.getOrgn());
	}
	
	
	/**
	 * 机构添加
	 */
	public String insert(Trd_ogcd trdOgcd, String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			if(trdOgcd.getLeve().equals("1")){
				LOGGER.info("trd_organ 表插入一级分行");
				organmap.insertOrgan(trdOgcd);
				LOGGER.info("trd_ogcd  表插入一级分行");
				organmap.insertOgcd(trdOgcd);
			}else{
				organmap.insertOgcd(trdOgcd);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_11.getCode(), null);
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setVnew("成功");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"机构号:"+trdOgcd.getOgcd()+"机构名:"+trdOgcd.getOgnm());
		logfile.setTymo("机构管理");
		logfile.setRemk("机构添加");
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "机构添加成功");
	}
	
	//机构修改
	public String updateOg(Trd_ogcd trdOgcd, String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = false;
		if(trdOgcd.getLeve().equals("1")){
			organmap.update(trdOgcd);
			organmap.updateOgran(trdOgcd);
			flag = true;
		}else{
			organmap.update(trdOgcd);
			flag =true;
		}
		if(flag){
			Logfile logfile = new Logfile();
			logfile.setProd(curUser.getProd());
			logfile.setUsem(curUser.getUsnm());
			logfile.setVnew("成功");
			logfile.setVold("登录ip:"+curUser.getCurIP()+"机构号:"+trdOgcd.getOgcd()+"机构名:"+trdOgcd.getOgnm());
			logfile.setTymo("机构管理");
			logfile.setRemk("机构修改");
			logfileCmdService.insertLog(logfile);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "机构修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_12.getCode(), null);
		}
	}
	//添加获取数据
	public String curLeveList(String userKey, String leve) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String userogcd = curUser.getCstn();
		try{
			if (leve.trim().equals("1")) {
				list = organmap.queryOrgan();
			} else if (leve.trim().equals("3")) {
				list = organmap.oneTwoLeve();
			} else {
				list = organmap.selleve(userogcd);
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
		}
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), JSON.toJSONString(list));
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_13.getCode(), null);
		}
	}
	//修改获取数据
	public String organUpList(String userKey, String leve) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String userogcd = curUser.getCstn();
		if (leve.trim().equals("1")) {
			list = queryOrgans();
		} else {
			list = upQueryOrgan(userogcd);
		}
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_14.getCode(), null);
		}
	}
	public List<Trd_ogcd> queryOrgans(){
		try {
			list = organmap.queryOrgan();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return list;
	}
	public List<Trd_ogcd> upQueryOrgan(String userogcd){
		try {
			if(userogcd.equals("0001")){
				list = organmap.upQueryOrgan1();
			}else{
				list = organmap.upQueryOrgan2(userogcd);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return list;
	}
	//获取合并的下拉框的机构列表
	public String getHeBing(String prod, String ogup, String ogcd){
		try {
			list = organmap.queryOneOgcd(prod, ogup, ogcd);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), JSON.toJSONString(list));
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_15.getCode(), null);
		}
	}
	//删除机构
	public String delog(String userKey,String ogcd){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		int b = 0;
		a = organmap.queryUser(curUser.getProd(), ogcd);
		if(a>0){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_20.getCode(), null);//特殊的弹窗
		}else{
			b = organmap.deleCytp(ogcd);
			if(b>0){
				Logfile logfile = new Logfile();
				logfile.setProd(curUser.getProd());
				logfile.setUsem(curUser.getUsnm());
				logfile.setVnew("成功");
				logfile.setVold("登录ip:"+curUser.getCurIP()+"机构号:"+ogcd);
				logfile.setTymo("机构管理");
				logfile.setRemk("机构删除");
				logfileCmdService.insertLog(logfile);
				return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "机构删除成功");
			}else{
				return ResultDomain.getRtnMsg(ErrorCodeSystem.E_19.getCode(), null);
			}
		}
	}
	
	//合并机构
	public String heBing(String userKey, String newogcd, String oldogcd){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		int a = 0;
		try {
			a = organmap.queryUser(prod, oldogcd);
			int b = 0;
			if(a>0){
				LOGGER.info("该机构下还有签约用户信息：开始移出");
				organmap.updateOgcd(prod,newogcd,oldogcd);//客户ogcd
				organmap.updateRgog(prod,newogcd,oldogcd);//客户rgog
				int c = organmap.deleCytp(oldogcd);	//删除旧的机构
				if(c>0){
					return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "机构合并（删除）成功");
				}else{
					return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "机构合并（删除）失败");
				}
			}else{
				LOGGER.info("该机构下没有签约用户可以直接删除：");
				b = organmap.deleCytp(oldogcd);
				if(b>0){
					return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "机构合并（删除）成功");
				}
				else{
					return ResultDomain.getRtnMsg(ErrorCodeSystem.E_18.getCode(), "机构合并（删除）失败");
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_18.getCode(), "机构合并（删除）失败");
		}
	}
	
	//分页获取数据
	public PageInfo<Trd_ogcd> getPageAllOgcd(String userKey, Integer pageNo, Integer pageSize) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Trd_ogcd trdOgcd = organmap.queryOgcdLeve(curUser.getCstn());
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<Trd_ogcd> lists = null;
	    try {
	    	lists = organmap.queryAllOrgan(trdOgcd.getLeve(), trdOgcd.getOgcd());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	    PageInfo<Trd_ogcd> page = new PageInfo<Trd_ogcd>(lists);
	    if(page.getList()!=null&&page.getList().size()>0){
			for (int i = 0; i < page.getList().size(); i++) {
				page.getList().get(i).setUsfg(page.getList().get(i).getUsfg().equals(
						"0") ? "开启" : "停用");
			}
		}
		return page;
	}
	//获取所有机构列表,未分页
	public String getAllOgcd(String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Trd_ogcd trdOgcd = organmap.queryOgcdLeve(curUser.getCstn());
		list = organmap.queryAllOrgan(trdOgcd.getLeve(), trdOgcd.getOgcd());
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
			list.get(i).setUsfg(list.get(i).getUsfg().equals(
						"0") ? "开启" : "停用");
			}
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_0.getCode(), null);
		}
	}
}
