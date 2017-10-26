package com.ls.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ls.domain.User;
import com.ls.service.UserService;
import com.ls.util.EncodingUtil;
import com.ls.util.EncrypMD5;
import com.ls.util.Page;
import com.ls.util.PageUtil;
import com.ls.util.PropUtil;
import com.ls.util.UserContextUtil;
/**
 * @author cjw 2017-05-04
 * 
 */
@RestController
public class UserController {
	
	private static Logger logger = LogManager.getLogger(UserController.class.getName());
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	/**
	 * 分页条件查询用户信息
	 * @param User user,Page page
	 * @return json:[list:{data},msg:{'':1,-1,-100}]
	 * */
	@RequestMapping(value = "/pages/queryUserByPage.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryObjectByPage(User user,Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> list = new ArrayList<User>();
		try{
			page = PageUtil.createPage(page.getEveryPage(),userService.getTotalCount(user),page.getCurrentPage());
			list = userService.listUserByPage(page, user);
			map.put("totalCount",page.getTotalCount());
			map.put("currentPage",page.getCurrentPage());
			map.put("totalPage",page.getTotalPage());
			map.put("list", list);
			map.put("msg","1");
		}catch(Exception e){
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
		}
		return map;
	}
	

	/**
	 * 
	 * @param String userId
	 * @return json:[{data},{'msg':1,-1,-100}]
	 * */
	@RequestMapping(value = "/pages/getUserById.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryObjectById(String userID){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(userID.equals("")){//参数控制
			map.put("msg","-1");
			map.put("info","无有效参数");
			return map;
		}
		try{
			User user = userService.getObjectById(User.class, userID);
			if(user == null){//对象不存在
				map.put("msg","-2");
				map.put("info","对象不存在");
				return map;
			}
			map.put("list", user);
			map.put("msg","1");
		}catch(Exception e){
			map.put("msg", "-100");
			map.put("info",e.getMessage());
			logger.error(e.getMessage());
		}
		return map;
	}
	
	/**
	 * 保存新建对象信息
	 * @param User Obj
	 * @return json:[{'msg':1,-1,-100}]
	 * */
	@RequestMapping(value = "/pages/addUser.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addObject(User user){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(user == null){
				map.put("msg","-1");
				map.put("info","未检测到需要保存的对象");
				return map;
			}
			userService.saveUser(user);
			map.put("msg","1");
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
		}
		return map;
	}
	
	/**
	 * 保存修改的对象信息
	 * @param User Obj
	 * @return json:[{'msg':1,-1,-100}]
	 * */
	@RequestMapping(value = "/pages/editeUser.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editeObject(User user){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(user == null || user.getUserID() == null){
				map.put("msg","-1");
				map.put("info","未检测到需要保存的对象");
				return map;
			}
			userService.updateUser(user);
			map.put("msg","1");
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
		}
		return map;
	}
	
	/**
	 * 删除对象信息，支持批量删除
	 * @param String idStr
	 * @return json:[{'msg':1,-1,-100}]
	 * */
	@RequestMapping(value = "/pages/delUser.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delObject(String items){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(items == null || items.equals("")){
				map.put("msg","-1");
				map.put("info","无有效参数");
				return map;
			}
			User user = UserContextUtil.getUser();
			List<User> list = new ArrayList<User>();
			String[] itemArray = items.split(",");
			for(String item : itemArray){
				if(item.equals(user.getUserID())){//不可删除自身账户
					logger.info(user.getLoginName()+"不可删除自身账户,操作终止！");
					map.put("msg","-200");
					map.put("info","不可删除账户");
					return map;
				}
				logger.info(user.getLoginName()+"删除账户:"+item);
				User ur =  new User();
				ur.setUserID(item);
				list.add(ur);
			}
			userService.deleteUser(list);
			
			map.put("msg","1");
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
		}
		return map;
	}
	
	/**
	 * 系统登录功能
	 * @RequestMapping value="请求的URL路径"
	 * Return 去向页面
	 * 用户登录验证功能
	 * Post方法
	 * */
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public ModelAndView login(User user, HttpSession httpSession) {
		try{
			if (user == null) {
				return new ModelAndView(new RedirectView("./#Object_Null_Error"));
			}
			String loginName = user.getLoginName();
			String password = user.getPassword();
			logger.info(loginName+"正在登录系统，验证中...");
			if (loginName == null || loginName.isEmpty() || password == null
					|| password.isEmpty()) {
				return new ModelAndView(new RedirectView("./#Param_Null_Error"));
			}
			User us = userService.getUserByLoginName(loginName);
			if (us == null) {
				return new ModelAndView(new RedirectView("./#User_Not_Found"));
			}
			if (!EncrypMD5.getMD5Code(password).equals(us.getPassword())) {
				return new ModelAndView(new RedirectView("./#MM_Error"));
			}
			logger.info(loginName+"通过验证，登录成功！");
			//修改最后登录时间
			userService.updateUserLoginInfo(us);
			
			//写入session
			Map<String, Object> map = new HashMap<String, Object>();
			//用户姓名
			map.put("username", us.getUserName());

			//系统版本信息
			map.put("version", PropUtil.getKeyValue("Version"));
			//网站浏览器Title
			map.put("title", EncodingUtil.getUtf8String(PropUtil.getKeyValue("SystemTitle")));
			httpSession.setAttribute("session", map);
			
			//将当前用户放入作用域
			UserContextUtil.setUser(us);
			return new ModelAndView(new RedirectView("./pages/"));// 登录成功
		}catch(Exception e){
			logger.error(e.getMessage());
			if (e instanceof CannotCreateTransactionException) {// 无法连接数据库密码错误等
				return new ModelAndView(new RedirectView("./#ConnectionDatabaseError"));
			}
			return new ModelAndView(new RedirectView("./#UnknownError"));
		}
	}
	
	/**
	 * 用户退出登录功能
	 * Get
	 * */
	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		User user = UserContextUtil.getUser();
		if(user != null){
			logger.info(user.getLoginName()+"退出登录");
		}
		
		httpSession.removeAttribute("session");
		
		return "redirect:./";
	}
	
}
