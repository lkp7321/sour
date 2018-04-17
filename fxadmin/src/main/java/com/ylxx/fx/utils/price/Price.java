// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Price.java

package com.ylxx.fx.utils.price;

import java.io.Serializable;

public class Price
	implements Serializable
{

	private static final long serialVersionUID = 0x6a7c359767a0dd6fL;
	private String updateTime;
	private double buyPrice;
	private double sellPrice;

	public Price()
	{
	}

	public String getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}

	public double getBuyPrice()
	{
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice)
	{
		this.buyPrice = buyPrice;
	}

	public double getSellPrice()
	{
		return sellPrice;
	}

	public void setSellPrice(double sellPrice)
	{
		this.sellPrice = sellPrice;
	}
}
