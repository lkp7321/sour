package com.ylxx.fx.service.impl.person.systemimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.person.system.BailSystemMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.system.BailSystemService;
import com.ylxx.fx.service.po.BailExceBean;
import com.ylxx.fx.service.po.BailMt4Bean;
import com.ylxx.fx.service.po.BailTranlist;
import com.ylxx.fx.service.po.Cmmptparac;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.TestFormatter;
@Service("bailSystemService")
public class BailSystemServiceImpl implements BailSystemService{
	private static final Logger log = LoggerFactory.getLogger(BailSystemServiceImpl.class);
	@Resource
	private BailSystemMapper bailSystemMap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;

	@Override
	public List<Cmmptparac> getAllBailSysPrice() {
		return bailSystemMap.selAllBailSysPrice();
	}

	@Override
	public boolean updates(String userKey, Cmmptparac bean) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			a = bailSystemMap.update(bean);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "更新产品参数:参数编号:"
					+ bean.getPaid() + ",参数名称:" + bean.getRemk()
					+ ",参数值:" + bean.getValu() + ",参数状态:"
					+ bean.getStat() + "成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("产品参数");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "更新产品参数:参数编号:"
					+ bean.getPaid() + ",参数名称:" + bean.getRemk()
					+ ",参数值:" + bean.getValu() + ",参数状态:"
					+ bean.getStat());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
				+ " 登录产品:" + curUser.getProd() + "更新产品参数:参数编号:"
				+ bean.getPaid() + ",参数名称:" + bean.getRemk()
				+ ",参数值:" + bean.getValu() + ",参数状态:"
				+ bean.getStat() + "失败!时间:"
				+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	//交易流水查询
	public List<Map<String, Object>> querytrancodes() {
		// TODO Auto-generated method stub
		return bailSystemMap.querytrancode();
	}

	//lcno,trdt,trtm,trds,cuno,cuac,trac,trsn,
	//usam,csfg,mt4c,stcd,ercd
	public List<BailTranlist> getsFxipTranlist(String acno, String strcuac,
			String trdtbegin, String trdtend, String trcd) {
		List<BailTranlist> list = null;
		try {
			list = bailSystemMap.selectFxipTranlist(acno, strcuac, trdtbegin, trdtend, trcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(list!=null && list.size()>0){
			TestFormatter test = new TestFormatter();
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setTrac(list.get(i).getAcco());
				list.get(i).setUsam(test.getDecimalFormat(list.get(i).getUsam()+"",2));
				list.get(i).setMt4c(list.get(i).getNote());
			}
		}
		return list;
	}
	
	//异常交易监控
	//lcno,trdt,trtm,cuno,cuac,acco,trcd,trds,usam,csfg,stcd,ercd
	public List<BailExceBean> bailExceptionData(String curDate,String toDate) {
		List<BailExceBean> list = null;
		try {
			list = bailSystemMap.selectExceptionList(curDate.trim(),toDate);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	//Mt4组设置
	public List<BailMt4Bean> queryBailMt4Para() {
		List<BailMt4Bean> list = null;
		try {
			list = bailSystemMap.selectMt4User();
		} catch (Exception e) {
			 log.error(e.getMessage(),e);
		}
		return list;
	}
	//添加
	public boolean insertPtpara(String userKey, BailMt4Bean cmmbean) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			a = bailSystemMap.add(cmmbean);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (a>0) {
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加MT4用户组设置:组号:"
					+ cmmbean.getGpty() + ",组名:" + cmmbean.getName()
					+ ",描述:" + cmmbean.getNote() + ",使用状态:"
					+ cmmbean.getUsfg() + "成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("MT4用户组设置");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "添加MT4用户组设置:组号:"
					+ cmmbean.getGpty() + ",组名:" + cmmbean.getName()
					+ ",描述:" + cmmbean.getNote() + ",使用状态:"
					+ cmmbean.getUsfg());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		} else {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加MT4用户组设置:组号:"
					+ cmmbean.getGpty() + ",组名:" + cmmbean.getName()
					+ ",描述:" + cmmbean.getNote() + ",使用状态:"
					+ cmmbean.getUsfg()+ "失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}
	//修改
	public boolean updatePtpara(String userKey, BailMt4Bean cmmbean) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			a = bailSystemMap.updateMt4(cmmbean);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (a>0) {
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "更新MT4用户组设置:组号:"
					+ cmmbean.getGpty() + ",组名:" + cmmbean.getName()
					+ ",描述:" + cmmbean.getNote() + ",使用状态:"
					+ cmmbean.getUsfg() + "成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("MT4用户组设置");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "更新MT4用户组设置:组号:"
					+ cmmbean.getGpty() + ",组名:" + cmmbean.getName()
					+ ",描述:" + cmmbean.getNote() + ",使用状态:"
					+ cmmbean.getUsfg());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		} else {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "更新MT4用户组设置:组号:"
					+ cmmbean.getGpty() + ",组名:" + cmmbean.getName()
					+ ",描述:" + cmmbean.getNote() + ",使用状态:"
					+ cmmbean.getUsfg()+ "失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}
	
	//异常交易处理
	public List<Map<String, Object>> seltrancodes() {
		return bailSystemMap.querytrancodeExcep();
	}
	//查询数据
	//lcno,trdt,trtm,cuno,cuac,acco,trcd,trds,usam,cxfg,stcd,ercd
	public List<BailExceBean> bailExceptionData(String curDate,String tranCode,String toDate) {
		List<BailExceBean> list = null;
		try {
			list = bailSystemMap.setExceptionList(curDate.trim(), toDate, tranCode);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	//恢复
	public String  updateInitialize(String userKey,String tranCode,List<BailExceBean> bailExceList){
		int a = 0;
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		//批量出金和余额利息并账有点不一样，批量出金只涉及到一张流水表，余额利息并账涉及到余额录入表和流水表
		try {
			if("M5704".equals(tranCode)){
				//批量出金时更新流水表中状态为1--MT4出金成功
				bailSystemMap.updateInitialize1(bailExceList);
			}else {
				String trcd ="";
				bailSystemMap.updateInitialize2(bailExceList);
				for (int j = 0; j < bailExceList.size(); j++) {
					if("M5711".equals(bailExceList.get(j).getTrcd())){
						trcd="M5715";
					}else if("M5714".equals(bailExceList.get(j).getTrcd())){
						trcd="M5716";
					}
					a = bailSystemMap.updateInitialize3(bailExceList.get(j).getLcno(),bailExceList.get(j).getTrdt(),trcd);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			//初始化成功
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "异常交易处理--》交易码为："+tranCode+"，点击恢复初始按钮，成功!时间:"
					+ DataTimeClass.getCurDateTime());
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "初始化成功");
		}else{
			//初始化失败
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "异常交易处理--》交易码为："+tranCode+"，点击恢复初始按钮，失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "初始化失败");
		}
	}
	
	public String updateSuccess(String userKey,String tranCode,List<BailExceBean> bailExceList){
		//批量出金和余额利息并账有点不一样，批量出金只涉及到一张流水表，余额利息并账涉及到余额录入表和流水表
		/*if("M5704".equals(tranCode)){
			//批量出金时更新流水表中状态为S
		}else {
			//余额并账、利息并账更新流水表中状态为S
		}*/
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag =false;
		try {
			bailSystemMap.updateSuccess(bailExceList);
			flag = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
		}
		String returnMessage="";
		if(flag){
			returnMessage="处理成功";
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "异常交易处理--》交易码为："+tranCode+"，点击处理成功按钮，成功!时间:"
					+ DataTimeClass.getCurDateTime());
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), returnMessage);
		}else{
			returnMessage="处理失败";
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "异常交易处理--》交易码为："+tranCode+"，点击处理处理按钮，失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), returnMessage);
		}
	}
}
