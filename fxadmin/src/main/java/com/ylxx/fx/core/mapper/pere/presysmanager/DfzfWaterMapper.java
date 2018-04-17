package com.ylxx.fx.core.mapper.pere.presysmanager;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.DfzfWater;


public interface DfzfWaterMapper {
	List<DfzfWater> selcDfzfWater(@Param("dgfieldbegin")String dgfieldbegin, @Param("dgfieldend")String dgfieldend, @Param("comStcd")String comStcd);


}
