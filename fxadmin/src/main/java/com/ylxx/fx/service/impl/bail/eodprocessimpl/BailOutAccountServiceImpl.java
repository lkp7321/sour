package com.ylxx.fx.service.impl.bail.eodprocessimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.bail.eodprocess.BailOutAccountMapper;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.service.bail.eodprocess.IBailOutAccountService;
import com.ylxx.fx.service.po.BailTranlist;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.TestFormatter;

@Service("bailOutAccountService")
public class BailOutAccountServiceImpl implements IBailOutAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BailOutAccountServiceImpl.class);
	@Resource
	private BailOutAccountMapper bailOutAccountMapper;
	@Resource
	private PdtRParaMapper pdtRParaMapper;
	
	//查询交易码
	public String seltrancode(){
		String result = "";
		try {
			List<HashMap<String, String>> trancodes = bailOutAccountMapper.querytrancode();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(trancodes));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//查询批量出金数据
	public String seleOutAccountList(String hddate,String status,String enddate,
			Integer pageNo,Integer pageSize){
		String result = "";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
	 	TestFormatter test = new TestFormatter();
	 	try {
			List<BailTranlist> outAccHDList = bailOutAccountMapper.selectOutAccountHDList(hddate, 
					status, enddate);
			for (int i = 0; i < outAccHDList.size(); i++) {
				outAccHDList.get(i).setUsam(test.getDecimalFormat(outAccHDList.get(i).getUsam(),2));
			}
			PageInfo<BailTranlist> page = new PageInfo<BailTranlist>(outAccHDList);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
	 	return result;
	}
	//核对提交
	public String hedui(String userKey,List<BailTranlist> list){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			for (int i = 0; i < list.size() ; i++) {
				boolean bo = bailOutAccountMapper.hedui(list.get(i).getLcno());
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd()
							+ "批量出金核对提交:交易流水号:" + list.get(i).getLcno()
							+ ",交易账号:" + list.get(i).getTrac()
							+ ",卡号:" + list.get(i).getCuac()
							+ ",状态："+list.get(i).getStcd()
							+ "核对提交为2成功!时间:" + nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("批量出金核对");
					loginfo.setRemk("核对提交");
					loginfo.setVold("登录产品:" + curUser.getProd() + "批量出金核对提交:交易流水号:" 
							+ list.get(i).getLcno()
							+ ",交易账号:" + list.get(i).getTrac()
							+ ",卡号:" + list.get(i).getCuac()
							+ ",状态："+list.get(i).getStcd()+"核对提交为2成功!");
					loginfo.setVnew("核对提交成功");
					loginfo.setProd(curUser.getProd());
					boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						if (i==list.size()-1) {
							result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
						}
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd()
							+ "批量出金核对提交:交易流水号:" + list.get(i).getLcno()
							+ ",交易账号:" + list.get(i).getTrac()
							+ ",卡号:" + list.get(i).getCuac()
							+ ",状态："+list.get(i).getStcd()
							+ "核对提交为2失败!时间:" + nowtime);
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;		
	}
	//核对取消
	public String cancelHedui(String userKey,List<BailTranlist> list){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			for (int i = 0; i < list.size() ; i++) {
				boolean bo = bailOutAccountMapper.cancelHedui(list.get(i).getLcno());
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd()
							+ "批量出金核对取消:交易流水号:" + list.get(i).getLcno()
							+ ",交易账号:" + list.get(i).getTrac()
							+ ",卡号:" + list.get(i).getCuac()
							+ ",状态："+list.get(i).getStcd()
							+ "核对取消成功!时间:" + nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("批量出金核对");
					loginfo.setRemk("核对取消");
					loginfo.setVold("登录产品:" + curUser.getProd() + "批量出金核对取消:交易流水号:" 
							+ list.get(i).getLcno()
							+ ",交易账号:" + list.get(i).getTrac()
							+ ",卡号:" + list.get(i).getCuac()
							+ ",状态："+list.get(i).getStcd()+"核对取消成功!");
					loginfo.setVnew("核对取消成功");
					loginfo.setProd(curUser.getProd());
					boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						if (i==list.size()-1) {
							result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
						}
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd()
							+ "批量出金核对取消:交易流水号:" + list.get(i).getLcno()
							+ ",交易账号:" + list.get(i).getTrac()
							+ ",卡号:" + list.get(i).getCuac()
							+ ",状态："+list.get(i).getStcd()
							+ "核对取消失败!时间:" + nowtime);
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;		
	}
	//复核
	public String fuhe(String userKey,List<BailTranlist> list){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			for (int i = 0; i < list.size() ; i++) {
				boolean bo = bailOutAccountMapper.fuhe(list.get(i).getLcno());
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd()
							+ "批量出金核对复核:交易流水号:" + list.get(i).getLcno()
							+ ",交易账号:" + list.get(i).getTrac()
							+ ",卡号:" + list.get(i).getCuac()
							+ ",状态："+list.get(i).getStcd()
							+ "-->复核成功!时间:" + nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("批量出金复核");
					loginfo.setRemk("复核");
					loginfo.setVold("登录产品:" + curUser.getProd() + "批量出金核对复核:交易流水号:" 
							+ list.get(i).getLcno()
							+ ",交易账号:" + list.get(i).getTrac()
							+ ",卡号:" + list.get(i).getCuac()
							+ ",状态："+list.get(i).getStcd()+"-->复核成功!");
					loginfo.setVnew("复核成功");
					loginfo.setProd(curUser.getProd());
					boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						if (i==list.size()-1) {
							result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
						}
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd()
							+ "批量出金核对复核:交易流水号:" + list.get(i).getLcno()
							+ ",交易账号:" + list.get(i).getTrac()
							+ ",卡号:" + list.get(i).getCuac()
							+ ",状态："+list.get(i).getStcd()
							+ "-->复核失败!时间:" + nowtime);
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;		
	}
}
