package com.ylxx.fx.controller.jsh;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.AttentionUser;
import com.ylxx.fx.service.jsh.JshAttentionUserService;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;

/**
 * 关注名单
 * @author lichaozheng
 *
 */
@Controller
public class JshAttentionUserController {
	private static final Logger log = LoggerFactory.getLogger(JshAttentionUserController.class);
	
	@Resource
	private JshAttentionUserService jshAttentionUserService;

	/**
	 * 添加白名单
	 * @param attentionUser
	 * @return
	 */
	@RequestMapping(value="/insertAttentionUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String insertAttentionUser(@RequestBody JshPages<AttentionUser> attentionUser) {
		log.info("开始添加关注名单");
		boolean flag = jshAttentionUserService.insertAttentionUser(attentionUser);
		if(flag) {
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "SUCCESS");
		}else {
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "ERROR");
		}
	}
	
	/**
	 * 修改白名单
	 * @param attentionUser
	 * @return
	 */
	@RequestMapping(value="/updateAttentionUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String updateAttentionUser(@RequestBody JshPages<AttentionUser> attentionUser) {
		boolean flag = jshAttentionUserService.updateAttentionUser(attentionUser);
		if(flag) {
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "SUCCESS");
		}else {
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "ERROR");
		}
	}
	
	/**
	 * 删除白名单
	 * @param attentionUser
	 * @return
	 */
	@RequestMapping(value="/deleteAttentionUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String deleteAttentionUser(@RequestBody JshPages<AttentionUser> attentionUser) {
		boolean flag = jshAttentionUserService.deleteAttentionUser(attentionUser);
		if(flag) {
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "SUCCESS");
		}else {
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "ERROR");
		}
	}
	
	/**
	 * 查询白名单
	 * @param pageNo
	 * @param pageSize
	 * @param attentionUser
	 * @return
	 */
	@RequestMapping(value="/selectAttentionUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selectAttentionUser( @RequestBody JshPages<AttentionUser> attentionUser) {
		
		/*Integer pageNo = 1;
		Integer pageSize = 10;
		JshPages<AttentionUser> attentionUser = new JshPages<AttentionUser>();
		AttentionUser attentionUserE = new AttentionUser();
		attentionUser.setEntity(attentionUserE);
		attentionUser.getEntity().setCunm("李");
		attentionUser.getEntity().setIdno("2");*/
		Integer pageNo = attentionUser.getPageNo();
		Integer pageSize = attentionUser.getPageSize();
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		List<AttentionUser> list = jshAttentionUserService.selectAttentionUser(attentionUser);
		PageInfo<AttentionUser> page=new PageInfo<AttentionUser>(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	
}
