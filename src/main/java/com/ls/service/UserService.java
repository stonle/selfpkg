package com.ls.service;

import java.util.List;

import com.ls.domain.User;
import com.ls.util.Page;
/**
 * @author cjw 2017-05-04
 * 
 */
public interface UserService extends BaseService<User, String> {
	// 分页条件查询
	List<User> listUserByPage(Page page, User user);

	// 获取分页条件查询记录总数
	int getTotalCount(User user);

	// 登录信息查询
	User getUserByLoginName(String loginName);

	// 根据对象ID属性获取对象
	User getUserById(User user);

	// 添加对象
	void saveUser(User user);

	// 更新对象
	void updateUser(User user);
	
	// 更新对象
	void updateUserLoginInfo(User user);
		

	// 删除对象
	void deleteUser(User user);

	// 批量删除对象
	void deleteUser(List<User> list);

	
}
