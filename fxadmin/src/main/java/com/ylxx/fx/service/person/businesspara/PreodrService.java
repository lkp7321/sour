package com.ylxx.fx.service.person.businesspara;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.Preodrlist;
import com.ylxx.fx.service.po.Trd_ogcd;
/**
 * 挂单流水查询
 * @author lz130
 *
 */
public interface PreodrService {
	/**
	 * 分页查询数据
	 * @param prod
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param comaogcd
	 * @param combogcd
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo<Preodrlist> getAllPreodrList(
			String prod, String strcuac, String trdtbegin, 
			String trdtend, String comaogcd, String combogcd,
			Integer pageNo, Integer pageSize);
	/**
	 * 导出
	 * @param prod
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param comaogcd
	 * @param combogcd
	 * @return
	 */
	List<Preodrlist> AllPreodrList(
			String prod, String strcuac, String trdtbegin, 
			String trdtend, String comaogcd, String combogcd);
	/**
	 * 查询机构1
	 * @return
	 */
	List<Trd_ogcd> getqueryOrgan1();
	/**
	 * 查询机构2
	 * @param comaogcd
	 * @return
	 */
	List<Trd_ogcd> getqueryOrgan2(String comaogcd);
}
