package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.ylxx.fx.service.po.Maxpavpoint;
/**
 * 最大优惠
 * @author lz130
 *
 */
public interface MaxpavpointMapper {
	/**
	 * 查询机构列表P001,P002,P003,P004
	 * @param userorgorgn
	 * @param userorgleve
	 * @return
	 */
	List<Map<String, String>> queryMaxpavOgcd(
			@Param("userorgorgn")String userorgorgn,
			@Param("userorgleve")String userorgleve);
	/**
	 * 查询所有最大优惠设置 P001, P002, P003, P004
	 * @param prod
	 * @param combogcd
	 * @return
	 */
	List<Maxpavpoint> selectMaxpavpoint(@Param("prod")String prod, 
			@Param("combogcd")String combogcd);
	
	/**
	 * 添加获取币别P002
	 * @param prcd
	 * @param ogcd
	 * @return
	 */
	List<Map<String,String>> selMaxexnmP002(@Param("prcd")String prcd, @Param("ogcd")String ogcd);
	/**
	 * 添加获取币别P003
	 * @param prcd
	 * @param ogcd
	 * @return
	 */
	List<Map<String,String>> selMaxexnmP003(@Param("prcd")String prcd, @Param("ogcd")String ogcd);
	/**
	 * 添加P002,P003
	 * @param prcd
	 * @param maxpoint
	 * @return
	 */
	int insertMaxpoint(@Param("prcd")String prcd, @Param("maxpoint")Maxpavpoint maxpoint);
	/**
	 * 删除P002,P003
	 * @param prcd
	 * @param ogcd
	 * @param exnm
	 * @return
	 */
	int deleteMaxpoint(@Param("prcd")String prcd, @Param("ogcd")String ogcd, @Param("exnm")String exnm);
	/**
	 * 修改P002,P003
	 * @param prcd
	 * @param maxpoint
	 * @return
	 */
	int updateMaxpoint(@Param("prcd")String prcd, @Param("maxpoint")Maxpavpoint maxpoint);
}
