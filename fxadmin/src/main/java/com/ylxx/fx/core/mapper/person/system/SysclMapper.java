package com.ylxx.fx.core.mapper.person.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Sysctl;
import com.ylxx.fx.service.po.Testtrac;
/**
 * 系统状态
 * @author lz130
 *
 */
public interface SysclMapper {
	/**
	 * 查询系统状态数据
	 * @param prcd
	 * @return
	 */
	List<Testtrac> selectTesttrac(@Param("prcd")String prcd);
	/**
	 * 修改系统参数
	 * @param prcd
	 * @param testtrac
	 * @return
	 * @throws Exception
	 */
	int upTesttrac(@Param("prcd")String prcd, 
			@Param("testtrac")Testtrac testtrac)throws Exception;
	/**
	 * 查询是否存在
	 * @param prcd
	 * @param cuac
	 * @return
	 */
	String selectTesttracD(@Param("prcd")String prcd, @Param("cuac")String cuac);
	/**
	 * 添加
	 * @param prcd
	 * @param testtrac
	 * @return
	 * @throws Exception
	 */
	void insertTesttracD(@Param("prcd")String prcd, 
			@Param("testtrac")Testtrac testtrac, @Param("trac")String trac)throws Exception;
	/**
	 * 删除
	 * @param prcd
	 * @param cuac
	 * @return
	 * @throws Exception
	 */
	int deleteSyscl(@Param("prcd")String prcd, 
			@Param("cuac")String cuac)throws Exception;
	//==========
	/**
	 * 查询系统总控
	 * @param prod
	 * @return
	 */
	String selectSysctl(@Param("prod")String prod);
	/**
	 * 查询平盘总控
	 * @param prod
	 * @return
	 */
	String selectpp(@Param("prod")String prod);
	int upPpsys(@Param("sysctl")Sysctl sysctl);
	int upSysclCon(@Param("sysctl")Sysctl sysctl);
}
