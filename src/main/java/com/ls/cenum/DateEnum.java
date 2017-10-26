package com.ls.cenum;
/**
 * 数据状态
 * @author Administrator
 *
 */
public enum DateEnum {
    DELETE(0),
    NO_DELETE(1);
	private int index;
	private DateEnum(int index){
		this.setIndex(index);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
}
