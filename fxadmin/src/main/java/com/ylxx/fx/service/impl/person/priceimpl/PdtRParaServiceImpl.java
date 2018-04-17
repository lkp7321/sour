package com.ylxx.fx.service.impl.person.priceimpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.service.person.price.IPdtRParaService;
import com.ylxx.fx.service.po.CurrMarketBean;
import com.ylxx.fx.service.po.CurrmsgBean;
import com.ylxx.fx.service.po.FiltrateBean;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.MaxFavPointBean;
import com.ylxx.fx.service.po.MktinfoBean;
import com.ylxx.fx.service.po.PdtChkParaBean;
import com.ylxx.fx.service.po.PdtCtrlPriTBean;
import com.ylxx.fx.service.po.PdtPointBean;
import com.ylxx.fx.service.po.PdtRParaTBean;
import com.ylxx.fx.service.po.PdtStoperBean;
import com.ylxx.fx.service.po.PdtinfoBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ReadSocketConfig;
import com.ylxx.fx.utils.ReadStringCodeConfig;
import com.ylxx.fx.utils.ResultDomain;

@Service("pdtRParaService")
public class PdtRParaServiceImpl implements IPdtRParaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PdtRParaServiceImpl.class);
	
	@Resource
	private PdtRParaMapper pdtrParaMapper;
	CurrmsgBean currmsgBean = null;
	PdtinfoBean pdtinfoBean = null;
	
	/**
	 * 查询报价参数:
	 * 分页显示查询报价参数:pageNo第几页,pageSize每页显示条数
	 */
	public String addpage(String prod,Integer pageNo,
			Integer pageSize){
	 	pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
	 	//调用mapper的方法
	 	List<PdtRParaTBean> pdtRParaTs = null;
		try {
			if (prod.equals("P007")) {
				pdtRParaTs = pdtrParaMapper.selAccExPrice(prod.trim());
			}else {
				pdtRParaTs = pdtrParaMapper.selectPriceNew(prod.trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageInfo<PdtRParaTBean> page = new PageInfo<PdtRParaTBean>(pdtRParaTs);
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), page);
	}
	//P999查询产品列表
	public String pdtCom(){
		String result = "";
		try {
			List<HashMap<String, String>> list = pdtrParaMapper.pdtCom();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询产品列表失败");
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//报价参数设置中读取币种交叉盘标识从产品币种表中读取 :根据币别对名称（主键）查询记录（以获得币别对类型）*
	public String selectObjPdtr(String prod, String exnm){
		String extp = null;
		String result = "";
		try {
			extp = pdtrParaMapper.selectObjPrice(prod, exnm.trim()).getExtp().trim();
			LOGGER.info("extp="+extp);
			if (extp!=null) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), extp);
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//查询货币对类型
	public String selectObjPdtr2(String exnm){
		String extp = null;
		String result = "";
		try {
			extp = pdtrParaMapper.selectObjPri(exnm).getExtp().trim();
			if (extp!=null) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), extp);
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//【校验】页面数据的查询
	public String getCurChk(String exnm,String prod) {
		PdtChkParaBean pdtChkParaBean = null;
		String result = "";
		try {
			//查询货币对
			currmsgBean = pdtrParaMapper.selectObjPri(exnm.trim());
			//查询产品信息
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			PdtChkParaBean pcpara = new PdtChkParaBean();
			pcpara.setTpfg(pdtinfoBean.getQtty().trim());
			pcpara.setTerm(pdtinfoBean.getTerm().trim());
			//pcpara.setExcd(currmsgBean.getExcd().trim());
			pcpara.setCxfg(pdtinfoBean.getCxfg().trim());
			pcpara.setExnm(currmsgBean.getExnm().trim());
			pdtChkParaBean = pdtrParaMapper.selectPrice(pcpara,pdtinfoBean.getPtid());
			if (pdtChkParaBean!=null) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtChkParaBean));
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//保存【校验】修改
	public String saveChkPara(String userKey,String prod,PdtChkParaBean pdtChk,String exnm){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean bo = false;
		String result = "";
		try {
			//查询货币对
			currmsgBean = pdtrParaMapper.selectObjPri(exnm);
			LOGGER.info("查询货币对执行成功!");
			//查询产品信息
			PdtinfoBean pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			LOGGER.info("查询产品信息执行成功!");
			BigDecimal vadg = new BigDecimal(10.0).pow(Integer.parseInt(currmsgBean.getPion()));
			DecimalFormat df = new DecimalFormat("0.0000000".substring(0, Integer
					.parseInt(currmsgBean.getPion()) + 2));
			BigDecimal mimd = pdtChk.getMdmd().subtract(pdtChk.getMxdp().divide(new BigDecimal(vadg.floatValue())));
			BigDecimal mxmd = pdtChk.getMdmd().add(pdtChk.getMxdp().divide(new BigDecimal(vadg.floatValue())));
			pdtChk.setXimd(df.format(mimd));
			pdtChk.setXxmd(df.format(mxmd));
			if (bo) {
				//TODO Psocket.SendSocketB(); 【方法为空】
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "报价参数,修改" + prod
						+ "校验参数:币别对:" + pdtChk.getExnm() + ",价格类型:" + pdtChk.getTpfg()
						+ ",钞汇标志:" + pdtChk.getCxfg() + ",买入价:" + pdtChk.getMdmd()
						+ ",最大波动点数:" + pdtChk.getMxdp() + ",中间价两次波动点差:"
						+ pdtChk.getMxbp() + ",合法波动次数:" + pdtChk.getMxct() + ",变为无效波动次数:"
						+ pdtChk.getMxud() + ",使用标志:" + pdtChk.getUsfg() + "成功!时间:"
						+ DataTimeClass.getCurTime());
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(DataTimeClass.getNowDay());
				loginfo.setRzsj(DataTimeClass.getCurTime());
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("报价校验参数");
				loginfo.setRemk("修改");
				loginfo.setVold("登录产品:" + curUser.getProd() + ",修改" + prod
						+ "报价参数,校验参数:币别对:" + pdtChk.getExnm() + ",价格类型:"
						+ pdtChk.getTpfg() + ",钞汇标志:" + pdtChk.getCxfg() + ",买入价:"
						+ pdtChk.getMdmd() + ",最大波动点数:" + pdtChk.getMxdp()
						+ ",中间价两次波动点差:" + pdtChk.getMxbp() + ",合法波动次数:"
						+ pdtChk.getMxct() + ",变为无效波动次数:" + pdtChk.getMxud() + ",使用标志:"
						+ pdtChk.getUsfg());
				loginfo.setVnew("成功");
				boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
				//TODO 保存成功后，执行ut.SendSocketB1();
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "报价参数,修改" + prod
						+ "校验参数:币别对:" + pdtChk.getExnm() + ",价格类型:" + pdtChk.getTpfg()
						+ ",钞汇标志:" + pdtChk.getCxfg() + ",买入价:" + pdtChk.getMdmd()
						+ ",最大波动点数:" + pdtChk.getMxdp() + ",中间价两次波动点差:"
						+ pdtChk.getMxbp() + ",合法波动次数:" + pdtChk.getMxct() + ",变为无效波动次数:"
						+ pdtChk.getMxud() + ",使用标志:" + pdtChk.getUsfg() );
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	//【停牌】页面初始化
	public String getCurStop(String prod,String exnm,String stid){
		PdtStoperBean pdtstop = null;
		String result = "";
		try {
			//currmsgBean = pdtrParaMapper.selectObjPri(exnm.trim());
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			PdtStoperBean pdtStoper = new PdtStoperBean();
			pdtStoper.setTpfg(pdtinfoBean.getQtty().trim());
			pdtStoper.setTerm(pdtinfoBean.getTerm().trim());
			//pdtStoper.setExcd(currmsgBean.getExcd().trim());
			//pdtStoper.setCxfg(pdtinfoBean.getCxfg().trim());
			pdtStoper.setExnm(exnm.trim());
			pdtStoper.setStid(stid.trim());
			pdtstop = pdtrParaMapper.selectStopPrice(pdtStoper, pdtinfoBean.getPtid());
			if (pdtstop!=null) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtstop));
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//保存【停牌】修改
	public String saveStop(String userKey, String prod, PdtStoperBean pdtStoper){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean bo = false;
		String result = "";
		try {
			//查询产品信息
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			LOGGER.info("查询产品信息成功!");
			//查询记录是否存在
			PdtStoperBean pdtStoperBean = pdtrParaMapper.selectStopPrice(pdtStoper, pdtinfoBean.getPtid());
			if (pdtStoperBean!=null) {
				pdtStoper.setCxfg(pdtStoperBean.getCxfg());
				pdtStoper.setUsfg("0");
				bo = pdtrParaMapper.updateStopPrice(pdtStoper, pdtinfoBean.getPtid().trim());
				LOGGER.info("记录更新成功!");
			}else {
				/*bo = pdtrParaMapper.insertStopPrice(pdtStoper, pdtinfoBean.getPtid().trim());
				LOGGER.info("记录添加成功!");*/
				LOGGER.info("该记录可能已被删除!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
			//获取当前时间
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				//TODO Psocket.SendSocketB();【方法为空】
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "报价参数,修改" + prod
						+ "停牌参数:币别对:" + pdtStoper.getExnm() + ",价格类型:" + pdtStoper.getTpfg()
						+ ",钞汇标志:" + pdtStoper.getCxfg() + ",停牌标志:" + pdtStoper.getStfg()
						+ "成功!时间:" + nowtime);
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "报价参数,修改" + prod
						+ "停牌参数:币别对:" + pdtStoper.getExnm() + ",价格类型:" + pdtStoper.getTpfg()
						+ ",钞汇标志:" + pdtStoper.getCxfg() + ",停牌标志:" + pdtStoper.getStfg()
						+ "失败!时间:" + nowtime);
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	//【点差】页面初始化
	public String getCurPoint(String prod, String exnm){
		PdtPointBean pdtPointBean = null;
		String result = "";
		try {
			//currmsgBean = pdtrParaMapper.selectObjPri(exnm.trim());
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			PdtPointBean pdtPoint = new PdtPointBean();
			pdtPoint.setPtid(pdtinfoBean.getPtid());
			pdtPoint.setTpfg(pdtinfoBean.getQtty().trim());
			pdtPoint.setTerm(pdtinfoBean.getTerm().trim());// 即期
			//pdtPoint.setExcd(currmsgBean.getExcd().trim());
			pdtPoint.setCxfg(pdtinfoBean.getCxfg().trim());
			pdtPoint.setExnm(exnm.trim());
			pdtPointBean = pdtrParaMapper.selectPointPrice(pdtPoint, pdtinfoBean.getPtid());
			if (pdtPointBean!=null) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtPointBean));
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	/**
	 * 保存【点差】修改
	 */
	public String savePoint(String userKey, String prod, PdtPointBean pdtPointBean){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setCurIP("127.0.0.1");
		curUser.setProd("P003");*/
		
		String result = "";
		boolean bo = false;
		boolean boo = false;
		try {
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			LOGGER.info("查询产品信息成功!");
			pdtPointBean.setPtid(pdtinfoBean.getPtid().trim());
			//修改产品点差表
			//查询记录是否存在
			PdtPointBean pdtPoint = pdtrParaMapper.selectPointPrice(pdtPointBean,prod);
			LOGGER.info("查询记录是否存在执行成功!");
			if(pdtPoint!=null){
				bo = pdtrParaMapper.updatePointPrice(pdtPointBean, prod);
				if (bo) {
					pdtPointBean.setCxfg(pdtPoint.getCxfg());
					LOGGER.info("更新"+prod+"产品点差表成功!");
				}else {
					LOGGER.error("更新"+prod+"产品点差表失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}else {
				/*bo = pdtrParaMapper.insertPointPrice(pdtPointBean, prod);
				LOGGER.info("点差数据添加成功!");*/
				LOGGER.info("该记录可能已被删除!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
			//修改最大优惠点差表
			List<MaxFavPointBean> maxFavPointBeans = pdtrParaMapper.selectMaxpavpoint(prod);
			LOGGER.info("最大优惠点差表查询成功!");
			if (maxFavPointBeans.size()>0) {
				String exnm = pdtPointBean.getExnm();
				String bhbd = pdtPointBean.getBhbd();
				String cubd = pdtPointBean.getCubd();
				String bhsd = pdtPointBean.getBhsd();
				String cusd = pdtPointBean.getCusd();
				String mxfv = "";
				int ibhbd=Integer.valueOf(bhbd).intValue();
				int icubd=Integer.valueOf(cubd).intValue();
				int ibhsd=Integer.valueOf(bhsd).intValue();
				int icusd=Integer.valueOf(cusd).intValue();
				int ibmax=icubd-ibhbd;
				int ismax=icusd-ibhsd;
				if(ismax>=ibmax){
					mxfv=String.valueOf(ibmax);//cubd-bhbd:总行对客户买入点差-总行对分行买入点差
				}else if(ismax<ibmax){
					mxfv=String.valueOf(ismax);//cusd-bhsd:总行对客户卖出点差-总行对分行卖出点差
				}
				//获取当前时间
				String nowtime = DataTimeClass.getCurDateTime();
				boo = pdtrParaMapper.upMaxpavpointByExnm(prod,mxfv,exnm);
				if (boo) {
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP() 
							+ " 登录产品:" + curUser.getProd() + "报价参数,修改" + prod
							+ "点差参数时联动修改最大优惠点差:币别对:" + pdtPointBean.getExnm() + ",最大优惠点差："
							+mxfv+"成功!时间:" + nowtime);	 
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP() 
							+ " 登录产品:" + curUser.getProd() + "报价参数,修改" + prod
							+ "点差参数时联动修改最大优惠点差:币别对:" + pdtPointBean.getExnm() + ",最大优惠点差："
							+mxfv+"失败!时间:" + nowtime);	
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}else {
				boo = true;
			}
			//获取当前时间
			String curtime = DataTimeClass.getCurDateTime();
			if (bo&&boo) {
				//TODO Psocket.SendSocketB();
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "报价参数,修改" + prod
						+ "点差参数:币别对:" + pdtPointBean.getExnm() + ",价格类型:"
						+ pdtPointBean.getTpfg() + ",钞汇标志:" + pdtPointBean.getCxfg() + ",价格生命周期:"
						+ pdtPointBean.getQtcy() + ",报价模式:" + pdtPointBean.getPrtp()
						+ ",对分行买入点差:" + pdtPointBean.getBhbd() + ",对分行卖出点差:"
						+ pdtPointBean.getBhsd() + ",对客户买入点差:" + pdtPointBean.getCubd()
						+ ",对客户卖出点差:" + pdtPointBean.getCusd() + "成功!时间:"
						+ curtime);
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "报价参数,修改" + prod
						+ "点差参数:币别对:" + pdtPointBean.getExnm() + ",价格类型:"
						+ pdtPointBean.getTpfg() + ",钞汇标志:" + pdtPointBean.getCxfg() + ",价格生命周期:"
						+ pdtPointBean.getQtcy() + ",报价模式:" + pdtPointBean.getPrtp()
						+ ",对分行买入点差:" + pdtPointBean.getBhbd() + ",对分行卖出点差:"
						+ pdtPointBean.getBhsd() + ",对客户买入点差:" + pdtPointBean.getCubd()
						+ ",对客户卖出点差:" + pdtPointBean.getCusd() + "失败!时间:"
						+ curtime);
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	//产品市场源头选择
	//返回当前市场信息（产品市场MKNM、市场权重设置MKSL、市场id MKID）
	public String curMkList(String prod, String exnm) {
		String result = "";
		List<CurrMarketBean> currMarketBeans = new ArrayList<CurrMarketBean>();
		try {
			PdtRParaTBean pdtrpara = pdtPara(prod,exnm);
			if (pdtrpara.getMkst() == null || "".equals(pdtrpara.getMkst().trim()))
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			String[] mkstList = pdtrpara.getMkst().trim().split("\\|");//市场源列表
			String[] mkslList = pdtrpara.getMksl().trim().split("\\|");//市场权重列表
			String[] mklvList = pdtrpara.getMklv().trim().split("\\|");//市场级别
			CurrMarketBean mark = null;
			for (int i = 0; i < mkstList.length; i++){
				mark = new CurrMarketBean();
				mark.setMkid(mkstList[i]);
				String mknm;
				//根据市场编号(报价源)查询市场信息表
				mknm = pdtrParaMapper.selectObjPrice1(mkstList[i]).getMknm().trim();
				mark.setMknm(mknm);
				mark.setMksl(mkslList[i]);
				mark.setMklv(mklvList[i]);
				currMarketBeans.add(mark);
			}
			if (currMarketBeans!=null&&currMarketBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(currMarketBeans));
			}else if (currMarketBeans!=null&&currMarketBeans.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		}catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//产品价格源
	public PdtRParaTBean pdtPara(String prod, String exnm) {
		PdtRParaTBean pdtRParaTBean = null;
		try {
			//查询货币对
			if (prod.equals("P007")) {
				currmsgBean = pdtrParaMapper.selectObjPrice(prod.trim(), exnm.trim());
			}else {
				currmsgBean = pdtrParaMapper.selectObjPri(exnm.trim());
			}			
			//查询产品信息
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			
			String tpfg = pdtinfoBean.getQtty().trim();// 价格类型:SPT
			String term = pdtinfoBean.getTerm().trim();// 期限:0
			pdtRParaTBean = new PdtRParaTBean();
			pdtRParaTBean.setTpfg(tpfg);//SPT
			pdtRParaTBean.setTerm(term);//0
			pdtRParaTBean.setExcd(currmsgBean.getExcd());//2914
			pdtRParaTBean.setCxfg(pdtinfoBean.getCxfg());//2
			pdtRParaTBean.setExnm(currmsgBean.getExnm());//AUDUSD
			//条件查询币别对的数据
			if (prod.equals("P007")) {
				pdtRParaTBean = pdtrParaMapper.selectAccExPrice(pdtRParaTBean, pdtinfoBean.getPtid());
			}else {
				pdtRParaTBean = pdtrParaMapper.selectPrice1(pdtRParaTBean, pdtinfoBean.getPtid());
			}
			LOGGER.info("【产品价格源】pdtPara方法执行成功!");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("【产品价格源】pdtPara方法执行失败!");
		}
		return pdtRParaTBean;		
	}
	//返回所有市场信息
	public String allMkList(String prod,String exnm) {
		String result = "";
		List<MktinfoBean> mktinfoBeans = new ArrayList<MktinfoBean>();
		try {
			//查询货币对
			if (prod.equals("P007")) {
				currmsgBean = pdtrParaMapper.selectObjPrice(prod.trim(),exnm.trim());
			}else {
				currmsgBean = pdtrParaMapper.selectObjPri(exnm.trim());
			}
			//查询产品信息
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			String tpfg = pdtinfoBean.getQtty().trim();//价格类型
			String cxfg = pdtinfoBean.getCxfg().trim();//钞汇标志
			//获取产品市场源头MKNM（、市场名称MKID）
			mktinfoBeans = selMark(currmsgBean.getExnm(), tpfg, cxfg);//AUDUSD、SPT、2
			if (mktinfoBeans!=null&&mktinfoBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(mktinfoBeans));
			}else if (mktinfoBeans!=null&&mktinfoBeans.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//获取产品市场源头MKNM（、市场名称MKID）
	public List<MktinfoBean> selMark(String exnm, String tpfg, String cxfg) {
		List<MktinfoBean> mktList = new ArrayList<MktinfoBean>();
		try {
			//查询所有价格源公告板表表名
			List<String> tabName = pdtrParaMapper.getMarkTab();
			for (int i = 0; i < tabName.size(); i++) {
				String mktab = tabName.get(i);
				MktinfoBean mktinfo = pdtrParaMapper.selMark(mktab,tpfg,cxfg,exnm);
				if (mktinfo!=null) {
					mktList.add(mktinfo);
				}
			}
			LOGGER.info("【获取产品市场源头】selMark方法执行成功!");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("【获取产品市场源头】selMark方法执行失败!");
		}
		return mktList;
	}
	//保存【产品市场源头选择】数据
	public String saveMarket(String userKey, String prod,PdtRParaTBean pdtRParaTBean){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		try {
			//查询产品信息
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			PdtRParaTBean pdtrPara = pdtPara(prod,pdtRParaTBean.getExnm().trim());
			pdtRParaTBean.setPmid(pdtrPara.getPmid());
			String exse = "";
			try {
				exse = pdtrParaMapper.selectExse(prod.trim(),pdtRParaTBean.getExnm().trim());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
			}			
			pdtRParaTBean.setExse(exse);
			//判断记录是否存在，以更新或添加记录
			PdtRParaTBean pdtrparat = new PdtRParaTBean();
			if (prod.equals("P007")) {
				pdtrparat = pdtrParaMapper.selectAccExPrice(pdtRParaTBean,pdtinfoBean.getPtid());
			}else {
				pdtrparat = pdtrParaMapper.selectPrice1(pdtRParaTBean,pdtinfoBean.getPtid());
			}
			boolean bo = false;
			if (pdtrparat!=null) {
				if (prod.equals("P007")) {
					bo = pdtrParaMapper.updateAccExPrice(pdtRParaTBean,pdtinfoBean.getPtid());
				}else {
					bo = pdtrParaMapper.updateMarkPrice(pdtRParaTBean,pdtinfoBean.getPtid());
				}				
			}else {
				//bo = pdtrParaMapper.insertMarkPrice(pdtRParaTBean,pdtinfoBean.getPtid());
				LOGGER.error("该记录可能已被删除!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
			//获取当前时间
			String nowtime = DataTimeClass.getCurDateTime();
			if(bo){
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + ",修改" + prod
						+ "报价参数,产品市场源头选择:币别对:" + pdtRParaTBean.getExnm() + ",价格类型:"
						+ pdtRParaTBean.getTpfg() + ",钞汇标志:" + pdtRParaTBean.getCxfg() + ",产品市场:"
						+ pdtRParaTBean.getMkst() + ",市场权重:" + pdtRParaTBean.getMksl() + "成功!时间:"
						+ nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("报价参数");
				loginfo.setRemk("修改");
				loginfo.setVold("登录产品:" + curUser.getProd() + ",修改" + prod
						+ "报价参数,产品市场源头选择:币别对:" + pdtRParaTBean.getExnm() + ",价格类型:"
						+ pdtRParaTBean.getTpfg() + ",钞汇标志:" + pdtRParaTBean.getCxfg() + ",产品市场:"
						+ pdtRParaTBean.getMkst() + ",市场权重:" + pdtRParaTBean.getMksl());
				loginfo.setVnew("成功");
				boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + ",修改" + prod
						+ "报价参数,产品市场源头选择:币别对:" + pdtRParaTBean.getExnm() + ",价格类型:"
						+ pdtRParaTBean.getTpfg() + ",钞汇标志:" + pdtRParaTBean.getCxfg() + ",产品市场:"
						+ pdtRParaTBean.getMkst() + ",市场权重:" + pdtRParaTBean.getMksl() + "失败!时间:"
						+ nowtime);
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	//查询当前策略
	public String selectGMNM(String ptid,String exnm){
		String result = "";
		try {
			PdtRParaTBean pdtrPara = pdtrParaMapper.selectGMNM(ptid.trim(), exnm);
			if (pdtrPara!=null) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtrPara));
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//查询所有策略信息
	public String selectPriceUs(){
		List<FiltrateBean> filtrateBeans;
		String result = "";
		try {
			filtrateBeans = pdtrParaMapper.selectPriceUs();
			if (filtrateBeans!=null&&filtrateBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(filtrateBeans));
			}else if (filtrateBeans!=null&&filtrateBeans.size()==0){
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;		
	}
	/**
	 * 保存【策略】的修改
	 */
	public String saveGmnm(String userKey,String prod,PdtRParaTBean pdt,String exnm){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		try {
			//查询产品信息
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			LOGGER.info("查询产品信息成功!");
			//查询产品价格源
			PdtRParaTBean pdtrPara = pdtPara(prod,exnm.trim());
			//没必要取下面3个字段并在sql中修改
			pdt.setMklv(pdtrPara.getMklv());
			pdt.setMksl(pdtrPara.getMksl());
			pdt.setMkst(pdtrPara.getMkst());
			//修改币别对的数据
			boolean bo = pdtrParaMapper.updateMarkPrice(pdt,pdtinfoBean.getPtid());
			//获取当前时间
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				LOGGER.info("修改币别对的数据成功!");
				//TODO Psocket.SendSocketB();
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ ",修改" + prod + "报价参数,策略参数:币别对:" + pdt.getExnm()
						+ ",价格类型:" + pdt.getTpfg() + ",钞汇标志:"
						+ pdt.getCxfg() + ",修改后策略:" + pdt.getPmid()
						+ "成功!时间:" + nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("报价策略参数");
				loginfo.setRemk("修改");
				loginfo.setVold("登录产品:" + curUser.getProd() + ",修改" + prod
						+ "报价参数,策略参数:币别对:" + pdt.getExnm() + ",价格类型:"
						+ pdt.getTpfg() + ",钞汇标志:" + pdt.getCxfg() + ",修改后策略:"
						+ pdt.getPmid());
				loginfo.setVnew("成功");
				boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ ",修改" + prod + "报价参数,策略参数:币别对:" + pdt.getExnm()
						+ ",价格类型:" + pdt.getTpfg() + ",钞汇标志:"
						+ pdt.getCxfg() + ",修改后策略:" + pdt.getPmid()
						+ "失败!时间:" + nowtime);
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;		
	}
	//【干预】页面初始化:返回当前干预器
	public String curPdtCtrl(String prod,String ctid,String exnm) {
		PdtCtrlPriTBean pcpri = new PdtCtrlPriTBean();
		String result = "";
		try {
			//currmsgBean = pdtrParaMapper.selectObjPri(exnm.trim());
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			String tpfg = pdtinfoBean.getQtty().trim();// 价格类型:SPT
			String cxfg = pdtinfoBean.getCxfg().trim();// 钞汇标志:2
			String term = pdtinfoBean.getTerm().trim();// 期限:0
			pcpri.setTpfg(tpfg);
			pcpri.setTerm(term);// 即期
			//pcpri.setExcd(currmsgBean.getExcd().trim());
			pcpri.setExnm(exnm.trim());
			pcpri.setCxfg(cxfg);
			pcpri.setCtid(ctid.trim());
			//查询实盘产品价格干预器
			pcpri = pdtrParaMapper.selectCtrlPrice(pcpri,pdtinfoBean.getPtid().trim());
			if (pcpri!=null) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pcpri));
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//保存【干预】的修改
	public String saveCtrl(String userKey,String prod,PdtCtrlPriTBean ctrl){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		boolean bo = false;
		try {
			//查询产品信息
			pdtinfoBean = pdtrParaMapper.selectObjP(prod.trim());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			Date bgtm = sdf.parse(ctrl.getBgtm());
			Date edtm = sdf.parse(ctrl.getEdtm());
			if (!bgtm.before(edtm)) {
				bo = false;
			}
			ctrl.setTpfg(pdtinfoBean.getQtty().trim());//SPT
			//查询记录是否存在
			PdtCtrlPriTBean pcpri = pdtrParaMapper.selectCtrlPrice(ctrl,pdtinfoBean.getPtid().trim());
			if (pcpri!=null) {
				ctrl.setUsfg("0");
				ctrl.setCtnm(pcpri.getCtnm());
				bo = pdtrParaMapper.updateCtrlPrice(ctrl,pdtinfoBean.getPtid().trim());
			}else {
				//bo = pdtrParaMapper.insertCtrlPrice(ctrl,pdtinfoBean.getPtid().trim());
				LOGGER.error("该记可能已被删除!");
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
			//获取当前时间
			String nowtime = sdf.format(new Date());
			if (bo) {
				//TODO Psocket.SendSocketB();
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + ",修改" + prod
						+ "报价参数,干预参数设置:币别对:" + ctrl.getExnm() + ",价格类型:"
						+ ctrl.getTpfg() + ",钞汇标志:" + ctrl.getCxfg() + ",干预ID:"
						+ ctrl.getCtid() + ",干预器名称:" + ctrl.getCtnm() + ",买入价干预点数:"
						+ ctrl.getNebp() + ",卖出价干预点数:" + ctrl.getNesp() + ",起始时间:"
						+ ctrl.getBgtm() + ",截止时间:" + ctrl.getEdtm() + ",干预标志:"
						+ ctrl.getStfg() + "成功!时间:" + nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("报价参数修改");
				loginfo.setRemk("修改");
				loginfo.setVold("登录产品:" + curUser.getProd() + ",修改" + prod
						+ "报价参数,干预参数设置:币别对:" + ctrl.getExnm() + ",价格类型:"
						+ ctrl.getTpfg() + ",钞汇标志:" + ctrl.getCxfg() + ",干预ID:"
						+ ctrl.getCtid() + ",干预器名称:" + ctrl.getCtnm() + ",买入价干预点数:"
						+ ctrl.getNebp() + ",卖出价干预点数:" + ctrl.getNesp() + ",起始时间:"
						+ ctrl.getBgtm() + ",截止时间:" + ctrl.getEdtm() + ",干预标志:"
						+ ctrl.getStfg());
				loginfo.setVnew("成功");
				boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + ",修改" + prod
						+ "报价参数,干预参数设置:币别对:" + ctrl.getExnm() + ",价格类型:"
						+ ctrl.getTpfg() + ",钞汇标志:" + ctrl.getCxfg() + ",干预ID:"
						+ ctrl.getCtid() + ",干预器名称:" + ctrl.getCtnm() + ",买入价干预点数:"
						+ ctrl.getNebp() + ",卖出价干预点数:" + ctrl.getNesp() + ",起始时间:"
						+ ctrl.getBgtm() + ",截止时间:" + ctrl.getEdtm() + ",干预标志:"
						+ ctrl.getStfg() + "失败!时间:" + nowtime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;		
	}
	//根据价格类型名称获取价格类型
	public String getTpfgByTpnm(String tpnm){
		String tpfg = null;
		try {
			tpfg = pdtrParaMapper.getTpfgByTpnm(tpnm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tpfg;
	}
	//根据策略名称查询策略编号
	public String getGmidByGmnm(String gmnm){
		String gmid = null;
		try {
			gmid = pdtrParaMapper.getGmidByGmnm(gmnm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gmid;
	}
	//账户交易->报价管理->报价参数设置:生效
	public String SendAccExPdtRparaSocket(){
		String result = "";
		ReadStringCodeConfig readString = new ReadStringCodeConfig();
		readString.readStringCodeProperties("strcode1");
		String stringCode = readString.getStringCode();
		ReadSocketConfig readsockt = new ReadSocketConfig();
		readsockt.readWainingProperties("pdtRParaConstForAccIp", "pdtRParaConstForAccPort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "<RELOAD>RATE</RELOAD>";
		LOGGER.info("ipB:" + strip + " portB:" + strport);
		Socket socket= null;
		PrintWriter out = null;
		BufferedReader in= null;
		try {
			socket = new Socket(strip,strport);
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(), stringCode)), true);
			out.println(content);
			out.flush();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_60.getCode(), "生效成功!");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_61.getCode(), "生效失败!");
		}finally{
			if(out!=null){
				out.close();
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(),e);
				}
			}
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(),e);
				}
			}
		}
		return result;
	}
}
