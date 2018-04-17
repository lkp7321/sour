package com.ylxx.fx.service.person.system;

import java.util.List;
import com.ylxx.fx.service.po.Ppchannel;

public interface PpchannelService {
	public String getAllPpchan(String userKey);
	public String changleChan(String userKey,
			List<Ppchannel> list1,String s);
}
