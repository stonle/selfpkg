package com.ls.dao;

import java.util.List;

import com.ls.domain.Device;
import com.ls.util.Page;

public interface DeviceDao extends BaseDAO<Device, String> {
	// 分页条件查询
	List<Device> listDeviceByPage(Page page, Device device,String couName);

	// 获取分页条件查询记录总数，辅助方法listUserByPage
	int getTotalCount(Device device,String couName);

}
