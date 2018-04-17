package com.ylxx.fx.service.jsh;

import java.util.List;

import com.ylxx.fx.core.domain.AttentionUser;
import com.ylxx.fx.service.po.jsh.JshPages;

/**
 * 关注名单
 * @author lichaozheng
 *
 */
public interface JshAttentionUserService {
	/**
	 * 插入关注名单
	 * @param attentionUser
	 * @return
	 */
	boolean insertAttentionUser(JshPages<AttentionUser> attentionUser);
	
	/**
	 * 更新关注名单
	 * @param attentionUser
	 * @return
	 */
	boolean updateAttentionUser(JshPages<AttentionUser> attentionUser);
	
	/**
	 * 查询关注名单
	 * @param attentionUser
	 * @return
	 */
	List<AttentionUser> selectAttentionUser(JshPages<AttentionUser> attentionUser);
	
	/**
	 * 删除关注名单
	 * @param userSeqn
	 * @return
	 */
	boolean deleteAttentionUser(JshPages<AttentionUser> attentionUser);

}
