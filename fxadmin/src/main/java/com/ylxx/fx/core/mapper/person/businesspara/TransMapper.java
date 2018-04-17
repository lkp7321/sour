package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Tranlist;
/**
 * 交易流水查询
 * @author lz130
 *
 */
public interface TransMapper {
	/**
	 * P001
	 * 查询交易码
	 * @param prod
	 * @return
	 */
	List<Map<String, String>> querytrancodeP001(@Param("prod")String prod);
	List<Map<String, String>> querytrancodeP002();
	List<Map<String, String>> querytrancodeP003();
	List<Map<String, String>> querytrancodeP004();
	/**
	 * P001
	 * 查询买币
	 * @param prod
	 * @return
	 */
	List<String> querybuyexnmP001(@Param("prod")String prod);
	List<String> querybuyexnmP002();
	List<String> querybuyexnmP003();
	/**
	 * 查询所有P001
	 * @param trancode
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param byexnm
	 * @param comaogcd
	 * @param combogcd
	 * @return
	 */
	List<Tranlist> selectFxipTranlistP001(@Param("trancode")String trancode,
			@Param("strcuac")String strcuac, @Param("trdtbegin")String trdtbegin, @Param("trdtend")String trdtend, 
			@Param("byexnm")String byexnm, @Param("comaogcd")String comaogcd, @Param("combogcd")String combogcd);
	/**
	 * 查询所有P002
	 * @param trancode
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param byexnm
	 * @param comaogcd
	 * @param combogcd
	 * @return
	 */
	List<Tranlist> selectFxipTranlistP002(@Param("trancode")String trancode,
			@Param("strcuac")String strcuac, @Param("trdtbegin")String trdtbegin, @Param("trdtend")String trdtend, 
			@Param("byexnm")String byexnm, @Param("comaogcd")String comaogcd, @Param("combogcd")String combogcd);
	/**
	 * 查询所有P003
	 * @param trancode
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param byexnm
	 * @param comaogcd
	 * @param combogcd
	 * @return
	 */
	List<Tranlist> selectFxipTranlistP003(
			@Param("trancode")String trancode, @Param("strcuac")String strcuac, 
			@Param("trdtbegin")String trdtbegin, @Param("trdtend")String trdtend, 
			@Param("byexnm")String byexnm, @Param("comaogcd")String comaogcd, 
			@Param("combogcd")String combogcd);
	/**
	 * 查询所有P004
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
	 * @return
	 */
	List<Tranlist> selectFxipTranlistP004(
			@Param("strlcno")String strlcno, @Param("strcuac")String strcuac, 
			@Param("strIdno")String strIdno, @Param("bgdate")String bgdate, 
			@Param("enddate")String enddate, @Param("com1")String com1, 
			@Param("com2")String com2, @Param("comaogcd")String comaogcd, 
			@Param("combogcd")String combogcd,@Param("comtrtp")String comtrtp,
			@Param("comchnl")String comchnl);
}
