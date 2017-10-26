/**   
* 
*/
package com.ls.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ls.domain.TerTaskInfo;
import com.ls.service.TerTaskService;
import com.ls.util.Page;
import com.ls.util.PageUtil;

/** 
* @ClassName: TaskController 
* @Description: 
* @author huanglei
* @date 2017年9月23日 上午9:35:01 
* @version V1.0    
*/
@RestController
@RequestMapping("pages")
public class TaskController {
	
	private static Logger logger = LogManager.getLogger(TaskController.class.getName());
	
	@Autowired
	private TerTaskService terTaskServiceImpl;
	
	@RequestMapping(value = "/queryTaskByPage.do", method = RequestMethod.GET)
	public Map<String,Object> queryTaskByPage(TerTaskInfo taskInfo,Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		List<TerTaskInfo> list = new ArrayList<TerTaskInfo>();
		try {
			page = PageUtil.createPage(page.getEveryPage(), terTaskServiceImpl.getTotalCount(taskInfo),
					page.getCurrentPage());
			list = terTaskServiceImpl.listTerTaskByPage(page, taskInfo);
			map.put("totalCount", page.getTotalCount());
			map.put("currentPage", page.getCurrentPage());
			map.put("totalPage", page.getTotalPage());
			map.put("list", list);
			map.put("msg", "1");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}
}
