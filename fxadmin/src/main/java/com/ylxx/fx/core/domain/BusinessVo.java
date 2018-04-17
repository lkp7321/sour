package com.ylxx.fx.core.domain;

import java.util.List;

import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.service.po.Maxpavpoint;
import com.ylxx.fx.service.po.Ptpara;
import com.ylxx.fx.service.po.Trd_Favrule;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.po.calendar.OriginalVO;
import com.ylxx.fx.service.po.calendar.TradeCodeVO;
import com.ylxx.fx.service.po.calendar.TradeProCalVO;
import com.ylxx.fx.utils.Table;

public class BusinessVo {
	private String userKey;
	private Integer pageNo;
	private Integer pageSize;
	private String strcuac;//卡号
	private String trdtbegin;
	private String trdtend;
	private String comaogcd;//机构1
	private String combogcd;//机构2
	private String ogcd;
	private String ognm;
	private String tableName;
	private List<Table> tableList;
	private String trancode;
	private String byexnm;
	private Ptpara ptpara;
	private String fvid;
	private String rule;
	private List<Favrule> favruleList;
	private List<Trd_Favrule> chlist;
	private Maxpavpoint maxpoint;
	private List<FavourRule> favourList;
	private TradeProCalVO tradeProCalVo;
	private String curLcno;//流水号
	private String strcuno;//客户号
	private String strsoac;//来源账号
	private String vurData; //日期
	private CalendarVO calVo;
	private String calendarID;
	private String levelTy;
	private String caltime;
	private String tradeCode;
	private String strlcno;
	private String strIdno;
	private String com1;//"0"/"1"
	private String com2;//"all"/
	private String comchnl;
	private String comtrtp;
	
	private List<OriginalVO> calList;
	private List<TradeCodeVO> codeList;
	
	
	public String getOgnm() {
		return ognm;
	}
	public void setOgnm(String ognm) {
		this.ognm = ognm;
	}
	public String getStrlcno() {
		return strlcno;
	}
	public void setStrlcno(String strlcno) {
		this.strlcno = strlcno;
	}
	public String getStrIdno() {
		return strIdno;
	}
	public void setStrIdno(String strIdno) {
		this.strIdno = strIdno;
	}
	public String getCom1() {
		return com1;
	}
	public void setCom1(String com1) {
		this.com1 = com1;
	}
	public String getCom2() {
		return com2;
	}
	public void setCom2(String com2) {
		this.com2 = com2;
	}
	
	public String getComchnl() {
		return comchnl;
	}
	public void setComchnl(String comchnl) {
		this.comchnl = comchnl;
	}
	public String getComtrtp() {
		return comtrtp;
	}
	public void setComtrtp(String comtrtp) {
		this.comtrtp = comtrtp;
	}
	public Maxpavpoint getMaxpoint() {
		return maxpoint;
	}
	public void setMaxpoint(Maxpavpoint maxpoint) {
		this.maxpoint = maxpoint;
	}
	public List<Trd_Favrule> getChlist() {
		return chlist;
	}
	public void setChlist(List<Trd_Favrule> chlist) {
		this.chlist = chlist;
	}
	public TradeProCalVO getTradeProCalVo() {
		return tradeProCalVo;
	}
	public void setTradeProCalVo(TradeProCalVO tradeProCalVo) {
		this.tradeProCalVo = tradeProCalVo;
	}
	public CalendarVO getCalVo() {
		return calVo;
	}
	public void setCalVo(CalendarVO calVo) {
		this.calVo = calVo;
	}
	public Ptpara getPtpara() {
		return ptpara;
	}
	public void setPtpara(Ptpara ptpara) {
		this.ptpara = ptpara;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
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
	public List<OriginalVO> getCalList() {
		return calList;
	}
	public void setCalList(List<OriginalVO> calList) {
		this.calList = calList;
	}
	public List<TradeCodeVO> getCodeList() {
		return codeList;
	}
	public void setCodeList(List<TradeCodeVO> codeList) {
		this.codeList = codeList;
	}
	public String getCaltime() {
		return caltime;
	}
	public void setCaltime(String caltime) {
		this.caltime = caltime;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getCalendarID() {
		return calendarID;
	}
	public void setCalendarID(String calendarID) {
		this.calendarID = calendarID;
	}
	public String getLevelTy() {
		return levelTy;
	}
	public void setLevelTy(String levelTy) {
		this.levelTy = levelTy;
	}
	public String getCurLcno() {
		return curLcno;
	}
	public void setCurLcno(String curLcno) {
		this.curLcno = curLcno;
	}
	public String getStrcuno() {
		return strcuno;
	}
	public void setStrcuno(String strcuno) {
		this.strcuno = strcuno;
	}
	public String getStrsoac() {
		return strsoac;
	}
	public void setStrsoac(String strsoac) {
		this.strsoac = strsoac;
	}
	public String getVurData() {
		return vurData;
	}
	public void setVurData(String vurData) {
		this.vurData = vurData;
	}
	public List<FavourRule> getFavourList() {
		return favourList;
	}
	public void setFavourList(List<FavourRule> favourList) {
		this.favourList = favourList;
	}
	public List<Favrule> getFavruleList() {
		return favruleList;
	}
	public void setFavruleList(List<Favrule> favruleList) {
		this.favruleList = favruleList;
	}
	public String getFvid() {
		return fvid;
	}
	public void setFvid(String fvid) {
		this.fvid = fvid;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
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
	public String getStrcuac() {
		return strcuac;
	}
	public void setStrcuac(String strcuac) {
		this.strcuac = strcuac;
	}
	public String getTrdtbegin() {
		return trdtbegin;
	}
	public void setTrdtbegin(String trdtbegin) {
		this.trdtbegin = trdtbegin;
	}
	public String getTrdtend() {
		return trdtend;
	}
	public void setTrdtend(String trdtend) {
		this.trdtend = trdtend;
	}
	public String getComaogcd() {
		return comaogcd;
	}
	public void setComaogcd(String comaogcd) {
		this.comaogcd = comaogcd;
	}
	public String getCombogcd() {
		return combogcd;
	}
	public void setCombogcd(String combogcd) {
		this.combogcd = combogcd;
	}
	public String getTrancode() {
		return trancode;
	}
	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}
	public String getByexnm() {
		return byexnm;
	}
	public void setByexnm(String byexnm) {
		this.byexnm = byexnm;
	}
	
}
