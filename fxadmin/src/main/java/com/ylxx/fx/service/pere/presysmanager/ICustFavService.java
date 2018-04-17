package com.ylxx.fx.service.pere.presysmanager;

import com.ylxx.fx.core.domain.CustFavVo;

public interface ICustFavService {

	String selcCustFavList(String cuno, String ogcd, String stat,Integer pageNo,Integer pageSize);

	String deleteCustFav(String userKey, String cuno);

	String selectETBhid(String ogcd, String ognm, Integer pageNo, Integer pageSize);

	String updCustFav(CustFavVo custFav);

	String selCunoExist(String cuno, String userKey);

	String insCustFav(CustFavVo custFav);


}
