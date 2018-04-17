package com.ylxx.fx.service.price.pricesource;

import java.util.*;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.CmmChkpara;
import com.ylxx.fx.service.po.Mktinfo;

public interface MktinfoService {
	public List<Mktinfo> getMkPrice();
	//校验器
//	public List<CmmChkpara> jiaoyansel(String mkid, String exnm);
	/**
	 * 查询校验器设置数据
	 * @param pageNo
	 * @param pageSize
	 * @param mkid
	 * @param exnm
	 * @return
	 */
	public PageInfo<CmmChkpara> pagejiaoyansel(
			Integer pageNo, Integer pageSize, 
			String mkid, String exnm);
	public boolean openChannel(String userKey, List<CmmChkpara> chlist);
	public boolean closeChannel(String userKey, List<CmmChkpara> chlist);
	public boolean updateCmmprice(CmmChkpara cmmbean, String userKey);
	
	public List<Map<String,Object>> selUpType();
}
