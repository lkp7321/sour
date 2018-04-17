package com.ylxx.fx.service.po.jsh;

/**
 * 系统参数
 * 系统参数表CMM_PTPARA
 * @author lz130
 *
 */
public class Cmm_Ptpara {
	/**
	 * 系统参数编号，C001、C002等
	 */
	private String paid;
	/**
	 * 分类编号
	 */
	private String pasubid;
	/**
	 * 参数名称
	 */
	private String remk;
	/**
	 * 参数值
	 */
	private String valu;
	/**
	 * 状态，0启用、1停用
	 */
	private String stat;

	public String getPasubid() {
		return pasubid;
	}

	public void setPasubid(String pasubid) {
		this.pasubid = pasubid;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getRemk() {
		return remk;
	}

	public void setRemk(String remk) {
		this.remk = remk;
	}

	public String getValu() {
		return valu;
	}

	public void setValu(String valu) {
		this.valu = valu;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
}
