package com.ls.cenum;

/**
 * 终端操作枚举类
 * @author Administrator
 *
 */
public enum TaskTerOperEnum {
   //0:没有终端操作；1；软件重启；2：重启系统；3：软件升级；4：配置文件修改
	NO_OPERATER(0),
	SOF_RESTART(1),
	SYS_RESTART(2),
	SOF_UOPGRADE(3),
	CONIFG_MODIFY(4);
	private int index;
	private TaskTerOperEnum(int index){
		this.setIndex(index);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
}
