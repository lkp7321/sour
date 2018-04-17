package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.MaxpavpointMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.businesspara.MaxpavpointService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.Maxpavpoint;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.LoginUsers;
@Service("maxpavpointService")
public class MaxpavpointServiceImpl implements MaxpavpointService{
	@Resource
	private MaxpavpointMapper maxpointmap;
	@Resource(name="")
	private LogfileCmdService logfileCmdService;
	private static final Logger log = LoggerFactory.getLogger(MaxpavpointServiceImpl.class);

	/**
	 * 查询所有
	 */
	public List<Maxpavpoint> selMaxpavpoint(CurrUser curUser, String combogcd) {
		List<Maxpavpoint> list = null;
		try {
			list = maxpointmap.selectMaxpavpoint(curUser.getProd(), combogcd);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return list;
	}
	/**
	 * 分页查询数据
	 */
	public PageInfo<Maxpavpoint> selPageMaxpavpoint(CurrUser curUser,String combogcd,
			Integer pageNo, Integer pageSize){
		List<Maxpavpoint> list = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			list = maxpointmap.selectMaxpavpoint(curUser.getProd(), combogcd);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		PageInfo<Maxpavpoint> page = new PageInfo<Maxpavpoint>(list);
		return page;
	}

	/**
	 * 查询机构下拉框
	 */
	public List<Map<String, String>> queryMaxpoint(String prod,
			String userorgorgn, String userorgleve) {
		List<Map<String, String>> hamaplist = null;
		try {
			if(prod.equals("P001")||prod.equals("P002") ||prod.equals("P003")||prod.equals("P004")){
				hamaplist = maxpointmap.queryMaxpavOgcd(userorgorgn, userorgleve);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return hamaplist;
	}
	/**
	 * 添加页面的币别P002,P003
	 */
	public List<Map<String,String>> getMaxExnm(String userKey, String ogcd){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd=curUser.getProd();
		List<Map<String,String>> list = null;
		try {
			if(prcd.equals("P002")){
				list = maxpointmap.selMaxexnmP002(prcd, ogcd);
			}else if(prcd.equals("P003")){
				list = maxpointmap.selMaxexnmP003(prcd, ogcd);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 添加 保存操作
	 */
	public boolean adMaxpoint(String userKey, Maxpavpoint maxpoint) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd = curUser.getProd();
		int a = 0;
		try {
			a = maxpointmap.insertMaxpoint(prcd,maxpoint);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加最大优惠机构:"
					+ maxpoint.getOgnm() + ",机构编号:" + maxpoint.getOgcd() + ",币别对:"
					+ maxpoint.getExnm() + ",币别对编号" + maxpoint.getExcd() + ",优惠点数:"
					+ maxpoint.getMxfv() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());

			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("最大优惠设置");
			loginfo.setRemk("添加");
			loginfo.setVold("登录产品:" + curUser.getProd() + "添加最大优惠机构:"
					+ maxpoint.getOgnm() + ",机构编号:" + maxpoint.getOgcd() + ",币别对:"
					+ maxpoint.getExnm() + ",币别对编号" + maxpoint.getExcd() + ",优惠点数:"
					+ maxpoint.getMxfv());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加最大优惠机构:"
					+ maxpoint.getOgnm() + ",机构编号:" + maxpoint.getOgcd() + ",币别对:"
					+ maxpoint.getExnm() + ",币别对编号" + maxpoint.getExcd() + ",优惠点数:"
					+ maxpoint.getMxfv() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	//修改
	public boolean upMaxpoint(String userKey, Maxpavpoint maxpoint) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd = curUser.getProd();
		int a = 0;
		try {
			a = maxpointmap.updateMaxpoint(prcd, maxpoint);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "修改最大优惠机构:"
					+ maxpoint.getOgnm() + ",机构编号:" + maxpoint.getOgcd() + ",币别对:"
					+ maxpoint.getExnm() + ",币别对编号" + maxpoint.getExcd() + ",优惠点数:"
					+ maxpoint.getMxfv() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("最大优惠设置");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "修改最大优惠机构:"
					+ maxpoint.getOgnm() + ",机构编号:" + maxpoint.getOgcd() + ",币别对:"
					+ maxpoint.getExnm() + ",币别对编号" + maxpoint.getExcd() + ",优惠点数:"
					+ maxpoint.getMxfv());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "修改最大优惠机构:"
					+ maxpoint.getOgnm() + ",机构编号:" + maxpoint.getOgcd() + ",币别对:"
					+ maxpoint.getExnm() + ",币别对编号" + maxpoint.getExcd() + ",优惠点数:"
					+ maxpoint.getMxfv() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	//删除
	public boolean deMaxpoint(String userKey, Maxpavpoint maxpoint) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd = curUser.getProd();
		int a = 0;
		try {
			a = maxpointmap.deleteMaxpoint(prcd, maxpoint.getOgcd(), maxpoint.getExnm());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "删除最大优惠机构编号:" + maxpoint.getOgcd()
					+ ",币别对:" + maxpoint.getExnm() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("最大优惠设置");
			loginfo.setRemk("删除");
			loginfo.setVold("登录产品:" + curUser.getProd() + "删除最大优惠机构编号:" + maxpoint.getOgcd()
					+ ",币别对:" + maxpoint.getExnm());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		}else{
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "删除最大优惠机构编号:" + maxpoint.getOgcd()
					+ ",币别对:" + maxpoint.getExnm() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}
	
}
