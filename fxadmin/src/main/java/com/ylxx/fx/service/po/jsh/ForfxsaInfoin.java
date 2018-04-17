package com.ylxx.fx.service.po.jsh;
/**
 * 冲销参数
 * @author lz130
 *
 */
public class ForfxsaInfoin {
	/* private String ctof;//资金去向类型
	 toac //资金去向卡号
	 common_user_code //用户名
	 password //密码
	 common_org_code //12位外管局机构号
	 msgno //交易流水
	 idtype_code //证件类型
	 idcode //证件号
	 ctycode //国别
	 bank_self_num //银行自身流水号
	//log.info("补充证件号码：") 
	 biz_tx_chnl_code //业务办理渠道代码
	//log.info("业务类型代码：" + "01") 
	 fcy_remit_amt//汇出资金（包括外汇票据）金额
	 person_name //姓名
	 purfx_acct_cny //购汇人民币账户
	 purfx_type_code//购汇资金属性代码
	 remark //备注
	 tchk_amt //旅行支票金额
	 txccy //币种
	 purfx_amt //购汇金额
	 purfx_cash_amt //购汇提钞金额
	 fcy_acct_amt //存入个人外汇账户金额
	 lcy_acct_no//个人外汇账户账号
*/
	private String COMMON_ORG_CODE; //12位外管局机构号
	private String COMMON_USER_CODE;//外管局柜员号
	private String PASSWORD;//外管局柜员密码
	private String MSGNO;//外管局交易流水号 
	private String REFNO;//业务参号
	private	String BANK_SELF_NUM; //银行自身流水号
	private String CANCEL_REASON; //撤销原因
	private String CANCEL_REMARK; //撤销说明
	public String getCOMMON_ORG_CODE() {
		return COMMON_ORG_CODE;
	}
	public void setCOMMON_ORG_CODE(String cOMMON_ORG_CODE) {
		COMMON_ORG_CODE = cOMMON_ORG_CODE;
	}
	public String getCOMMON_USER_CODE() {
		return COMMON_USER_CODE;
	}
	public void setCOMMON_USER_CODE(String cOMMON_USER_CODE) {
		COMMON_USER_CODE = cOMMON_USER_CODE;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getMSGNO() {
		return MSGNO;
	}
	public void setMSGNO(String mSGNO) {
		MSGNO = mSGNO;
	}
	public String getREFNO() {
		return REFNO;
	}
	public void setREFNO(String rEFNO) {
		REFNO = rEFNO;
	}
	public String getBANK_SELF_NUM() {
		return BANK_SELF_NUM;
	}
	public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
		BANK_SELF_NUM = bANK_SELF_NUM;
	}
	public String getCANCEL_REASON() {
		return CANCEL_REASON;
	}
	public void setCANCEL_REASON(String cANCEL_REASON) {
		CANCEL_REASON = cANCEL_REASON;
	}
	public String getCANCEL_REMARK() {
		return CANCEL_REMARK;
	}
	public void setCANCEL_REMARK(String cANCEL_REMARK) {
		CANCEL_REMARK = cANCEL_REMARK;
	}
	 

}
