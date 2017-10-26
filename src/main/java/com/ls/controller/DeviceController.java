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

import com.ls.domain.Device;
import com.ls.domain.TerTaskInfo;
import com.ls.domain.User;
import com.ls.service.DeviceService;
import com.ls.service.TerTaskService;
import com.ls.util.Page;
import com.ls.util.PageUtil;
import com.ls.util.UserContextUtil;

@RestController
@RequestMapping("pages")
public class DeviceController {
	private static Logger logger = LogManager.getLogger(DeviceController.class.getName());
	
	@Autowired
	private DeviceService deviceServiceImpl;
    
    @Autowired
    private TerTaskService terTaskServiceImpl;
	/**
	 * 分页获取终端设备
	 * @param device
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/queryDeviceByPage.do", method = RequestMethod.GET)
	public Map<String, Object> queryObjectByPage(Device device,Page page,String couName){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Device> list = new ArrayList<Device>();
		try{
			page = PageUtil.createPage(page.getEveryPage(),deviceServiceImpl.getTotalCount(device,couName),page.getCurrentPage());
			list = deviceServiceImpl.listDeviceByPage(page, device,couName);
			map.put("totalCount",page.getTotalCount());
			map.put("currentPage",page.getCurrentPage());
			map.put("totalPage",page.getTotalPage());
			map.put("list", list);
			map.put("msg","1");
		}catch(Exception e){
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}
  
	/**
	 * 删除对象信息，支持批量删除
	 * @param String idStr
	 * @return json:[{'msg':1,-1,-100}]
	 * */
	@RequestMapping(value = "/delDevice.do", method = RequestMethod.DELETE)
	public Map<String, Object> delObject(@RequestParam("items")String items){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(items == null || items.equals("")){
				map.put("msg","-1");
				map.put("info","无有效参数");
				return map;
			}
			deviceServiceImpl.deleteDevice(items);
			map.put("msg","1");
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
			return map;
		}
		return map;
	}
	
	/**
	 * 添加终端设备
	 * @param User Obj
	 * @return json:[{'msg':1,-1,-100}]
	 * */
	@RequestMapping(value = "/addDevice.do", method = RequestMethod.POST)
	public Map<String, Object> addObject(Device device){
		Map<String, Object> map = new HashMap<String, Object>();
		User user = UserContextUtil.getUser();
		try{
			if(device == null){
				map.put("msg","-1");
				map.put("info","未检测到需要保存的对象");
				return map;
			}
			device.setUser(user);
			deviceServiceImpl.saveDevice(device);
			map.put("msg","1");
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
			return map;
		}
		return map;
	}
	
	/**
	 * @param String userId
	 * @return json:[{data},{'msg':1,-1,-100}]
	 * */
	@RequestMapping(value = "/getDeviceById.do", method = RequestMethod.GET)
	public Map<String, Object> queryDeviceById(@RequestParam("devId")String deviceID){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(deviceID.equals("")){//参数控制
			map.put("msg","-1");
			map.put("info","无有效参数");
			return map;
		}
		try{
			Device device = deviceServiceImpl.getDeviceByDeviceId(deviceID);
			if(device == null){//对象不存在
				map.put("msg","-2");
				map.put("info","对象不存在");
				return map;
			}
			map.put("list", device);
			map.put("msg","1");
		}catch(Exception e){
			map.put("msg", "-100");
			map.put("info",e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}
	
	/**
	 * 保存修改的终端设备信息
	 * @param Device Obj
	 * @return json:[{'msg':1,-1,-100}]
	 * */
	@RequestMapping(value = "/editeDevice.do", method = RequestMethod.PUT)
	public Map<String, Object> editeObject(Device device){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(device == null){
				map.put("msg","-1");
				map.put("info","未检测到需要保存的对象");
				return map;
			}
			deviceServiceImpl.updateDevice(device);
			map.put("msg","1");
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
			return map;
		}
		return map;
	}
	@RequestMapping(value = "/addDeviceCouier.do", method = RequestMethod.PUT)
	public Map<String,Object> addDeviceCouier(@RequestParam("devNum")String devNum,@RequestParam("courierId")String courierId){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			deviceServiceImpl.addDeviceCouier(devNum,courierId);
			map.put("msg","1");
			
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
			return map;
		}
		return map;
	}
	
	/**
	 * 重启终端设置
	 *      "deviceIdStr":deviceIdStr,
			"cmdType":cmdType
	 * @return
	 */
	@RequestMapping(value = "/restart.do", method = RequestMethod.POST)
	public Map<String,Object> restart(@RequestParam("deviceIdStr")String deviceIdStr,@RequestParam("cmdType")Integer cmdType){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			terTaskServiceImpl.saveTerOperations(deviceIdStr,cmdType);
			map.put("msg","1");
			map.put("info", "sucess");
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
			return map;
		}
		return map;
	}
	/**
	 * 终端系统升级设置
	 *"version":selfpkg,
					"deviceNums":deviceIdStr
	 * @return
	 */
	@RequestMapping(value = "/upTerGrade.do", method = RequestMethod.POST)
	public Map<String,Object> upTerGrade(@RequestParam("version")String version,@RequestParam("deviceNums")String deviceNums){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			terTaskServiceImpl.saveDevVersions(version,deviceNums);
			map.put("msg","1");
			map.put("info", "sucess");
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
			return map;
		}
		return map;
	}
	@RequestMapping(value = "/addDeviceConfig.do", method = RequestMethod.POST)
	public Map<String,Object> addDeviceConfig(@RequestBody List<TerTaskInfo> terTaskInfos){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			
			terTaskServiceImpl.saveTerConfig(terTaskInfos);
			map.put("msg","1");
			map.put("info", "sucess");
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
			return map;
		}
		return map;
	}
	@RequestMapping(value = "/addCloudUrl.do", method = RequestMethod.POST)
	public Map<String,Object> addCloudUrl(@RequestParam("cloudUrl")String cloudUrl,@RequestParam("deviceNum")String deviceNum){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			deviceServiceImpl.addCloudUrl(cloudUrl,deviceNum);
			map.put("msg","1");
			map.put("info", "sucess");
		}catch(Exception e){
			logger.error(e.getMessage());
			map.put("msg","-100");
			map.put("info",e.getMessage());
			return map;
		}
		return map;
	}
}
