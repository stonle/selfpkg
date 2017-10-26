package com.ls.cenum;

/**
 * 任务是否接受枚举类
 * @author Administrator
 *
 */
public enum TaskRecStateEnum {
	//任务未接收	
	NO_RECEIVED(0),
	//任务已接收
	RECEIVED(1);
	
    private int index;
    
    private TaskRecStateEnum(int index){
    	this.setIndex(index);
    }

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
    
    
	
}
