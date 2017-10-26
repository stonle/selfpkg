/**   
* 
*/
package com.ls.dao;

import java.util.List;

import com.ls.domain.CourierInfo;
import com.ls.util.Page;

/** 
* @ClassName: CourierDao 
* @Description: 
* @author huanglei
* @date 2017年9月5日 下午2:53:19 
* @version V1.0    
*/
public interface CourierDao extends BaseDAO<CourierInfo, String> {
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
	 * 根据手机号查询快递员，判断手机号的唯一性
	 * @param phone
	 * @return
	 */
	CourierInfo  queryCourierByPhone(String phone);
}
