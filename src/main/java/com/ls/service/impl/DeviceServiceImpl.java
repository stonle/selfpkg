package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.DateEnum;
import com.ls.dao.DeviceDao;
import com.ls.domain.CourierInfo;
import com.ls.domain.Device;
import com.ls.service.DeviceService;
import com.ls.util.Page;
import com.ls.util.Utils;

@Service
public class DeviceServiceImpl extends BaseServiceImpl<Device, String> implements DeviceService{

	@Autowired
	private DeviceDao deviceDaoImpl;
	@Override
	public List<Device> listDeviceByPage(Page page, Device device,String couName) {
		return deviceDaoImpl.listDeviceByPage(page, device,couName);
	}

	@Override
	public int getTotalCount(Device device,String couName) {
		return deviceDaoImpl.getTotalCount(device,couName);
	}

	@Override
	public Device getDeviceByDeviceId(String deviceId) {
		return deviceDaoImpl.getObjectById(Device.class, deviceId);
	}

	@Override
	public Device getDeviceById(Device deviceId) {
		return null;
	}

	@Override
	public void saveDevice(Device device) {
		//设备投放时间设置
		device.setCreateDate(Utils.getCurrentTime());
		device.setStatus(DateEnum.NO_DELETE.getIndex());
		deviceDaoImpl.saveObject(device);
	}

	@Override
	public void updateDevice(Device device) {
	    Device dev = deviceDaoImpl.getObjectById(Device.class, device.getDevNum());
	    dev.setDevName(device.getDevName());
	    dev.setRemark(device.getRemark());
	    dev.setDevAddr(device.getDevAddr());
	    dev.getCompanyInfo().setCopId(device.getCompanyInfo().getCopId());
	    dev.setProvince(device.getProvince());
	    dev.setCity(device.getCity());
	    dev.setDistrict(device.getDistrict());
	}

	@Override
	public void deleteDevice(String items) {
          String[] strs = items.split(",");
          for(String str:strs){
        	  deviceDaoImpl.getObjectById(Device.class, str).setStatus(DateEnum.DELETE.getIndex());
          }
		
	}

	@Override
	public void addDeviceCouier(String devNum, String courierId) {
		CourierInfo courierInfo = new CourierInfo();
		courierInfo.setCouId(courierId);
		deviceDaoImpl.getObjectById(Device.class, devNum).setCourierInfo(courierInfo);
		
	}

	@Override
	public void addCloudUrl(String cloudNum, String deviceNum) {
		deviceDaoImpl.getObjectById(Device.class, deviceNum).setCloudNum(cloudNum);
	}

}
