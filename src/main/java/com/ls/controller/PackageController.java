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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ls.dao.OrderDao;
import com.ls.dao.PackageDao;
import com.ls.domain.OrderInfo;
import com.ls.domain.PackageInfo;
import com.ls.service.PkgCheckService;
import com.ls.util.Page;
import com.ls.util.PageUtil;


@RestController
@RequestMapping("pages")
public class PackageController {
	private static Logger logger = LogManager.getLogger(PackageController.class.getName());

	@Autowired
	private OrderDao orderDaoImpl;
    
	@Autowired
	private PackageDao packageDaoImpl;
    
	@Autowired
	private PkgCheckService pkgCheckServiceImpl;
	@RequestMapping(value = "/queryPackageByPage.do", method = RequestMethod.GET)
	public Map<String, Object> queryObjectByPage(OrderInfo packageVo, Page page,String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		try {
			page = PageUtil.createPage(page.getEveryPage(), orderDaoImpl.getTotalCount(packageVo,phone),
					page.getCurrentPage());
			list = orderDaoImpl.listPackageByPage(page, packageVo,phone);
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
	@RequestMapping(value = "/getPkgInfoBypckNum.do", method = RequestMethod.GET)
    public Map<String,Object> getPkgInfoBypckNum(@RequestParam("pkgNum")String pkgNum){
    	Map<String, Object> map = new HashMap<String, Object>();
		try {
			PackageInfo packageInfo = packageDaoImpl.getPckInfoByPkgNum(pkgNum);
			map.put("list", packageInfo);
			map.put("msg", "1");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
		}
		return map;
    }
	/**
	 * devNum,pkgNum,flag
	 * @return
	 */
	@RequestMapping(value = "/saveResult.do", method = RequestMethod.PUT)
	public Map<String,Object> saveResult(@RequestParam("devNum")String devNum,@RequestParam("pkgNum")String pkgNum,@RequestParam("flag")boolean flag){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pkgCheckServiceImpl.updatePkgCheckInfo(devNum,pkgNum,flag);
			map.put("info", "sucess");
			map.put("msg", "1");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
		}
		return map;
	}
}
