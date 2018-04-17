package com.ylxx.fx.service.person.fxipmonitor;

import java.util.List;

import com.ylxx.fx.service.po.CkTotalBean;
import com.ylxx.fx.service.po.User;

public interface GoldCKtotalService {
	//市场报价
	public List<CkTotalBean> ckTotalData();
	public List<CkTotalBean> ckTotal();
	public boolean updateCKZCYK(User user, CkTotalBean ckTotalBean);
	public CkTotalBean ckToalSYYL();
}
