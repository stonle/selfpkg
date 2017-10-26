package com.ls.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import java.util.List;

import com.ls.cenum.DateEnum;
import com.ls.dao.UserDAO;
import com.ls.domain.User;
import com.ls.service.UserService;
import com.ls.util.DateTimeUtil;
import com.ls.util.EncrypMD5;
import com.ls.util.GuidCreatorUtil;
import com.ls.util.Page;
import com.ls.util.StringUtil;

/**
 * @author cjw 2017-05-04
 * 
 */
@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

	@Resource(name = "userDaoImpl")
	private UserDAO userDao;

	public List<User> listUserByPage(Page page, User user) {
		return userDao.listUserByPage(page, user);
	}

	public int getTotalCount(User user) {
		return userDao.getTotalCount(user);
	}

	public User getUserByLoginName(String loginName) {
		if (loginName == null) {
			throw new RuntimeException("Param_Null_Error#loginName[String]");
		}
		return userDao.getUserByLoginName(loginName);
	}

	public User getUserById(User user) {
		if (user == null || user.getUserID() == null) {
			throw new RuntimeException("Param_Null_Error#User[Object]");
		}
		return this.getObjectById(User.class, user.getUserID());
	}

	public void saveUser(User user) {
		try {
			if (user == null) {
				throw new RuntimeException("Object_Null_Error");
			}
			String loginName = StringUtil.filterString(user.getLoginName());
			if (loginName.equals("") || loginName.toLowerCase().equals("admin")) {
				throw new RuntimeException("Forbidden_String:" + loginName);
			}
			//登录名唯一性校验
			User u = this.getUserByLoginName(loginName);
			if(u != null){
				throw new RuntimeException("LoginName already exists, please re-enter...");
			}
			User ur = new User();
			ur.setUserID(new GuidCreatorUtil().toString());
			ur.setCreateDate(DateTimeUtil.getFormatDate());
			ur.setCellphone(StringUtil.filterString(user.getCellphone()));
			ur.setUserName(StringUtil.filterString(user.getUserName()));
			ur.setLoginName(loginName);
			ur.setPassword(EncrypMD5.getMD5Code(StringUtil.filterString(user.getPassword())));
			ur.setStatus(DateEnum.NO_DELETE.getIndex());
			ur.setUserType(1);
			ur.setRemark(StringUtil.filterString(user.getRemark()));


			userDao.saveObject(ur);
			
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public void updateUser(User user) {
		try {
			if (user == null) {
				throw new RuntimeException("Object_Null_Error");
			}
			User ur = this.getUserById(user);
			if (ur == null) {
				throw new RuntimeException("Object_Not_Exist");
			}
			ur.setCellphone(StringUtil.filterString(user.getCellphone()));
			ur.setUserName(StringUtil.filterString(user.getUserName()));
			ur.setRemark(StringUtil.filterString(user.getRemark()));
			
			this.updateObject(ur);
			
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public void updateUserLoginInfo(User user) {
		try {
			if (user == null) {
				throw new RuntimeException("Object_Null_Error");
			}
			User u = this.getUserById(user);
			if (u == null) {
				throw new RuntimeException("Object_Not_Exist");
			}
			u.setLastLoginDate(DateTimeUtil.getFormatDate());
			
			userDao.updateObject(u);
			
			
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void deleteUser(User user) {
		try {
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void deleteUser(List<User> list) {
		try {
			if (list == null) {
				throw new RuntimeException("Object_Null_Error");
			}
			for (User user : list) {
				User ur = this.getUserById(user);
				if (ur.getLoginName().equals("admin")) {
					throw new RuntimeException("Delete_Without_Permission");
				}
				userDao.deleteObject(ur);
			}
			
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
