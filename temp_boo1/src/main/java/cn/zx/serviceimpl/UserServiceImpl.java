package cn.zx.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zx.entity.User;
import cn.zx.mapper.UserMapper;
import cn.zx.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMap;
	
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userMap.selUser();
	}

}
