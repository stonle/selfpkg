/**   
* 
*/
package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.TaskExeEnum;
import com.ls.cenum.TaskRecStateEnum;
import com.ls.cenum.TaskTerOperEnum;
import com.ls.dao.TerTaskDao;
import com.ls.domain.TerTaskInfo;
import com.ls.domain.User;
import com.ls.service.TerTaskService;
import com.ls.util.Page;
import com.ls.util.UserContextUtil;
import com.ls.util.Utils;

/** 
* @ClassName: TerTaskServiceImpl 
* @Description: 
* @author huanglei
* @date 2017年9月20日 下午3:40:16 
* @version V1.0    
*/
@Service
public class TerTaskServiceImpl extends BaseServiceImpl<TerTaskInfo,Integer> implements TerTaskService{
    
	@Autowired
	private TerTaskDao terTaskDaoImpl;
	@Override
	public void saveTerOperations(String deviceNums, int cmdType) {
		User user = UserContextUtil.getUser();
		String devNums[] = deviceNums.split(",");
		for(String str:devNums){
			//先查询是否有操作没有执行  或者执行失败
			TerTaskInfo terTaskInfo = terTaskDaoImpl.getDevVersionByDevNum(str);
			if(terTaskInfo!=null){
				throw new RuntimeException(str+"有终端命令执行中，不能进行该操作！");
			}
			TerTaskInfo terOperation = new TerTaskInfo();
			terOperation.setCreateTime(Utils.getCurrentTime());
			terOperation.setDeviceNum(str);
			terOperation.setCreateUser(user.getUserName());
			terOperation.setStatus(TaskExeEnum.NO_EXECUTED.getIndex());
			terOperation.setOperType(cmdType);
			terOperation.setIsSend(TaskRecStateEnum.NO_RECEIVED.getIndex());
			terTaskDaoImpl.saveObject(terOperation);
		}
		 
	}

	@Override
	public void saveDevVersions(String version, String deviceNums) {
		 User user = UserContextUtil.getUser();
			String devNums[] = deviceNums.split(",");
			for(String str:devNums){
				TerTaskInfo terTaskInfo = terTaskDaoImpl.getDevVersionByDevNum(str);
				if(terTaskInfo != null){
					throw new RuntimeException(str+"有终端命令执行中，不能进行该操作！");
				}
				TerTaskInfo terTask= new TerTaskInfo();
				terTask.setCreateTime(Utils.getCurrentTime());
				terTask.setCreateUser(user.getUserName());
				terTask.setDeviceNum(str);
				terTask.setbVersion(version);
				//操作命令类型
				terTask.setOperType(TaskTerOperEnum.SOF_UOPGRADE.getIndex());
				terTask.setStatus(TaskExeEnum.NO_EXECUTED.getIndex());
				terTask.setIsSend(TaskRecStateEnum.NO_RECEIVED.getIndex());
				terTaskDaoImpl.saveObject(terTask);
			}
		
	}
  
	@Override
	public void saveTerConfig(List<TerTaskInfo> terTaskInfos) {
		User user = UserContextUtil.getUser();
       for(TerTaskInfo ter:terTaskInfos){
    	   ter.setOperType(TaskTerOperEnum.CONIFG_MODIFY.getIndex());
    	   ter.setCreateUser(user.getLoginName());
    	   ter.setCreateTime(Utils.getCurrentTime());
    	   ter.setIsSend(TaskRecStateEnum.NO_RECEIVED.getIndex());
    	   ter.setStatus(TaskExeEnum.NO_EXECUTED.getIndex());
    	   terTaskDaoImpl.saveObject(ter);
       }
		
	}
	@Override
	public int getTotalCount(TerTaskInfo terTaskInfo) {
		return terTaskDaoImpl.getTotalCount(terTaskInfo);
	}

	@Override
	public List<TerTaskInfo> listTerTaskByPage(Page page, TerTaskInfo terTaskInfo) {
		return terTaskDaoImpl.listTerTaskByPage(page, terTaskInfo);
	}
}
