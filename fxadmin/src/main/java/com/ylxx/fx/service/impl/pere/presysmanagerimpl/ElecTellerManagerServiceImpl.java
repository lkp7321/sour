package com.ylxx.fx.service.impl.pere.presysmanagerimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.ElecTellerManagerVo;
import com.ylxx.fx.core.mapper.LogfileCmdMapper;
import com.ylxx.fx.core.mapper.pere.presysmanager.ElecTellerManagerMapper;
import com.ylxx.fx.service.pere.presysmanager.IElecTellerManagerService;
import com.ylxx.fx.service.po.CustLevelBean;
import com.ylxx.fx.service.po.ElecTellerManager;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.QueryNationCode;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodeCust;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("iElecTellerManagerService")

public class ElecTellerManagerServiceImpl implements IElecTellerManagerService{
	private static final Logger LOGGER = LoggerFactory.getLogger(ElecTellerManagerServiceImpl.class);
	@Resource
	private ElecTellerManagerMapper elecTellerManagerMapper;
	@Resource(name="logfileCmdMapper")
	private LogfileCmdMapper logfileCmdMapper;
	//初始化电子柜员信息列表
	public String selcTellerList(){
		String result="";
		try {
			List<ElecTellerManager> list= elecTellerManagerMapper.selctTellerList();
			if (list.size()>0) {
				LOGGER.info("条数为："+list.size());
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			}else{
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//分页
	public PageInfo<ElecTellerManager> pageSelcAllTellerList(Integer pageNo, Integer pageSize){
		List<ElecTellerManager> list = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			list = elecTellerManagerMapper.selctTellerList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		PageInfo<ElecTellerManager> page = new PageInfo<ElecTellerManager>(list);
		return page;
	}
	//导出
	public List<ElecTellerManager> selcAllTellerList(){
		List<ElecTellerManager> list = null;
		try {
			list = elecTellerManagerMapper.selctTellerList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return list;
	}
	
	//条件查询电子柜员信息列表
	public String selcPreMessage(String trtltxt, String comdata1, String bhidp,Integer pageNo,Integer pageSize){
		String result="";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			List<ElecTellerManager> list =elecTellerManagerMapper.selectTOgcd(trtltxt.trim(),comdata1.trim(),bhidp.trim());
			if (list.size()>0) {
				LOGGER.info("条数为："+list.size());
				PageInfo<ElecTellerManager> page = new PageInfo<ElecTellerManager>(list);
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}else{
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
		
		
	}

	@Override
	public String deleteEleTeller(String userKey,String tellerid) throws Exception {
			 String result="";
			 CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
			/*//测试
			 CurrUser curUser=new CurrUser();
			 curUser.setCstn("");
			 curUser.setCurIP("");
			 curUser.setOrgn("");
			 curUser.setProd("P999");
			 curUser.setUsnm("chenrui");
			 curUser.setUspt("");*/
			 try{
				 boolean flag =elecTellerManagerMapper.deleteEleT(tellerid.trim());
				 String nowtime = DataTimeClass.getCurDateTime();
				 if (flag == true) {
					 LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
								+ " 登录产品:" + curUser.getProd() + "删除柜员信息 柜员:"
								+ tellerid + ",成功!时间:"
								+ DataTimeClass.getCurDateTime());
						Logfile loginfo = new Logfile();
						loginfo.setRzdt(nowtime.substring(0,8));
						loginfo.setRzsj(nowtime.substring(9));
						loginfo.setUsem(curUser.getUsnm());
						loginfo.setTymo("柜员信息管理 ");
						loginfo.setRemk("删除");
						loginfo.setVold("删除柜员ID:"+ tellerid);
						loginfo.setVnew("成功");
						loginfo.setProd(curUser.getProd());
						logfileCmdMapper.insertLog(loginfo);
						result= ResultDomain.getRtnMsg(ErrorCodePrice.E_30.getCode(), "删除成功");
				 }
				 }catch(Exception e){
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "删除柜员信息 柜员:"
					+ tellerid + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
					result= ResultDomain.getRtnMsg(ErrorCodePrice.E_31.getCode(), "删除失败");
				}
			return result;
	}

	@Override
	public String updateEleT(String userKey,ElecTellerManagerVo etmVo) throws Exception {
		String result = ""; 
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		/* CurrUser curUser=new CurrUser();
		 curUser.setCstn("");
		 curUser.setCurIP("");
		 curUser.setOrgn("");
		 curUser.setProd("P999");
		 curUser.setUsnm("chenrui");
		 curUser.setUspt("");*/
		try {
			boolean flag = elecTellerManagerMapper.updateEleT(etmVo);
			String nowtime = DataTimeClass.getCurDateTime();
			if (flag == true) {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
				+ " 登录产品:" + curUser.getProd() + "修改柜员信息 柜员:"
				+ etmVo.getTrtl() + "柜员机构:" +etmVo.getBhid()+ "外管局机构:"+etmVo.getOgcd()
				+ "柜员渠道:"+etmVo.getChnl() + "柜员类型:"+etmVo.getTltp()+ ",成功!时间:"
				+ DataTimeClass.getCurDateTime());
				
		Logfile loginfo = new Logfile();
		loginfo.setRzdt(nowtime.substring(0,8));
		loginfo.setRzsj(nowtime.substring(9));
		loginfo.setUsem(curUser.getUsnm());
		loginfo.setTymo("柜员信息管理 ");
		loginfo.setRemk(" 修改 ");
		loginfo.setVold("修改柜员信息 柜员:"
				+ etmVo.getTrtl() + "柜员机构:" +etmVo.getBhid()+ "外管局机构:"+etmVo.getOgcd()
				+ "柜员渠道:"+etmVo.getChnl() + "柜员类型:"+etmVo.getTltp() );
		loginfo.setVnew("成功");
		loginfo.setProd(curUser.getProd());
		logfileCmdMapper.insertLog(loginfo);
		result= ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), "修改成功");
				}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
			+ " 登录产品:" + curUser.getProd() + "修改柜员信息 柜员:"
			+ etmVo.getTrtl() + "柜员机构:" +etmVo.getBhid()+ "外管局机构:"+etmVo.getOgcd()
			+ "柜员渠道:"+etmVo.getChnl() + "柜员类型:"+etmVo.getTltp() + ",失败!时间:"
			+ DataTimeClass.getCurDateTime());
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), "修改失败");
		}
		return result;
	}

	@Override
	public String isTrtlBhidExist(String userKey,String bhid, String trtl) throws Exception {
		String result="";
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		 /*CurrUser curUser=new CurrUser();
		 curUser.setCstn("");
		 curUser.setCurIP("");
		 curUser.setOrgn("");
		 curUser.setProd("P999");
		 curUser.setUsnm("chenrui");
		 curUser.setUspt("");*/
		 try{
			 List<ElecTellerManager> list =elecTellerManagerMapper.isTrtlBhidExist(trtl.trim(), bhid.trim());
			 if (list.size()>0) {
				 	LOGGER.info("用户：" + curUser.getUsnm() + ",登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + " 用户"+trtl+"已存在 "
					+ "时间" + DataTimeClass.getCurDateTime());
				    LOGGER.info("条数为："+list.size());
					result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			 }
			 else{
				 result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
			 }
			 }catch(Exception e){
				 e.printStackTrace();
				 LOGGER.error("用户：" + curUser.getUsnm() + ",登录IP:" + curUser.getCurIP()
					+ "登录产品:" + curUser.getProd() + " 用户"+trtl+"可以注册 "
					+ "时间" + DataTimeClass.getCurDateTime());
				 result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
			}
		return result;
	}
	//初始化外管局机构
	@Override
	public String selectOgcd() throws Exception {
		
		String result="";
		try {
			List<ElecTellerManager> list= elecTellerManagerMapper.selectOgcd();
			if (list.size()>0) {
				LOGGER.info("条数为："+list.size());
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			}
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
		
	}
	//用户添加机构框
	@Override
	public String selectTOgcd(String orgn, String usnm,Integer pageNo,Integer pageSize) throws Exception {
		String result="";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			List<ElecTellerManager> list =elecTellerManagerMapper.selOrganTab(usnm.trim(), orgn.trim());
			if (list.size()>0) {
				LOGGER.info("条数为："+list.size());
				PageInfo<ElecTellerManager> page = new PageInfo<ElecTellerManager>(list);
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}else{
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}

	@Override
	public String selectChnlP() throws Exception {
		String result="";
		try {
			List<HashMap<String, String>> list= elecTellerManagerMapper.selectChnlP();
			if (list.size()>0) {
				LOGGER.info("条数为："+list.size());
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			}
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}

	
	//添加外管局机构
	@Override
	public String insertEleT(String userKey,ElecTellerManagerVo etmVo) throws Exception {
		String result = ""; 
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser=new CurrUser();
		 curUser.setCstn("");
		 curUser.setCurIP("");
		 curUser.setOrgn("");
		 curUser.setProd("P999");
		 curUser.setUsnm("chenrui");
		 curUser.setUspt("");*/
		
		try {
			boolean flag = elecTellerManagerMapper.insertEleT(etmVo);
			String nowtime = DataTimeClass.getCurDateTime();
			 if (flag == true) {
				 LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加外管局机构:"
					+ etmVo.getOgcd() + ",柜员:" + etmVo.getTrtl() + ",密码:"
					+ etmVo.getPass() + ",柜员类型：" + etmVo.getTltp() + ",柜员机构:"
					+ etmVo.getBhid() + ",柜员渠道：" + etmVo.getChnl() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
					Logfile loginfo = new Logfile();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("柜员信息管理 ");
					loginfo.setRemk("添加");
					loginfo.setVold("添加外管局机构:"
							+ etmVo.getOgcd() + ",柜员:" + etmVo.getTrtl() + ",密码:"
							+ etmVo.getPass() + ",柜员类型：" + etmVo.getTltp() + ",柜员机构:"
							+ etmVo.getBhid() + ",柜员渠道：" + etmVo.getChnl());
					loginfo.setVnew("成功");
					loginfo.setProd(curUser.getProd());
					logfileCmdMapper.insertLog(loginfo);
					result= ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(), "添加成功");
			 }
			 }catch(Exception e){
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
				+ " 登录产品:" + curUser.getProd() + "添加外管局机构:"
				+ etmVo.getOgcd() + ",柜员:" + etmVo.getTrtl() + ",密码:"
				+ etmVo.getPass() + ",柜员类型：" + etmVo.getTltp() + ",柜员机构:"
				+ etmVo.getBhid() + ",柜员渠道：" + etmVo.getChnl() + ",失败!时间:"
				+ DataTimeClass.getCurDateTime());
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_21.getCode(), "添加失败");
			}
		return result;
	}
	

}
