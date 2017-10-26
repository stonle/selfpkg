/**   
* 
*/
package com.ls.service;

import java.util.List;

import com.ls.domain.TerTaskInfo;
import com.ls.util.Page;

/** 
 * 
* @ClassName: TerTaskService 
* @Description: 
* @author huanglei
* @date 2017年9月20日 下午3:31:21 
* @version V1.0    
*/
public interface TerTaskService extends BaseService<TerTaskInfo,Integer>{
    
	
	/**
	 * //重启终端设置或者重启系统设置
	 * 
	 * @param deviceIdStr  设备编号
	 * @param cmdType   类型 1：重启软件 2：重启系统
	 */
	void saveTerOperations(String deviceNums,int cmdType);
	
	/**
	 * //终端系统升级设置
	 * @param version 软件本本好
	 * @param deviceNums  设备变编号
	 *  type:3：软件升级
	 */
	void saveDevVersions(String version,String deviceNums);
	
	
	/**
	 * 增加配置选项
	 * @param terTaskInfos
	 * type 4:修改配置文件
	 */
	void saveTerConfig(List<TerTaskInfo> terTaskInfos);
	
	
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
