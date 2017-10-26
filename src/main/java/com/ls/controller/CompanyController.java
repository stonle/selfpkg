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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ls.domain.CompanyInfo;
import com.ls.domain.CourierInfo;
import com.ls.service.CompanyService;
import com.ls.service.CourierService;
import com.ls.util.Page;
import com.ls.util.PageUtil;
import com.ls.util.StringUtil;

/** 
* @ClassName: CompanyController 
* @Description: 
*  *  app*：表示安卓端的接口 ；
*     其它一般接口,即是页面接口
* @author huanglei
* @date 2017年9月4日 上午8:51:33 
* @version V1.0    
*/
@RestController
@RequestMapping("pages")
public class CompanyController {
     private static Logger logger = LogManager.getLogger(CompanyController.class.getName());
     @Autowired
     private CompanyService CompanyServiceImpl;
     
     @Autowired
     private CourierService courierServiceImpl;
     /**
      * 快递公司列表查询
      * @param page
      * @return
      */
 	 @RequestMapping(value = "/queryCompanyByPage.do", method = RequestMethod.GET)
     public Map<String,Object> queryCompanyByPage(Page page){
    		Map<String, Object> map = new HashMap<String, Object>();
    		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
    		try {
    			page = PageUtil.createPage(page.getEveryPage(), CompanyServiceImpl.getTotalCount(),
    					page.getCurrentPage());
    			list = CompanyServiceImpl.listCompanyByPage(page);
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
     /**
      * 服务器注册快递公司信息
      * @param companyInfo
      * @return
      */
 	 @RequestMapping(value = "/addCompany.do", method = RequestMethod.POST)
 	 public Map<String,Object> addCompany(CompanyInfo companyInfo){
 		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (companyInfo == null) {
				map.put("msg", "-1");
				map.put("info", "未检测到需要保存的对象");
				return map;
			}
			CompanyServiceImpl.addCompany(companyInfo);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
 	 }
 	 /**
 	  * 根据id获取公司对象
 	  * @param copId
 	  * @return
 	  */
 	 @RequestMapping(value = "/getCompanyById.do", method = RequestMethod.GET)
 	 public Map<String,Object> getCompanyById(@RequestParam("copId")Integer copId){
 		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (copId == null) {
				map.put("msg", "-1");
				map.put("info", "not null");
				return map;
			}
			CompanyInfo companyInfo = CompanyServiceImpl.getCompanyById(copId);
			map.put("msg", "1");
			map.put("info", "sucess");
			map.put("list", companyInfo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
 	 }
	 /**
 	  * 编辑对象
 	  * @param copId
 	  * @return
 	  */
 	 @RequestMapping(value = "/editeCompany.do", method = RequestMethod.PUT)
 	 public Map<String,Object> editeCompany(CompanyInfo companyInfo){
 		Map<String, Object> map = new HashMap<String, Object>();
		try {
		    CompanyServiceImpl.editeCompany(companyInfo);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
 	 }
    /**
     * 删除快递公司信息 
     * @param items
     * @return
     */
 	 @RequestMapping(value = "/delCompany.do", method = RequestMethod.DELETE)
     public Map<String,Object> delCompanyInfo(@RequestParam("items")String items){
 		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!StringUtil.isNotEmpty(items)) {
				map.put("msg", "-1");
				map.put("info", "未检测到需要删除的对象");
				return map;
			}
			CompanyServiceImpl.delCompanyInfo(items);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
     }
 	 
     /**
      *  快递员列表详情查询
      * @param courierInfo
      * @param page
      * @return
      */
 	 @RequestMapping(value = "/queryCourierByPage.do", method = RequestMethod.GET)
 	 public Map<String,Object> queryCourierByPage(CourierInfo courierInfo,Page page,String copName){
 		Map<String, Object> map = new HashMap<String, Object>();
 		if(StringUtil.isNotEmpty(copName)){
 			CompanyInfo companyInfo = new CompanyInfo();
 	 		companyInfo.setCopName(copName);
 	 		courierInfo.setCompanyInfo(companyInfo);
 		}
		List<CourierInfo> list = new ArrayList<CourierInfo>();
		try {
			page = PageUtil.createPage(page.getEveryPage(), courierServiceImpl.getTotalCount(courierInfo),
					page.getCurrentPage());
			list = courierServiceImpl.queryCourierByPage(courierInfo,page);
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
 	 /**
 	  * 快递公司列表查询,用于手机端快递员注册，选择快递公司
 	  * @return
 	  */
 	 @RequestMapping(value = "/applistCompany.do", method = RequestMethod.GET)
 	 public Map<String,Object> listCompany(){
  		Map<String, Object> map = new HashMap<String, Object>();
  		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
 		try {
 			list = CompanyServiceImpl.listCompany();
 			map.put("msg", "1");
 			map.put("info", "sucess");
 			map.put("list", list);
 		} catch (Exception e) {
 			logger.error(e.getMessage());
 			map.put("msg", "-100");
 			map.put("info", e.getMessage());
 			return map;
 		}
 		return map;
 	 }
 	 
     /**
      * 快递员注册
      * @param courierInfo
      * @return
      */
 	 @RequestMapping(value = "/appaddCourier.do", method = RequestMethod.POST)
 	 public Map<String,Object> addCourier(@RequestBody CourierInfo courierInfo){
 		Map<String, Object> map = new HashMap<String, Object>();
 		try {
 			courierServiceImpl.addCourier(courierInfo);
 			map.put("msg", "1");
 			map.put("info", "sucess");
 		} catch (Exception e) {
 			logger.error(e.getMessage());
 			map.put("msg", "-100");
 			map.put("info", e.getMessage());
 			return map;
 		}
 		return map;
 	 }
 	  
 	 /**
 	 * 快递员信息查询
 	 * @param couId
 	 * @return
 	 */
	 @RequestMapping(value = "/appqueryCourierInfo.do", method = RequestMethod.GET)
 	 public Map<String,Object> queryCourierInfo(@RequestParam("couId")String couId){
 		Map<String, Object> map = new HashMap<String, Object>();
 		try {
 			CourierInfo info = courierServiceImpl.queryCourierInfo(couId);
 			map.put("msg", "1");
 			map.put("info", "sucess");
 			map.put("obj", info);
 		} catch (Exception e) {
 			logger.error(e.getMessage());
 			map.put("msg", "-100");
 			map.put("info", e.getMessage());
 			return map;
 		}
 		return map;
 	 }
    /**
     *  //快递员信息完善
     * @param courierInfo
     * @return
     */
	 @RequestMapping(value = "/appmodifyCourierInfo.do", method = RequestMethod.PUT)
 	 public Map<String,Object> modifyCourierInfo(@RequestBody CourierInfo courierInfo){
 		Map<String, Object> map = new HashMap<String, Object>();
 		try {
 		    courierServiceImpl.modifyCourierInfo(courierInfo);
 			map.put("msg", "1");
 			map.put("info", "sucess");
 		} catch (Exception e) {
 			logger.error(e.getMessage());
 			map.put("msg", "-100");
 			map.put("info", e.getMessage());
 			return map;
 		}
 		return map;
 	 }

	 /**
	  *      //删除\账号冻结(只能通过管理员在页面操作)
	  * @param CouId
	  * @return
	  */
	 @RequestMapping(value = "/delCourierInfos.do", method = RequestMethod.DELETE)
     public Map<String,Object> delCourierInfos(@RequestParam("items")String items){
 		Map<String, Object> map = new HashMap<String, Object>();
 		try {
 		    courierServiceImpl.delCourierInfos(items);
 			map.put("msg", "1");
 			map.put("info", "sucess");
 		} catch (Exception e) {
 			logger.error(e.getMessage());
 			map.put("msg", "-100");
 			map.put("info", e.getMessage());
 			return map;
 		}
 		return map; 
     }
	 
	 /**
	  * 快递员登录
	  * @param couPhone
	  * @param pwd
	  * @return
	  */
	 @RequestMapping(value = "/delcourierLoginIn.do", method = RequestMethod.GET)
	 public Map<String,Object> courierLoginIn(@RequestParam("couPhone")String couPhone,@RequestParam("pwd")String pwd){
		 Map<String, Object> map = new HashMap<String, Object>();
	 		try {
	 		    String couId = courierServiceImpl.courierLoginIn(couPhone,pwd);
	 			map.put("msg", "1");
	 			map.put("info", "sucess");
	 			map.put("str", couId);
	 		} catch (Exception e) {
	 			logger.error(e.getMessage());
	 			map.put("msg", "-100");
	 			map.put("info", e.getMessage());
	 			return map;
	 		}
	 		return map; 
	 }
}
