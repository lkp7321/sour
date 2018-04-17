package com.ylxx.fx.core.mapper.person.system;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ylxx.fx.service.po.Trd_ogcd;

public interface OrganizationMapper {
	
	public Trd_ogcd queryOgcdLeve(@Param("userOrgn")String userOrgn);
	
	public List<Trd_ogcd> queryAllOrgan(@Param("orgLeve")String orgLeve,
			@Param("userOrgn")String userOrgn);
	
	public int insertOgcd(@Param("ogcdObj")Trd_ogcd ogcdObj);
	public int insertOrgan(@Param("ogcdObj")Trd_ogcd ogcdObj);
	
	public int updateOgran(@Param("ogcdObj")Trd_ogcd ogcdObj); 
	public int update(@Param("ogcdObj")Trd_ogcd ogcdObj);
	
	public List<Trd_ogcd> queryOrgan();
	public List<Trd_ogcd> oneTwoLeve();
	public List<Trd_ogcd> selleve(@Param("userogcd")String userogcd);
	
	public List<Trd_ogcd> upQueryOrgan1();
	public List<Trd_ogcd> upQueryOrgan2(@Param("userogcd")String userogcd);
	
	public int deleCytp(@Param("ogcd")String ogcd);
	public int queryUser(@Param("prod")String prod,@Param("ogcd")String ogcd);
	public int queryOgcd();
	public List<Trd_ogcd> queryOneOgcd(@Param("prod")String prod,
			@Param("ogup")String ogup,@Param("ogcd")String ogcd);
	public int updateOgcd(@Param("prod")String prod, 
			@Param("newogcd")String newogcd, @Param("oldogcd")String oldogcd);
	public int updateRgog(@Param("prod")String prod, 
			@Param("newogcd")String newogcd, @Param("oldogcd")String oldogcd);
}
