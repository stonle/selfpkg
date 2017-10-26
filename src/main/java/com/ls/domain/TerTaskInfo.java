/**   
* 
*/
package com.ls.domain;

import com.ls.util.DateTimeUtil;
import com.ls.util.StringUtil;
import com.ls.util.Utils;

public class TerTaskInfo {
	private Integer id;
	//终端编号
	private String deviceNum;
	private String bVersion;
	//操作类型
	private Integer operType;
	
	//终端操作名称
	@SuppressWarnings("unused")
	private String commandName;
	
	//配置文件key
	private String configKey;
	//配置文件value
    private String configValue;
    //配置文件类型
    private Integer configType;
    //执行状态
    private Integer status;
    //接收状态
    private Integer isSend;
    //执行结果
    private Integer isResult;
    private String createUser;
    private String createTime;
    //执行时间
    private String executeTime;
    private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDeviceNum() {
		return deviceNum;
	}
	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	public String getbVersion() {
		return bVersion;
	}
	public void setbVersion(String bVersion) {
		this.bVersion = bVersion;
	}
	public Integer getOperType() {
		return operType;
	}
	public void setOperType(Integer operType) {
		this.operType = operType;
	}
	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public Integer getConfigType() {
		return configType;
	}
	public void setConfigType(Integer configType) {
		this.configType = configType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return DateTimeUtil.convertFormatDate(createTime);
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsSend() {
		return isSend;
	}
	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}
	public String getExecuteTime() {
		return DateTimeUtil.convertFormatDate(executeTime);
	}
	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}
	public Integer getIsResult() {
		return isResult;
	}
	public void setIsResult(Integer isResult) {
		this.isResult = isResult;
	}
	public String getCommandName() {
		switch (operType) {
		case 1:
			return "软件重启";
		case 2:
			return "系统重启";
		case 3:
			return "软件升级";
		case 4:
			return "配置文件修改";

		default:
			return "没有终端操作";
		}
	
	}
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
    
}
