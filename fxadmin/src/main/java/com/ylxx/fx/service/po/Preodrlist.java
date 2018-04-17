package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Preodrlist implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String cuac;// 卡号
	public String ogcd;// 机构号
	public String ognm;// 机构名称

	public String rqno;// CHAR(8) 挂盘申请号（合同号）
	public String trdt;// CHAR(8) 申请日期
	public String sqno;// NUMBER(2) 选择顺序号（买入币种号，区别选择追加挂盘记录）
	public String odtp;// CHAR(1) （挂单类型：0.正常挂单（单向止盈、单向止损、双向） 1.止盈追加 2.止损追加
	// 3.选择挂单）(考虑双向追加)
	public String bsfg;// CHAR(1) 买卖方向, 买 0, 卖 1
	public String exse;// NUMBER(2) 币别对序号
	public String exnm;// CHAR(6) 币别对名称
	public String excd;// CHAR(4) 币别对编码
	public String trcd;// CHAR(5) 交易代码
	public String trms;
	public String trtm;// CHAR(8) 申请时间
	public String vddt;// CHAR(8) 有效日期
	public String prdt;// CHAR(8) 成交日期
	public String prtm;// CHAR(8) 成交时间
	public String lcno;// CHAR(8) 本地成交流水号
	public String tlcs;// CHAR(8) 后台流水号
	public String cuno;// CHAR(18) 客户号
	public String trac;// CHAR(16) 交易账号
	public String cyen;// CHAR(3) 账户币种名称
	public String bynm;// CHAR（3） 买入币种英文名
	public String slnm;// CHAR（3） 卖出币种英文名
	public String cxfg;// CHAR(1) 钞汇标志
	public String akpc;// NUMBER(12,6） 止盈委托汇率
	public String zspc;// NUMBER(12,6） 止损委托汇率
	public String byam;// NUMBER(13,2） 买入金额
	public String slam;// NUMBER(13,2） 卖出金额
	public String usam;// NUMBER(13,2） 折美元金额
	public String fvda;// NUMBER 优惠点数
	public String mtpc;// NUMBER(12,6） 匹配汇率
	public String expc;// NUMBER(12,6） 成交汇率（按客户挂盘汇率，加上优惠）
	public String pram;// NUMBER(13,2） 盈亏金额（折美元）
	public String cono;// CHAR (8) 撤单流水号
	public String sequ;// CHAR(8) 交易报文头信息流水号
	public String qdtp;// CHAR(1) 渠道类型（1-柜台；2-网银；3-自助；4-电话银行；9-内交易 此时网点号送0000）
	public String stcd;// CHAR（1）
	// 记录状态（A.挂盘申请、M.匹配、P.成交、O.超期撤单、C.客户撤单、W.等待生效、I.生效撤单、F.记账失败
	public String ercd;// CHAR（5） 错误码
	public String ertx;// VARCHAR(100) 当STCD 为F时具体失败说明
	public Preodrlist(String cuac, String ogcd, String ognm, String rqno,
			String trdt, String sqno, String odtp, String bsfg, String exse,
			String exnm, String excd, String trcd, String trms, String trtm,
			String vddt, String prdt, String prtm, String lcno, String tlcs,
			String cuno, String trac, String cyen, String bynm, String slnm,
			String cxfg, String akpc, String zspc, String byam, String slam,
			String usam, String fvda, String mtpc, String expc, String pram,
			String cono, String sequ, String qdtp, String stcd, String ercd,
			String ertx) {
		super();
		this.cuac = cuac;
		this.ogcd = ogcd;
		this.ognm = ognm;
		this.rqno = rqno;
		this.trdt = trdt;
		this.sqno = sqno;
		this.odtp = odtp;
		this.bsfg = bsfg;
		this.exse = exse;
		this.exnm = exnm;
		this.excd = excd;
		this.trcd = trcd;
		this.trms = trms;
		this.trtm = trtm;
		this.vddt = vddt;
		this.prdt = prdt;
		this.prtm = prtm;
		this.lcno = lcno;
		this.tlcs = tlcs;
		this.cuno = cuno;
		this.trac = trac;
		this.cyen = cyen;
		this.bynm = bynm;
		this.slnm = slnm;
		this.cxfg = cxfg;
		this.akpc = akpc;
		this.zspc = zspc;
		this.byam = byam;
		this.slam = slam;
		this.usam = usam;
		this.fvda = fvda;
		this.mtpc = mtpc;
		this.expc = expc;
		this.pram = pram;
		this.cono = cono;
		this.sequ = sequ;
		this.qdtp = qdtp;
		this.stcd = stcd;
		this.ercd = ercd;
		this.ertx = ertx;
	}
	public Preodrlist() {
		super();
	}
	public String getCuac() {
		return cuac;
	}
	public void setCuac(String cuac) {
		this.cuac = cuac;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public String getOgnm() {
		return ognm;
	}
	public void setOgnm(String ognm) {
		this.ognm = ognm;
	}
	public String getRqno() {
		return rqno;
	}
	public void setRqno(String rqno) {
		this.rqno = rqno;
	}
	public String getTrdt() {
		return trdt;
	}
	public void setTrdt(String trdt) {
		this.trdt = trdt;
	}
	public String getSqno() {
		return sqno;
	}
	public void setSqno(String sqno) {
		this.sqno = sqno;
	}
	public String getOdtp() {
		return odtp;
	}
	public void setOdtp(String odtp) {
		this.odtp = odtp;
	}
	public String getBsfg() {
		return bsfg;
	}
	public void setBsfg(String bsfg) {
		this.bsfg = bsfg;
	}
	public String getExse() {
		return exse;
	}
	public void setExse(String exse) {
		this.exse = exse;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getExcd() {
		return excd;
	}
	public void setExcd(String excd) {
		this.excd = excd;
	}
	public String getTrcd() {
		return trcd;
	}
	public void setTrcd(String trcd) {
		this.trcd = trcd;
	}
	public String getTrms() {
		return trms;
	}
	public void setTrms(String trms) {
		this.trms = trms;
	}
	public String getTrtm() {
		return trtm;
	}
	public void setTrtm(String trtm) {
		this.trtm = trtm;
	}
	public String getVddt() {
		return vddt;
	}
	public void setVddt(String vddt) {
		this.vddt = vddt;
	}
	public String getPrdt() {
		return prdt;
	}
	public void setPrdt(String prdt) {
		this.prdt = prdt;
	}
	public String getPrtm() {
		return prtm;
	}
	public void setPrtm(String prtm) {
		this.prtm = prtm;
	}
	public String getLcno() {
		return lcno;
	}
	public void setLcno(String lcno) {
		this.lcno = lcno;
	}
	public String getTlcs() {
		return tlcs;
	}
	public void setTlcs(String tlcs) {
		this.tlcs = tlcs;
	}
	public String getCuno() {
		return cuno;
	}
	public void setCuno(String cuno) {
		this.cuno = cuno;
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
	public String getBynm() {
		return bynm;
	}
	public void setBynm(String bynm) {
		this.bynm = bynm;
	}
	public String getSlnm() {
		return slnm;
	}
	public void setSlnm(String slnm) {
		this.slnm = slnm;
	}
	public String getCxfg() {
		return cxfg;
	}
	public void setCxfg(String cxfg) {
		this.cxfg = cxfg;
	}
	public String getAkpc() {
		return akpc;
	}
	public void setAkpc(String akpc) {
		this.akpc = akpc;
	}
	public String getZspc() {
		return zspc;
	}
	public void setZspc(String zspc) {
		this.zspc = zspc;
	}
	public String getByam() {
		return byam;
	}
	public void setByam(String byam) {
		this.byam = byam;
	}
	public String getSlam() {
		return slam;
	}
	public void setSlam(String slam) {
		this.slam = slam;
	}
	public String getUsam() {
		return usam;
	}
	public void setUsam(String usam) {
		this.usam = usam;
	}
	public String getFvda() {
		return fvda;
	}
	public void setFvda(String fvda) {
		this.fvda = fvda;
	}
	public String getMtpc() {
		return mtpc;
	}
	public void setMtpc(String mtpc) {
		this.mtpc = mtpc;
	}
	public String getExpc() {
		return expc;
	}
	public void setExpc(String expc) {
		this.expc = expc;
	}
	public String getPram() {
		return pram;
	}
	public void setPram(String pram) {
		this.pram = pram;
	}
	public String getCono() {
		return cono;
	}
	public void setCono(String cono) {
		this.cono = cono;
	}
	public String getSequ() {
		return sequ;
	}
	public void setSequ(String sequ) {
		this.sequ = sequ;
	}
	public String getQdtp() {
		return qdtp;
	}
	public void setQdtp(String qdtp) {
		this.qdtp = qdtp;
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
	public String getErtx() {
		return ertx;
	}
	public void setErtx(String ertx) {
		this.ertx = ertx;
	}
	
}
