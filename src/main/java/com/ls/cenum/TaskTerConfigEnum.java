package com.ls.cenum;

/**
 * 终端配置文件操作枚举类
 * @author Administrator
 *
 */
public enum TaskTerConfigEnum {
	//0：增加，1：删除，2：修改
	ADDFILED(0),
	DELETEFIELD(1),
	MODIFYFIELD(2);
	private int index;
	private  TaskTerConfigEnum(int index){
		this.setIndex(index);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
