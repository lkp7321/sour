package com.ylxx.fx.core.mapper.jsh;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ylxx.fx.core.domain.AttentionUser;

/**
 * 关注名单
 * @author lichaozheng
 *
 */
public interface JshAttentionUserMapper {
	
	/**
	 * 插入关注名单
	 * @param attentionUser
	 * @return
	 */
	void insertAttentionUser(@Param("attentionUser")AttentionUser attentionUser);
	
	/**
	 * 更新关注名单
	 * @param attentionUser
	 * @return
	 */
	void updateAttentionUser(@Param("attentionUser")AttentionUser attentionUser);
	
	/**
	 * 查询关注名单
	 * @param attentionUser
	 * @return
	 */
	List<AttentionUser> selectAttentionUser(@Param("cunm")String cunm, @Param("idno")String idno);
	
	/**
	 * 删除关注名单
	 * @param userSeqn
	 * @return
	 */
	void deleteAttentionUser(@Param("userSeqn")String userSeqn);


}
