package com.ylxx.fx.service.po.jsh;

import java.io.Serializable;
/**
 * 交易流水
 * @author lz130
 *
 */
public class JshTrdTranList implements Serializable{
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交易流水号
	 */
	private String lcno;
	/**
	 * 交易日期YYYYMMDD
	 */
	private String trdt;
	/**
	 * 交易时间HH:MM:SS
	 */
	private String trtm;
	/**
	 * 交易代码
	 */
	private String trcd;
	/**
	 * 客户号
	 */
	private String cuno;
	/**
	 * 卡号，折号
	 */
	private String cuac;
	/**
	 * 交易账号
	 */
	private String trac;
	/**
	 * 币种英文名简称
	 */
	private String cyen;
	/**
	 * 资金账户类型（0 卡 1折）
	 */
	private String caty;
	/**
	 * 钞汇标志（0 钞 1汇）
	 */
	private String cxfg;
	/**
	 * 内部定义的交易类型
	 */
	private String trtp;
	/**
	 * 买入币种
	 */
	private String bycy;
	/**
	 * 买入币种英文名简称
	 */
	private String bynm;
	/**
	 * 卖出币种
	 */
	private String slcy;
	/**
	 * 卖出币种英文名简称
	 */
	private String slnm;
	/**
	 * 客户上送汇率
	 */
	private String uspc;
	/**
	 * 交易类型
	 */
	private String trty;
	/**
	 * 买入金额
	 */
	private String byam;
	private String expc;// 成交汇率
	private String slam;// 卖出金额
	private String pram;// 盈亏金额（折美元）
	private String amut;// 发生额（转账）
	private String busd;// 币种1折美元金额（分行价）
	private String susd;// 右币种折美元金额（分行价）
	private String usam;// 折美元金额
	private String olno;// 被冲交易流水
	private String tcby;// 交易币别对客户买入价
	private String tcsl;// 交易币别对客户卖出价
	private String tbby;// 交易币别对分行买入价
	private String tbsl;// 交易币别对分行卖出价
	private String exna;// 币别对A
	private String elcb;//叉盘币种1对usd客户买入价
	private String elcs;//叉盘币种1对usd客户卖出价
	private String elbb;//叉盘币种1对usd分行买入价
	private String elbs;//叉盘币种1对usd分行卖出价
	private String exnb;//币别对B
	private String ercb;//叉盘币种2对usd客户买入价
	private String ercs;//叉盘币种2对usd客户卖出价
	private String erbb;//叉盘币种2对usd分行买入价
	private String erbs;//叉盘币种2对usd分行卖出价
	private String trfg;//交易方式
	private String stcd;//记录状态
	private String ercd;//错误码
	private String culv;//参数值
	private String cyam;//人名币金额
	private String fxid;//外管局购汇参号
	private String exfg;//结售汇类型
	private String exid;//结售汇项目代码
	private String cutp;//结售汇客户类型
	private String extr;//结售汇交易项目代码
	private String pftp;//售汇种类
	private String fram;//冻结金额
	private String frid;//冻结编号
	private String vlfg;//是否检查最大最小值
	private String csof;//资金来源类型
	private String soac;//资金来源账号
	private String ctof;//资金去向类型
	private String toac;//资金去向账号
	private String sfid;//结汇参号
	private String bspc;//标准价
	private String olnl;//
	private String trsn;//渠道流水号
	private String trtl;//交易柜员
	private String tram;//交易金额
	private String fonm;//够/结汇外币名称
	private String cynm;//购/结汇人名币名称
	private String ogcd;//机构号
	private String bktr;//暂未使用（资金去向）
	private String bktl;//
	private String pitr;//支付平台订单号
	private String pitl;//支付平台订单号
	private String sevd;//服务编码
	private String idcd;//系统id
	private String cros;//交易标识
	private String dttl;//
	private String dttr;//
	private String rttl;//
	private String rttr;//
	private String todt;//后台记账日期
	private String idtp;//证件类型
	private String idno;//证件号码
	private String cunm;//客户中文姓名
	private String extp;//币别对类型
	private String foam;//外币金额
	private String trog;//
	private String dealtime;//交易运行时间
	private String pdld;//产品编码
	private String optp;//行为编码
	private String bksn;//调用后台流水号
	private String otsn;//
	private String status_code;//状态标识码
	public String getLcno() {
		return lcno;
	}
	public void setLcno(String lcno) {
		this.lcno = lcno;
	}
	public String getTrdt() {
		return trdt;
	}
	public void setTrdt(String trdt) {
		this.trdt = trdt;
	}
	public String getTrtm() {
		return trtm;
	}
	public void setTrtm(String trtm) {
		this.trtm = trtm;
	}
	public String getTrcd() {
		return trcd;
	}
	public void setTrcd(String trcd) {
		this.trcd = trcd;
	}
	public String getCuno() {
		return cuno;
	}
	public void setCuno(String cuno) {
		this.cuno = cuno;
	}
	public String getCuac() {
		return cuac;
	}
	public void setCuac(String cuac) {
		this.cuac = cuac;
	}
	public String getTrac() {
		return trac;
	}
	public void setTrac(String trac) {
		this.trac = trac;
	}
	public String getCyen() {
		return cyen;
	}
	public void setCyen(String cyen) {
		this.cyen = cyen;
	}
	public String getCaty() {
		return caty;
	}
	public void setCaty(String caty) {
		this.caty = caty;
	}
	public String getCxfg() {
		return cxfg;
	}
	public void setCxfg(String cxfg) {
		this.cxfg = cxfg;
	}
	public String getTrtp() {
		return trtp;
	}
	public void setTrtp(String trtp) {
		this.trtp = trtp;
	}
	public String getBycy() {
		return bycy;
	}
	public void setBycy(String bycy) {
		this.bycy = bycy;
	}
	public String getBynm() {
		return bynm;
	}
	public void setBynm(String bynm) {
		this.bynm = bynm;
	}
	public String getSlcy() {
		return slcy;
	}
	public void setSlcy(String slcy) {
		this.slcy = slcy;
	}
	public String getSlnm() {
		return slnm;
	}
	public void setSlnm(String slnm) {
		this.slnm = slnm;
	}
	public String getUspc() {
		return uspc;
	}
	public void setUspc(String uspc) {
		this.uspc = uspc;
	}
	public String getTrty() {
		return trty;
	}
	public void setTrty(String trty) {
		this.trty = trty;
	}
	public String getByam() {
		return byam;
	}
	public void setByam(String byam) {
		this.byam = byam;
	}
	public String getExpc() {
		return expc;
	}
	public void setExpc(String expc) {
		this.expc = expc;
	}
	public String getSlam() {
		return slam;
	}
	public void setSlam(String slam) {
		this.slam = slam;
	}
	public String getPram() {
		return pram;
	}
	public void setPram(String pram) {
		this.pram = pram;
	}
	public String getAmut() {
		return amut;
	}
	public void setAmut(String amut) {
		this.amut = amut;
	}
	public String getBusd() {
		return busd;
	}
	public void setBusd(String busd) {
		this.busd = busd;
	}
	public String getSusd() {
		return susd;
	}
	public void setSusd(String susd) {
		this.susd = susd;
	}
	public String getUsam() {
		return usam;
	}
	public void setUsam(String usam) {
		this.usam = usam;
	}
	public String getOlno() {
		return olno;
	}
	public void setOlno(String olno) {
		this.olno = olno;
	}
	public String getTcby() {
		return tcby;
	}
	public void setTcby(String tcby) {
		this.tcby = tcby;
	}
	public String getTcsl() {
		return tcsl;
	}
	public void setTcsl(String tcsl) {
		this.tcsl = tcsl;
	}
	public String getTbby() {
		return tbby;
	}
	public void setTbby(String tbby) {
		this.tbby = tbby;
	}
	public String getTbsl() {
		return tbsl;
	}
	public void setTbsl(String tbsl) {
		this.tbsl = tbsl;
	}
	public String getExna() {
		return exna;
	}
	public void setExna(String exna) {
		this.exna = exna;
	}
	public String getElcb() {
		return elcb;
	}
	public void setElcb(String elcb) {
		this.elcb = elcb;
	}
	public String getElcs() {
		return elcs;
	}
	public void setElcs(String elcs) {
		this.elcs = elcs;
	}
	public String getElbb() {
		return elbb;
	}
	public void setElbb(String elbb) {
		this.elbb = elbb;
	}
	public String getElbs() {
		return elbs;
	}
	public void setElbs(String elbs) {
		this.elbs = elbs;
	}
	public String getExnb() {
		return exnb;
	}
	public void setExnb(String exnb) {
		this.exnb = exnb;
	}
	public String getErcb() {
		return ercb;
	}
	public void setErcb(String ercb) {
		this.ercb = ercb;
	}
	public String getErcs() {
		return ercs;
	}
	public void setErcs(String ercs) {
		this.ercs = ercs;
	}
	public String getErbb() {
		return erbb;
	}
	public void setErbb(String erbb) {
		this.erbb = erbb;
	}
	public String getErbs() {
		return erbs;
	}
	public void setErbs(String erbs) {
		this.erbs = erbs;
	}
	public String getTrfg() {
		return trfg;
	}
	public void setTrfg(String trfg) {
		this.trfg = trfg;
	}
	public String getStcd() {
		return stcd;
	}
	public void setStcd(String stcd) {
		this.stcd = stcd;
	}
	public String getErcd() {
		return ercd;
	}
	public void setErcd(String ercd) {
		this.ercd = ercd;
	}
	public String getCulv() {
		return culv;
	}
	public void setCulv(String culv) {
		this.culv = culv;
	}
	public String getCyam() {
		return cyam;
	}
	public void setCyam(String cyam) {
		this.cyam = cyam;
	}
	public String getFxid() {
		return fxid;
	}
	public void setFxid(String fxid) {
		this.fxid = fxid;
	}
	public String getExfg() {
		return exfg;
	}
	public void setExfg(String exfg) {
		this.exfg = exfg;
	}
	public String getExid() {
		return exid;
	}
	public void setExid(String exid) {
		this.exid = exid;
	}
	public String getCutp() {
		return cutp;
	}
	public void setCutp(String cutp) {
		this.cutp = cutp;
	}
	public String getExtr() {
		return extr;
	}
	public void setExtr(String extr) {
		this.extr = extr;
	}
	public String getPftp() {
		return pftp;
	}
	public void setPftp(String pftp) {
		this.pftp = pftp;
	}
	public String getFram() {
		return fram;
	}
	public void setFram(String fram) {
		this.fram = fram;
	}
	public String getFrid() {
		return frid;
	}
	public void setFrid(String frid) {
		this.frid = frid;
	}
	public String getVlfg() {
		return vlfg;
	}
	public void setVlfg(String vlfg) {
		this.vlfg = vlfg;
	}
	public String getCsof() {
		return csof;
	}
	public void setCsof(String csof) {
		this.csof = csof;
	}
	public String getSoac() {
		return soac;
	}
	public void setSoac(String soac) {
		this.soac = soac;
	}
	public String getCtof() {
		return ctof;
	}
	public void setCtof(String ctof) {
		this.ctof = ctof;
	}
	public String getToac() {
		return toac;
	}
	public void setToac(String toac) {
		this.toac = toac;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getBspc() {
		return bspc;
	}
	public void setBspc(String bspc) {
		this.bspc = bspc;
	}
	public String getOlnl() {
		return olnl;
	}
	public void setOlnl(String olnl) {
		this.olnl = olnl;
	}
	public String getTrsn() {
		return trsn;
	}
	public void setTrsn(String trsn) {
		this.trsn = trsn;
	}
	public String getTrtl() {
		return trtl;
	}
	public void setTrtl(String trtl) {
		this.trtl = trtl;
	}
	public String getTram() {
		return tram;
	}
	public void setTram(String tram) {
		this.tram = tram;
	}
	public String getFonm() {
		return fonm;
	}
	public void setFonm(String fonm) {
		this.fonm = fonm;
	}
	public String getCynm() {
		return cynm;
	}
	public void setCynm(String cynm) {
		this.cynm = cynm;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public String getBktr() {
		return bktr;
	}
	public void setBktr(String bktr) {
		this.bktr = bktr;
	}
	public String getBktl() {
		return bktl;
	}
	public void setBktl(String bktl) {
		this.bktl = bktl;
	}
	public String getPitr() {
		return pitr;
	}
	public void setPitr(String pitr) {
		this.pitr = pitr;
	}
	public String getPitl() {
		return pitl;
	}
	public void setPitl(String pitl) {
		this.pitl = pitl;
	}
	public String getSevd() {
		return sevd;
	}
	public void setSevd(String sevd) {
		this.sevd = sevd;
	}
	public String getIdcd() {
		return idcd;
	}
	public void setIdcd(String idcd) {
		this.idcd = idcd;
	}
	public String getCros() {
		return cros;
	}
	public void setCros(String cros) {
		this.cros = cros;
	}
	public String getDttl() {
		return dttl;
	}
	public void setDttl(String dttl) {
		this.dttl = dttl;
	}
	public String getDttr() {
		return dttr;
	}
	public void setDttr(String dttr) {
		this.dttr = dttr;
	}
	public String getRttl() {
		return rttl;
	}
	public void setRttl(String rttl) {
		this.rttl = rttl;
	}
	public String getRttr() {
		return rttr;
	}
	public void setRttr(String rttr) {
		this.rttr = rttr;
	}
	public String getTodt() {
		return todt;
	}
	public void setTodt(String todt) {
		this.todt = todt;
	}
	public String getIdtp() {
		return idtp;
	}
	public void setIdtp(String idtp) {
		this.idtp = idtp;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getCunm() {
		return cunm;
	}
	public void setCunm(String cunm) {
		this.cunm = cunm;
	}
	public String getExtp() {
		return extp;
	}
	public void setExtp(String extp) {
		this.extp = extp;
	}
	public String getFoam() {
		return foam;
	}
	public void setFoam(String foam) {
		this.foam = foam;
	}
	public String getTrog() {
		return trog;
	}
	public void setTrog(String trog) {
		this.trog = trog;
	}
	public String getDealtime() {
		return dealtime;
	}
	public void setDealtime(String dealtime) {
		this.dealtime = dealtime;
	}
	public String getPdld() {
		return pdld;
	}
	public void setPdld(String pdld) {
		this.pdld = pdld;
	}
	public String getOptp() {
		return optp;
	}
	public void setOptp(String optp) {
		this.optp = optp;
	}
	public String getBksn() {
		return bksn;
	}
	public void setBksn(String bksn) {
		this.bksn = bksn;
	}
	public String getOtsn() {
		return otsn;
	}
	public void setOtsn(String otsn) {
		this.otsn = otsn;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	
}
