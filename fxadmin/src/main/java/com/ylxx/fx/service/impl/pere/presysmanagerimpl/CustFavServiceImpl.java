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
import com.ylxx.fx.core.domain.CustFavVo;
import com.ylxx.fx.core.mapper.LogfileCmdMapper;
import com.ylxx.fx.core.mapper.pere.presysmanager.CustFavMapper;
import com.ylxx.fx.service.pere.presysmanager.ICustFavService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("custFavService")
public class CustFavServiceImpl implements ICustFavService{
private static final Logger LOGGER = LoggerFactory.getLogger(CustFavServiceImpl.class);
	@Resource
	private CustFavMapper custFavMapper;
	@Resource(name="logfileCmdMapper")
	private LogfileCmdMapper logfileCmdMapper;

	@Override
	public String selcCustFavList(String cuno, String ogcd, String stat,Integer pageNo,Integer pageSize) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
	String result="";
		try {
			List<HashMap<String,String>> list=custFavMapper.selectCustFavList(cuno,ogcd,stat);
			if(list.size()>0) {
			PageInfo<HashMap<String,String>> page=new PageInfo<HashMap<String,String>>(list);
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}else {
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(),"查询无结果");
			}
		}catch(Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询异常");
		}
		
		return result;
	}

	@Override
	public String deleteCustFav(String userKey, String cuno) {
		String result="";
		 CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		 try{
			 boolean flag = custFavMapper.delCustFav(cuno);
			 if (flag == true) {
				 LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "删除柜员信息 柜员:"
							+ cuno + ",成功!时间:"
							+ DataTimeClass.getCurDateTime());
					Logfile loginfo = new Logfile();
					loginfo.setRzdt(DataTimeClass.getCurDateTime().substring(0,8));
					loginfo.setRzsj(DataTimeClass.getCurDateTime().substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("柜员信息管理 ");
					loginfo.setRemk("删除");
					loginfo.setVold("删除柜员:"+ cuno);
					loginfo.setVnew("成功");
					loginfo.setProd(curUser.getProd());
					logfileCmdMapper.insertLog(loginfo);
					result= ResultDomain.getRtnMsg(ErrorCodePrice.E_30.getCode(), "删除成功");
			 }
			 }catch(Exception e){
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
				+ " 登录产品:" + curUser.getProd() + "删除柜员信息 柜员:"
				+ cuno + ",失败!时间:"
				+ DataTimeClass.getCurDateTime());
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_31.getCode(), "删除失败");
			}
		return result;
}

	@Override
	public String selectETBhid(String ogcd, String ognm,Integer pageNo,Integer pageSize) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
	String result="";
		try {
			List<HashMap<String,String>> list=custFavMapper.selectETBhid(ogcd.trim(),ognm.trim());
			if(list.size()>0) {
			PageInfo<HashMap<String,String>> page=new PageInfo<HashMap<String,String>>(list);
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		}else {
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(),"查询无结果");
		}
		}catch(Exception e) {
			e.printStackTrace();
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查询异常");
		}
		return result;
	}

	@Override
	public String updCustFav(CustFavVo custFav) {
		String result="";
		 CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(custFav.getUserKey());
		 try{
			 boolean flag = custFavMapper.updCustFav(custFav.getFvgh(),custFav.getFvjh(),custFav.getStat(),custFav.getOgcd(),custFav.getCuno());
			 if (flag == true) {
				 LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "修改柜员信息 柜员:"
							+ custFav.getOgcd() + ",客户:" + custFav.getCuno() + ",购汇优惠:"
							+ custFav.getFvgh() + ",结汇优惠：" + custFav.getFvjh() + ",状态:"
							+ custFav.getStat() + ",成功!时间:"
							+ DataTimeClass.getCurDateTime());
					Logfile loginfo = new Logfile();
					loginfo.setRzdt(DataTimeClass.getCurDateTime().substring(0,8));
					loginfo.setRzsj(DataTimeClass.getCurDateTime().substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("柜员信息管理 ");
					loginfo.setRemk("修改");
					loginfo.setVold("修改柜员信息 柜员:"
							+ custFav.getOgcd() + ",客户:" + custFav.getCuno() + ",购汇优惠:"
							+ custFav.getFvgh() + ",结汇优惠：" + custFav.getFvjh() + ",状态:"
							+ custFav.getStat());
					loginfo.setVnew("成功");
					loginfo.setProd(curUser.getProd());
					logfileCmdMapper.insertLog(loginfo);
					result= ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), "修改成功");
			 }
			 }catch(Exception e){
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
				+ " 登录产品:" + curUser.getProd() + "修改柜员信息 柜员:"
				+ custFav.getOgcd() + ",客户:" + custFav.getCuno() + ",购汇优惠:"
				+ custFav.getFvgh() + ",结汇优惠：" + custFav.getFvjh() + ",状态:"
				+ custFav.getStat() + ",失败!时间:"
				+ DataTimeClass.getCurDateTime());
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), "修改失败");
			}
		return result;	
		}


	@Override
	public String selCunoExist(String cuno, String userKey) {
		String result="";
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			int count = custFavMapper.selCunoExist(cuno);
			if (count>0) {
				LOGGER.info("用户：" + curUser.getUsnm() + ",登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + " 用户"+cuno+"已存在 "
						+ "时间" + DataTimeClass.getCurDateTime());
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "该用户已存在");
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + ",登录IP:" + curUser.getCurIP()
				+ "登录产品:" + curUser.getProd() + " 用户"+cuno+"可以注册 "
				+ "时间" + DataTimeClass.getCurDateTime());
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "该用户可以使用");
			}
		} catch (Exception e) {
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "查重异常");
		}
		return result;
	}

	@Override
	public String insCustFav(CustFavVo custFav) {
		String result="";
		/*CurrUser curUser=new CurrUser();
		 curUser.setProd("P999");
		 curUser.setUsnm("chenrui");*/
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(custFav.getUserKey());
		try{
			 custFavMapper.insCustFav(custFav);
				 LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "添加机构:"
							+ custFav.getOgcd() + ",客户:" + custFav.getCuno() + ",购汇优惠:"
							+ custFav.getFvgh() + ",结汇优惠：" + custFav.getFvjh() + ",状态:"
							+ custFav.getStat() + ",成功!时间:"
							+ DataTimeClass.getCurDateTime());
					Logfile loginfo = new Logfile();
					loginfo.setRzdt(DataTimeClass.getCurDateTime().substring(0,8));
					loginfo.setRzsj(DataTimeClass.getCurDateTime().substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("柜员信息管理 ");
					loginfo.setRemk("添加");
					loginfo.setVold("添加外管局机构:"
							+ custFav.getOgcd() + ",客户:" + custFav.getCuno() + ",购汇优惠:"
							+ custFav.getFvgh() + ",结汇优惠：" + custFav.getFvjh() + ",状态:"
							+ custFav.getStat());
					loginfo.setVnew("成功");
					loginfo.setProd(curUser.getProd());
					logfileCmdMapper.insertLog(loginfo);
					result= ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(), "添加成功");
			 }catch(Exception e){
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
				+ " 登录产品:" + curUser.getProd() + "添加外管局机构:"
				+ custFav.getOgcd() + ",客户:" + custFav.getCuno() + ",购汇优惠:"
				+ custFav.getFvgh() + ",结汇优惠：" + custFav.getFvjh() + ",状态:"
				+ custFav.getStat() + ",失败!时间:"
				+ DataTimeClass.getCurDateTime());
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_21.getCode(), "添加失败");
			}
		return result;	
	}
}
