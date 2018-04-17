package com.ylxx.fx.service.person.fxipmonitor;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.po.AccinfoMonitorBean;
import com.ylxx.fx.service.po.ChangeInfoList;
import com.ylxx.fx.service.po.CkTotalBean;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.service.po.QYInfoList;
/**
 * 积存金（P003）
 * 监控管理
 * @author lz130
 *
 */
public interface AccumBrnchService {
	/**
	 * 总分行价格
	 * @return
	 */
	List<FxipMonitorVo> getAccumAllPdtBrnch();
	//客户价格
	public List<FxipMonitorVo> getAccumAllPdtCust();
	//总敞口
	/**
	 * 市场报价
	 * 数据1
	 * @return
	 */
	List<CkTotalBean> ckTotalData();
	/**
	 * 总敞口   AND 折算敞口
	 * 数据2
	 * @return
	 */
	List<CkTotalBean> ckTotal();
	//提取损益
	public boolean updateCKZCYK(String userKey,CkTotalBean ckTotalBean);
	//获取盈亏
	public CkTotalBean ckToalSYYL(); 
	//分类敞口
	public List<Map<String,Object>> getaccumCknoTree();
	/**
	 * 分类敞口
	 * @param prcd
	 * @param ckno
	 * @return
	 */
	List<FormuleBean> getselectclassCk(String prcd,String ckno);
	
	/**
	 * 账户信息统计
	 * @param orgn：机构编号
	 * @param date：日期
	 * @return
	 */
	List<AccinfoMonitorBean> getAccinfoList(String orgn,String date);
	/**
	 * 交易信息统计
	 */
	List<ChangeInfoList> getChangeInfo(String dateBegin, String dateEnd,
			String comaogcd,String comaleve,String comalabel);
	/**
	 * 签约信息统计
	 * @param trdtbegin
	 * @param userKey
	 * @return
	 */
	PageInfo<QYInfoList> getQYAccumuRegTrade(Integer pageNo, Integer pageSize, String trdtbegin, String userKey);
}
