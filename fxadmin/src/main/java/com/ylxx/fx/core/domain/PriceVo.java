package com.ylxx.fx.core.domain;

import java.util.List;

import com.ylxx.fx.service.po.CmmCtrlpri;
import com.ylxx.fx.service.po.CmmCtrlswh;
import com.ylxx.fx.service.po.CmmStoper;
import com.ylxx.fx.service.po.HandQuoteVoBean;
import com.ylxx.fx.service.po.PdtChkParaBean;
import com.ylxx.fx.service.po.PdtCtrlPriTBean;
import com.ylxx.fx.service.po.PdtCtrlSwhBean;
import com.ylxx.fx.service.po.PdtPointBean;
import com.ylxx.fx.service.po.PdtRParaTBean;
import com.ylxx.fx.service.po.PdtStoperBean;
import com.ylxx.fx.service.po.PdtValidateBean;

public class PriceVo {
	
	private String userKey;
	private int pageNo;//第几页
	private int pageSize;//一页显示的记录数
	private String prod;//产品号
	private String usnm;//用户名
	private String exnm;//货币对名称
	private String stid;//停牌器ID
	private String ctid;//干预器ID
	private String usfg;//总控开关:启用、停用
	private String excd;//币种代码
	private String tpfg;//价格类型SPT即期FWD远期SWP掉期OPT期权
	private String tpnm;//价格类型名称
	private String term;//期限(1W一周/1M一月/1Y一年)
	private String cxfg;//钞汇标志(2钞,1汇)
	private String stfg;//审核状态
	private String labnm;//标签名(提交、复核、未通过)(启用、停用)
	private String optm;//操作日期
	private String mkid;//市场编号
	
	private LoginUser logUser;
	private PdtChkParaBean pdtChk;//校验
	private PdtStoperBean pdtStoper;//停牌
	private PdtPointBean pdtPoint;//点差
	private PdtRParaTBean pdtrPara;//市场
	private PdtCtrlPriTBean pdtCtrl;//干预
	private PdtCtrlSwhBean pdtCtrlSwh;//点差干预
	private PdtValidateBean pdtVa;//产品均价校验器
	private HandQuoteVoBean hqVo;//手工报价录入
	private List<PdtCtrlSwhBean> pdtCtrlSwhs;
	private List<PdtStoperBean> pdtStopers;
	private List<PdtValidateBean> pdtVas;//产品均价校验器
	private List<HandQuoteVoBean> hqVoList;//手工报价s
	private List<CmmStoper> cmmStopers;//手工快速停牌
	private CmmCtrlswh cmmCtrl;
	private List<CmmCtrlswh> cmmCtrls;
	private CmmCtrlpri cmmCtrlpri;
	private List<CmmCtrlpri> cmmCtrlpris;
	public List<CmmStoper> getCmmStopers() {
		return cmmStopers;
	}
	public void setCmmStopers(List<CmmStoper> cmmStopers) {
		this.cmmStopers = cmmStopers;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public LoginUser getLogUser() {
		return logUser;
	}
	public void setLogUser(LoginUser logUser) {
		this.logUser = logUser;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
	public String getUsnm() {
		return usnm;
	}
	public void setUsnm(String usnm) {
		this.usnm = usnm;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getStid() {
		return stid;
	}
	public void setStid(String stid) {
		this.stid = stid;
	}
	public PdtChkParaBean getPdtChk() {
		return pdtChk;
	}
	public void setPdtChk(PdtChkParaBean pdtChk) {
		this.pdtChk = pdtChk;
	}
	public PdtStoperBean getPdtStoper() {
		return pdtStoper;
	}
	public void setPdtStoper(PdtStoperBean pdtStoper) {
		this.pdtStoper = pdtStoper;
	}
	public PdtPointBean getPdtPoint() {
		return pdtPoint;
	}
	public void setPdtPoint(PdtPointBean pdtPoint) {
		this.pdtPoint = pdtPoint;
	}
	public PdtRParaTBean getPdtrPara() {
		return pdtrPara;
	}
	public void setPdtrPara(PdtRParaTBean pdtrPara) {
		this.pdtrPara = pdtrPara;
	}
	public String getCtid() {
		return ctid;
	}
	public void setCtid(String ctid) {
		this.ctid = ctid;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public String getExcd() {
		return excd;
	}
	public void setExcd(String excd) {
		this.excd = excd;
	}
	public PdtCtrlPriTBean getPdtCtrl() {
		return pdtCtrl;
	}
	public void setPdtCtrl(PdtCtrlPriTBean pdtCtrl) {
		this.pdtCtrl = pdtCtrl;
	}
	public PdtCtrlSwhBean getPdtCtrlSwh() {
		return pdtCtrlSwh;
	}
	public void setPdtCtrlSwh(PdtCtrlSwhBean pdtCtrlSwh) {
		this.pdtCtrlSwh = pdtCtrlSwh;
	}
	public PdtValidateBean getPdtVa() {
		return pdtVa;
	}
	public void setPdtVa(PdtValidateBean pdtVa) {
		this.pdtVa = pdtVa;
	}
	public HandQuoteVoBean getHqVo() {
		return hqVo;
	}
	public void setHqVo(HandQuoteVoBean hqVo) {
		this.hqVo = hqVo;
	}
	public List<PdtCtrlSwhBean> getPdtCtrlSwhs() {
		return pdtCtrlSwhs;
	}
	public void setPdtCtrlSwhs(List<PdtCtrlSwhBean> pdtCtrlSwhs) {
		this.pdtCtrlSwhs = pdtCtrlSwhs;
	}
	public List<PdtStoperBean> getPdtStopers() {
		return pdtStopers;
	}
	public void setPdtStopers(List<PdtStoperBean> pdtStopers) {
		this.pdtStopers = pdtStopers;
	}
	public String getTpfg() {
		return tpfg;
	}
	public void setTpfg(String tpfg) {
		this.tpfg = tpfg;
	}
	public String getTpnm() {
		return tpnm;
	}
	public void setTpnm(String tpnm) {
		this.tpnm = tpnm;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getCxfg() {
		return cxfg;
	}
	public void setCxfg(String cxfg) {
		this.cxfg = cxfg;
	}
	public String getStfg() {
		return stfg;
	}
	public void setStfg(String stfg) {
		this.stfg = stfg;
	}
	public String getLabnm() {
		return labnm;
	}
	public void setLabnm(String labnm) {
		this.labnm = labnm;
	}
	public String getOptm() {
		return optm;
	}
	public void setOptm(String optm) {
		this.optm = optm;
	}
	public List<PdtValidateBean> getPdtVas() {
		return pdtVas;
	}
	public void setPdtVas(List<PdtValidateBean> pdtVas) {
		this.pdtVas = pdtVas;
	}
	public List<HandQuoteVoBean> getHqVoList() {
		return hqVoList;
	}
	public void setHqVoList(List<HandQuoteVoBean> hqVoList) {
		this.hqVoList = hqVoList;
	}
	public String getMkid() {
		return mkid;
	}
	public void setMkid(String mkid) {
		this.mkid = mkid;
	}
	public CmmCtrlswh getCmmCtrl() {
		return cmmCtrl;
	}
	public void setCmmCtrl(CmmCtrlswh cmmCtrl) {
		this.cmmCtrl = cmmCtrl;
	}
	public List<CmmCtrlswh> getCmmCtrls() {
		return cmmCtrls;
	}
	public void setCmmCtrls(List<CmmCtrlswh> cmmCtrls) {
		this.cmmCtrls = cmmCtrls;
	}
	public CmmCtrlpri getCmmCtrlpri() {
		return cmmCtrlpri;
	}
	public void setCmmCtrlpri(CmmCtrlpri cmmCtrlpri) {
		this.cmmCtrlpri = cmmCtrlpri;
	}
	public List<CmmCtrlpri> getCmmCtrlpris() {
		return cmmCtrlpris;
	}
	public void setCmmCtrlpris(List<CmmCtrlpri> cmmCtrlpris) {
		this.cmmCtrlpris = cmmCtrlpris;
	}
}
