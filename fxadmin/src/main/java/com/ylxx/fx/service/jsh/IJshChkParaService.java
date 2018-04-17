package com.ylxx.fx.service.jsh;

import com.ylxx.fx.service.po.jsh.JshChkParaVo;

public interface IJshChkParaService {

	String selJshChkPara(String exnm, Integer pageNo, Integer pageSize);
	String insJshChkPara(JshChkParaVo jshChkPara);
	String updJshChkPara(JshChkParaVo jshChkPara);
	String delJshChkPara(JshChkParaVo jshChkPara);
	String selChkParaExnmExist(JshChkParaVo chkPara);
	String selChkParaExnm();
}
