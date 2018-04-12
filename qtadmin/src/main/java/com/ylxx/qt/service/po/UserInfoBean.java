package com.ylxx.qt.service.po;

import java.io.Serializable;

public class UserInfoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userid; // 用户id
	private String username; // 用户名
	private String password; // 密码
	private String investor; // 账号
	private String roleid; // 角色
	private String phonenumber; // 手机号
	private String email; // 邮箱
	private String remark; // 备注
	private String isnull; // 是否为空
	private String truename; // 真实名字
	private String sex; // 性别
	private String residence; // 居住地
	private String qq; // qq
	private String education; // 学历
	private String position; // 职位
	private String profession; // 职业
	private String certificatetype;// 证件类型
	private String certificateno; // 证件号码
	private String signature; // 个性签名
	private String timezone; // 时区
	private String issecret; // 是否公开
	private String weixinID; // 微信ID

	public UserInfoBean() {

	}

	public UserInfoBean(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UserInfoBean(String username, String password, String phonenumber, String email, String remark,
			String truename, String sex, String residence, String qq, String education, String position,
			String profession, String certificatetype, String certificateno, String signature, String timezone,
			String issecret, String weixinID) {
		super();
		this.username = username;
		this.password = password;
		this.phonenumber = phonenumber;
		this.email = email;
		this.remark = remark;
		this.truename = truename;
		this.sex = sex;
		this.residence = residence;
		this.qq = qq;
		this.education = education;
		this.position = position;
		this.profession = profession;
		this.certificatetype = certificatetype;
		this.certificateno = certificateno;
		this.signature = signature;
		this.timezone = timezone;
		this.issecret = issecret;
		this.weixinID = weixinID;
	}

	public UserInfoBean(String userid, String username, String password, String phonenumber, String remark) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.phonenumber = phonenumber;
		this.remark = remark;
	}
	/*
	 * public UserInfoBean(String userid, String username, String password,
	 * String phonenumber, String email, String remark,String truename,String
	 * sex,String residence, String qq,String education,String position,String
	 * profession,String certificatetype, String certificateno,String
	 * signature,String timezone,String issecret) { super(); this.userid =
	 * userid; this.username = username; this.password = password;
	 * this.phonenumber = phonenumber; this.email = email; this.remark = remark;
	 * this.truename = truename; this.sex = sex; this.residence = residence;
	 * this.qq = qq; this.education = education; this.position = position;
	 * this.profession = profession; this.certificatetype = certificatetype;
	 * this.certificateno = certificateno; this.signature = signature;
	 * this.timezone = timezone; this.issecret = issecret; }
	 */

	public UserInfoBean(String userid, String investor, String username) {
		super();
		this.userid = userid;
		this.username = username;
		this.investor = investor;
	}

	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserInfoBean [username=" + username + ", password=" + password + ", investor=" + investor + ", roleid="
				+ roleid + "]";
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEamil() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getIsnull() {
		return isnull;
	}

	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getCertificatetype() {
		return certificatetype;
	}

	public void setCertificatetype(String certificatetype) {
		this.certificatetype = certificatetype;
	}

	public String getCertificateno() {
		return certificateno;
	}

	public void setCertificateno(String certificateno) {
		this.certificateno = certificateno;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getIssecret() {
		return issecret;
	}

	public void setIssecret(String issecret) {
		this.issecret = issecret;
	}

	public String getWeixinID() {
		return weixinID;
	}

	public void setWeixinID(String weixinID) {
		this.weixinID = weixinID;
	}

}
