package com.ls.cenum;

/**
 * 终端任务执行结果枚举类
 * @author Administrator
 *
 */
public enum TaskResultEnum {
	SUCCESS(1),
	FAIL(0);
	private int index;
	private TaskResultEnum(int index){
		this.setIndex(index);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
