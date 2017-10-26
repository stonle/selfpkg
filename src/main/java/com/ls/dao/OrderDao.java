/**   
* 
*/
package com.ls.dao;

import java.util.List;

import com.ls.domain.OrderInfo;
import com.ls.util.Page;

/** 
* @ClassName: OrderDao 
* @Description: 
* @author huanglei
* @date 2017年9月2日 上午11:16:23 
* @version V1.0    
*/
public interface OrderDao extends BaseDAO<OrderInfo, String>{
	
	int getTotalCount(String memberId);
	
	List<OrderInfo> queryOrderInfos(String memberId,Page page);
	
	
	int getTotalCount(OrderInfo packageVo,String phone);
	
	
	List<OrderInfo>  listPackageByPage(Page page,OrderInfo packageVo, String phone);
	
	/**
	 * 
	 * @return
	 */
	OrderInfo ckeckOrder(String phone,String pickCode);
	
	//终端接口
	int getTotalCountByPhone(String phone);
	
	//终端接口
	List<OrderInfo> listPackageByPhone(String phone,Page page);
}
