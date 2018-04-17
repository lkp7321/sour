package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Tranlist implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ognm;
	private String lcno;// 交易流水号
	private String trdt;// 交易日期YYYYMMDD
	private String trtm;// 交易时间HH:MM:SS
	private String trds;// 交易代码
	private String cuno;// 客户号
	private String cuac;// 账号
	private String trac;// 交易账号
	private String cyen;// 币种英文名简称
	private String caty;// 账户类型（0 卡 1折）
	private String cxfg;// 钞汇标志（0 钞 1汇）
	private String trtp;// 1、转入 2：转出 3:外汇买卖 4.签约5解约 6查询,7其他
	private String bycy;// 买入币种
	private String bynm;// 买入币种英文名简称
	private String slcy;// 卖出币种
	private String slnm;// 卖出币种英文名简称
	private String uspc;// 客户上送汇率
	private String trty;// 即时交易上送类型
	private String rrdt;// 容忍点差
	private String byam;// 买入金额
	private String expc;// 成交汇率
	private String slam;// 卖出金额
	private String pram;// 盈亏金额（折美元）
	private String amut;// 发生额（转账）
	private String bymd;// 买入币别兑美元的中间价
	private String slmd;// 卖出币别兑美元的中间价
	private String bspc;// 基准汇率
	private String cspc;// 成本汇率
	private String fvda;// 优惠点数
	private String brsy;// 分行损益
	private String usam;// 折美元金额
	private String trfg;// 交易方式1-即时交易成交 2-指定汇率交易成交 3-委托交易成交
	private String stcd;// 记录状态
	private String ercd;// 错误码
	private String qdno;
	private String rgog;
	
	private String mddt;
	private String trsn;
	private String oprt;
	public String ogcd;//机构
	private String rgtp;//签约状态
	private String rgdt;//签约日期
	private String rgtm;//签约时间
	private String crdt;//解约日期
	private String crtm;//解约时间
	private String msfy;//定投频率
	public String getRgtp() {
		return rgtp;
	}
	public void setRgtp(String rgtp) {
		this.rgtp = rgtp;
	}
	public String getRgdt() {
		return rgdt;
	}
	public void setRgdt(String rgdt) {
		this.rgdt = rgdt;
	}
	public String getRgtm() {
		return rgtm;
	}
	public void setRgtm(String rgtm) {
		this.rgtm = rgtm;
	}
	public String getCrdt() {
		return crdt;
	}
	public void setCrdt(String crdt) {
		this.crdt = crdt;
	}
	public String getCrtm() {
		return crtm;
	}
	public void setCrtm(String crtm) {
		this.crtm = crtm;
	}
	public String getMsfy() {
		return msfy;
	}
	public void setMsfy(String msfy) {
		this.msfy = msfy;
	}
	public String getMddt() {
		return mddt;
	}
	public void setMddt(String mddt) {
		this.mddt = mddt;
	}
	public String getTrsn() {
		return trsn;
	}
	public void setTrsn(String trsn) {
		this.trsn = trsn;
	}
	public String getOprt() {
		return oprt;
	}
	public void setOprt(String oprt) {
		this.oprt = oprt;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public Tranlist(String ognm, String lcno, String trdt, String trtm,
			String trds, String cuno, String cuac, String trac, String cyen,
			String caty, String cxfg, String trtp, String bycy, String bynm,
			String slcy, String slnm, String uspc, String trty, String rrdt,
			String byam, String expc, String slam, String pram, String amut,
			String bymd, String slmd, String bspc, String cspc, String fvda,
			String brsy, String usam, String trfg, String stcd, String ercd,
			String qdno, String rgog) {
		super();
		this.ognm = ognm;
		this.lcno = lcno;
		this.trdt = trdt;
		this.trtm = trtm;
		this.trds = trds;
		this.cuno = cuno;
		this.cuac = cuac;
		this.trac = trac;
		this.cyen = cyen;
		this.caty = caty;
		this.cxfg = cxfg;
		this.trtp = trtp;
		this.bycy = bycy;
		this.bynm = bynm;
		this.slcy = slcy;
		this.slnm = slnm;
		this.uspc = uspc;
		this.trty = trty;
		this.rrdt = rrdt;
		this.byam = byam;
		this.expc = expc;
		this.slam = slam;
		this.pram = pram;
		this.amut = amut;
		this.bymd = bymd;
		this.slmd = slmd;
		this.bspc = bspc;
		this.cspc = cspc;
		this.fvda = fvda;
		this.brsy = brsy;
		this.usam = usam;
		this.trfg = trfg;
		this.stcd = stcd;
		this.ercd = ercd;
		this.qdno = qdno;
		this.rgog = rgog;
	}
	public Tranlist() {
		super();
	}
	public String getOgnm() {
		return ognm;
	}
	public void setOgnm(String ognm) {
		this.ognm = ognm;
	}
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
	public String getTrds() {
		return trds;
	}
	public void setTrds(String trds) {
		this.trds = trds;
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
	public String getRrdt() {
		return rrdt;
	}
	public void setRrdt(String rrdt) {
		this.rrdt = rrdt;
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
	public String getBymd() {
		return bymd;
	}
	public void setBymd(String bymd) {
		this.bymd = bymd;
	}
	public String getSlmd() {
		return slmd;
	}
	public void setSlmd(String slmd) {
		this.slmd = slmd;
	}
	public String getBspc() {
		return bspc;
	}
	public void setBspc(String bspc) {
		this.bspc = bspc;
	}
	public String getCspc() {
		return cspc;
	}
	public void setCspc(String cspc) {
		this.cspc = cspc;
	}
	public String getFvda() {
		return fvda;
	}
	public void setFvda(String fvda) {
		this.fvda = fvda;
	}
	public String getBrsy() {
		return brsy;
	}
	public void setBrsy(String brsy) {
		this.brsy = brsy;
	}
	public String getUsam() {
		return usam;
	}
	public void setUsam(String usam) {
		this.usam = usam;
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
	public String getQdno() {
		return qdno;
	}
	public void setQdno(String qdno) {
		this.qdno = qdno;
	}
	public String getRgog() {
		return rgog;
	}
	public void setRgog(String rgog) {
		this.rgog = rgog;
	}
	
}
