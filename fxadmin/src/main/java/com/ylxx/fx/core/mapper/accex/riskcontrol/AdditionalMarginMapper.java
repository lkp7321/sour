package com.ylxx.fx.core.mapper.accex.riskcontrol;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.ylxx.fx.service.po.AdditionalMargin;

public interface AdditionalMarginMapper {
	
	List<AdditionalMargin> addMargin(@Param("prod")String prod) throws Exception;
	
	List<AdditionalMargin> selectPtpara(@Param("prod")String prod) throws Exception;

}
