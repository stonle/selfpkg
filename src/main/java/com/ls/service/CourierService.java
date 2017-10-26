/**   
* 
*/
package com.ls.service;

import java.util.List;

import com.ls.domain.CourierInfo;
import com.ls.util.Page;

/** 
* @ClassName: CourierService 
* @Description: 
* @author huanglei
* @date 2017年9月5日 下午2:51:09 
* @version V1.0    
*/
public interface CourierService extends BaseService<CourierInfo, String> {
     /**
     * 根据条件获取总条数
     * @param courierInfo
     * @return
     */
	int getTotalCount(CourierInfo courierInfo);
	
	
	/**
	 * 分页查询快递员列表
	 * @param courierInfo
	 * @param page
	 * @return
	 */
	List<CourierInfo> queryCourierByPage(CourierInfo courierInfo,Page page);
	
	
	/**
	 * 快递员注册
	 * @param courierInfo
	 */
	void addCourier(CourierInfo courierInfo);
	
	/**
	 * 快递员信息查询
	 * @param couId
	 * @return
	 */
	CourierInfo queryCourierInfo(String couId);
	
	/**
	 * 快递员信息完善
	 * @param courierInfo
	 */
	void modifyCourierInfo(CourierInfo courierInfo);
	
	/**
	 *删除快递原信息
	 * @param items
	 */
	void delCourierInfos(String items);
	
	/**
	 * 登录校验
	 * @param couPhone
	 * @param pwd
	 * @return
	 */
	String courierLoginIn(String couPhone,String pwd);
}
