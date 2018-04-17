package com.ylxx.fx.service.po.jsh;

import java.math.BigDecimal;
/**
 * 定投签约
 * @author lz130
 *
 */
public class TbTrd_RegMsgList {
	/**
	 * 卡号/交易账号
	 */
	private String cuac;
	/**
	 * 交易流水号
	 */
	private String lcno;
	/**
	 * 定投编号
	 */
	private String rsno;
	/**
	 * 额度标志
	 */
	private String vlfg;
	/**
	 * 客户号
	 */
	private String cuno;
	/**
	 * 证件号码
	 */
	private String idno;
	/**
	 * 国别
	 */
	private String cont;
	/**
	 * 证件类型代码
	 */
	private String idtp;
	/**
	 * 银行自身流水号
	 */
	private String bank_self_num;
	/**
	 * 客户姓名
	 */
	private String cunm;
	/**
	 * 结汇/购汇客户类型
	 */
	private String cutp;
	/**
	 * 结购汇交易项目代码
	 */
	private String extr;
	/**
	 * 结购汇交易种类
	 */
	private String extp;
	/**
	 * 购汇用途
	 */
	private String bytp;
	/**
	 * 结购汇标志
	 */
	private String jgfg;
	/**
	 * 外币币种
	 */
	private String fonm;
	/**
	 * 人民币币种
	 */
	private String cynm;
	/**
	 * 外币金额
	 */
	private BigDecimal foam;
	/**
	 * 人民币金额
	 */
	private BigDecimal cyam;
	/**
	 * 标准比别对
	 */
	private String exnm;
	/**
	 * 货币对价格
	 */
	private BigDecimal expc;
	/**
	 * 定投频率
	 */
	private String msfy;
	/**
	 * 资金来源类型
	 */
	private String csof;
	/**
	 * 资金来源账号
	 */
	private String soac;
	/**
	 * 资金去向类型
	 */
	private String ctof;
	/**
	 * 资金去向账号
	 */
	private String toac;
	/**
	 * 尾箱号
	 */
	private String culi;
	/**
	 * 收款人账号
	 */
	private String pano;
	/**
	 * 收款人姓名
	 */
	private String panm;
	/**
	 * 汇路
	 */
	private String path;
	/**
	 * 摘要
	 */
	private String post;
	/**
	 * 收款人账号类型
	 */
	private String patp;
	/**
	 * 收款账号开户行
	 */
	private String prid;
	/**
	 * 收款账户开户行名称
	 */
	private String prnm;
	/**
	 * 备注
	 */
	private String smry;
	/**
	 * 签约日期
	 */
	private String rgdt;
	/**
	 * 签约时间
	 */
	private String rgtm;
	/**
	 * 解约日期
	 */
	private String crdt;
	/**
	 * 解约时间
	 */
	private String crtm;
	/**
	 * 执行日期
	 */
	private String erdt;
	/**
	 * 签约状态
	 */
	private String rgtp;
	
	
	public String getCuac() {
		return cuac;
	}
	public void setCuac(String cuac) {
		this.cuac = cuac;
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
	public String getLcno() {
		return lcno;
	}
	public void setLcno(String lcno) {
		this.lcno = lcno;
	}
	public String getRsno() {
		return rsno;
	}
	public void setRsno(String rsno) {
		this.rsno = rsno;
	}
	public String getVlfg() {
		return vlfg;
	}
	public void setVlfg(String vlfg) {
		this.vlfg = vlfg;
	}
	public String getCuno() {
		return cuno;
	}
	public void setCuno(String cuno) {
		this.cuno = cuno;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getIdtp() {
		return idtp;
	}
	
	public String getExtr() {
		return extr;
	}
	public void setExtr(String extr) {
		this.extr = extr;
	}
	public void setIdtp(String idtp) {
		this.idtp = idtp;
	}
	public String getBank_self_num() {
		return bank_self_num;
	}
	public void setBank_self_num(String bank_self_num) {
		this.bank_self_num = bank_self_num;
	}
	public String getCunm() {
		return cunm;
	}
	public void setCunm(String cunm) {
		this.cunm = cunm;
	}
	public String getCutp() {
		return cutp;
	}
	public void setCutp(String cutp) {
		this.cutp = cutp;
	}
	public String getExtp() {
		return extp;
	}
	public void setExtp(String extp) {
		this.extp = extp;
	}
	public String getBytp() {
		return bytp;
	}
	public void setBytp(String bytp) {
		this.bytp = bytp;
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
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	
	public BigDecimal getExpc() {
		return expc;
	}
	public void setExpc(BigDecimal expc) {
		this.expc = expc;
	}
	public String getMsfy() {
		return msfy;
	}
	public void setMsfy(String msfy) {
		this.msfy = msfy;
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
	public String getCuli() {
		return culi;
	}
	public void setCuli(String culi) {
		this.culi = culi;
	}
	public String getPano() {
		return pano;
	}
	public void setPano(String pano) {
		this.pano = pano;
	}
	public String getPanm() {
		return panm;
	}
	public void setPanm(String panm) {
		this.panm = panm;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPatp() {
		return patp;
	}
	public void setPatp(String patp) {
		this.patp = patp;
	}
	public String getPrid() {
		return prid;
	}
	public void setPrid(String prid) {
		this.prid = prid;
	}
	public String getPrnm() {
		return prnm;
	}
	public void setPrnm(String prnm) {
		this.prnm = prnm;
	}
	public String getSmry() {
		return smry;
	}
	public void setSmry(String smry) {
		this.smry = smry;
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
	public String getErdt() {
		return erdt;
	}
	public void setErdt(String erdt) {
		this.erdt = erdt;
	}
	public String getRgtp() {
		return rgtp;
	}
	public void setRgtp(String rgtp) {
		this.rgtp = rgtp;
	}
	
}
