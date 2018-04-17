package com.ylxx.fx.core.mapper.jsh;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.jsh.JshChkParaVo;

public interface JshChkParaMapper {
	void insJshChkPara(@Param("chkPara")JshChkParaVo chkPara);

	void updJshChkPara(@Param("chkPara")JshChkParaVo chkPara);

	List<HashMap<String, String>> selJshChkPara(@Param("exnm")String exnm);

	void delJshChkPara(@Param("chkPara")JshChkParaVo chkPara);

	int selChkParaExnmExist(@Param("chkPara")JshChkParaVo chkPara);

	List<HashMap<String, String>> selChkParaExnm();

}
