package com.ylxx.fx.service.po.jsh;

import java.math.BigDecimal;
/**
 * 定投交易流水
 * @author lz130
 *
 */
public class TbTrd_TranList {
	private String cuac;        //交易账号 
	private String lcno;		//流水号
	private String idno;		//证件号码
	private String rsno;		//定投编号
	private String cuno;		//客户号
	private String jgfg;		//结购汇标志
	private String fonm;		//外币币种
	private String cynm;		//人民币币种
	private BigDecimal foam;		//外币金额
	private BigDecimal cyam;		//人民币金额
	private BigDecimal expc;		//成交价格
	private String trdt;		//交易日期
	private String trtm;		//交易时间
	private String type;		//流水类型
	private String status_code;	//状态标识码
	public String getLcno() {
		return lcno;
	}
	public void setLcno(String lcno) {
		this.lcno = lcno;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getRsno() {
		return rsno;
	}
	public void setRsno(String rsno) {
		this.rsno = rsno;
	}
	public String getCuno() {
		return cuno;
	}
	public void setCuno(String cuno) {
		this.cuno = cuno;
	}
	public String getJgfg() {
		return jgfg;
	}
	public void setJgfg(String jgfg) {
		this.jgfg = jgfg;
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
	public BigDecimal getFoam() {
		return foam;
	}
	public void setFoam(BigDecimal foam) {
		this.foam = foam;
	}
	public BigDecimal getCyam() {
		return cyam;
	}
	public void setCyam(BigDecimal cyam) {
		this.cyam = cyam;
	}
	public BigDecimal getExpc() {
		return expc;
	}
	public void setExpc(BigDecimal expc) {
		this.expc = expc;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getCuac() {
		return cuac;
	}
	public void setCuac(String cuac) {
		this.cuac = cuac;
	}
}
