package com.ls.cenum;
/**
 * 任务执行类
 * @author Administrator
 *
 */
public enum TaskExeEnum {
    EXECUTED(1),
    NO_EXECUTED(0);
	private int index;
	private TaskExeEnum(int index){
		this.setIndex(index);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}
