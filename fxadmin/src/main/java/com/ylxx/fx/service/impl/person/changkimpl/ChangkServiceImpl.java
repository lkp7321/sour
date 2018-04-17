package com.ylxx.fx.service.impl.person.changkimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.PpsyBeanDomain;
import com.ylxx.fx.core.mapper.person.changk.ChangkMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.changk.ChangkService;
import com.ylxx.fx.service.po.Ck_Dictionary;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.PpsyBean;
import com.ylxx.fx.service.po.TbCk_ppcontrol;
import com.ylxx.fx.service.po.TbCk_rulet;
import com.ylxx.fx.service.po.TrdSpcut;
import com.ylxx.fx.service.po.TrdTynm;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodeCk;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("selckService")
public class ChangkServiceImpl implements ChangkService {
	
	@Resource
	private ChangkMapper ckmap;
	private static final Logger log = LoggerFactory.getLogger(ChangkServiceImpl.class);
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	
	//------------------------敞口流水查询
	//获取币别
	public String getUSDExnm(String userKey) {
		// TODO Auto-generated method stub
		String msg="";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		List<String> usdExnm = ckmap.selUSDExnm(curUser.getProd());
		if(usdExnm.size()>0&&usdExnm!=null){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_00.getCode(), usdExnm);
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null); 
		}
		return msg;
	}

	//获取数据
	public List<PpsyBean> getCondition(String userKey,String sartDate, String endDate,
			String strExnm, String lkno) {
		// TODO Auto-generated method stub
		List<PpsyBean> Ppsylist = null;
		try {
			Ppsylist = ckmap.selCondition(sartDate, endDate, strExnm, lkno);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Ppsylist;
	}
	
	//-------------------------敞口规则设置
	//查询页面数据
	public String getSelck_rulet(String userKey) {
		// TODO Auto-generated method stub
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd = curUser.getProd();
		String msg = "";
		List<TbCk_rulet> tbRulet = ckmap.selck_rulet(prcd);
		if(tbRulet.size()>0&&tbRulet!=null){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_01.getCode(), tbRulet);
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null); 
		}
		return msg;
	}

	//查询左下拉框（账户，币别，折美元金额）
	public String getCkdictionary(String userKey) {
		// TODO Auto-generated method stub
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prcd = curUser.getProd();
		String msg = "";
		List<Ck_Dictionary> ckdictionlist = ckmap.selCkDictionary(prcd);
		if(ckdictionlist.size()>0&&ckdictionlist!=null){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_01.getCode(), ckdictionlist);
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null); 
		}
		return msg;
	}

	//查询账户的右下拉框
	public String getTrdCusType(String userKey) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String msg = "";
		List<TrdTynm> trdCustlist = ckmap.sleTrdCustType(curUser.getProd());
		if(trdCustlist.size()>0&&trdCustlist!=null){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_01.getCode(), trdCustlist);
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null);
		}
		return msg;
	}
	//初始化添加窗口
	public String getProdCkno(String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String msg ="";
		String ms = "";
		if(curUser.getProd().equals("P001")){
			ms = "p001";
		}else if(curUser.getProd().equals("P002")){
			ms = "p002";
		}else if(curUser.getProd().equals("P003")){
			ms = "p003";
		}
		TbCk_rulet tbrulet = ckmap.selRuleRecord(ms, curUser.getProd());
		if(tbrulet!=null){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_01.getCode(), tbrulet);
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null);
		}
		return msg;
	}
	/***
	 * 敞口规则添加
	 */
	public String insertCkRuleComm(CurrUser curUser,TbCk_rulet ckRulet, String ip){
		ckRulet.setPrcd(curUser.getProd());
		try {
			int x = ckmap.inSelRulet(ckRulet);				// 添加验证
			if(x>0){
				return ResultDomain.getRtnMsg(ErrorCodeCk.E_07.getCode(), null);
			}else{
				String data = DataTimeClass.getNowDay();
				String time = DataTimeClass.getCurTime();
				ckmap.insClassTotal(ckRulet, data, time);	// 添加客户分类敞口
				ckmap.insppControl(ckRulet);				// 添加自动平盘规则
				ckmap.insCkRule(ckRulet);					// 添加规则
			}	
		} catch (Exception e) {
			log.error("添加平盘规则失败");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_02.getCode(), "添加平盘规则失败，请检查数据");
		}
		log.info("用户：" + curUser.getUsnm() + " 登录IP:"
				+ ip + " 登录产品:" + curUser.getProd()
				+ "添加敞口规则:敞口编号:" + ckRulet.getCkno() + ",敞口级别:"
				+ ckRulet.getLeve() + "敞口名称:" + ckRulet.getCknm()
				+ ",可用状态:" + ckRulet.getUsfg() + ",规则描述:"
				+ ckRulet.getRule() + ",规则含义:" + ckRulet.getRuhy()
				+ ",规则条数:" + ckRulet.getRcnt() + ",成功!时间:"
				+ DataTimeClass.getCurDateTime());
		Logfile loginfo = new Logfile();
		loginfo.setUsem(curUser.getUsnm());
		loginfo.setProd(curUser.getProd());
		loginfo.setTymo("敞口规则管理");
		loginfo.setRemk("添加");
		loginfo.setVold("登录产品:" + curUser.getProd() + "添加敞口规则:敞口编号:"
				+ ckRulet.getCkno() + ",敞口级别:" + ckRulet.getLeve()
				+ "敞口名称:" + ckRulet.getCknm() + ",可用状态:"
				+ ckRulet.getUsfg() + ",规则描述:" + ckRulet.getRule()
				+ ",规则含义:" + ckRulet.getRuhy() + ",规则条数:"
				+ ckRulet.getRcnt());
		loginfo.setVnew("成功");
		logfileCmdService.insertLog(loginfo);
		return ResultDomain.getRtnMsg(ErrorCodeCk.E_01.getCode(), "添加敞口规则成功");
	}
	
	/**
	 * 敞口规则修改
	 */
	public String upCkRuleComm(CurrUser curUser, TbCk_rulet ckrulet, String ip) {
		ckrulet.setPrcd(curUser.getProd());
		try {
			int count = ckmap.upSel(ckrulet);
			if(count>0){
				return ResultDomain.getRtnMsg(ErrorCodeCk.E_08.getCode(), null);
			}else{
				ckmap.upCkRule(ckrulet);
			}
		} catch (Exception e) {
			log.error("修改敞口规则出错");
			log.error(e.getMessage(), e);
			log.error("\n用户：" + curUser.getUsnm() + " 登录IP:"
					+ ip + " 登录产品:" + curUser.getProd()
					+ "修改敞口规则:敞口编号:" + ckrulet.getCkno() + ",敞口级别:"
					+ ckrulet.getLeve() + ",敞口名称:" + ckrulet.getCknm()
					+ ",可用状态:" + ckrulet.getUsfg() + ",规则描述:"
					+ ckrulet.getRule() + ",规则含义:" + ckrulet.getRuhy()
					+ ",规则条数:" + ckrulet.getRcnt() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_05.getCode(), null);
		}
		log.info("\n用户：" + curUser.getUsnm() + " 登录IP:"
				+ ip + " 登录产品:" + curUser.getProd()
				+ "修改敞口规则:敞口编号:" + ckrulet.getCkno() + ",敞口级别:"
				+ ckrulet.getLeve() + ",敞口名称:" + ckrulet.getCknm()
				+ ",可用状态:" + ckrulet.getUsfg() + ",规则描述:"
				+ ckrulet.getRule() + ",规则含义:" + ckrulet.getRuhy()
				+ ",规则条数:" + ckrulet.getRcnt() + ",成功!时间:"
				+ DataTimeClass.getCurDateTime());
		Logfile loginfo = new Logfile();
		loginfo.setUsem(curUser.getUsnm());
		loginfo.setProd(curUser.getProd());
		loginfo.setTymo("敞口规则管理");
		loginfo.setRemk("修改");
		loginfo.setVold("登录产品:" + curUser.getProd() + "修改敞口规则:敞口编号:"
				+ ckrulet.getCkno() + ",敞口级别:" + ckrulet.getLeve()
				+ ",敞口名称:" + ckrulet.getCknm() + ",可用状态:"
				+ ckrulet.getUsfg() + ",规则描述:" + ckrulet.getRule()
				+ ",规则含义:" + ckrulet.getRuhy() + ",规则条数:"
				+ ckrulet.getRcnt());
		loginfo.setVnew("成功");
		logfileCmdService.insertLog(loginfo);
		return ResultDomain.getRtnMsg(ErrorCodeCk.E_01.getCode(), "修改敞口规则成功");
	}
	
	/**
	 * 敞口规则级别展示，查询
	 */
	public String getRuletLevel(String userKey) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String msg = "";
		List<TbCk_rulet> listRulet = ckmap.selLeveCknm(curUser.getProd());
		if(listRulet.size()>0&&listRulet!=null){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_01.getCode(), listRulet);
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null);
		}
		return msg;
	}
	/**
	 * 敞口规则级别修改
	 */
	public String setRuletLevel(CurrUser curUser, List<TbCk_rulet> ruletList, String ip) {
		StringBuffer strleve = new StringBuffer("");
		for (int i = 0; i < ruletList.size(); i++) {
			ruletList.get(i).setPrcd(curUser.getProd());
			ruletList.get(i).setLeve(i+1);
			strleve.append(ruletList.get(i).getCknm()+" | ");
		}
		try{
			ckmap.upLevelCknm(ruletList);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			log.error("用户：" + curUser.getUsnm() + " 登录IP:"
					+ ip + " 登录产品:" + curUser.getProd()
					+ "调整后敞口规则优先级顺序为:" + strleve + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_06.getCode(), null);
		}
		log.info("用户：" + curUser.getUsnm() + " 登录IP:"
				+ ip + " 登录产品:" + curUser.getProd()
				+ "调整后敞口规则优先级顺序为:" + strleve + ",成功!时间:"
				+ DataTimeClass.getCurDateTime());
		return ResultDomain.getRtnMsg(ErrorCodeCk.E_01.getCode(), "敞口规则级别修改成功");
	}
	
	//--------------------------平盘规则设置
	/**
	 * 查询平盘规则
	 */
	public String getppCon(CurrUser curUser, String ckno, Integer pageNo, Integer pageSize) {
		List<TbCk_ppcontrol> ppconlist = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			ppconlist = ckmap.selPpcontrolList(curUser.getProd(), ckno);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ResultDomain.getRtnMsg(ErrorCodeCk.E_21.getCode(), new PageInfo<TbCk_ppcontrol>(ppconlist));
	}
	/**
	 * 查询敞口名称
	 */
	public String getSelect(String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		List<TbCk_ppcontrol> ppconlist = ckmap.selGroupCkno(curUser.getProd());
		if(ppconlist.size()>0&&ppconlist!=null){
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_21.getCode(), ppconlist);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null);
		}
	}
	/**
	 * 修改平盘方式
	 */
	public boolean upPPconlist(CurrUser curUser, List<TbCk_ppcontrol> listppcon, String ip){
		String ppFSString = "";
		String PPFSExnm = "";
		for (int i = 0; i < listppcon.size(); i++) {
			if(listppcon.get(i).getCkno().equals("9999")){
				listppcon.get(i).setPpfs("01");
			}
			listppcon.get(i).setPrcd(curUser.getProd());
			ppFSString += listppcon.get(i).getCkno()+"||"; 
			PPFSExnm += listppcon.get(i).getExnm()+"||"; 
		}
		try {
			ckmap.upPpconlist(listppcon);
			log.info("\n用户：" + curUser.getUsnm() + " 登录IP:"
					+ ip + " 登录产品:" + curUser.getProd()
					+ "\n修改平盘方式:敞口编号" + ppFSString + ",币别对:"
					+ PPFSExnm + ",平盘方式:"
					+ listppcon.get(0).getPpfs() + " 成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("平盘方式");
			loginfo.setRemk("修改平盘方式");
			loginfo.setVold("登录IP:" + ip + " 登录产品:"
					+ curUser.getProd() + "修改平盘方式:敞口编号"
					+ ppFSString + ",币别对:" + PPFSExnm
					+ ",平盘方式:" + listppcon.get(0).getPpfs());
			loginfo.setVnew("修改成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		} catch (Exception e) {
			log.error("修改平盘方式出错！");
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	/**
	 * 平盘规则修改
	 */
	public String upPPcon(CurrUser curUser,TbCk_ppcontrol ppcontrol, String ip){
		ppcontrol.setPrcd(curUser.getProd());
		int a = ckmap.upPpControl(ppcontrol);
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "平盘规则修改:敞口编号"
					+ ppcontrol.getCkno() + ",币别对:" + ppcontrol.getExnm()
					+ ",平盘方式:" + ppcontrol.getPpfs() + ",达限方式:"
					+ ppcontrol.getDxfs() + ",达限金额:" + ppcontrol.getDxje()
					+ ",平盘余额:" + ppcontrol.getPpye() + ",止盈点数:"
					+ ppcontrol.getZybl() + ",止损点数:" + ppcontrol.getZsbl()
					+ ",止盈金额:" + ppcontrol.getZyam() + ",止损金额:"
					+ ppcontrol.getZsam() + ",可用状态:" + ppcontrol.getCkfg()
					+ ",重复次数:" + ppcontrol.getCont() + "成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setProd(curUser.getProd());
			loginfo.setTymo("平盘规则管理");
			loginfo.setRemk("平盘规则修改");
			loginfo.setVold("登录IP:" + ip + " 登录产品:"
					+ curUser.getProd() + "平盘规则修改:敞口编号" + ppcontrol.getCkno()
					+ ",币别对:" + ppcontrol.getExnm() + ",平盘方式:"
					+ ppcontrol.getPpfs() + ",达限方式:" + ppcontrol.getDxfs()
					+ ",达限金额:" + ppcontrol.getDxje() + ",平盘余额:"
					+ ppcontrol.getPpye() + ",止盈点数:" + ppcontrol.getZybl()
					+ ",止损点数:" + ppcontrol.getZsbl() + ",止盈金额:"
					+ ppcontrol.getZyam() + ",止损金额:" + ppcontrol.getZsam()
					+ ",可用状态:" + ppcontrol.getCkfg() + ",重复次数:"
					+ ppcontrol.getCont());
			loginfo.setVnew("修改成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_21.getCode(), "修改平盘规则成功");
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "平盘规则修改:敞口编号"
					+ ppcontrol.getCkno() + ",币别对:" + ppcontrol.getExnm()
					+ ",平盘方式:" + ppcontrol.getPpfs() + ",达限方式:"
					+ ppcontrol.getDxfs() + ",达限金额:" + ppcontrol.getDxje()
					+ ",平盘余额:" + ppcontrol.getPpye() + ",止盈点数:"
					+ ppcontrol.getZybl() + ",止损点数:" + ppcontrol.getZsbl()
					+ ",止盈金额:" + ppcontrol.getZyam() + ",止损金额:"
					+ ppcontrol.getZsam() + ",可用状态:" + ppcontrol.getCkfg()
					+ ",延迟秒数:" + ppcontrol.getYcsj() + "失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_23.getCode(), null);
		}
	}

	//-------------------------特殊账户分类
	/**
	 * 特殊账户分类  查询
	 */
	public String getTRD_TYNM(CurrUser curUser, String apfg) {
		// TODO Auto-generated method stub
		String msg = "";
		List<TrdTynm> trdtynmlist = null;
		try {
			trdtynmlist = ckmap.selectTRD_TYNM(curUser.getProd(),apfg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(trdtynmlist.size()>0&&trdtynmlist!=null){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_16.getCode(), trdtynmlist);
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null);
		}
		return msg;
	}
	
	/**
	 * 特殊账户分类 删除
	 */
	public String deleteT_TYNM(CurrUser curUser, TrdTynm trdtynm) {
		String msg = "";
		int a = 0;
		try {
			a = ckmap.deleteTRD_TYNM(curUser.getProd(), trdtynm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "删除特殊类型账号,类型名称:" + trdtynm.getTynm()
					+ ",编号:" + trdtynm.getApfg() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("特殊类型账号");
			loginfo.setRemk("删除");
			loginfo.setVold("登录产品:" + curUser.getProd() + "删除特殊类型账号,类型名称:"
					+ trdtynm.getTynm() + ",编号:" + trdtynm.getApfg());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_16.getCode(), "删除成功");
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "删除特殊类型账号,类型名称:" + trdtynm.getTynm()
					+ ",编号:" + trdtynm.getApfg() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_17.getCode(), null);
		}
		return msg;
	}

	/**
	 * 特殊账户分类添加
	 */
	public String insertT_TYNM(CurrUser curUser, TrdTynm trdtynm) {
		int a = 0;
		try {
			if(curUser.getProd().equals("P001")){
				int x = ckmap.insel(curUser.getProd(), trdtynm);
				if(x>0){
					log.info("该数据已存在！");
					return ResultDomain.getRtnMsg(ErrorCodeCk.E_20.getCode(), null);
				}else{
					a = ckmap.insertTRD_TYNM(curUser.getProd(), trdtynm);
				}
			}else {
				int x1 = ckmap.insel1(curUser.getProd(), trdtynm);
				if(x1>0){
					log.info("该数据已存在！");
					return ResultDomain.getRtnMsg(ErrorCodeCk.E_20.getCode(), null);
				}else{
					a = ckmap.insertTRD_TYNM2(curUser.getProd(), trdtynm);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加特殊类型账号,类型名称:"
					+ trdtynm.getTynm() + "，编号："+trdtynm.getApfg()+",状态:" + trdtynm.getStat() + "成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("特殊类型账号");
			loginfo.setRemk("添加");
			loginfo.setVold("登录产品:" + curUser.getProd() + "添加特殊类型账号,类型名称:"
					+ trdtynm.getTynm() + "，编号："+trdtynm.getApfg()+",状态:" + trdtynm.getStat());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_16.getCode(), "添加成功");
		}else{
			log.info("\n登陆用户："+curUser.getUsnm()+" 登陆产品："+curUser.getProd()+" 登陆ip："+curUser.getCurIP()+
					"添加特殊账户分类：账户名称："+trdtynm.getTynm()+",编号："+trdtynm.getApfg()+
					"，状态："+trdtynm.getStat()+
					"失败");
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_18.getCode(), null);
		}
	}

	//特殊账户分类修改
	public String updateT_TYNM(CurrUser curUser, TrdTynm trdtynm) {
		// TODO Auto-generated method stub
		String msg = "";
		int a = 0;
		try {
			a = ckmap.updateTRD_TYNM(curUser.getProd(), trdtynm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "修改特殊类型账号,类型名称:"
					+ trdtynm.getTynm() + ",编号:" + trdtynm.getApfg() + ",状态:"
					+ trdtynm.getStat() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());

			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("特殊类型账号");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "修改特殊类型账号,类型名称:"
					+ trdtynm.getTynm() + ",编号:" + trdtynm.getApfg() + ",状态:"
					+ trdtynm.getStat());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_16.getCode(), "修改成功");
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "修改特殊类型账号,类型名称:"
					+ trdtynm.getTynm() + ",编号:" + trdtynm.getApfg() + ",状态:"
					+ trdtynm.getStat() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_19.getCode(), null);
		}
		return msg;
	}
	
	//------------------------特殊账户收集
	//特殊账号收集  查询
	public String getTRD_SPCUT(CurrUser curUser, String cuno) {
		// TODO Auto-generated method stub
		String msg = "";
		List<TrdSpcut> trdspcutlist = null;
		try {
			trdspcutlist = ckmap.selectTRD_SPCUT(curUser.getProd(), cuno); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(trdspcutlist.size()>0&&trdspcutlist!=null){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_11.getCode(), trdspcutlist);
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null);
		}
		return msg;
	}

	//特殊账号收集  删除
	public String deleteTRD_SPCUT(CurrUser curUser, String cuno) {
		// TODO Auto-generated method stub
		String msg = "";
		int a = ckmap.deleteTRD_SPCUT(curUser.getProd(), cuno);
		if(a>0){
			Logfile logfile = new Logfile();
			logfile.setProd(curUser.getProd());
			logfile.setUsem(curUser.getUsnm());
			logfile.setVnew("成功");
			logfile.setVold("登录ip:"+curUser.getCurIP()+"客户号:"+cuno);
			logfile.setTymo("特殊账号收集");
			logfile.setRemk("删除");
			logfileCmdService.insertLog(logfile);
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_11.getCode(), "删除成功");
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_12.getCode(), null);
		}
		return msg;
	}
	
	//特殊账号收集  下拉框
	public String getTrdTynm(String userKey) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String msg = "";
		List<TrdTynm> tynmlist = null;
		try {
			tynmlist = ckmap.selectTYNM(curUser.getProd());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(tynmlist!=null&&tynmlist.size()>0){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_11.getCode(), tynmlist);
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null);
		}
		return msg;
	}
	
	//特殊账号收集  添加
	public String insertTrdSpcut(CurrUser curUser, TrdSpcut trdspcut) {
		// TODO Auto-generated method stub
		int a = 0;
		try {
			int x = ckmap.selIns(curUser.getProd(), trdspcut.getCuno());
			if(x>0){
				return ResultDomain.getRtnMsg(ErrorCodeCk.E_15.getCode(), null);
			}else{
				a = ckmap.insertTRD_SPCUT(curUser.getProd(), trdspcut);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("添加特殊账号收集成功");
			log.info("用户：" + curUser.getUsnm() + " 登录IP:"
					+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
					+ "添加特殊账号收集,类型名称:" + trdspcut.getTynm() + ",卡号:"
					+ trdspcut.getCuno() + ",状态:" + trdspcut.getStat() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("特殊类型账号");
			loginfo.setRemk("添加");
			loginfo.setVold("登录产品:" + curUser.getProd() + "添加特殊账号收集,类型名称:"
					+ trdspcut.getTynm() + ",卡号:" + trdspcut.getCuno() + ",状态:"
					+ trdspcut.getStat());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_11.getCode(), "添加成功");
		}else{
			log.error("添加特殊账号收集失败");
			log.error("用户：" + curUser.getUsnm() + " 登录IP:"
					+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
					+ "添加特殊账号收集,类型名称:" + trdspcut.getTynm() + ",卡号:"
					+ trdspcut.getCuno() + ",状态:" + trdspcut.getStat() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return ResultDomain.getRtnMsg(ErrorCodeCk.E_13.getCode(), null);
		}
	}
	
	//修改
	public String updateTrdSpcut(CurrUser curUser, TrdSpcut trdspcut) {
		// TODO Auto-generated method stub
		String msg = "";
		int a = 0;
		try {
			a = ckmap.updateTRD_SPCUT(curUser.getProd(), trdspcut);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log
			.info("用户：" + curUser.getUsnm() + " 登录IP:"
					+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
					+ "修改特殊账号收集,类型名称:" + trdspcut.getTynm() + ",卡号:"
					+ trdspcut.getCuno() + ",状态:" + trdspcut.getStat() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("特殊账号收集");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "修改特殊账号收集,类型名称:"
					+ trdspcut.getTynm() + ",卡号:" + trdspcut.getCuno() + ",状态:"
					+ trdspcut.getStat());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_11.getCode(), "修改成功");
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:"
					+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
					+ "修改特殊账号收集,类型名称:" + trdspcut.getTynm() + ",卡号:"
					+ trdspcut.getCuno() + ",状态:" + trdspcut.getStat() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_14.getCode(), null);
		}
		return msg;
	}

	//-----------------------平盘损益查询
	//数据
	public List<PpsyBeanDomain> getPpsy(String userKey,String sartDate, String endDate) {
		// TODO Auto-generated method stub
		List<PpsyBeanDomain> listPp = null;
		try {
			listPp = ckmap.selPpsy(sartDate, endDate);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return listPp;
	}

	//汇总
	public String getPpTosy(CurrUser curUser, String start, String end) {
		// TODO Auto-generated method stub
		String msg = "";
		PpsyBean PpTo = ckmap.selPptosy(start, end);
		if(PpTo!=null){
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), JSON.toJSONString(PpTo));
		}else{
			msg = ResultDomain.getRtnMsg(ErrorCodeCk.E_0.getCode(), null);
		}
		return msg;
	}

	
	

}
