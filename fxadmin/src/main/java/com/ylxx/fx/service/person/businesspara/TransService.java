package com.ylxx.fx.service.person.businesspara;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.Tranlist;
/**
 * 查询交易流水
 * @author lz130
 *
 */
public interface TransService {
	//导出查询所有  交易流水
	public List<Tranlist> getAllTrans(String prod, String trancode, 
			String strcuac, String trdtbegin, String trdtend, 
			String byexnm, String comaogcd, String combogcd);
	/**
	 * 分页查询所有 交易流水
	 * @param prod
	 * @param trancode
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param byexnm
	 * @param comaogcd
	 * @param combogcd
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo<Tranlist> getPageAllTrans(String prod, String trancode, 
			String strcuac, String trdtbegin, String trdtend, 
			String byexnm, String comaogcd, String combogcd,
			Integer pageNo, Integer pageSize);
	//P004
	List<Tranlist> queryPerelistExcel(String strlcno, String strcuac,String strIdno,
			String bgdate, String enddate, String com1, String com2,
			String comaogcd, String combogcd,String comtrtp,String comchnl); 
	/**
	 * P004
	 * @param strlcno
	 * @param strcuac
	 * @param strIdno
	 * @param bgdate
	 * @param enddate
	 * @param com1
	 * @param com2
	 * @param comaogcd
	 * @param combogcd
	 * @param comtrtp
	 * @param comchnl
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo<Tranlist> queryPerelist(
			String strlcno, String strcuac, String strIdno,
			String bgdate, String enddate, String com1, 
			String com2, String comaogcd, String combogcd,
			String comtrtp, String comchnl,
			Integer pageNo, Integer pageSize);
	//查询交易码
	public List<Map<String, String>> seltrancode(String prod);
	//查询币别
	public List<String> selbyexnm(String prod);
}
