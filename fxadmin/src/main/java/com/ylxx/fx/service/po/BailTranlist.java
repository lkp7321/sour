package com.ylxx.fx.service.po;

public class BailTranlist {
	private String acco;
	private String note;
	public String ognm;// 机构名称
	public String lcno;// 本地流水号
	public String trdt;// 交易日期YYYYMMDD
	public String trtm;// 交易时间HH:MM:SS
	public String trds;// 交易代码
	public String cuno;// 客户号
	public String cuac;// 账号
	public String trac;// 交易账号
	public String cyen;// 币种英文名简称
	public String caty;// 账户类型（0 卡 1折）
	public String cxfg;// 钞汇标志（0 钞 1汇）
	public String trtp;// 1、转入 2：转出 3:外汇买卖 4.签约5解约 6查询,7其他
	public String bycy;// 买入币种
	public String bynm;// 买入币种英文名简称
	public String slcy;// 卖出币种
	public String slnm;// 卖出币种英文名简称
	public String uspc;// 客户上送汇率
	public String trty;// 即时交易上送类型
	public String rrdt;// 容忍点差
	public String byam;// 买入金额
	public String expc;// 成交汇率
	public String slam;// 卖出金额
	public String pram;// 盈亏金额（折美元）
	public String amut;// 发生额（转账）
	public String bymd;// 买入币别兑美元的中间价
	public String slmd;// 卖出币别兑美元的中间价
	public String bspc;// 基准汇率
	public String cspc;// 成本汇率
	public String fvda;// 优惠点数
	public String brsy;// 分行损益
	public String usam;// 折美元金额
	public String trfg;// 交易方式1-即时交易成交 2-指定汇率交易成交 3-委托交易成交
	public String stcd;// 记录状态
	public String ercd;// 错误码
	public String qdno;
	public String rgog;
	public String mt4c;// MT4用户组
	public String trsn;// 交易流水号
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAcco() {
		return acco;
	}
	public void setAcco(String acco) {
		this.acco = acco;
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
	public String getMt4c() {
		return mt4c;
	}
	public void setMt4c(String mt4c) {
		this.mt4c = mt4c;
	}
	public String getTrsn() {
		return trsn;
	}
	public void setTrsn(String trsn) {
		this.trsn = trsn;
	}
}
