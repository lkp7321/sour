package com.ylxx.fx.core.domain;

import java.util.List;

import com.ylxx.fx.service.po.PpsyBean;
import com.ylxx.fx.service.po.TbCk_ppcontrol;
import com.ylxx.fx.service.po.TbCk_rulet;
import com.ylxx.fx.service.po.TrdSpcut;
import com.ylxx.fx.service.po.TrdTynm;
import com.ylxx.fx.utils.Table;

public class CkVo {
	private Integer pageNo;
	private Integer pageSize;
	private String sartDate;//
	private String endDate;//
	private String strExnm;//币别
	private String lkno;//敞口流水号
	private String tableName;
	private List<Table> tableList;
	private String userKey;
	private String ckno;//币别，平盘规则设置
	private String apfg;//编号，分类
	private String cuno;//卡号，收集
	private LoginUser loginuser;
	private TrdTynm trdTynm;//特殊账户分类
	private TrdSpcut trdSpcut;//特殊账户收集
	private TbCk_rulet ckRulet;//敞口规则
	private PpsyBean ppsybean;//平盘损益
	private TbCk_ppcontrol ppcontro;//平盘规则
	private List<TbCk_rulet> ruletList;//敞口规则列表
	private List<TbCk_ppcontrol> listppcon;//平盘规则列表
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Table> getTableList() {
		return tableList;
	}
	public void setTableList(List<Table> tableList) {
		this.tableList = tableList;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getSartDate() {
		return sartDate;
	}
	public void setSartDate(String sartDate) {
		this.sartDate = sartDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStrExnm() {
		return strExnm;
	}
	public void setStrExnm(String strExnm) {
		this.strExnm = strExnm;
	}
	public String getLkno() {
		return lkno;
	}
	public void setLkno(String lkno) {
		this.lkno = lkno;
	}
	public String getCkno() {
		return ckno;
	}
	public void setCkno(String ckno) {
		this.ckno = ckno;
	}
	public String getApfg() {
		return apfg;
	}
	public void setApfg(String apfg) {
		this.apfg = apfg;
	}
	public String getCuno() {
		return cuno;
	}
	public void setCuno(String cuno) {
		this.cuno = cuno;
	}
	public LoginUser getLoginuser() {
		return loginuser;
	}
	public void setLoginuser(LoginUser loginuser) {
		this.loginuser = loginuser;
	}
	public TrdTynm getTrdTynm() {
		return trdTynm;
	}
	public void setTrdTynm(TrdTynm trdTynm) {
		this.trdTynm = trdTynm;
	}
	public TrdSpcut getTrdSpcut() {
		return trdSpcut;
	}
	public void setTrdSpcut(TrdSpcut trdSpcut) {
		this.trdSpcut = trdSpcut;
	}
	public TbCk_rulet getCkRulet() {
		return ckRulet;
	}
	public void setCkRulet(TbCk_rulet ckRulet) {
		this.ckRulet = ckRulet;
	}
	public PpsyBean getPpsybean() {
		return ppsybean;
	}
	public void setPpsybean(PpsyBean ppsybean) {
		this.ppsybean = ppsybean;
	}
	public TbCk_ppcontrol getPpcontro() {
		return ppcontro;
	}
	public void setPpcontro(TbCk_ppcontrol ppcontro) {
		this.ppcontro = ppcontro;
	}
	public List<TbCk_rulet> getRuletList() {
		return ruletList;
	}
	public void setRuletList(List<TbCk_rulet> ruletList) {
		this.ruletList = ruletList;
	}
	public List<TbCk_ppcontrol> getListppcon() {
		return listppcon;
	}
	public void setListppcon(List<TbCk_ppcontrol> listppcon) {
		this.listppcon = listppcon;
	}
	public CkVo(String sartDate, String endDate, String strExnm, String lkno,
			String ckno, String apfg, String cuno, LoginUser loginuser,
			TrdTynm trdTynm, TrdSpcut trdSpcut, TbCk_rulet ckRulet,
			PpsyBean ppsybean, TbCk_ppcontrol ppcontro,
			List<TbCk_rulet> ruletList, List<TbCk_ppcontrol> listppcon) {
		super();
		this.sartDate = sartDate;
		this.endDate = endDate;
		this.strExnm = strExnm;
		this.lkno = lkno;
		this.ckno = ckno;
		this.apfg = apfg;
		this.cuno = cuno;
		this.loginuser = loginuser;
		this.trdTynm = trdTynm;
		this.trdSpcut = trdSpcut;
		this.ckRulet = ckRulet;
		this.ppsybean = ppsybean;
		this.ppcontro = ppcontro;
		this.ruletList = ruletList;
		this.listppcon = listppcon;
	}
	public CkVo() {
		super();
	}
	
	
	
	
}
