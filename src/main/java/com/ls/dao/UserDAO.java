package com.ls.dao;

import java.util.List;

import com.ls.domain.User;
import com.ls.util.Page;

/**
 * @author cjw 2017-05-04
 * 
 */
public interface UserDAO extends BaseDAO<User, String> {
	// 通过用户名获取用户信息：登录功能
	User getUserByLoginName(String loginName);

	// 分页条件查询
	List<User> listUserByPage(Page page, User user);

	// 获取分页条件查询记录总数，辅助方法listUserByPage
	int getTotalCount(User user);
}
