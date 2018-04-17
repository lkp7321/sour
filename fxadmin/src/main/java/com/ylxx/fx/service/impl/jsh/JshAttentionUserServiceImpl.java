package com.ylxx.fx.service.impl.jsh;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.AttentionUser;
import com.ylxx.fx.core.mapper.jsh.JshAttentionUserMapper;
import com.ylxx.fx.service.jsh.JshAttentionUserService;
import com.ylxx.fx.service.po.jsh.JshPages;

@Service("jshAttentionUserService")
public class JshAttentionUserServiceImpl implements JshAttentionUserService{
	private static final Logger log = LoggerFactory.getLogger(JshAttentionUserServiceImpl.class);
	@Resource
	private JshAttentionUserMapper jshAttentionUserMapper;


	/**
	 * 插入关注名单
	 * @param attentionUser
	 * @return
	 */
	@Override
	public boolean insertAttentionUser(JshPages<AttentionUser> attentionUser) {
		String userKey = attentionUser.getUserKey();
		AttentionUser attUser = attentionUser.getEntity(); 
		try {
			jshAttentionUserMapper.insertAttentionUser(attUser);
		} catch (Exception e) {
			log.error("添加关注名单信息失败");
			log.error(e.getMessage(), e);
			return false;
		}
		return true;	
	}

	/**
	 * 更新关注名单
	 * @param attentionUser
	 * @return
	 */
	@Override
	public boolean updateAttentionUser(JshPages<AttentionUser> attentionUser) {
		String userKey = attentionUser.getUserKey();
		AttentionUser attUser = attentionUser.getEntity(); 
		try {
			jshAttentionUserMapper.updateAttentionUser(attUser);
		} catch (Exception e) {
			log.error("添加关注名单信息失败");
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 查询关注名单
	 * @param attentionUser
	 * @return
	 */
	@Override
	public List<AttentionUser> selectAttentionUser(JshPages<AttentionUser> attentionUser) {
		List<AttentionUser> list = null;
		try {
			list = jshAttentionUserMapper.selectAttentionUser(attentionUser.getEntity().getCunm(),attentionUser.getEntity().getIdno());
		} catch (Exception e) {
			log.error("查询关注名单出错");
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 删除关注名单
	 * @param userSeqn
	 * @return
	 */
	@Override
	public boolean deleteAttentionUser(JshPages<AttentionUser> attentionUser) {
		String userKey = attentionUser.getUserKey();
		String user_seqn = attentionUser.getEntity().getUser_Seqn();
		try {
			jshAttentionUserMapper.deleteAttentionUser(user_seqn);
		} catch (Exception e) {
			log.error("删除关注名单失败");
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	
	
}
