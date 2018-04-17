package com.ylxx.fx.controller.person.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylxx.fx.core.domain.SystemVo;
import com.ylxx.fx.service.person.system.PpchannelService;
import com.ylxx.fx.service.po.Ppchannel;
/**
 * 系统管理  平盘通道控制
 * @author lz130
 *
 */
@Controller
public class PpchannelController {
	private static final Logger log = LoggerFactory.getLogger(PpchannelController.class);
	@Resource(name="ppchanService")
	private PpchannelService ppchanService;
	
	/**
	 * 获取所有平盘通道数据
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getPPchannel.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPPchannel(HttpServletRequest req, @RequestBody String userKey){
		log.info("获取平盘通道数据");
		return ppchanService.getAllPpchan(userKey);
	}
	
	/**
	 * 修改平盘通道 状态
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/changeChannel.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String changeChannel(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		List<Ppchannel> list = sysVo.getList();
		String s = sysVo.getS();
		return ppchanService.changleChan(userKey, list, s);
	}
}
