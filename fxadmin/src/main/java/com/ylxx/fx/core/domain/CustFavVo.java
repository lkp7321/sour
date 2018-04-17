package com.ylxx.fx.core.domain;

import java.util.List;

import com.ylxx.fx.utils.Table;

public class CustFavVo {
	private String ognm;// 交易流水号
	private String lcno;// 交易流水号
	private String trdt;// 交易日期YYYYMMDD
	private String trtm;// 交易时间HH:MM:SS
	private String trcd;// 交易代码
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
	private String culv;
	private String exfg;
	private String exid;
	private String cutp;
	private String extr;
	private String pftp;//冻结标志
	private String vlfg;
	private String tram;
	private String fonm;
	private String fram;
	private String csof;
	private String soac;
	private String ctof;
	private String toac;
	private String cash;
	private String fxid;
	private String cunm;//客户姓名
	private String idtp;//证件类型
	private String idno;//证件号码
	private String extp;//结售汇项目类型
	private String chnl;//渠道
	private String stat;//处理标识
	private String olno;//冲销流水号
	//外管局国别映射表
	private String cont;//国家
	private String pety;//交易主体
	private String name;//国别
	private String cmbccout;//民生国别
	private String cout;//外管局国别
	private String copycout;//国别备注
	//登陆外管局用户信息表
	private String ogcd;//机构
	private String trtl;//柜员号
	private String tltp;//柜员类型
	private String bhid;//柜员机构
	private String tellerid;//柜员id
	private String pass;//柜员密码
	//客户优惠信息
	private String fvjh ;//结汇优惠
	private String fvgh ;//购汇优惠
	private Integer pageNo;
	private Integer pageSize;
	private String userKey;
	private String dgfieldbegin;//起始时间
	private String dgfieldend;//结束时间
	private String comStcd;//状态
	private String tableName;
	private List<Table> tableList;
	
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
	public String getCulv() {
		return culv;
	}
	public void setCulv(String culv) {
		this.culv = culv;
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
	public String getVlfg() {
		return vlfg;
	}
	public void setVlfg(String vlfg) {
		this.vlfg = vlfg;
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
	public String getFram() {
		return fram;
	}
	public void setFram(String fram) {
		this.fram = fram;
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
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public String getFxid() {
		return fxid;
	}
	public void setFxid(String fxid) {
		this.fxid = fxid;
	}
	public String getCunm() {
		return cunm;
	}
	public void setCunm(String cunm) {
		this.cunm = cunm;
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
	public String getExtp() {
		return extp;
	}
	public void setExtp(String extp) {
		this.extp = extp;
	}
	public String getChnl() {
		return chnl;
	}
	public void setChnl(String chnl) {
		this.chnl = chnl;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getOlno() {
		return olno;
	}
	public void setOlno(String olno) {
		this.olno = olno;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getPety() {
		return pety;
	}
	public void setPety(String pety) {
		this.pety = pety;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCmbccout() {
		return cmbccout;
	}
	public void setCmbccout(String cmbccout) {
		this.cmbccout = cmbccout;
	}
	public String getCout() {
		return cout;
	}
	public void setCout(String cout) {
		this.cout = cout;
	}
	public String getCopycout() {
		return copycout;
	}
	public void setCopycout(String copycout) {
		this.copycout = copycout;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public String getTrtl() {
		return trtl;
	}
	public void setTrtl(String trtl) {
		this.trtl = trtl;
	}
	public String getTltp() {
		return tltp;
	}
	public void setTltp(String tltp) {
		this.tltp = tltp;
	}
	public String getBhid() {
		return bhid;
	}
	public void setBhid(String bhid) {
		this.bhid = bhid;
	}
	public String getTellerid() {
		return tellerid;
	}
	public void setTellerid(String tellerid) {
		this.tellerid = tellerid;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getFvjh() {
		return fvjh;
	}
	public void setFvjh(String fvjh) {
		this.fvjh = fvjh;
	}
	public String getFvgh() {
		return fvgh;
	}
	public void setFvgh(String fvgh) {
		this.fvgh = fvgh;
	}
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
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getDgfieldbegin() {
		return dgfieldbegin;
	}
	public void setDgfieldbegin(String dgfieldbegin) {
		this.dgfieldbegin = dgfieldbegin;
	}
	public String getDgfieldend() {
		return dgfieldend;
	}
	public void setDgfieldend(String dgfieldend) {
		this.dgfieldend = dgfieldend;
	}
	public String getComStcd() {
		return comStcd;
	}
	public void setComStcd(String comStcd) {
		this.comStcd = comStcd;
	}
}
