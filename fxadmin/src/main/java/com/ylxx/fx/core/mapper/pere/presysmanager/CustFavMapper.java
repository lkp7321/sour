package com.ylxx.fx.core.mapper.pere.presysmanager;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.CustFavVo;

public interface CustFavMapper {

	List<HashMap<String, String>> selectCustFavList(@Param("cunotext")String cuno, @Param("ogcdtext")String ogcd, @Param("stat")String stat);
	boolean delCustFav(@Param("cuno")String cuno);
	List<HashMap<String, String>> selectETBhid(@Param("ogcd")String ogcd, @Param("ognm")String ognm);
	boolean updCustFav(@Param("custFav")CustFavVo custFav);
	boolean updCustFav(@Param("fvgh")String fvgh, @Param("fvjh")String fvjh, @Param("stat")String stat, @Param("ogcd")String ogcd, @Param("cuno")String cuno);
	int selCunoExist(@Param("cuno")String cuno);
	void insCustFav(@Param("custFav")CustFavVo custFav);
}
