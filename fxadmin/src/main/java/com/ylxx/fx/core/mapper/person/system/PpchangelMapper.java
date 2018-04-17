package com.ylxx.fx.core.mapper.person.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Ppchannel;

public interface PpchangelMapper {
	public List<Ppchannel> selectexnm(@Param("prcd")String prcd);
	public void openChan(@Param("list")List<Ppchannel> list);
	public void closeChan(@Param("list")List<Ppchannel> list);
}
