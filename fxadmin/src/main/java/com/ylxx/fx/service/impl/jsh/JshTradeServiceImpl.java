package com.ylxx.fx.service.impl.jsh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.afcat.jsh.po.ForfxsaInfoin;
import com.alibaba.fastjson.JSON;
import com.cmbc.safe.rmi.bean.DeleteIndividualFXSAInfoRequestBean;
import com.cmbc.safe.rmi.bean.DeleteIndividualFXSAInfoResponseBean;
import com.cmbc.safe.rmi.bean.DeleteIndividualFXSEInfoRequestBean;
import com.cmbc.safe.rmi.bean.DeleteIndividualFXSEInfoResponseBean;
import com.cmbc.safe.rmi.service.ISearchSafeByJsh;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hr.fxip.common.data.PersistenceConst;
import com.ylxx.fx.core.mapper.jsh.JshTradeMapper;
import com.ylxx.fx.service.jsh.JshTradeService;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.service.po.jsh.JshTrdTranList;
import com.ylxx.fx.service.po.jsh.TbTrd_safeAccinfo;
import com.ylxx.fx.service.po.jsh.Trd_errorstate;
import com.ylxx.fx.service.po.jsh.Trd_tranlist;
import com.ylxx.fx.service.po.jsh.WgCountry;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.SpringContextHelper;

/**
 * 国别管理
 */
@Service("jshTradeService")
public class JshTradeServiceImpl implements JshTradeService {
	private static final Logger log = LoggerFactory.getLogger(JshTradeServiceImpl.class);
	@Resource
	private JshTradeMapper jshTradeMapper;

	/**
	 * 添加国别
	 */
	@Override
	public String insetrcout(JshPages<WgCountry> wgCountry) {
		String userKey = wgCountry.getUserKey();
		String name = wgCountry.getEntity().getName();
		String cmbccout = wgCountry.getEntity().getCmbccout();
		String cout = wgCountry.getEntity().getCout();
		String copycout = wgCountry.getEntity().getCopycout();
		try {
			jshTradeMapper.insetrcout(name, cmbccout, cout, copycout);
		} catch (Exception e) {
			log.error("添加国别失败");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "ERROR");
		}
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "SUCCESS");
	}

	/**
	 * 修改国别
	 */
	@Override
	public String updatecout(JshPages<WgCountry> wgCountry) {
		String userKey = wgCountry.getUserKey();
		String name = wgCountry.getEntity().getName();
		String cmbccout = wgCountry.getEntity().getCmbccout();
		String cout = wgCountry.getEntity().getCout();
		String copycout = wgCountry.getEntity().getCopycout();
		try {
			jshTradeMapper.updatecout(name, cmbccout, cout, copycout);
		} catch (Exception e) {
			log.error("修改国别失败");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "ERROR");
		}
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "SUCCESS");
	}

	/**
	 * 分页查询国别
	 */
	@Override
	public String selectcout(Integer pageNo, Integer pageSize, String name) {
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		List<HashMap<String, String>> list = null;
		try {
			list = jshTradeMapper.selectcout(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageInfo<HashMap<String, String>> page = new PageInfo<HashMap<String, String>>(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}

	/**
	 * 删除外管局国别信息
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public String deletecout(JshPages<WgCountry> wgCountry) {
		String userKey = wgCountry.getUserKey();
		String cmbccout = wgCountry.getEntity().getCmbccout();
		try {
			jshTradeMapper.deletecout(cmbccout);
		} catch (Exception e) {
			log.error("删除外管国别失败");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "ERROR");
		}
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "SUCCESS");
	}

	/**
	 * 查询告警表
	 */
	@Override
	public String selecterror(Integer pageNo, Integer pageSize, String trdt, String lcno) {
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		List<Trd_errorstate> trd_errorstate = null;
		try {
			trd_errorstate = jshTradeMapper.selecterror(trdt, lcno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageInfo<Trd_errorstate> page = new PageInfo<Trd_errorstate>(trd_errorstate);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
		
	}

	/**
	 * 删除告警表
	 */
	@Override
	public String deleteerror(JshPages<WgCountry> wgCountry) {
		String userKey = wgCountry.getUserKey();
		try {
			String originatechannelserialno = wgCountry.getEntity().getName();
			jshTradeMapper.deleteerror(originatechannelserialno);
		} catch (Exception e) {
			log.error("删除告警错误");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "ERROR");
		}
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "SUCCESS");
	}

	/**
	 * 查询流水
	 */
	@Override
	public String selecttranlist(Integer pageNo, Integer pageSize,String ercd,String cuno,String trsn,String trdt) {
		/*Integer pageNo = jshTranList.getPageNo();
		Integer pageSize = jshTranList.getPageSize();*/
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		List<Trd_tranlist> trd_tranlist = null;
		try {
			/*String trdt = jshTranList.getEntity().getTrdt();
			String trsn = jshTranList.getEntity().getTrsn();
			String cuno = jshTranList.getEntity().getCuno();
			String ercd = jshTranList.getEntity().getErcd();*/
			trd_tranlist = jshTradeMapper.selecttranlist(trdt, trsn, cuno, ercd);
		} catch (Exception e) {
			log.error("查询流水记录失败");
			log.error(e.getMessage(), e);
		}
		PageInfo<Trd_tranlist> page = new PageInfo<Trd_tranlist>(trd_tranlist);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}

	/**
	 * 购汇冲销外管局
	 * 
	 * @param jshTranList
	 * @return
	 */
	@Override
	public String forfxsaInfoin(ForfxsaInfoin forfxsaInfoin) {
		String msg = "";
		try {
			ISearchSafeByJsh GH = (ISearchSafeByJsh) SpringContextHelper.getBean("serviceExporter");
			ISearchSafeByJsh JH = (ISearchSafeByJsh) SpringContextHelper.getBean("serviceExporter");
			DeleteIndividualFXSAInfoRequestBean request = new DeleteIndividualFXSAInfoRequestBean();
			DeleteIndividualFXSEInfoRequestBean requestt = new DeleteIndividualFXSEInfoRequestBean();
			if (forfxsaInfoin.getJSfalg().equals(jshTradeMapper.getlist(forfxsaInfoin.getBANK_SELF_NUM()))) {
				// 购汇
				request.setBANK_SELF_NUM(forfxsaInfoin.getBANK_SELF_NUM());
				request.setCANCEL_REASON(forfxsaInfoin.getCANCEL_REASON());
				request.setCANCEL_REMARK(forfxsaInfoin.getCANCEL_REMARK());
				request.setCOMMON_ORG_CODE(forfxsaInfoin.getCOMMON_ORG_CODE());
				request.setCOMMON_USER_CODE(forfxsaInfoin.getCOMMON_USER_CODE());
				request.setMSGNO(jshTradeMapper.getmsgno());
				request.setPASSWORD(forfxsaInfoin.getPASSWORD());
				request.setREFNO(jshTradeMapper.getfxid(forfxsaInfoin.getBANK_SELF_NUM()));
				DeleteIndividualFXSAInfoResponseBean response = GH.doDeleteIndividualFXSAInfo(request);
				if (!PersistenceConst.errNU.equals(response.getHEAD_CODE())) {
					msg = ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "ERROR");
				}else
					msg = ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "SUCCESS");
			} else {
				// 结汇
				requestt.setBANK_SELF_NUM(forfxsaInfoin.getBANK_SELF_NUM());
				requestt.setCANCEL_REASON(forfxsaInfoin.getCANCEL_REASON());
				requestt.setCANCEL_REMARK(forfxsaInfoin.getCANCEL_REMARK());
				requestt.setCOMMON_ORG_CODE(forfxsaInfoin.getCOMMON_ORG_CODE());
				requestt.setCOMMON_USER_CODE(forfxsaInfoin.getCOMMON_USER_CODE());
				requestt.setMSGNO(jshTradeMapper.getmsgno());
				requestt.setPASSWORD(forfxsaInfoin.getPASSWORD());
				requestt.setREFNO(jshTradeMapper.getfxid(forfxsaInfoin.getBANK_SELF_NUM()));
				DeleteIndividualFXSEInfoResponseBean response = JH.doDeleteIndividualFXSEInfo(requestt);
				if (!PersistenceConst.errNU.equals(response.getHEAD_CODE())) {
					msg = ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "ERROR");
				}else
					msg = ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "SUCCESS");
			}
		}catch (Exception e) {
			log.error("外管局冲销错误");
			log.error(e.getMessage(), e);
			msg = ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "外管局连接失败");
		} 
			return msg;
		

	}

	/**
	 * 更新流水表中的记录状态及错误码，告警表的处理标记
	 */
	@Override
	public String upErrorTranlist(String trsn) {
		String result = "";
		try {
			boolean upError = jshTradeMapper.upError(trsn);
			if (upError) {
				log.info("更新告警表的处理标记成功!");
				boolean upTranlist = jshTradeMapper.upTranlist(trsn);
				if (upTranlist) {
					log.info("更新结售汇流水表的记录状态和错误码成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
				} else {
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			} else {
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}

	/**
	 * 查询柜员号、密码
	 */
	@Override
	public String getLoginOgcd(String bhid, String chnl) {
		String result = "";
		TbTrd_safeAccinfo accInfo = new TbTrd_safeAccinfo();
		try {
			accInfo = jshTradeMapper.getLoginOgcd(bhid, chnl);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(accInfo));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}

	/**
	 * 导出Excel
	 * 告警
	 */
	public List<Trd_errorstate> getAllWgErrorList(String trdt, String lcno) {
		List<Trd_errorstate> trd_errorstate = null;
		try {
			trd_errorstate = jshTradeMapper.selecterror(trdt, lcno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trd_errorstate;
	}
	/**
	 * 导出Excel
	 * 流水
	 */
	@Override
	public List<Trd_tranlist> getAllWgTranList(String ercd,String cuno,String trsn,String trdt) {
		List<Trd_tranlist> trd_tranlist = null;
		try {
			trd_tranlist = jshTradeMapper.selecttranlist(trdt, trsn, cuno, ercd);
		} catch (Exception e) {
			log.error("查询流水记录失败");
			log.error(e.getMessage(), e);
		}
		log.info("当前流水数据总量："+trd_tranlist.size());
		return trd_tranlist;
	}

}
