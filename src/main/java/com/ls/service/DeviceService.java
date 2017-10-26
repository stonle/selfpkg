package com.ls.service;

import java.util.List;

import com.ls.domain.Device;
import com.ls.util.Page;

public interface DeviceService extends BaseService<Device, String>{
	    // 分页条件查询
		List<Device> listDeviceByPage(Page page, Device device,String couName);

		// 获取分页条件查询记录总数
		int getTotalCount(Device device,String couName);

		// 查询设备信息根据设备Id
		Device getDeviceByDeviceId(String deviceId);

		// 根据对象ID属性获取对象
		Device getDeviceById(Device deviceId);

		// 添加对象
		void saveDevice(Device device);

		// 更新对象
		void updateDevice(Device device);
		
		// 更新对象
		//void updateUserLoginInfo(User user);

		// 删除对象
		void deleteDevice(String item);
		
		/**
		 * 为终端分配快递员
		 */
		void addDeviceCouier(String devNum,String courierId);
		
		/**
		 * 添加视频云存储
		 */
		void addCloudUrl(String cloudUrl,String deviceNum);
}
