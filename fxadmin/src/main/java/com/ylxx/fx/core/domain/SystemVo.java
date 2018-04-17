package com.ylxx.fx.core.domain;

import java.util.List;

import com.ylxx.fx.service.po.Currmsg;
import com.ylxx.fx.service.po.Cytp;
import com.ylxx.fx.service.po.Ppchannel;
import com.ylxx.fx.service.po.Sysctl;
import com.ylxx.fx.service.po.Testtrac;
import com.ylxx.fx.service.po.Trd_ogcd;

public class SystemVo {
	private String userKey;
	private Testtrac testtrac;
	private Sysctl sysctl;
	private String cuac;//卡号
	private String s;//平盘通道开启的操作
	private List<Ppchannel> list;//平盘通道的数组
	private String strTxt;//错误码
	private String strTxt1;//错误描述
	private Trd_ogcd trdOgcd;//机构
	private String ogcd;//机构编号
	private String leve;//级别
	private String newogcd;
    private String oldogcd;
    private String exnm;
    private Currmsg currmsg;
	private Cytp cytp;
	private String cytpName;
	private Integer pageNo;
	private Integer pageSize;
	
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

	public String getCytpName() {
		return cytpName;
	}

	public void setCytpName(String cytpName) {
		this.cytpName = cytpName;
	}

	public Cytp getCytp() {
		return cytp;
	}

	public void setCytp(Cytp cytp) {
		this.cytp = cytp;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getExnm() {
		return exnm;
	}

	public void setExnm(String exnm) {
		this.exnm = exnm;
	}

	public Currmsg getCurrmsg() {
		return currmsg;
	}

	public void setCurrmsg(Currmsg currmsg) {
		this.currmsg = currmsg;
	}

	public String getNewogcd() {
		return newogcd;
	}

	public void setNewogcd(String newogcd) {
		this.newogcd = newogcd;
	}

	public String getOldogcd() {
		return oldogcd;
	}

	public void setOldogcd(String oldogcd) {
		this.oldogcd = oldogcd;
	}

	public Trd_ogcd getTrdOgcd() {
		return trdOgcd;
	}
	public void setTrdOgcd(Trd_ogcd trdOgcd) {
		this.trdOgcd = trdOgcd;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public String getLeve() {
		return leve;
	}
	public void setLeve(String leve) {
		this.leve = leve;
	}
	public String getStrTxt1() {
		return strTxt1;
	}
	public void setStrTxt1(String strTxt1) {
		this.strTxt1 = strTxt1;
	}
	public String getStrTxt() {
		return strTxt;
	}
	public void setStrTxt(String strTxt) {
		this.strTxt = strTxt;
	}
	public List<Ppchannel> getList() {
		return list;
	}
	public void setList(List<Ppchannel> list) {
		this.list = list;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public Testtrac getTesttrac() {
		return testtrac;
	}
	public void setTesttrac(Testtrac testtrac) {
		this.testtrac = testtrac;
	}
	public Sysctl getSysctl() {
		return sysctl;
	}
	public void setSysctl(Sysctl sysctl) {
		this.sysctl = sysctl;
	}
	public String getCuac() {
		return cuac;
	}
	public void setCuac(String cuac) {
		this.cuac = cuac;
	}
	
	public SystemVo() {
		super();
	}
	
}
