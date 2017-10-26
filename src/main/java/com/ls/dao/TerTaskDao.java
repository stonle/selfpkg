/**   
* 
*/
package com.ls.dao;

import java.util.List;

import com.ls.domain.TerTaskInfo;
import com.ls.util.Page;

/** 
* @ClassName: TerTaskDao 
* @Description: 
* @author huanglei
* @date 2017年9月20日 下午3:42:03 
* @version V1.0    
*/
public interface TerTaskDao extends BaseDAO<TerTaskInfo, Integer> {
	/**
	 * 根据终端编获取终端操作信息
	 * @param devNum
	 * @return
	 */
	TerTaskInfo getDevVersionByDevNum(String devNum,boolean flag);
	
	TerTaskInfo getDevVersionByDevNum(String devNum);
	
	TerTaskInfo getDevVersionByVersion(String devNum,String version);
	
	
	List<TerTaskInfo> queryTerConfigsByDevNum(String devNum,boolean flag);
	
	
	/**
	 * 根据条件获取总条数
	 * @param terTaskInfo
	 * @return
	 */
	int getTotalCount(TerTaskInfo terTaskInfo);
	
	/**
	 * 分页查询
	 * @param page
	 * @param taskInfo
	 * @return
	 */
	List<TerTaskInfo> listTerTaskByPage(Page page, TerTaskInfo taskInfo);
}
