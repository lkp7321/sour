package com.ylxx.fx.service.po;

import java.io.Serializable;

public class UserBean implements Serializable {

	private static final long serialVersionUID = -2682305557890221059L;
	private Integer id;
	private String username;
	private String password;
	private Double account;

	public UserBean() {
		super();
	}

	public UserBean(Integer id, String username, String password, Double account) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.account = account;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getAccount() {
		return account;
	}

	public void setAccount(Double account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "UserBean [account=" + account + ", id=" + id + ", password=" + password + ", username=" + username
				+ "]";
	}
}
