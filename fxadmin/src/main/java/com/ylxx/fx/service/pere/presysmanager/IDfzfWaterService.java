package com.ylxx.fx.service.pere.presysmanager;

import java.util.List;
import com.ylxx.fx.core.domain.CustFavVo;

public interface IDfzfWaterService {

	public String selcDfzfWater(String dgfieldbegin, String dgfieldend, String comStcd, Integer pageNo, Integer pageSize);
	public List<CustFavVo> selcAllDfzfWater(String dgfieldbegin, String dgfieldend, String comStcd);
}
