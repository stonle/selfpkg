/**   
* 
*/
package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.DateEnum;
import com.ls.dao.CourierDao;
import com.ls.domain.CourierInfo;
import com.ls.service.CourierService;
import com.ls.util.GuidCreatorUtil;
import com.ls.util.Page;
import com.ls.util.StringUtil;
import com.ls.util.Utils;

/** 
* @ClassName: CourierServiceImpl 
* @Description: 
* @author huanglei
* @date 2017年9月5日 下午2:51:52 
* @version V1.0    
*/
@Service
public class CourierServiceImpl extends BaseServiceImpl<CourierInfo, String> implements CourierService {
    @Autowired
    private CourierDao courierDaoImpl;

	@Override
	public int getTotalCount(CourierInfo courierInfo) {

		return courierDaoImpl.getTotalCount(courierInfo);
	}
	@Override
	public List<CourierInfo> queryCourierByPage(CourierInfo courierInfo, Page page) {
		return courierDaoImpl.queryCourierByPage(courierInfo, page);
	}
    /**
     * 快递员注册
     */
	@Override
	public void addCourier(CourierInfo courierInfo) {
		if(!StringUtil.isNotEmpty(courierInfo.getPwd()) || !StringUtil.isNotEmpty(courierInfo.getCouPhone())){
			throw new RuntimeException("信息不能不空！");
		}
		//校验手机的唯一性
		if(courierDaoImpl.queryCourierByPhone(courierInfo.getCouPhone()) == null){
			courierInfo.setCouId(new GuidCreatorUtil().toString());
			courierInfo.setStatus(DateEnum.NO_DELETE.getIndex());
			courierInfo.setCreateTime(Utils.getCurrentTime());
			courierDaoImpl.saveObject(courierInfo);
		}else{
			throw new RuntimeException("手机号已存在！");
		}
	}
	@Override
	public CourierInfo queryCourierInfo(String couId) {
		return courierDaoImpl.getObjectById(CourierInfo.class, couId);
	}
	@Override
	public void modifyCourierInfo(CourierInfo courierInfo) {
		//校验手机的唯一性
		if(courierDaoImpl.queryCourierByPhone(courierInfo.getCouPhone()) == null){
	        //根据couId加载数据
			CourierInfo cInfo = courierDaoImpl.getObjectById(CourierInfo.class, courierInfo.getCouId());
			courierInfo.setStatus(DateEnum.NO_DELETE.getIndex());
			courierInfo.setCreateTime(cInfo.getCreateTime());
			courierDaoImpl.megreObject(courierInfo);
		}else{
			throw new RuntimeException("该手机号已被注册！");
		}
	}
	@Override
	public void delCourierInfos(String items) {
		String[] strs = items.split(",");
		for(String str:strs){
			courierDaoImpl.getObjectById(CourierInfo.class, str).setStatus(DateEnum.DELETE.getIndex());
		}
	}
	@Override
	public String courierLoginIn(String couPhone, String pwd) {
        CourierInfo courierInfo = courierDaoImpl.queryCourierByPhone(couPhone);
        if(courierInfo==null){
        	throw new RuntimeException("用户名错误！");
        }else{
        	if(!pwd.equals(courierInfo.getPwd())){
        		throw new RuntimeException("密码错误！");
        	}
        }
		return courierInfo.getCouId();
	}
   
}
