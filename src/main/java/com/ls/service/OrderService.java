/**   
* 
*/
package com.ls.service;

import java.util.List;
import java.util.SortedMap;

import com.ls.domain.BaseAddress;
import com.ls.domain.OrderInfo;
import com.ls.http.AppVersionVo;
import com.ls.util.Page;

/** 
* @ClassName: OrderService 
* @Description: 
* @author huanglei
* @date 2017年9月2日 上午11:11:04 
* @version V1.0    
*/
public interface OrderService extends BaseService<OrderInfo,String> {
	void addOrderInfo(OrderInfo orderInfo);
	
	void deleteOrderInfo(String oderId);
	
	int getTotalCount(String memberId);
	
	List<OrderInfo> queryOrderInfos(String memberId,Page page);
	
	BaseAddress queryBaseAddress();
	
	/**
	 * 根据手机号获取订单信息
	 * @param phone
	 * @return
	 */
	int getTotalCountByPhone(String phone);
	
	/**
	 * app端程序更新信息
	 * @param curVersion
	 * @return
	 */
	AppVersionVo checkProgramVersion(String curVersion);
	
	//订单支付业业务逻辑
	String checkOrderInfoPayMent(SortedMap<Object, Object> packageParams);
	
}
