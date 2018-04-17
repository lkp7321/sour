package com.ylxx.fx.service.po;

import java.io.Serializable;

public class TbCk_rulet implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prcd; //产品名称
	private String cknm; //敞口名称
	private String ckno; //敞口编号
	private Integer leve; //敞口级别
	private Integer rcnt; //敞口规则个数
	private String rule; //规则描述
	private String ruhy; //规则含义
	private String usfg; //状态
	
	public String getPrcd() {
		return prcd;
	}
	public void setPrcd(String prcd) {
		this.prcd = prcd;
	}
	public String getCknm() {
		return cknm;
	}
	public void setCknm(String cknm) {
		this.cknm = cknm;
	}
	public String getCkno() {
		return ckno;
	}
	public void setCkno(String ckno) {
		this.ckno = ckno;
	}
	public Integer getLeve() {
		return leve;
	}
	public void setLeve(Integer leve) {
		this.leve = leve;
	}
	public Integer getRcnt() {
		return rcnt;
	}
	public void setRcnt(Integer rcnt) {
		this.rcnt = rcnt;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getRuhy() {
		return ruhy;
	}
	public void setRuhy(String ruhy) {
		this.ruhy = ruhy;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public TbCk_rulet(String prcd, String cknm, String ckno, Integer leve,
			Integer rcnt, String rule, String ruhy, String usfg) {
		super();
		this.prcd = prcd;
		this.cknm = cknm;
		this.ckno = ckno;
		this.leve = leve;
		this.rcnt = rcnt;
		this.rule = rule;
		this.ruhy = ruhy;
		this.usfg = usfg;
	}
	public TbCk_rulet() {
		super();
	}
	
}
